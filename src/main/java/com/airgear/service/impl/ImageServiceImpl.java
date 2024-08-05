package com.airgear.service.impl;

import com.airgear.dto.ImagesSaveResponse;
import com.airgear.exception.GoodsExceptions;
import com.airgear.exception.ImageExceptions;
import com.airgear.exception.UserExceptions;
import com.airgear.model.Goods;
import com.airgear.model.GoodsImages;
import com.airgear.repository.GoodsRepository;
import com.airgear.repository.UserRepository;
import com.airgear.service.ImageService;
import com.airgear.utils.DirectoryPathUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private static final String IMAGE_EXTENSIONS_PNG = "image/png";
    private static final String IMAGE_EXTENSIONS_JPEG = "image/jpeg";
    private static final Path BASE_DIR = DirectoryPathUtil.getBasePath();

    @Value("${images.file.size}")
    private long maxFileSizeBytes;

    @Value("${images.max.number}")
    private int maxNumberImages;

    private final UserRepository userRepository;
    private final GoodsRepository goodsRepository;

    @Override
    @Transactional
    public ImagesSaveResponse uploadImages(String email, MultipartFile[] images, Long goodsId) {
        Goods goods = getGoods(goodsId);

        List<GoodsImages> currentImages = goods.getImages();
        List<GoodsImages> imagesList = new ArrayList<>();

        if (currentImages.size() + images.length > maxNumberImages)
            throw ImageExceptions.tooManyImages(maxNumberImages);

        fillImagesList(images, imagesList);
        currentImages.addAll(imagesList);
        goods.setImages(currentImages);
        goodsRepository.save(goods);

        List<String> imageUrls = imagesList.stream()
                .map(GoodsImages::getImageUrl)
                .collect(Collectors.toList());

        return new ImagesSaveResponse(imageUrls);
    }

    @Override
    public FileSystemResource downloadImage(Long userId, Long goodsId, String imageUrl) {
        if (!userRepository.existsById(userId)) UserExceptions.userNotFound(userId);
        if (!goodsRepository.existsById(goodsId)) GoodsExceptions.goodsNotFound(goodsId);

        Goods goods = getGoods(goodsId);
        List<String> urls = goods.getImages().stream()
                .map(GoodsImages::getImageUrl)
                .toList();
        if (!urls.contains(imageUrl)) ImageExceptions.urlNotExistInGoods(goodsId, imageUrl);

        Path imagePath = Paths.get(BASE_DIR.toString(), imageUrl);
        log.info("image path : {}", imagePath);
        File file = imagePath.toFile();
        if (!file.exists()) ImageExceptions.imageNotFound(userId, goodsId, imageUrl);

        return new FileSystemResource(file);
    }

    @Override
    public byte[] getImageBytesById(String imageId) throws IOException {
        String imagePath = BASE_DIR.resolve(imageId).toString();
        return Files.readAllBytes(Path.of(imagePath));
    }

    @Override
    public MediaType getImageMediaType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        return switch (extension) {
            case "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoodsImages> getImagesByGoodsId(Long goodsId) {
        Goods goods = getGoods(goodsId);
        return goods.getImages();
    }

    private Goods getGoods(Long goodsId) {
        return goodsRepository.findById(goodsId)
                .orElseThrow(() -> GoodsExceptions.goodsNotFound(goodsId));
    }

    private void fillImagesList(MultipartFile[] images, List<GoodsImages> imagesList) {
        for (MultipartFile image : images) {
            try {
                log.info("Uploading: Name: {}, Type: {}, Size: {}", image.getOriginalFilename(), image.getContentType(), image.getSize());
                String filePath = uploadImage(image);
                GoodsImages goodsImage = new GoodsImages();
                goodsImage.setImageUrl(filePath);
                imagesList.add(goodsImage);
            } catch (IOException e) {
                log.error("Failed to upload photo", e);
                ImageExceptions.uploadImageProblem(image.getOriginalFilename());
            }
        }
    }

    private String uploadImage(MultipartFile file) throws IOException {
        validateFile(file);
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
        Path filePath = BASE_DIR.resolve(uniqueFileName);
        file.transferTo(filePath.toFile());
        return uniqueFileName;
    }

    private void validateFile(MultipartFile image) {
        if (image.isEmpty() || image.getSize() > maxFileSizeBytes) {
            throw ImageExceptions.invalidImageSize(image.getOriginalFilename());
        }

        String contentType = image.getContentType();
        if (contentType == null ||
                !(contentType.equals(IMAGE_EXTENSIONS_PNG) || contentType.equals(IMAGE_EXTENSIONS_JPEG)))
            throw ImageExceptions.invalidImageExtension(image.getOriginalFilename());
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = getExtension(originalFilename);
        return UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);
    }

    private String getExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
}

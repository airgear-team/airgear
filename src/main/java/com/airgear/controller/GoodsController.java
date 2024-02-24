package com.airgear.controller;

import com.airgear.dto.GoodsDto;
import com.airgear.exception.ForbiddenException;
import com.airgear.model.Goods;
import com.airgear.model.RentalAgreement;
import com.airgear.model.User;
import com.airgear.service.GoodsService;
import com.airgear.service.UserService;
import com.airgear.service.Utils;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/featured")
    public String user() {
        //TODO add return some random goods for home page
        return "Goods for home page";
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public Goods createGoods(Authentication auth, @RequestBody GoodsDto goods) {
        User user = userService.findByUsername(auth.getName());
        goods.setUser(user);
        Goods savedGoods = goodsService.saveGoods(goods);
        return savedGoods;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PutMapping("/{goodsId}")
    public ResponseEntity<Goods> updateGoods(
            Authentication auth,
            @PathVariable Long goodsId,
            @Valid @RequestBody Goods updatedGoods) {
        User user = userService.findByUsername(auth.getName());
        Goods existingGoods = goodsService.getGoodsById(goodsId);
        if (user.getId() != existingGoods.getUser().getId() && !user.getRoles().contains("ADMIN")) {
            throw new ForbiddenException("It is not your goods");
        }
        if (updatedGoods.getName() != null) {
            existingGoods.setName(updatedGoods.getName());
        }
        if (updatedGoods.getDescription() != null) {
            existingGoods.setDescription(updatedGoods.getDescription());
        }
        if (updatedGoods.getPrice() != null) {
            existingGoods.setPrice(updatedGoods.getPrice());
        }
        if (updatedGoods.getLocation() != null) {
            existingGoods.setLocation(updatedGoods.getLocation());
        }
        Goods updatedGoodsEntity = goodsService.updateGoods(existingGoods);
        return ResponseEntity.ok(updatedGoodsEntity);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> deleteGoods(Authentication auth, @PathVariable Long goodsId){
        User user = userService.findByUsername(auth.getName());
        Goods goods = goodsService.getGoodsById(goodsId);
        if(user.getId()!=goods.getUser().getId() && !user.getRoles().contains("ADMIN")){
            throw new ForbiddenException("It is not your goods");
        }
        goodsService.deleteGoodsById(goodsId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/download/rental/{goodsId}")
    @ResponseBody
    public FileSystemResource download(Authentication auth, @PathVariable Long goodsId, @Valid @RequestBody
            RentalAgreement rental, HttpServletResponse resp) {
        Goods goods = goodsService.getGoodsById(goodsId);
        rental.setGoods(goods);
        try {
            File fileTemplate = Utils.getAgreement(rental);
            File pdfDest = new File("output.pdf");
            ConverterProperties converterProperties = new ConverterProperties();
            HtmlConverter.convertToPdf(new FileInputStream(fileTemplate),
                    new FileOutputStream(pdfDest), converterProperties);
            resp.setContentType("application/pdf");
            resp.setHeader("Content-disposition", "attachment; filename=output.pdf");
            return new FileSystemResource(pdfDest);
        } catch (IOException e) {
            throw new RuntimeException("Проблема з загрузкою договора оренди!");
        }
    }
}
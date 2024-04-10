package com.airgear.service.impl;

import com.airgear.dto.RentalAgreementDto;
import com.airgear.exception.RentalExceptions;
import com.airgear.service.GoodsService;
import com.airgear.service.RentalAgreementService;
import com.airgear.utils.Utils;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
public class RentalAgreementServiceImpl implements RentalAgreementService {

    private final GoodsService goodsService;

    @Override
    public ResponseEntity<FileSystemResource> generateRentalAgreementResponse(RentalAgreementDto rental, Long goodsId){
        rental.setGoods(goodsService.getGoodsById(goodsId));
        try {
            File pdfFile = generateRentalAgreementPdf(rental);
            FileSystemResource resource = new FileSystemResource(pdfFile);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .header("Content-disposition", "attachment; filename=" + pdfFile.getName())
                    .body(resource);
        } catch (IOException e) {
            throw RentalExceptions.badFile();
        }

    }

    private File generateRentalAgreementPdf(RentalAgreementDto rental) throws IOException {
        File fileTemplate = Utils.getAgreement(rental);
        File pdfDest = new File("output.pdf");

        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(fileTemplate),
                new FileOutputStream(pdfDest), converterProperties);

        return pdfDest;
    }
}
package com.airgear.service.impl;

import com.airgear.exception.GenerateRentalAgreementException;
import com.airgear.model.RentalAgreement;
import com.airgear.model.goods.Goods;
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
    public ResponseEntity<FileSystemResource> generateRentalAgreementResponse(RentalAgreement rental, Long goodsId){
        Goods goods = goodsService.getGoodsById(goodsId);
        rental.setGoods(goods);
        try {
            File pdfFile = this.generateRentalAgreementPdf(rental);
            FileSystemResource resource = new FileSystemResource(pdfFile);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                    .header("Content-disposition", "attachment; filename=" + pdfFile.getName())
                    .body(resource);
        } catch (IOException e) {
            throw new GenerateRentalAgreementException("The problem with loading the lease agreement.");
        }
    }

    private File generateRentalAgreementPdf(RentalAgreement rental) throws IOException {
        File fileTemplate = Utils.getAgreement(rental);
        File pdfDest = new File("output.pdf");

        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(fileTemplate),
                new FileOutputStream(pdfDest), converterProperties);

        return pdfDest;
    }
}
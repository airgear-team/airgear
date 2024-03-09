package com.airgear.service.impl;

import com.airgear.model.RentalAgreement;
import com.airgear.model.goods.Goods;
import com.airgear.service.GoodsService;
import com.airgear.service.RentalAgreementService;
import com.airgear.utils.Utils;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class RentalAgreementServiceImpl implements RentalAgreementService {

    @Autowired
    private GoodsService goodsService;

    @Override
    public ResponseEntity<FileSystemResource> generateRentalAgreementResponse(RentalAgreement rental, Long goodsId) throws IOException {
        Goods goods = goodsService.getGoodsById(goodsId);
        rental.setGoods(goods);
        File pdfFile = this.generateRentalAgreementPdf(rental);

        FileSystemResource resource = new FileSystemResource(pdfFile);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .header("Content-disposition", "attachment; filename=" + pdfFile.getName())
                .body(resource);
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
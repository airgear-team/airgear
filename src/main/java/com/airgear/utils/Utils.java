package com.airgear.utils;

import com.airgear.controller.GoodsController;
import com.airgear.dto.RentalAgreementRequest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static File getAgreement(RentalAgreementRequest agreement) throws IOException {
        ClassLoader classLoader = GoodsController.class.getClassLoader();
        File fileTemplate = new File(classLoader.getResource("forms/rentalAgreementTemplate.html").getFile());
        Document doc = Jsoup.parse(fileTemplate,null);
        doc.getElementsByClass("lessor").forEach(x->x.text(agreement.getLessor()));
        doc.getElementsByClass("renter").forEach(x->x.text(agreement.getRenter()));
        doc.getElementById("date").text(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if(agreement.getGoal()!=null) {
            doc.getElementById("name").text(agreement.getGoods().getName());
            doc.getElementById("price").text(String.format("%.02f", agreement.getGoods().getPrice()));
            doc.getElementById("goal").text(agreement.getGoal().isEmpty() ? "використання за призначенням" : agreement.getGoal());
        }
        doc.getElementById("time").text(" з "+agreement.getFirstDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+
               " по "+agreement.getLastDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        doc.getElementById("rental_price").text(String.format("%.02f", agreement.getRentalPrice()));
        doc.getElementById("tax").text(String.format("%.02f", Math.round(100*agreement.getRentalPrice()/6)/100.00));
        doc.getElementById("lessor_doc").text(agreement.getLessorDocument());
        doc.getElementById("renter_doc").text(agreement.getRenterDocument());
        doc.getElementById("lessor_phone").text(agreement.getLessorPhone());
        doc.getElementById("renter_phone").text(agreement.getRenterPhone());

        FileWriter writer=new FileWriter(fileTemplate);
        writer.write(doc.toString());
        writer.flush();
        writer.close();

        return fileTemplate;
    }
}

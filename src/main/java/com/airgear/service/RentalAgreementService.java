package com.airgear.service;

import com.airgear.model.RentalAgreement;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

public interface RentalAgreementService {
    ResponseEntity<FileSystemResource> generateRentalAgreementResponse(RentalAgreement rental, Long goodsId);
}

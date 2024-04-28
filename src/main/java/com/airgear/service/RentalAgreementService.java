package com.airgear.service;

import com.airgear.dto.RentalAgreementRequest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

public interface RentalAgreementService {
    ResponseEntity<FileSystemResource> generateRentalAgreementResponse(RentalAgreementRequest rental, Long goodsId);
}

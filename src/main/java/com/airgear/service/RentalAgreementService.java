package com.airgear.service;

import com.airgear.dto.RentalAgreementDto;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;

public interface RentalAgreementService {
    ResponseEntity<FileSystemResource> generateRentalAgreementResponse(RentalAgreementDto rental, Long goodsId);
}

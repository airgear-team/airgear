package com.airgear.controller;

import com.airgear.dto.*;
import com.airgear.model.goods.Goods;
import com.airgear.model.RentalAgreement;
import com.airgear.service.*;
import com.airgear.service.ComplaintService;
import com.airgear.service.GoodsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.math.BigDecimal;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/goods")
@AllArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final ComplaintService complaintService;
    private final RentalAgreementService rentalAgreementService;

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping
    public ResponseEntity<GoodsDto> createGoods(Authentication auth, @RequestBody GoodsDto goods) {
        return ResponseEntity.ok(goodsService.createGoods(auth.getName(), goods));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/{goodsId}")
    public ResponseEntity<GoodsDto> getGoodsById(HttpServletRequest request, Authentication auth,
                                                 @PathVariable Long goodsId) {
        return ResponseEntity.ok(goodsService.getGoodsById(request.getRemoteAddr(), auth.getName(), goodsId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PutMapping("/{goodsId}")
    public ResponseEntity<GoodsDto> updateGoods(
                                                Authentication auth,
                                                @PathVariable Long goodsId,
                                                @Valid @RequestBody GoodsDto updatedGoods) {
        return ResponseEntity.ok(goodsService.updateGoods(auth.getName(), goodsId, updatedGoods));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> deleteGoods(Authentication auth, @PathVariable Long goodsId) {
        goodsService.deleteGoods(auth.getName(), goodsId);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("addToFavorites/{goodsId}")
    public ResponseEntity<GoodsDto> addToFavorites(Authentication auth, @PathVariable Long goodsId) {
        return ResponseEntity.ok(goodsService.addToFavorites(auth.getName(), goodsId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/download/rental/{goodsId}")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long goodsId,
                                                       @Valid @RequestBody RentalAgreement rental) {
         return rentalAgreementService.generateRentalAgreementResponse(rental, goodsId);

    }

    @GetMapping("/random-goods")
    public List<GoodsDto> getRandomGoods(
            @RequestParam(required = false, name = "category") String categoryName,
            @RequestParam(required = false, name = "quantity", defaultValue = "12") int quantity) {
        return goodsService.getRandomGoods(categoryName, quantity);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/similar-goods")
    public Page<GoodsDto> getSimilarGoods(
            @RequestParam(required = false, name = "category") String categoryName,
            @RequestParam(name = "price") BigDecimal price) {
        return goodsService.getSimilarGoods(categoryName, price);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @GetMapping("/filter")
    public Page<Goods> filterGoods(
            @RequestParam(name = "category", required = false) String categoryName,
            @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
            @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
            Pageable pageable) {
        return goodsService.filterGoods(categoryName, minPrice, maxPrice, pageable);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/{goodsId}/complaint")
    public ResponseEntity<ComplaintDto> addComplaint (Authentication auth,
                                                      @PathVariable Long goodsId,
                                                      @Valid @RequestBody ComplaintDto complaint) {
        return ResponseEntity.ok(complaintService.save(auth.getName(), goodsId, complaint));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PostMapping("/addTopPlacements")
    public ResponseEntity<TopGoodsPlacementDto> addTopGoodsPlacements(@Valid @RequestBody TopGoodsPlacementDto topGoodsPlacementDto) {
        return ResponseEntity.ok(goodsService.addTopGoodsPlacements(topGoodsPlacementDto));
    }
}
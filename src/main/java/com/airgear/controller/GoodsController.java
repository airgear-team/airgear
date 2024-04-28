package com.airgear.controller;

import com.airgear.dto.ComplaintDto;
import com.airgear.dto.GoodsDto;
import com.airgear.dto.RentalAgreementDto;
import com.airgear.dto.TopGoodsPlacementDto;
import com.airgear.service.ComplaintService;
import com.airgear.service.GoodsService;
import com.airgear.service.RentalAgreementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/goods")
@AllArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;
    private final ComplaintService complaintService;
    private final RentalAgreementService rentalAgreementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GoodsDto> createGoods(@AuthenticationPrincipal String email,
                                                @RequestBody @Valid GoodsDto goods,
                                                UriComponentsBuilder ucb) {
        GoodsDto response = goodsService.createGoods(email, goods);
        return ResponseEntity
                .created(ucb.path("/goods/{id}").build(response.getId()))
                .body(response);
    }

    @GetMapping("/{goodsId}")
    public GoodsDto getGoodsById(HttpServletRequest request,
                                 @AuthenticationPrincipal String email,
                                 @PathVariable Long goodsId) {
        return goodsService.getGoodsById(request.getRemoteAddr(), email, goodsId);
    }

    @PutMapping("/{goodsId}")
    public GoodsDto updateGoods(@AuthenticationPrincipal String email,
                                @PathVariable Long goodsId,
                                @Valid @RequestBody GoodsDto updatedGoods) {
        return goodsService.updateGoods(email, goodsId, updatedGoods);
    }

    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> deleteGoods(@AuthenticationPrincipal String email, @PathVariable Long goodsId) {
        goodsService.deleteGoods(email, goodsId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{goodsId}/favorite")
    public GoodsDto addToFavorites(@AuthenticationPrincipal String email, @PathVariable Long goodsId) {
        return goodsService.addToFavorites(email, goodsId);
    }

    @PostMapping("/{goodsId}/agreement")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long goodsId,
                                                       @Valid @RequestBody RentalAgreementDto rental) {
        return rentalAgreementService.generateRentalAgreementResponse(rental, goodsId);

    }

    @PostMapping("/{goodsId}/complaint")
    public ResponseEntity<ComplaintDto> addComplaint(@AuthenticationPrincipal String email,
                                                     @PathVariable Long goodsId,
                                                     @RequestBody @Valid ComplaintDto complaint) {
        return ResponseEntity.ok(complaintService.save(email, goodsId, complaint));
    }

    @PostMapping("/top")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TopGoodsPlacementDto> addTopGoodsPlacements(@RequestBody @Valid TopGoodsPlacementDto topGoodsPlacementDto) {
        return ResponseEntity.ok(goodsService.addTopGoodsPlacements(topGoodsPlacementDto));
    }

    @GetMapping("/random")
    public List<GoodsDto> getRandomGoods(
            @RequestParam(required = false, name = "category") String categoryName,
            @RequestParam(required = false, name = "quantity", defaultValue = "12") int quantity) {
        return goodsService.getRandomGoods(categoryName, quantity);
    }

    @GetMapping("/similar")
    public Page<GoodsDto> getSimilarGoods(@RequestParam(required = false, name = "category") String categoryName,
                                          @RequestParam(name = "price") BigDecimal price) {
        return goodsService.getSimilarGoods(categoryName, price);
    }

    @GetMapping("/filter")
    public Page<GoodsDto> filterGoods(@RequestParam(name = "category", required = false) String categoryName,
                                      @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
                                      @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
                                      Pageable pageable) {
        return goodsService.filterGoods(categoryName, minPrice, maxPrice, pageable);
    }
}

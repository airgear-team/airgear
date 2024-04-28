package com.airgear.controller;

import com.airgear.dto.*;
import com.airgear.service.ComplaintService;
import com.airgear.service.GoodsService;
import com.airgear.service.RentalAgreementService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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

    @GetMapping
    @PageableAsQueryParam
    public Page<GoodsGetResponse> listGoods(@Parameter(hidden = true) Pageable pageable) {
        return goodsService.getAllGoods(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GoodsCreateResponse> createGoods(@AuthenticationPrincipal String email,
                                                           @RequestBody @Valid GoodsCreateRequest request,
                                                           UriComponentsBuilder ucb) {
        GoodsCreateResponse response = goodsService.createGoods(email, request);
        return ResponseEntity
                .created(ucb.path("/goods/{id}").build(response.getId()))
                .body(response);
    }

    @GetMapping("/{goodsId}")
    public GoodsGetResponse getGoodsById(HttpServletRequest request,
                                         @AuthenticationPrincipal String email,
                                         @PathVariable Long goodsId) {
        return goodsService.getGoodsById(request.getRemoteAddr(), email, goodsId);
    }

    @PutMapping("/{goodsId}")
    public GoodsUpdateResponse updateGoods(@AuthenticationPrincipal String email,
                                           @PathVariable Long goodsId,
                                           @RequestBody @Valid GoodsUpdateRequest request) {
        return goodsService.updateGoods(email, goodsId, request);
    }

    @DeleteMapping("/{goodsId}")
    public ResponseEntity<String> deleteGoods(@AuthenticationPrincipal String email,
                                              @PathVariable Long goodsId) {
        goodsService.deleteGoods(email, goodsId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{goodsId}/favorite")
    public GoodsGetResponse addToFavorites(@AuthenticationPrincipal String email,
                                           @PathVariable Long goodsId) {
        return goodsService.addToFavorites(email, goodsId);
    }

    @PostMapping("/{goodsId}/agreement")
    public ResponseEntity<FileSystemResource> download(@PathVariable Long goodsId,
                                                       @RequestBody @Valid RentalAgreementRequest request) {
        return rentalAgreementService.generateRentalAgreementResponse(request, goodsId);

    }

    @PostMapping("/{goodsId}/complaint")
    public ResponseEntity<ComplaintCreateResponse> addComplaint(@AuthenticationPrincipal String email,
                                                                @PathVariable Long goodsId,
                                                                @RequestBody @Valid ComplaintCreateRequest request) {
        return ResponseEntity.ok(complaintService.save(email, goodsId, request));
    }

    @PostMapping("/top")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TopGoodsPlacementDto> addTopGoodsPlacements(@RequestBody @Valid TopGoodsPlacementDto request) {
        return ResponseEntity.ok(goodsService.addTopGoodsPlacements(request));
    }

    @GetMapping("/random")
    public List<GoodsGetRandomResponse> getRandomGoods(
            @RequestParam(required = false, name = "category") String categoryName,
            @RequestParam(required = false, name = "quantity", defaultValue = "12") int quantity) {
        return goodsService.getRandomGoods(categoryName, quantity);
    }

    @GetMapping("/similar")
    public Page<GoodsSearchResponse> getSimilarGoods(@RequestParam(required = false, name = "category") String categoryName,
                                                     @RequestParam(name = "price") BigDecimal price) {
        return goodsService.getSimilarGoods(categoryName, price);
    }

    @GetMapping("/filter")
    public Page<GoodsSearchResponse> filterGoods(@RequestParam(name = "category", required = false) String categoryName,
                                                 @RequestParam(name = "min_price", required = false) BigDecimal minPrice,
                                                 @RequestParam(name = "max_price", required = false) BigDecimal maxPrice,
                                                 Pageable pageable) {
        return goodsService.filterGoods(categoryName, minPrice, maxPrice, pageable);
    }
}

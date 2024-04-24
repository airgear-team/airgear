package com.airgear.controller;

import com.airgear.dto.GoodsCreateRequest;
import com.airgear.service.GoodsService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@AllArgsConstructor
public class SearchController {

    private final GoodsService goodsService;

    @GetMapping(value = "/{goodsName}")
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<GoodsCreateRequest> listGoodsByName(@Parameter(hidden = true) Pageable pageable,
                                                    @PathVariable String goodsName) {
        return goodsService.listGoodsByName(pageable, goodsName);
    }
}

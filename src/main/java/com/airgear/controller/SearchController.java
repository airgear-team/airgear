package com.airgear.controller;

import com.airgear.utils.Routes;
import com.airgear.dto.GoodsResponseDTO;
import com.airgear.service.GoodsService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The {@code SearchController} for handling search operations related to goods.
 * <p>
 *
 * @author Oleksandr Tuleninov
 * @version 01
 */
@RestController
@RequestMapping(Routes.SEARCH)
public class SearchController {

    private final GoodsService goodsService;

    public SearchController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * Endpoint for listing goods by name.
     *
     * @param pageable  The pagination information.
     * @param goodsName The name of the goods to search for.
     * @return A Page of GoodsResponse containing goods matching the specified name.
     */
    @GetMapping(
            value = "/{goodsName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN','MODERATOR', 'USER')")
    @PageableAsQueryParam
    public Page<GoodsResponseDTO> listGoodsByName(@Parameter(hidden = true) Pageable pageable,
                                                  @PathVariable String goodsName) {
        return goodsService.listGoodsByName(pageable, goodsName);
    }
}

package com.airgear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDownloadRequest {

    @PositiveOrZero(message = "user id must be 0 or positive number")
    long userId;

    @PositiveOrZero(message = "goods id must be 0 or positive number")
    long goodsId;

    @NotBlank(message = "image id must not be blank")
    String imageId;

}

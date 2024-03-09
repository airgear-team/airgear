package com.airgear.model;

import com.airgear.model.goods.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalAgreement {
    static int count =0;

    @NotBlank(message = "Lessor cannot be blank")
    @Size(min = 10, max = 255, message = "Name length must be between 3 and 255 characters")
    private String lessor;

    @NotBlank(message = "Renter cannot be blank")
    @Size(min = 10, max = 255, message = "Name length must be between 3 and 255 characters")
    private String renter;

    @NotNull(message = "First date cannot be null")
    private OffsetDateTime firstdate;
    @NotNull(message = "Last date cannot be null")
    private OffsetDateTime lastdate;

    private Goods goods;
    private String goal;

    private double rentalprice;
    private String lessordocument;
    private String renterdocument;
    private String lessorphone;
    private String renterphone;


}

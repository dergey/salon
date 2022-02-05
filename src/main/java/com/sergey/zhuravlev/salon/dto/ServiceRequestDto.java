package com.sergey.zhuravlev.salon.dto;

import com.sergey.zhuravlev.salon.domain.enumeration.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceRequestDto {

    @NotNull
    private String title;

    @NotNull
    private Sex sex;

    @NotNull
    private BigDecimal price;

}

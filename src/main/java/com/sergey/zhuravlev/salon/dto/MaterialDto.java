package com.sergey.zhuravlev.salon.dto;

import com.sergey.zhuravlev.salon.domain.enumeration.Unit;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaterialDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private Unit unit;

    @NotNull
    private BigDecimal price;

}

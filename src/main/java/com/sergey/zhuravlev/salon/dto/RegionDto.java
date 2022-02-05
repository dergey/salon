package com.sergey.zhuravlev.salon.dto;

import com.sergey.zhuravlev.salon.domain.enumeration.RegionStatus;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@ApiModel(description = "todo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDto {

    private Long id;

    private RegionStatus status;

    @NotNull
    private String regionName;

}

package com.sergey.zhuravlev.salon.dto;

import com.sergey.zhuravlev.salon.domain.enumeration.Sex;
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
public class ClientRequestDto {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private Sex sex;

}

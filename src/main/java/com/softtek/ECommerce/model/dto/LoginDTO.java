package com.softtek.ECommerce.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "LoginDTO", description = "DTO utilizado para loguear un usuario")
public class LoginDTO {
    @NotEmpty
    @Size(min=3,message="El nombre de usuario o email debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "usernameOrEmail",dataType = "String" ,example = "matias9486",position = 0)
    private String usernameOrEmail;

    @NotEmpty
    @Size(min=3,message="El password debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "password",dataType = "String" ,example = "password",position = 1)
    private String password;
}

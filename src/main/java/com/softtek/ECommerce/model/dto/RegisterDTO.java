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
@ApiModel(value = "RegisterDTO", description = "DTO utilizado para registrar un usuario")
public class RegisterDTO {
    @NotEmpty
    @Size(min=5,message="El nombre debe contener al menos 5 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Matias ",position = 0)
    private String nombre;

    @NotEmpty
    @Size(min=5,message="El nombre de usuario debe contener al menos 5 caracteres.")
    @ApiModelProperty(value = "username",dataType = "String",example = "matias9486",position = 1)
    private String username;

    @NotEmpty
    @Size(min=5,message="El email debe contener al menos 5 caracteres.")
    @ApiModelProperty(value = "email",dataType = "String",example = "matias@gmail.com",position = 3)
    private String email;

    @NotEmpty
    @Size(min=5,message="El password debe contener al menos 5 caracteres.")
    @ApiModelProperty(value = "password",dataType = "String",example = "mipassword12",position = 3)
    private String password;
}

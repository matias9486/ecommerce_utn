package com.softtek.ECommerce.model.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.softtek.ECommerce.model.Enums.TipoRol;
import com.softtek.ECommerce.model.dto.views.UsuarioViews;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ApiModel(value = "UsuarioDTO", description = "DTO utilizado para mostrar un usuario")
public class UsuarioDTO {
    @JsonView(UsuarioViews.DTO.class)
    @ApiModelProperty(value = "id",dataType = "Long",example = "1",position = 0)
    private Long id;

    @NotEmpty
    @Size(min=3,message="El nombre debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombre",dataType = "String",example = "Jose",position = 1)
    private String nombre;

    @JsonView(UsuarioViews.DTO.class)
    @NotEmpty
    @Size(min=3,message="El apellido debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "apellido",dataType = "String",example = "Martinez",position = 2)
    private String apellido;

    @JsonView(UsuarioViews.DTO.class)
    @NotEmpty
    @Size(min=8,message="El nombre de usuario debe contener al menos 3 caracteres.")
    @ApiModelProperty(value = "nombreUsuario",dataType = "String",example = "joseMartinez86",position = 3)
    private String nombreUsuario;

    @JsonView(UsuarioViews.DTOConPassword.class)
    @NotEmpty
    @Size(min=8,message="El password debe contener al menos 8 caracteres.")
    @ApiModelProperty(value = "password",dataType = "String",example = "mipass234",position = 4)
    private String password;

    @NotEmpty
    @Size(min=3)
    @Email
    @ApiModelProperty(value = "email",dataType = "String",example = "joseMartinez86@gmail.com",position = 3)
    private String email;

    @NotNull
    //@ApiModelProperty(value = "tipoRol",dataType = "TipoRol"/*,example = "joseMartinez86@gmail.com"*/,position = 4)
    private TipoRol tipoRol;

}

package com.softtek.ECommerce;

import com.softtek.ECommerce.Service.*;
import com.softtek.ECommerce.model.Enums.MediosDePago;
import com.softtek.ECommerce.model.Enums.TipoRol;
import com.softtek.ECommerce.model.dto.*;
import com.softtek.ECommerce.model.entity.Tienda;
import com.softtek.ECommerce.model.entity.TipoPersonalizacion;

/*
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
*/

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication

public class ECommerceApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

/*
	@Bean
	public CommandLineRunner init(UsuariosService usuarios,
								  AreaPersonalizacionService areas,
								  TipoPersonalizacionService tipos,
								  PosiblePersonalizacionService personalizaciones,
								  ProductoBaseService productos,
								  TiendaService tiendas,
								  PersonalizacionService personalizacionesTienda,
								  PersonalizacionesEspecificasService personalizacionesEspecificas,
								  ProductosPersonalizadosService productosPersonalizados,
								  PublicacionesService publicaciones) {
		return (pepe) -> {
			PasswordEncoder pass= new BCryptPasswordEncoder();
			System.out.println("admin pass: "+ pass.encode("admin"));
			System.out.println("usuario pass: "+ pass.encode("usuario"));

			//crendo admin para loguearme en swagger
			UsuarioDTO admin= new UsuarioDTO();
			admin.setNombre("Admin");
			admin.setApellido("Super");
			admin.setEmail("admin@gmail.com");
			admin.setPassword(pass.encode("admin"));
			admin.setNombreUsuario("admin");
			admin.setTipoRol(TipoRol.ROLE_ADMIN);
			usuarios.guardarUsuario(admin);

			//creando vendedor
			UsuarioDTO vendedor= new UsuarioDTO();
			vendedor.setNombre("Apu");
			vendedor.setApellido("Nahasapametilan");
			vendedor.setEmail("apus@gmail.com");
			vendedor.setPassword(pass.encode("vendedor"));
			vendedor.setNombreUsuario("vendedor");
			vendedor.setTipoRol(TipoRol.ROLE_VENDEDOR);
			usuarios.guardarUsuario(vendedor);

			//creando gestor
			UsuarioDTO u= new UsuarioDTO();
			u.setNombre("Matias");
			u.setApellido("Alancay");
			u.setEmail("matias@gmail.com");
			u.setPassword(pass.encode("gestor"));
			u.setNombreUsuario("gestor");
			u.setTipoRol(TipoRol.ROLE_GESTOR);
			usuarios.guardarUsuario(u);


			//u=usuarios.buscarPorId(3L);

			AreaPersonalizacionCreateDTO nuevaArea= new AreaPersonalizacionCreateDTO();
			nuevaArea.setNombre("Frente");
			areas.guardarArea(3L,nuevaArea);

			AreaPersonalizacionCreateDTO nuevaArea2= new AreaPersonalizacionCreateDTO();
			nuevaArea2.setNombre("Frente-Medio");
			areas.guardarArea(3L,nuevaArea2);

			AreaPersonalizacionCreateDTO nuevaArea3= new AreaPersonalizacionCreateDTO();
			nuevaArea3.setNombre("Dorso-Medio");
			areas.guardarArea(3L,nuevaArea3);

			TipoPersonalizacionCreateDTO imagen= new TipoPersonalizacionCreateDTO();
			imagen.setNombre("Imagen");
			tipos.guardarTipo(3L,imagen);

			TipoPersonalizacionCreateDTO texto= new TipoPersonalizacionCreateDTO();
			texto.setNombre("Texto");
			tipos.guardarTipo(3L,texto);


			//posibles personlazaciones(area+tipo)
			PosiblePersonalizacionCreateDTO personalizacion= new PosiblePersonalizacionCreateDTO();
			personalizacion.setAreaId(1L);
			personalizacion.setTipoId(1L);
			personalizaciones.guardar(3L,personalizacion);

			PosiblePersonalizacionCreateDTO textoEspalda= new PosiblePersonalizacionCreateDTO();
			textoEspalda.setAreaId(3L);
			textoEspalda.setTipoId(2L);
			personalizaciones.guardar(3L,textoEspalda);

			//productos bases
			ProductoBaseCreateDTO remera= new ProductoBaseCreateDTO();
			remera.setNombre("Remera");
			remera.setDescripcion("Remera talle S");
			remera.setPrecio(500.00);
			remera.setTiempoFabricacion(10);
			productos.guardar(3L,remera);

			ProductoBaseCreateDTO campera= new ProductoBaseCreateDTO();
			campera.setNombre("Campera");
			campera.setDescripcion("Campera talle S");
			campera.setPrecio(500.00);
			campera.setTiempoFabricacion(10);
			productos.guardar(3L,campera);

			//productos con posibles personalizaciones
			ProductoBasePosiblePersonalizacionDTO personalizacionProducto=new ProductoBasePosiblePersonalizacionDTO();
			personalizacionProducto.setPosiblePersonalizacionId(1L);
			productos.agregarPosiblePersonalizacion(1L,personalizacionProducto);

			ProductoBasePosiblePersonalizacionDTO dorsoTexto=new ProductoBasePosiblePersonalizacionDTO();
			dorsoTexto.setPosiblePersonalizacionId(2L);
			productos.agregarPosiblePersonalizacion(2L,dorsoTexto);

			//tienda
			TiendaCreateDTO miTienda=new TiendaCreateDTO();
			miTienda.setNombre("Mercado Libre");
			tiendas.guardar(1L,miTienda);

			//agregando medios de pago a Tienda
			MedioPagoCreateDTO medioPago=new MedioPagoCreateDTO();
			medioPago.setMedioPago(MediosDePago.TARJETA_CREDITO);
			tiendas.agregarMedioPago(1L,medioPago);
			medioPago.setMedioPago(MediosDePago.TARJETA_DEBITO);
			tiendas.agregarMedioPago(1L,medioPago);
			medioPago.setMedioPago(MediosDePago.TRANSFERENCIA);
			tiendas.agregarMedioPago(1L,medioPago);

			PersonalizacionCreateDTO logoUTN= new PersonalizacionCreateDTO();
			logoUTN.setContenido("logo.jpg");
			logoUTN.setPrecio(200.00);
			logoUTN.setNombre("Logo UTN");
			personalizacionesTienda.guardar(1L,logoUTN);

			PersonalizacionCreateDTO fraseUTN= new PersonalizacionCreateDTO();
			fraseUTN.setContenido("UTN MDP");
			fraseUTN.setPrecio(100.00);
			fraseUTN.setNombre("Frase UTN-MDP");
			personalizacionesTienda.guardar(1L,fraseUTN);

			PersonalizacionEspecificaCreateDTO especifica=new PersonalizacionEspecificaCreateDTO();
			especifica.setPosiblePersonalizacionId(1L);
			especifica.setPersonalizacionId(1L);
			personalizacionesEspecificas.guardar(1L,especifica);

			PersonalizacionEspecificaCreateDTO textoEnEspalda=new PersonalizacionEspecificaCreateDTO();
			textoEnEspalda.setPosiblePersonalizacionId(2L);
			textoEnEspalda.setPersonalizacionId(2L);
			personalizacionesEspecificas.guardar(1L,textoEnEspalda);

			//producto personalizado
			ProductoPersonalizadoCreateDTO productoPersonalizado=new ProductoPersonalizadoCreateDTO();
			productoPersonalizado.setNombre("Remera Negra");
			productoPersonalizado.setDescripcion("Remera negra de algodon");
			productoPersonalizado.setProductoBaseId(1L);
			productosPersonalizados.guardar(1L,productoPersonalizado);

			ProductoPersonalizadoCreateDTO productoCampera=new ProductoPersonalizadoCreateDTO();
			productoCampera.setNombre("Campera Negra");
			productoCampera.setDescripcion("Campera negra de Polar");
			productoCampera.setProductoBaseId(2L);
			productosPersonalizados.guardar(1L,productoCampera);


			//agregando personalizacion especifica al producto
			ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO productoConPersonalizacion=new ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO();
			productoConPersonalizacion.setPersonalizacionEspecificaId(1L);
			productosPersonalizados.agregarPersonalizacionEspecifica(1L,productoConPersonalizacion);

			//agregando personalizacion especifica a campera
			ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO productoConPersonalizacionCampera=new ProductoPersonalizadoConPersonalizacionEspecificaCreateDTO();
			productoConPersonalizacionCampera.setPersonalizacionEspecificaId(2L);
			productosPersonalizados.agregarPersonalizacionEspecifica(2L,productoConPersonalizacionCampera);


			PublicacionCreateDTO publicacion= new PublicacionCreateDTO();
			publicacion.setDescripcion("Remera UTN Negra");
			publicacion.setProductoId(1L);
			publicaciones.guardar(1L,publicacion);



		};
	}*/


}

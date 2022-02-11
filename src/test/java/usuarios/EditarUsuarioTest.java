package usuarios;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Url.loginUrl;
import static utils.Url.userUrl;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import io.restassured.http.ContentType;
import pojo.UsuarioPojo;

public class EditarUsuarioTest {
	public String id;
	private String token;
	
	
		@Before
		public void beforeEach() {
			
			UsuarioPojo usuario = new UsuarioPojo();
			usuario.setEmail("teste156983@qa.com.br");
			usuario.setPassword("teste");
			
			this.token = 
					given()
						.contentType(ContentType.JSON)
						.body(usuario)
					.when()
						.post(loginUrl)
					.then()
						.statusCode(200)
						.extract()
						.path("authorization");
			
	}
	

	@Test
	@DisplayName("Cenario 1 - Editar um usuario existente")
	public void testEditarUsuario() {
		
	id = given()
			.contentType(ContentType.JSON)
			.header("Authorization", token)
	    .when()
			.get(userUrl)
			.then()
				.statusCode(200)
				.extract()
					.path("usuarios[0]._id");
					
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", token)
		.when()
			.put(userUrl.concat(id))
		.then()
			.statusCode(200)
			.body("message", equalTo("Registro alterado com sucesso"));
	}

	@Test
	@DisplayName("Cenario 2 - Alterar permissão de administrador")
	public void testAlterarPermissaoAdministrador() {
		
		UsuarioPojo usuario = new UsuarioPojo();
		usuario.setAdministrador("false");

		given()
			.contentType(ContentType.JSON)
			.header("Authorization", token)
			.body(usuario)
		.when()
			.put(userUrl.concat(id))
		.then()
			.statusCode(200)
			.body("nome", equalTo("nome é obrigatório"))
			.body("email", equalTo("email é obrigatório"))
			.body("password", equalTo("password é obrigatório"));
	}
	
}

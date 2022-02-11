package usuarios;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runners.MethodSorters;

import io.restassured.http.ContentType;
import pojo.UsuarioPojo;

import static utils.Url.loginUrl;
import static utils.Url.userUrl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListaUsuariosTest {
	
	private String token;
	String id;
	
	
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
	@DisplayName("Cenario 1 - Validar listagem de usuários")
	public void testValidarListagemUsuarios() {
			given()
				.contentType(ContentType.JSON)
				.header("Authorization", token)
				.queryParam("email", "luiza_korn@hotmail.com")
			.when()
				.get(userUrl)
			.then()
			.statusCode(200)
			.body("usuarios[0].nome",equalTo("Teste Bruna"))
			.body("usuarios[0].password",equalTo("Test"))
			.body("usuarios.email[0]", equalTo("luiza_korn@hotmail.com"))
			.body("usuarios[0].administrador", equalTo("false"));
			
	}
	
	
	@Test
	@DisplayName("Cenario 2 - Validar listagem de usuários com usuario especifico")
	public void testValidarListagemUsuariosEspecifico() {
			given()
				.contentType(ContentType.JSON)
				.header("Authorization", token)
			.when()
				.get(userUrl)
			.then()
			.statusCode(200)
			.body("usuarios.nome[0]", notNullValue())
            .body("usuarios.nome[1]", notNullValue());
	}
	
		
	@Test
	@DisplayName("Cenario 3 - Validar listagem de usuários com usuario que não existe")
	public void testValidarListagemUsuariosQueNaoExiste() {
			given()
				.contentType(ContentType.JSON)
				.header("Authorization", token)
				.queryParam("email", "zzzzzzzzzzzzzzzzzzzzzzz")
			.when()
				.get(userUrl)
			.then()
			.statusCode(400)
			.body("email",equalTo( "email deve ser um email válido"));
	}
}








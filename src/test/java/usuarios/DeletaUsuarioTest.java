package usuarios;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.Url.loginUrl;
import static utils.Url.userUrl;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runners.MethodSorters;

import io.restassured.http.ContentType;
import pojo.UsuarioPojo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeletaUsuarioTest {
	
	private String token;
	String id;
	
	@Before
	 public void criarUsuario() {
		
	
		UsuarioPojo usuario = new UsuarioPojo();
		usuario.setNome("Teste de Exclusão");
		usuario.setEmail("testaexclusão7@email.com.br");
		usuario.setPassword("teste");
		usuario.setAdministrador("true");
		
		id =  given() 
				.contentType(ContentType.JSON) 
		  		.body(usuario) 
		  	.when() 
		  		 .post(userUrl)
		    .then()
		  		.statusCode(201) 
		  		.assertThat() 
		  			.body("message",equalTo("Cadastro realizado com sucesso"))
		  		.extract()
	  			.path("_id");
		
		  
	}
	
	@Test
	@DisplayName("Cenario 1 - Deletar Usuario Existente")
	public void testDeletarUsuarioExistente() {
		
		this.token = 
				given()
					.contentType(ContentType.JSON)
					.body("{\r\n"
							+ "    \"email\": \"teste156983@qa.com.br\",\r\n"
							+ "    \"password\": \"teste\"\r\n"
							+ "}")
				.when()
					.post(loginUrl)
				.then()
					.statusCode(200)
					.extract()
						.path("authorization");
	
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", token)
	    .when()
	    	.delete(userUrl.concat(id))
	     .then()
			.statusCode(200)
			.assertThat().body("message", equalTo("Registro excluído com sucesso"));
	   }

	
	@Test
	@DisplayName("Cenario 2 - Deletar Usuario não existente na base")
	public void testDeletarUsuarioNaoExistente() {

		this.token = 
				given()
					.contentType(ContentType.JSON)
					.body("{\r\n"
							+ "    \"email\": \"teste156983@qa.com.br\",\r\n"
							+ "    \"password\": \"teste\"\r\n"
							+ "}")
				.when()
					.post(loginUrl)
				.then()
					.statusCode(200)
					.extract()
						.path("authorization");
		
		given()
			.contentType(ContentType.JSON)
			.header("Authorization", token)
	    .when()
	    	.delete(userUrl.concat("25458"))
	     .then()
			.statusCode(200)
			.assertThat().body("message", equalTo("Nenhum registro excluído"));
		}

	}


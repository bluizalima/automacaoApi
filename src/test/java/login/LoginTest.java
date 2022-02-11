package login;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runners.MethodSorters;

import io.restassured.http.ContentType;
import static utils.Url.loginUrl;
import static utils.Url.userUrl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class LoginTest {

	  @Test
	  @DisplayName("Cenario 1 - Validar usuário cadastrado com sucesso ") 
	  public void testCadastroComSucesso() {
	  given() 
	  	.contentType(ContentType.JSON) 
	  		.body("{\r\n"
	  				+ "  \"nome\": \"Fulano da Silva\",\r\n"
	  				+ "  \"email\": \"teste156983@qa.com.br\",\r\n"
	  				+ "  \"password\": \"teste\",\r\n"
	  				+ "  \"administrador\": \"true\"\r\n"
	  				+ "}") 
	  .when() 
	  	.post(userUrl)
	  .then() 
	  	.statusCode(201) 
	  	.assertThat() 
	  		.body("message",equalTo("Cadastro realizado com sucesso")); 
	  
	  }
	  
	  @Test
	  @DisplayName("Cenario 2 - Validar acesso com usuario cadastrado") 
	  public void testValidarAcessoComSucesso() {
	  given() 
	  	.contentType(ContentType.JSON) 
	  		.body("{\"email\": \"teste156983@qa.com.br\",\r\n"
	  				+ "\"password\": \"teste\"}") 
	  .when() 
	  	.post(loginUrl)
	  .then() 
	  	.statusCode(200) 
	  	.assertThat() 
	  		.body("message",equalTo("Login realizado com sucesso")); 
	  
	  }
	  
	  @Test
	  @DisplayName("Cenario 3 - Validar acesso com usuario não cadastrado") 
	  public void testValidarAcessoUsuarioNaoCadastrado() {
	  given() 
	  	.contentType(ContentType.JSON) 
	  		.body("{\"email\": \"tmatrix@qa.com.br\",\r\n"
	  				+ "\"password\": \"teste\"}") 
	  .when() 
	  	.post(loginUrl)
	  .then() 
	  	.statusCode(401) 
	  	.assertThat() 
	  		.body("message",equalTo("Email e/ou senha inválidos")); 
	  
	  }


}

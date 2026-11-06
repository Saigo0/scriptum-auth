package br.com.scriptum.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void testLoginSucesso() {

        String corpoRequisicao = """
            {
                "usuario": "admin",
                "senha": "123456"
            }
            """;
            
        given()
            .contentType(ContentType.JSON)
            .body(corpoRequisicao)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(200)
            .body("token", notNullValue());
    }

    @Test
    public void testLoginSenhaIncorreta() {
        String corpoRequisicao = """
            {
                "usuario": "admin",
                "senha": "senha-errada"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(corpoRequisicao)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(401)
            .body(is("Credenciais inválidas"));
    }

    @Test
    public void testLoginUsuarioIncorreto() {
        String corpoRequisicao = """
            {
                "usuario": "hacker",
                "senha": "123456"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(corpoRequisicao)
        .when()
            .post("/auth/login")
        .then()
            .statusCode(401)
            .body(is("Credenciais inválidas"));
    }
}
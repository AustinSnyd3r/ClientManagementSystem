import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthIntegrationTests {
    @BeforeAll
    static void setUp(){
        RestAssured.baseURI = "http://localhost:4004/api";
    }

    @Test
    public void shouldReturnOkayWithValidToken(){
        // 1. Arrange - Setup the call to the auth service to test the auth.
        // 2. Act - Make the call to the auth service itself.
        // 3. Assert - Assert our http return value is OK or 200

        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "password123"
                    }
                """;

        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        System.out.println("Generated Token: " + response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedWithNoToken(){
        String loginPayload = """
                    {
                        "email": "testuser@test.com",
                        "password": "WRONG_PASSWORD_FOR_TEST_USER"
                    }
                """;


        Response response = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401)
                .extract()
                .response();
    }

    @Test
    public void shouldReturnUnauthorizedWithBadToken(){
        String header = "Bearer fake_token";

        Response response = given()
                .header("Authorization", header)
                .when()
                .get("/auth/validate")
                .then()
                .statusCode(401)
                .extract()
                .response();

    }
}

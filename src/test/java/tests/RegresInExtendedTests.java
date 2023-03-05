package tests;

import model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.RegisterSpec.*;

public class RegresInExtendedTests {

    @DisplayName("Register-Successful")
    @Test
    void registerWithSpecSuccessful() {
        RegisterBodyLombokModel registerBody = new RegisterBodyLombokModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("pistol");

        RegisterResponseLombokModel registerResponse =
                step("Post register data", () ->
                        given(registerRequestSpec)
                                .body(registerBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(registerResponseSpec)
                                .extract().as(RegisterResponseLombokModel.class));
        step("Verify register response", () -> {
            assertThat(registerResponse.getId()).isNotNull();
            assertThat(registerResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        });
    }


    @DisplayName("Register without password")
    @Test
    void registerWithSpecUnsuccessful() {

        RegisterBodyLombokModel registerBody = new RegisterBodyLombokModel();
        registerBody.setEmail("eve.holt@reqres.in");
        RegisterResponseErrorModel registerResponseError =
                step("Post register data", () ->
                        given(registerRequestSpec)
                                .body(registerBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(registerResponseSpecPsw400)
                                .extract().as(RegisterResponseErrorModel.class));
        step("Verify error", () -> {
            assertThat(registerResponseError.getError()).isEqualTo("Missing password");
        });
    }

    @DisplayName("Register without email")
    @Test
    void registerWithSpecUnsuccessfulWithoutEmail() {

        RegisterBodyLombokModel registerBody = new RegisterBodyLombokModel();
        registerBody.setPassword("pistol");
        RegisterResponseErrorModel registerResponseError =
                step("Post register data", () ->
                        given(registerRequestSpec)
                                .body(registerBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(registerResponseSpecPsw400)
                                .extract().as(RegisterResponseErrorModel.class));
        step("Verify error", () -> {
            assertThat(registerResponseError.getError()).isEqualTo("Missing email or username");
        });
    }


    @DisplayName("Register without data")
    @Test
    void registerWithSpecUnsuccessfulWithoutData() {

        RegisterBodyLombokModel registerBody = new RegisterBodyLombokModel();
        RegisterResponseErrorModel registerResponseError =
                step("Post register data", () ->
                        given(registerRequestSpec)
                                .body(registerBody)
                                .when()
                                .post("/register")
                                .then()
                                .spec(registerResponseSpecPsw400)
                                .extract().as(RegisterResponseErrorModel.class));
        step("Verify error", () -> {
            assertThat(registerResponseError.getError()).isEqualTo("Missing email or username");
        });
    }


    @DisplayName("Successful update")
    @Test
    void updateJobSuccessful() {
        UpdateBodyLombokModel updateBody = new UpdateBodyLombokModel();
        updateBody.setName("morpheus");
        updateBody.setJob("zion resident");
        UpdateResponseModel updateResponse =
                step("Post update data", () ->
                        given(registerRequestSpec)
                                .body(updateBody)
                                .when()
                                .put("/users/2")
                                .then()
                                .spec(updateResponseSpec)
                                .extract().as(UpdateResponseModel.class));
        step("Verify update response", () -> {
            assertThat(updateResponse.getName()).isEqualTo("morpheus");
            assertThat(updateResponse.getJob()).isEqualTo("zion resident");
            assertThat(updateResponse.getUpdatedAt()).isNotNull();
        });
    }
}


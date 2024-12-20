package api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    private final RequestSpecification requestSpec;

    public BaseClient(String url) {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        requestSpec = given()
                .baseUri(url)
                .contentType(ContentType.JSON)
                .log()
                .all()
                .filter(new AllureRestAssured());

        if (url.matches("^(https)://.*$")) {
            requestSpec.relaxedHTTPSValidation();
        }
    }

    @Step("Sending GET request with path params '{endpoint}'")
    protected <T> T sendGet(String endpoint, int expectedStatusCode, Class<T> responseClass) {
        return given()
                .spec(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(responseClass);
    }

    @Step("Sending POST request with path params '{endpoint}'")
    protected <T, R> T sendPost(String endpoint, R body, int expectedStatusCode, Class<T> responseClass) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(responseClass);
    }

    @Step("Sending PUT request with path params '{endpoint}'")
    protected <T, R> T sendPut(String endpoint, R body, int expectedStatusCode, Class<T> responseClass) {
        return given()
                .spec(requestSpec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(responseClass);
    }

    @Step("Sending DELETE request with path params '{endpoint}'")
    protected <T> T sendDelete(String endpoint, int expectedStatusCode, Class<T> responseClass) {
        return given()
                .spec(requestSpec)
                .when()
                .delete(endpoint)
                .then()
                .statusCode(expectedStatusCode)
                .extract()
                .as(responseClass);
    }
}
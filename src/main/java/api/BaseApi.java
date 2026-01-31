package api;

import config.BookStoreConfig;
import config.LoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseApi {

    protected RequestSpecification buildRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BookStoreConfig.instance.baseURI())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addFilter(new LoggingFilter())
                .build();
    }

    private ValidatableResponse sendRequest(Method method, String endpoint, Map<String, String> queryParams, int expectedStatusCode
    ) {
        return RestAssured.given()
                .spec(buildRequestSpecification())
                .queryParams(queryParams)
                .when()
                .request(method, endpoint)
                .then()
                .statusCode(expectedStatusCode);
    }

    private ValidatableResponse sendRequest(Method method, String endpoint, int expectedStatusCode
    ) {
        return RestAssured.given()
                .spec(buildRequestSpecification())
                .when()
                .request(method, endpoint)
                .then()
                .statusCode(expectedStatusCode);
    }


    private ValidatableResponse sendRequest(Method method, String endpoint, Object requestBody, int expectedStatusCode) {
        return RestAssured.given()
                .spec(buildRequestSpecification())
                .body(requestBody)
                .when()
                .request(method, endpoint)
                .then()
                .statusCode(expectedStatusCode);
    }

    protected ResponseData get(String endpoint, HashMap<String, String> queryParams, int statusCode) {
        return new ResponseData(sendRequest(Method.GET, endpoint, queryParams, statusCode));
    }

    protected ResponseData get(String endpoint, int statusCode) {
        return new ResponseData(sendRequest(Method.GET, endpoint, statusCode));
    }

    protected <T> ResponseData post(String endpoint, T requestBody, int expectedStatusCode) {
        return new ResponseData(sendRequest(Method.POST, endpoint, requestBody, expectedStatusCode));
    }

    protected <T> ResponseData patch(String endpoint, T requestBody, int expectedStatusCode) {
        return new ResponseData(sendRequest(Method.PATCH, endpoint, requestBody, expectedStatusCode));
    }

    protected <T> ResponseData put(String endpoint, T requestBody, int expectedStatusCode) {
        return new ResponseData(sendRequest(Method.PUT, endpoint, requestBody, expectedStatusCode));
    }

    protected <T> ResponseData delete(String endpoint, HashMap<String, String> queryParams, int statusCode) {
        return new ResponseData(sendRequest(Method.DELETE, endpoint, queryParams, statusCode));
    }

    protected <T> ResponseData delete(String endpoint, int statusCode) {
        return new ResponseData(sendRequest(Method.DELETE, endpoint, statusCode));
    }

}

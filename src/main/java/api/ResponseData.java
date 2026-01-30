package api;

import io.restassured.response.ValidatableResponse;

import java.util.List;

public class ResponseData {

    private final ValidatableResponse response;

    public ResponseData(ValidatableResponse response) {
        this.response = response;
    }

    public <T> T as(Class<T> clazz) {
        return response.extract().as(clazz);
    }

    public <T> List<T> asList(Class<T> clazz) {
        return response.extract().jsonPath().getList("", clazz);
    }


    public ValidatableResponse asResponse() {
        return response;
    }
}

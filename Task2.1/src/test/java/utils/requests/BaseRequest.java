package utils.requests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.PropertyProvider;

import static io.restassured.RestAssured.given;

public class BaseRequest {
    private static final PropertyProvider provider = PropertyProvider.getInstance();
    private static final String BASE_URL = provider.getProperty("base.url");
    private static RequestSpecification specification;

    public static void init() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        specification = builder
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static Response postRequest(Endpoint endpoint, Object body) {
        return given()
                .spec(specification)
                .when()
                .body(body, ObjectMapperType.GSON)
                .post(endpoint.getPath());
    }

    public static Response getRequest(Endpoint endpoint, String... params) {
        return given()
                .spec(specification)
                .when()
                .get(endpoint.getPathWithParams(params));
    }
}

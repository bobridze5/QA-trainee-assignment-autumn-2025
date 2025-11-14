package utils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.announcement.AnnouncementRequest;
import pojo.announcement.Request;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseRequest {
    private static final PropertyProvider provider = PropertyProvider.getInstance();
    private static final String BASE_URL = provider.getProperty("base.url");

    private static RequestSpecification specification;
    private static String endpoint;

    public static void init(String route) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder builder = new RequestSpecBuilder();
        specification = builder
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .setAccept(ContentType.JSON)
                .build();

        endpoint = route;
    }

    public static Response postRequest(Request request) {
        return given()
                .spec(specification)
                .basePath(endpoint)
                .when()
                .body(request, ObjectMapperType.GSON)
                .post();
//                .then()
//                .statusCode(200);
//                .extract().jsonPath().get("status");
    }


    public static Response getRequest(String uuid) {
        return given()
                .spec(specification)
                .basePath(endpoint.replace(":id", uuid))
                .when()
                .get();
    }

    public static AnnouncementRequest createAnnouncementRequest(
            Integer sellerID,
            String name,
            Integer price,
            Map<String, Integer> statistics
    ) {
        return AnnouncementRequest
                .builder()
                .sellerID(sellerID)
                .name(name)
                .price(price)
                .statistics(statistics)
                .build();
    }

}

package tests;

import io.restassured.filter.log.UrlDecoder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pojo.announcement.AnnouncementRequest;
import pojo.announcement.AnnouncementResponse;
import utils.helpers.AnnouncementUtils;
import utils.requests.BaseRequest;
import utils.helpers.DataProviders;
import utils.helpers.Validator;
import utils.requests.Endpoint;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class AnnouncementTest extends BaseTest {
    private SoftAssert softy;

    @BeforeClass
    void setUp() {
        BaseRequest.init();
    }

    @BeforeMethod
    void setSoftAssert() {
        softy = new SoftAssert();
    }

    @AfterMethod
    void assertSoftyAll() {
        softy.assertAll();
    }

    @Test(
            description = "TR001 Успешное сохранение объявления",
            dataProvider = "announcementPositiveData",
            dataProviderClass = DataProviders.class
    )
    void saveAnnouncement_success_should_return_200_and_announcementID(
            Integer sellerID,
            String name,
            Integer price,
            Integer likes,
            Integer viewCount,
            Integer contacts
    ) {
        AnnouncementRequest announcement = AnnouncementUtils.createAnnouncement(
                sellerID,
                name,
                price,
                likes,
                viewCount,
                contacts
        );

        Response response = BaseRequest.postRequest(Endpoint.CREATE_ANNOUNCEMENT, announcement);

        String str = response.then()
                .statusCode(200)
                .extract().jsonPath().getString("status");

        String UUID = str.split(" ")[3];
        softy.assertTrue(Validator.isValidUUID(UUID), "Вернулась строка отличная от UUID");
    }

    @Test(
            description = "TR002 Неуспешное сохранение объявления",
            dataProvider = "getNegativeAnnouncementData",
            dataProviderClass = DataProviders.class
    )
    void saveAnnouncement_failed_should_return_400_and_statusMessage(
            Integer sellerID,
            String name,
            Integer price,
            Integer likes,
            Integer viewCount,
            Integer contacts,
            String expectedMessage
    ) {
        AnnouncementRequest request = AnnouncementUtils.createAnnouncement(
                sellerID,
                name,
                price,
                likes,
                viewCount,
                contacts
        );


        Response response = BaseRequest.postRequest(Endpoint.CREATE_ANNOUNCEMENT, request);

        String resultMessage = response.then()
                .statusCode(400)
                .extract().jsonPath().getString("result.message");

        Assert.assertEquals(resultMessage, expectedMessage);
    }

    @Test(
            description = "TR003 Успешное получение данных объявления по id"
    )
    void getAnnouncement_success_should_return_200_and_announcementData() {
        Integer sellerID = 1111233;
        String name = "testItem";
        Integer price = 1000;
        Integer likes = 10;
        Integer viewCount = 20;
        Integer contacts = 110;

        String announcementId = AnnouncementUtils.createAnnouncementAndGetId(
                sellerID,
                name,
                price,
                likes,
                viewCount,
                contacts
        );

        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT, announcementId);
        response.then().statusCode(200);

        AnnouncementResponse announcement = List.of(response.getBody().as(AnnouncementResponse[].class)).getFirst();
        softy.assertEquals(announcement.getId(), announcementId, "Id объявления не совпали");
        softy.assertEquals(announcement.getName(), name, "Name объявления не совпали");
        softy.assertEquals(announcement.getPrice(), price, "Price объявления не совпали");
        softy.assertEquals(announcement.getStatistics().getLikes(), likes,
                "Likes объявления не совпали");
        softy.assertEquals(announcement.getStatistics().getViewCount(), viewCount,
                "viewCount объявления не совпали");
        softy.assertEquals(announcement.getStatistics().getContacts(), contacts,
                "Contacts объявления не совпали");
    }

    @Test(
            description = "TR004 Неуспешное получение данных объявления при невалидном id объявления",
            dataProvider = "announcementInvalidId",
            dataProviderClass = DataProviders.class
    )
    void getAnnouncement_failed_should_return_400_and_statusMessage(String id) {
        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT, id);

        String message = response.then()
                .statusCode(400)
                .extract().jsonPath().getString("result.message");

        String[] parts = message.split(":");
        message = parts[0] + ":" + UrlDecoder.urlDecode(parts[1], StandardCharsets.UTF_8, false);

        String expectedMessage = "ID айтема не UUID: " + id;
        softy.assertEquals(message, expectedMessage, "Сообщения об ошибке не совпали");
    }

    @Test(
            description = "TR005 Неуспешное получение данных объявления при валидном id объявления",
            dataProvider = "announcementCorrectIdDoestExits",
            dataProviderClass = DataProviders.class
    )
    void getAnnouncement_failed_should_return_404_and_statusMessage(String id) {
        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT, id);
        String message = response.then()
                .statusCode(404)
                .extract().jsonPath().getString("result.message");

        String expectedMessage = "item " + id + " not found";
        softy.assertEquals(message, expectedMessage, "Сообщения об ошибке не совпали");
    }


}

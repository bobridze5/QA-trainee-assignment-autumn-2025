package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pojo.announcement.AnnouncementRequest;
import utils.helpers.AnnouncementUtils;
import utils.requests.BaseRequest;
import utils.helpers.DataProviders;
import utils.helpers.UUIDValidator;
import utils.requests.Endpoint;

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
    void saveAnnouncement_should_return_200(
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
        softy.assertTrue(UUIDValidator.isValidUUID(UUID), "Вернулась строка отличная от UUID");
    }

    @Test(
            description = "TR002 Неуспешное сохранение объявления",
            dataProvider = "announcementNegativeData",
            dataProviderClass = DataProviders.class
    )
    void saveAnnouncement_should_return_400(
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
            description = "TR003 Успешное получение поста по ID поста"
    )
    void getAnnouncement_should_return_200() {
        Integer sellerID = 1111233;
        String name = "testItem";
        Integer price = 1000;
        Integer likes = 10;
        Integer viewCount = 20;
        Integer contacts = 110;

        String id = AnnouncementUtils.createAnnouncementAndGetId(
                sellerID,
                name,
                price,
                likes,
                viewCount,
                contacts
        );

        System.out.println(id);

        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT,id);

        response.then().statusCode(200);


    }


}

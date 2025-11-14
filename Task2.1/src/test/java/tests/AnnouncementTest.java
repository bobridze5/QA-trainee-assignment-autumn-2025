package tests;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pojo.announcement.Request;
import utils.BaseRequest;
import utils.DataProviders;
import utils.UUIDValidator;

import java.util.Map;

public class AnnouncementTest extends BaseTest {
    private SoftAssert softy;

    @BeforeClass
    void setUp() {
        BaseRequest.init(provider.getProperty("route.announcement.create"));
    }

    @BeforeMethod
    void setSoftAssert(){
        softy = new SoftAssert();
    }

    @AfterMethod
    void assertSoftyAll(){
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
        Request announcement = BaseRequest.createAnnouncementRequest(
                sellerID,
                name,
                price,
                Map.of(
                        "likes", likes,
                        "viewCount", viewCount,
                        "contacts", contacts
                )
        );

        Response response = BaseRequest.postRequest(announcement);

        String str = response.then()
                .statusCode(200)
                .extract().jsonPath().getString("status");

        String UUID = str.split(" ")[3];
        softy.assertTrue(UUIDValidator.isValidUUID(UUID));
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
        Request request = BaseRequest.createAnnouncementRequest(
                sellerID,
                name,
                price,
                Map.of(
                        "likes", likes,
                        "viewCount", viewCount,
                        "contacts", contacts
                )
        );


        Response response = BaseRequest.postRequest(request);

        String resultMessage = response.then()
                .statusCode(400)
                .extract().jsonPath().getString("result.message");

        Assert.assertEquals(resultMessage, expectedMessage);
    }


}

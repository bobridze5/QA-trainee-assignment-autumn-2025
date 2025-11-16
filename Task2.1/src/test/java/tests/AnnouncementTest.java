package tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.filter.log.UrlDecoder;
import io.restassured.response.Response;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pojo.announcement.AnnouncementRequest;
import pojo.announcement.AnnouncementResponse;
import pojo.announcement.Statistics;
import utils.helpers.AnnouncementUtils;
import utils.requests.BaseRequest;
import utils.helpers.DataProviders;
import utils.requests.Endpoint;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Epic("Объявления")
@Feature("Управление объявлениями")
public class AnnouncementTest extends BaseTest {
    private SoftAssert softy;
    private Integer sellerID;

    @BeforeClass
    void setUp() {
        super.setProvider();
        sellerID = Integer.parseInt(provider.getProperty("announcement.user.id"));
        BaseRequest.init();

        AnnouncementUtils.deleteAllAnnouncements();
    }

    @BeforeMethod
    void setSoftAssert() {
        softy = new SoftAssert();
    }

    @AfterMethod(dependsOnMethods = {"clearTestData"})
    void assertSoftyAll() {
        softy.assertAll();
    }

    @AfterMethod(alwaysRun = true)
    void clearTestData() {
        AnnouncementUtils.deleteAllAnnouncements();
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
        softy.assertTrue(AnnouncementUtils.isValidUUID(UUID), "Вернулась строка отличная от UUID");
    }

    @Test(
            description = "TR002 Неуспешное сохранение объявления",
            dataProvider = "announcementNegativeData",
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

        softy.assertEquals(resultMessage, expectedMessage);
    }

    @Test(
            description = "TR003 Успешное получение данных объявления по id"
    )
    void getAnnouncement_success_should_return_200_and_announcementData() {
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

    @Test(
            description = "TR006 Успешное получение всех постов пользователя",
            dataProvider = "announcementCount",
            dataProviderClass = DataProviders.class
    )
    void getAnnouncements_by_sellerID_success_should_return_200_and_list_announcementData(int count) {
        List<String> createdIds = AnnouncementUtils.createAndGetIdsAnnouncements(count);
        Set<String> expectedIds = new HashSet<>(createdIds);

        Response response = BaseRequest.getRequest(Endpoint.GET_ALL_ANNOUNCEMENT, String.valueOf(sellerID));
        response.then().statusCode(200);
        List<AnnouncementResponse> announcements = List.of(response.getBody().as(AnnouncementResponse[].class));

        softy.assertEquals(announcements.size(), count, "Неверное количество объявлений");
        Set<String> actualIds = new HashSet<>();

        for (var announcement : announcements) {
            softy.assertEquals(announcement.getSellerId(), sellerID, "Объявление не принадлежит пользователю");
            actualIds.add(announcement.getId());
        }

        softy.assertTrue(actualIds.containsAll(expectedIds), "Не все созданные ID присутствуют");
        softy.assertEquals(actualIds.size(), expectedIds.size(), "Есть лишние или дублирующиеся ID");
    }

    @Test(
            description = "TR007 Успешное получение всех постов пользователя",
            dataProvider = "announcementInvalidSellerID",
            dataProviderClass = DataProviders.class
    )
    void getAnnouncements_by_sellerID_failed_should_return_400_and_statusMessage(String sellerID) {
        Response response = BaseRequest.getRequest(Endpoint.GET_ALL_ANNOUNCEMENT, sellerID);

        String actualMessage = response.then()
                .statusCode(400)
                .extract().jsonPath().getString("result.message");

        String expectedMessage = "передан некорректный идентификатор продавца";
        softy.assertEquals(actualMessage, expectedMessage, "Сообщения об ошибке не совпадают");
    }

    @Test(description = "TR008 Успешное получение статистики объявления по id")
    void getAnnouncementStatistic_success_should_return_200_and_statisticData() {
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

        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT_STATISTICS, id);
        response.then().statusCode(200);

        Statistics statistics = List.of(response.getBody().as(Statistics[].class)).getFirst();

        softy.assertEquals(statistics.getLikes(), likes, "likes не совпадают");
        softy.assertEquals(statistics.getViewCount(), viewCount, "viewCount не совпадают");
        softy.assertEquals(statistics.getContacts(), contacts, "contacts не совпадают");
    }

    @Test(
            description = "TR009 Неуспешное получение статистики объявления при невалидном id",
            dataProvider = "announcementInvalidId",
            dataProviderClass = DataProviders.class
    )
    void getAnnouncementStatistic_failed_should_return_400_and_statusMessage(String id) {
        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT_STATISTICS, id);

        String actualMessage = response.then()
                .statusCode(400)
                .extract().jsonPath().getString("result.message");

        String expectedMessage = "передан некорректный идентификатор объявления";
        softy.assertEquals(actualMessage, expectedMessage, "Сообщения об ошибке не совпадают");
    }


    @Test(
            description = "TR010 Неуспешное получение статистики объявления при валидном id",
            dataProvider = "announcementCorrectIdDoestExits",
            dataProviderClass = DataProviders.class
    )
    void getAnnouncementStatistic_failed_should_return_404_and_statusMessage(String id){
        Response response = BaseRequest.getRequest(Endpoint.GET_ANNOUNCEMENT_STATISTICS, id);

        String actualMessage = response.then()
                .statusCode(404)
                .extract().jsonPath().getString("result.message");

        String expectedMessage = "statistic " + id + " not found";
        softy.assertEquals(actualMessage, expectedMessage, "Сообщения об ошибке не совпадают");
    }

}

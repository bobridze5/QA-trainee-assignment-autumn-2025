package utils.helpers;

import io.restassured.response.Response;
import pojo.announcement.AnnouncementRequest;
import pojo.announcement.Statistics;
import utils.requests.BaseRequest;
import utils.requests.Endpoint;

public class AnnouncementUtils {

    public static Statistics createStatistics(Integer likes, Integer viewCount, Integer contacts) {
        return Statistics.builder()
                .likes(likes)
                .viewCount(viewCount)
                .contacts(contacts)
                .build();
    }

    public static AnnouncementRequest createAnnouncement(
            Integer sellerID,
            String name,
            Integer price,
            Integer likes,
            Integer viewCount,
            Integer contacts
    ) {
        Statistics statistics = createStatistics(likes, viewCount, contacts);

        return AnnouncementRequest
                .builder()
                .sellerID(sellerID)
                .name(name)
                .price(price)
                .statistics(statistics)
                .build();
    }

    public static String createAnnouncementAndGetId(
            Integer sellerID,
            String name,
            Integer price,
            Integer likes,
            Integer viewCount,
            Integer contacts
    ) {
        AnnouncementRequest request = createAnnouncement(
                sellerID,
                name,
                price,
                likes,
                viewCount,
                contacts
        );

        Response response = BaseRequest.postRequest(Endpoint.CREATE_ANNOUNCEMENT, request);
        String id = response.then()
                .statusCode(200)
                .extract().jsonPath().getString("status");

        return id.split(" ")[3];
    }
}

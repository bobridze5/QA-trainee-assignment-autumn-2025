package utils.helpers;

import io.restassured.response.Response;
import pojo.announcement.AnnouncementRequest;
import pojo.announcement.AnnouncementResponse;
import pojo.announcement.Statistics;
import utils.PropertyProvider;
import utils.requests.BaseRequest;
import utils.requests.Endpoint;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnnouncementUtils {
    private static final PropertyProvider provider = PropertyProvider.getInstance();
    private static final Integer sellerID = Integer.parseInt(provider.getProperty("announcement.user.id"));
    private static final SecureRandom random = new SecureRandom();

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


    public static List<String> createAndGetIdsAnnouncements(int count) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            String id = createAnnouncementAndGetId(
                    sellerID,
                    generateRandomStringOfAlphabet(generateRandomNumberInRange(1, 100)),
                    generateRandomNumberInRange(1, 10000),
                    generateRandomNumberInRange(1, 10000),
                    generateRandomNumberInRange(1, 10000),
                    generateRandomNumberInRange(1, 10000)
            );

            result.add(id);
        }
        return result;
    }

    public static void deleteAllAnnouncements(){
        Response response = BaseRequest.getRequest(Endpoint.GET_ALL_ANNOUNCEMENT, String.valueOf(sellerID));
        List<AnnouncementResponse> list = List.of(response.getBody().as(AnnouncementResponse[].class));
        list.forEach(i -> BaseRequest.deleteRequest(Endpoint.DELETE_ANNOUNCEMENT, i.getId())
                .then()
                .statusCode(200)
        );
    }

    public static boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static String generateRandomStringOfAlphabet(int length) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char symbol = (char) (random.nextInt(26) + 'A');
            boolean isUpper = random.nextBoolean();
            symbol = isUpper ? symbol : Character.toLowerCase(symbol);
            builder.append(symbol);
        }

        return builder.toString();
    }

    public static int generateRandomNumber(boolean isNegativeAllowed) {
        if (!isNegativeAllowed) {
            return random.nextInt(Integer.MAX_VALUE) + 1;
        }

        return random.nextInt();
    }

    public static int generateRandomNumberInRange(int origin, int bound) {
        return random.nextInt(origin, bound);
    }
}

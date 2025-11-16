package utils.helpers;

import org.testng.annotations.DataProvider;
import utils.PropertyProvider;

/**
 * Класс, который предоставляет наборы тестовых данных для тестов
 */
public class DataProviders {
    private static final PropertyProvider provider = PropertyProvider.getInstance();
    private static final Integer sellerId = Integer.parseInt(provider.getProperty("announcement.user.id"));

    @DataProvider(name = "announcementPositiveData")
    public Object[][] getPositiveAnnouncementsData() {
        return new Object[][]{
                {sellerId, "TestItem", 1000, 1, 2, 3},
                {sellerId, "TestItem2", 1, 1000, 100, 50},
                {sellerId, "TestItem3", 999999, 20, 30, 90},
//                {sellerId, "TestItem4", 100, 0, 1, 1}, Найдены баги
//                {sellerId, "TestItem5", 100, 0, 0, 1},
//                {sellerId, "TestItem6", 100, 0, 0, 0}
        };
    }

    @DataProvider(name = "announcementNegativeData")
    public Object[][] getNegativeAnnouncementData() {
        return new Object[][]{
//                {-123456789, "TestItem", 1000, 1, 2, 3, "поле sellerID обязательно"}, Найдены баги
                {sellerId, "", 1000, 1, 1, 1, "поле name обязательно"},
//                {sellerId, "TestItem", -1, 1, 1, 1, "поле price обязательно"},
//                {sellerId, "TestItem", 1, -1, 1, 1, "поле likes обязательно"},
//                {sellerId, "TestItem", 1, 1, -1, 1, "поле viewCount обязательно"},
//                {sellerId, "TestItem", 1, 1, 1, -1, "поле contacts обязательно"}
        };
    }

    @DataProvider(name = "announcementInvalidId")
    public Object[][] getAnnouncementInvalidId() {
        return new Object[][]{
                {"abc"},
                {"123"},
                {"1ab2c3"},
                {"ABC"},
                {"_=$@!"},
                {"a644ac2-6536-4caf-b09d-f350c9695d23"},
                {"a644ac2bc-6536-4caf-b09d-f350c9695d23"},
                {"a644ac2b-653-4caf-b09d-f350c9695d23"},
                {"a644ac2b-6536c-4caf-b09d-f350c9695d23"},
                {"a644ac2b-6536-caf-b09d-f350c9695d23"},
                {"a644ac2b-6536-44caf-b09d-f350c9695d23"},
                {"a644ac2b-6536-4caf-ab09d-f350c9695d23"},
                {"a644ac2b-6536-4caf-09d-f350c9695d23"},
                {"a644ac2b-6536-4caf-b09d-350c9695d23"},
                {"a644ac2b-6536-4caf-b09d-ff350c9695d23"},
                {"gggggggg-gggg-gggg-gggg-gggggggggggg"}
        };
    }

    @DataProvider(name = "announcementCorrectIdDoestExits")
    public Object[][] getAnnouncementCorrectIdDoestExist() {
        return new Object[][]{
                {"a644ac2b-6536-4caf-b09d-f350c9695d23"},
                {"aaaaaaaa-bbbb-cccc-dddd-eeeeeeeeeeee"},
                {"11111111-0000-3333-9999-555555555555"},
                {"1f1f1f1f-2f2f-3f3f-4f4f-5f5f5f5f5f5f"}
        };
    }

    @DataProvider(name = "announcementCount")
    public Object[][] getAnnouncementCountToCreate(){
        return new Object[][]{
                {0},
                {2},
                {10},
        };
    }

    @DataProvider(name = "announcementInvalidSellerID")
    public Object[][] getAnnouncementInvalidSellerID(){
        return new Object[][]{
                {"abc"},
                {"1ab2c3"},
                {"ABC"},
                {"_=$@!"}
        };
    }
}

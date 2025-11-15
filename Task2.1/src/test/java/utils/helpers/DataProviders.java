package utils.helpers;

import org.testng.annotations.DataProvider;

/**
 * Класс, который предоставляет наборы тестовых данных для тестов
 */
public class DataProviders {

    @DataProvider(name = "announcementPositiveData")
    public Object[][] getPositiveAnnouncementsData() {
        return new Object[][]{
                {123456789, "TestItem", 1000, 1, 2, 3},
                {111111111, "TestItem2", 1, 1000, 100, 50},
                {999999999, "TestItem3", 999999, 20, 30, 90},
                {123456789, "TestItem4", 100, 0, 1, 1},
                {123456789, "TestItem5", 100, 0, 0, 1},
                {123456789, "TestItem6", 100, 0, 0, 0}
        };
    }

    @DataProvider(name = "announcementNegativeData")
    public Object[][] getNegativeAnnouncementData() {
        return new Object[][]{
                {-123456789, "TestItem", 1000, 1, 2, 3, "поле sellerID обязательно"},
                {123456789, "", 1000, 1, 1, 1, "поле name обязательно"},
                {123456789, "TestItem", -1, 1, 1, 1, "поле price обязательно"},
                {123456789, "TestItem", 1, -1, 1, 1, "поле likes обязательно"},
                {123456789, "TestItem", 1, 1, -1, 1, "поле viewCount обязательно"},
                {123456789, "TestItem", 1, 1, 1, -1, "поле contacts обязательно"}
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
}

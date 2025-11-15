package utils.helpers;

import org.testng.annotations.DataProvider;

/**
 * Класс, который предоставляет наборы тестовых данных для тестов
 */
public class DataProviders {

    @DataProvider(name = "announcementPositiveData")
    public Object[][] getPositiveAnnouncementsData(){
        return new Object[][] {
                {123456789, "TestItem", 1000, 1, 2, 3},
                {111111111, "TestItem2", 1, 1000, 100, 50},
                {999999999, "TestItem3", 999999, 20, 30, 90},
                {123456789, "TestItem4", 100, 0, 1, 1},
                {123456789, "TestItem5", 100, 0, 0, 1},
                {123456789, "TestItem6", 100, 0, 0, 0}
        };
    }

    @DataProvider(name = "announcementNegativeData")
    public Object[][] getData(){
        return new Object[][]{
                {-123456789, "TestItem", 1000, 1, 2, 3, "поле sellerID обязательно"},
                {123456789, "", 1000, 1, 1, 1, "поле name обязательно"},
                {123456789, "TestItem", -1, 1, 1, 1, "поле price обязательно"},
                {123456789, "TestItem", 1, -1, 1, 1, "поле likes обязательно"},
                {123456789, "TestItem", 1, 1, -1, 1, "поле viewCount обязательно"},
                {123456789, "TestItem", 1, 1, 1, -1, "поле contacts обязательно"}
        };
    }
}

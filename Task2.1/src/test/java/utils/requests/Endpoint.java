package utils.requests;

import utils.PropertyProvider;

import java.util.HashMap;
import java.util.Map;


public enum Endpoint {
    CREATE_ANNOUNCEMENT("route.announcement.post"),
    GET_ANNOUNCEMENT("route.announcement.get"),
    GET_ANNOUNCEMENT_STATISTICS("route.announcement.get.statistics"),
    GET_ALL_ANNOUNCEMENT("route.announcement.get.all");


    private static final PropertyProvider provider = PropertyProvider.getInstance();
    private static final Map<String, String> endpoints = new HashMap<>();

    private final String propertyKey;

    Endpoint(String propertyKey){
        this.propertyKey = propertyKey;
    }


    public String getPath(){
        return endpoints.computeIfAbsent(propertyKey, provider::getProperty);
    }

    /**
     * Метод заменяет параметры пути на переданные значения по порядку
     * <p>
     * <b>Например</b>: <br>
     *  Endpoint: {@code api/v1/:userId/:postId} <br>
     *  params: {@code ["12345", "93"]} <br>
     *  результат: {@code api/v1/12345/93 }
     * </p>
     * @param params параметры пути
     * @return новый путь с переданными значениями
     */
    public String getPathWithParams(String... params){
        String path = getPath();
        String[] parts = path.split("/");
        StringBuilder builder = new StringBuilder();
        int paramIndex = 0;

        for (String part : parts) {
            if (part.startsWith(":") && paramIndex < params.length) {
                builder.append("/").append(params[paramIndex++]);
            } else {
                builder.append("/").append(part);
            }
        }

        return builder.substring(1);
    }
}

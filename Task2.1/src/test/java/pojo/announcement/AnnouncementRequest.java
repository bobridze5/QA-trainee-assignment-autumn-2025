package pojo.announcement;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
public class AnnouncementRequest implements Request {
    @SerializedName("sellerID")
    Integer sellerID;

    @SerializedName("name")
    String name;

    @SerializedName("price")
    Integer price;

    @SerializedName("statistics")
    Map<String, Integer> statistics;

}

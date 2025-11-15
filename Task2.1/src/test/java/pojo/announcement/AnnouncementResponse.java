package pojo.announcement;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnnouncementResponse {
    @SerializedName("createdAt")
    String createdAt;

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("price")
    Integer price;

    @SerializedName("statistics")
    Statistics statistics;

}

package pojo.announcement;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Statistics {
    @SerializedName("likes")
    Integer likes;

    @SerializedName("viewCount")
    Integer viewCount;

    @SerializedName("contacts")
    Integer contacts;
}

package ru.narbut.axel.data.entity.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PhotoPageResponseDto{
    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("totalHits")
    @Expose
    private int totalHits;

    @SerializedName("hits")
    @Expose
    private List<PhotoResponseDto> hits = null;
}
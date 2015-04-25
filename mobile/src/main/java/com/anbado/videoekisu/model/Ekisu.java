package com.anbado.videoekisu.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by SeunOh on 15. 4. 25..
 */
@Data
@Accessors(prefix = "m")
@NoArgsConstructor
public class Ekisu {
    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("index")
    private String mIndex;
    @SerializedName("video")
    private String mVideo;
    @SerializedName("thumbnail")
    private String mThumbnail;
    @SerializedName("section")
    private String mSection;
    @SerializedName("duration")
    private int mDuration;
    @SerializedName("sharelink")
    private String mShareLink;
    @SerializedName("created")
    private String mCreated;
    @SerializedName("category")
    private Category mCategory;

}

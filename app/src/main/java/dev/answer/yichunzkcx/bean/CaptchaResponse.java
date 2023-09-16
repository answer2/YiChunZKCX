package dev.answer.yichunzkcx.bean;

import com.google.gson.annotations.SerializedName;

public class CaptchaResponse {
    @SerializedName("code")
    private int code;
    
    @SerializedName("img")
    private String image;
    
    @SerializedName("uuid")
    private String uuid;

    public int getCode() {
        return code;
    }

    public String getImage() {
        return image;
    }

    public String getUuid() {
        return uuid;
    }
}

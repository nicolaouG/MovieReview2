package com.gnicolaou.user.moviereview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/*
Map the JSON Data to Java:

Generated from
http://www.jsonschema2pojo.org/

Source type: JSON
Annotation style: GSON
Use double numbers
Include getters and setters
Allow additional properties
 */

public class Resource {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("width")
    @Expose
    private Integer width;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}

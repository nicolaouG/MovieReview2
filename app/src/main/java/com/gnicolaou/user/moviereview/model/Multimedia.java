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

public class Multimedia {

    @SerializedName("resource")
    @Expose
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

}
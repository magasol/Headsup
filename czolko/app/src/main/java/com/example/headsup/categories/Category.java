package com.example.headsup.categories;

import java.io.Serializable;

public class Category implements Serializable {

    public int nameId;
    public String imageName;

    Category(int nameId, String imageName) {
        this.nameId = nameId;
        this.imageName = imageName;
    }
}

package com.example.headsup.categories;

import java.io.Serializable;

public class Category implements Serializable {

    public int nameId;
    public int imageId;

    Category(int nameId, int imageId) {
        this.nameId = nameId;
        this.imageId = imageId;
    }
}

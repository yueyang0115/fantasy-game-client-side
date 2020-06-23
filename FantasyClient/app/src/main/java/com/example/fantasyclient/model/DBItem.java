package com.example.fantasyclient.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class DBItem implements Serializable {
    private String item_class;
    private String item_properties;


    public DBItem() {
    }

    public DBItem(String item_class, String item_properties) {
        this.item_class = item_class;
        this.item_properties = item_properties;
    }

    public String getItem_class() {
        return item_class;
    }

    public void setItem_class(String item_class) {
        this.item_class = item_class;
    }

    public String getItem_properties() {
        return item_properties;
    }

    public void setItem_properties(String item_properties) {
        this.item_properties = item_properties;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBItem)) return false;
        DBItem dbItem = (DBItem) o;
        return Objects.equals(item_class, dbItem.item_class) &&
                Objects.equals(item_properties, dbItem.item_properties);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(item_class, item_properties);
    }
}


package ch.lebo_apps.contactme;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by miki-ubuntu on 15.1.18..
 */

public class Product {

    private int id;
    private String name;
    private String number;
    private String image;

    public Product() {
        super();
    }

    public Product(int id, String name, String number, String image) {
        super();
        this.id = id;
        this.name = name;
        this.number = number;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", number=" + number + "]";
    }
}
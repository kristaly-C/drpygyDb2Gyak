package org.example;

public class Cars {
    private int id;
    private String manufacturer;
    private String color;
    private int price;

    public Cars(){
        this.id = 0;
        this.manufacturer = "";
        this.color = "";
        this.price = 0;
    };

    public Cars(int id, String manufacturer, String model, int price) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.color = model;
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "id=" + id +
                ", manufacturer='" + manufacturer + '\'' +
                ", model='" + color + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}

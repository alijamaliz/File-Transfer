package model;

/**
 * Created by Alireza on 5/11/2017.
 */
public class Transfer {
    private String name, date, address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Transfer(String name, String date, String address) {

        this.name = name;
        this.date = date;
        this.address = address;
    }
}

package chat.com.chatrmi;

import java.io.Serializable;

public class Seller implements Serializable{

    private String name;
    private String numberPhone;

    /*
     * Getters and Setters
     */


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getNumberPhone() {
        return numberPhone;
    }
    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
    public Seller(String name, String numberPhone) {
        this.name = name;
        this.numberPhone = numberPhone;
    }
    @Override
    public String toString() {
        return "Seller [name=" + name + ", numberPhone=" + numberPhone + "]";
    }
}


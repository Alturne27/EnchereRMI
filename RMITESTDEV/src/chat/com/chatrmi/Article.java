package chat.com.chatrmi;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Article  implements Serializable{
    private double initialPrice;
    private double currentPrice;
    private String description;
    private LocalDateTime dateCloture;
    private String lastBidder;
    private InterfaceClient adjudicataire;
    private byte[] photo;
    private Seller seller;

    //Constructeurs
    public Article (double initialPrice, String description, LocalDateTime  date, byte[] photo, Seller seller){
        this.initialPrice = initialPrice;
        this.currentPrice = initialPrice;
        this.description = description;
        this.dateCloture=date;
        this.lastBidder="Pas encore de bid";
        this.adjudicataire = null;
        this.photo=photo;
        this.seller=seller;
    }

    // GETTERS && SETTERS
    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(LocalDateTime dateCloture) {
        this.dateCloture = dateCloture;
    }

    public String getLastBidder() {
        return lastBidder;
    }

    public void setLastBidder(String lastBidder) {
        this.lastBidder = lastBidder;
    }

    public InterfaceClient getAdjudicataire() {
        return adjudicataire;
    }

    public void setAdjudicataire(InterfaceClient adjudicataire) {
        this.adjudicataire = adjudicataire;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    @Override
    public String toString(){
        return " Article aux enchères de description "+ this.description +  "de prix initial : "+ this.initialPrice + ", et de prix dans les enchères courant "+ this.currentPrice;
    }

}


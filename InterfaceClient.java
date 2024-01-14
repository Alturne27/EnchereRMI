package chat.com.chatrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceClient extends Remote {
    public void AuctionService(String name, double price) throws RemoteException;

    public void AuctionArticalInfo(Article article) throws RemoteException;

    public String getNameBidder() throws RemoteException;

    public double getPrice() throws RemoteException;

    public void setPrice(double price) throws RemoteException;

    public void setNameBidder(String name) throws RemoteException;

    public String getSellerInfo(Seller seller) throws RemoteException;

    public Article getArticle() throws RemoteException;

    public void updateAuction(String name, double price) throws RemoteException;


}


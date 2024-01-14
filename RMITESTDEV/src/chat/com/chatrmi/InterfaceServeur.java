package chat.com.chatrmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceServeur extends Remote {


    public void joinAuction(InterfaceClient interfaceClient) throws RemoteException;
    public Article getAuctionInfo(InterfaceClient interfaceClient) throws Exception;
    public void endAuction (InterfaceClient interfaceClient, Seller seller) throws RemoteException;
    public void shutdownServer() throws RemoteException;

    public void registerClient(InterfaceClient client) throws RemoteException;


    Article getCurrentAuctionInfo() throws RemoteException;

    public void updateAuction(String bidder, double price) throws Exception;





}
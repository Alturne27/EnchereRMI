package chat.com.chatrmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ServeurDistant extends UnicastRemoteObject implements InterfaceServeur{

    public ArrayList<InterfaceClient> encherisseurs = new ArrayList<InterfaceClient>();
    public Article article;





    protected ServeurDistant(Article article) throws RemoteException {
        this.article = article;
    }

    public synchronized void registerClient(InterfaceClient client) throws RemoteException {
        if(!encherisseurs.contains(client)){
            encherisseurs.add(client);
            System.out.println("Nouveau participant : " + client.getNameBidder());
        }
    }


    @Override
    public synchronized Article getCurrentAuctionInfo(){
        return article;
    }

    @Override
    public synchronized void joinAuction(InterfaceClient interfaceClient) throws RemoteException {

        registerClient(interfaceClient);
    }

    @Override
    public Article getAuctionInfo(InterfaceClient interfaceClient) throws Exception {

        if (interfaceClient.getPrice()>article.getCurrentPrice() && article.getDateCloture().isAfter(LocalDateTime.now())){
            article.setAdjudicataire(interfaceClient);
            article.setLastBidder(interfaceClient.getNameBidder());
            article.setCurrentPrice(interfaceClient.getPrice());


        }
        return null;
    }

    @Override
    public void endAuction(InterfaceClient interfaceClient, Seller seller) throws RemoteException {
        System.out.println("C'est la fin des enchères, félicitations vous avez gagné !");
        interfaceClient.getSellerInfo(seller);


    }
    public void shutdownServer() throws RemoteException{
        UnicastRemoteObject.unexportObject(this, true);
    }


    @Override
    public void updateAuction(String bidder, double price) throws Exception {
        System.out.println("L'offre de " + bidder + " est de "+ price +"\n");
        this.article.setLastBidder(bidder);
        this.article.setCurrentPrice(price);
        System.out.println("L'adjudicataire : "+ article.getLastBidder());


    }


}

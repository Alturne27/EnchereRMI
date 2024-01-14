package chat.com.chatrmi;

import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientDistant extends UnicastRemoteObject implements InterfaceClient {
    private String name;
    private double price;


    private EnchereController controller;

    private InterfaceServeur serveur;





    //Constructeur par défaut
    public ClientDistant(String name) throws Exception{
        this.name = name;
    }

    public ClientDistant() throws Exception{
        this.name = "Anonyme";
    }

    @Override
    public void AuctionService(String name, double price) throws RemoteException {
        this.serveur.getCurrentAuctionInfo().setCurrentPrice(price);
        System.out.println("L'offre de " + this.serveur.getCurrentAuctionInfo().getLastBidder() + " est de "+ this.serveur.getCurrentAuctionInfo().getCurrentPrice() +"\n");
        this.serveur.getCurrentAuctionInfo().setLastBidder(name);
        System.out.println("L'adjudicataire : "+ this.serveur.getCurrentAuctionInfo().getLastBidder());
    }



    @Override
    public String getNameBidder() throws RemoteException {
        return this.name;

    }

    @Override
    public double getPrice() throws RemoteException {
        return this.price;
    }

    @Override
    public void setPrice(double price) throws RemoteException {
        this.price = price;
    }

    @Override
    public void setNameBidder(String name) throws RemoteException {
        this.name = name;
    }

    @Override
    public String getSellerInfo(Seller seller) throws RemoteException {
        String infos = "Vendeur : " + seller.getName() + "\n" + "Tel : " + seller.getNumberPhone();
        return infos;
    }






    @Override
    public void updateAuction(String name, double price) throws RemoteException {
        System.out.println("L'offre de " + name + " est de " + price + "\n");
        this.serveur.getCurrentAuctionInfo().setLastBidder(name);
        System.out.println("L'adjudicataire : " + this.serveur.getCurrentAuctionInfo().getLastBidder());

        Platform.runLater(() -> {
            try {
                this.controller.updateLabels(
                        this.serveur.getCurrentAuctionInfo().getSeller().toString(),
                        this.serveur.getCurrentAuctionInfo().getDescription(),
                        String.valueOf(this.serveur.getCurrentAuctionInfo().getCurrentPrice())
                );
                System.out.println("Mise à jour affichée sur l'IHM");
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void startAuctionUpdates() {
        // Utilisez un ScheduledExecutorService pour interroger périodiquement le serveur
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            try {
                Article latestInfo = this.serveur.getCurrentAuctionInfo();
                Platform.runLater(() -> {
                    try {
                        updateAuction(latestInfo.getLastBidder(), latestInfo.getCurrentPrice());
                        System.out.println("Mise à jour reçue : " + latestInfo.getLastBidder() + " - " + latestInfo.getCurrentPrice());
                        this.controller.updateLabels(
                                latestInfo.getSeller().toString(),
                                latestInfo.getDescription(),
                                String.valueOf(latestInfo.getCurrentPrice())
                        );
                        System.out.println(Objects.equals(latestInfo.getLastBidder(), this.name));
                        System.out.println("L'adjudicataire : " + latestInfo.getLastBidder());
                        System.out.println("Le nom du client : " + this.name);

                        this.controller.setLabelVendeurVisible(Objects.equals(latestInfo.getLastBidder(), this.name));

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }


    public void setController(EnchereController controller) {
        this.controller = controller;
    }

    public void setServeur(InterfaceServeur serveur) {
        this.serveur = serveur;
    }

    public InterfaceServeur getServeur() {
        return this.serveur;
    }
}


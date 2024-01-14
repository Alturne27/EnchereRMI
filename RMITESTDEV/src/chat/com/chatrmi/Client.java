package chat.com.chatrmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {

    public Client() {

    }

    public void start() throws Exception {
        try (Scanner sc = new Scanner(System.in)) {
            InterfaceServeur serveur = (InterfaceServeur) Naming.lookup("serveurVente");
            System.out.println("\nBienvenue dans le système de vente, veuillez entrer votre prénom : ");
            String name = new Scanner(System.in).nextLine();
            ClientDistant client = new ClientDistant(name);

            serveur.joinAuction(client);
            while (true) {

                System.out.println("voulez vous encherir ? : ");
                double price = new Scanner(System.in).nextDouble();

                client.setPrice(price);
                serveur.getAuctionInfo(client);



            }


        } catch (MalformedURLException | RemoteException | NotBoundException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {

        Client c = new Client();

        c.start();

    }
}


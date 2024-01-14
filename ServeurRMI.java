package chat.com.chatrmi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
//import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;

public class ServeurRMI {
    public static void main(String[] args) throws IOException, InterruptedException {
        try{
            LocateRegistry.createRegistry(1099);
            System.out.println("Annuaire créé ...");

            String path = "C:\\Users\\adelu\\IdeaProjects\\RMITESTDEV\\src\\chat\\com\\chatrmi\\photoCorvette.jpg";
            byte[] image = Files.readAllBytes(Paths.get(path));




            Seller seller = new Seller("Dubois", "+33 7 66 34 23 89");
            Article article = new Article(0.0, "Voiture de collection \n sortie en 1958", LocalDateTime.now().withSecond(0).withNano(0).plusMinutes(3), image, seller);
            ServeurDistant obj = new ServeurDistant(article);
            Naming.rebind("serveurVente",obj);

            System.out.println("Service enregistrés....");

            while (true){
                if (LocalDateTime.now().isAfter(article.getDateCloture())){

                    for(InterfaceClient client : obj.encherisseurs){
                        obj.registerClient(client);
                    }

                    obj.article.getAdjudicataire().getSellerInfo(obj.article.getSeller());
                    System.out.println("Fermeture du serveur...");

                    obj.shutdownServer();
                    System.exit(0);
                    break;
                }
                Thread.sleep(1000);
            }
        }
        catch (RemoteException e){
            e.printStackTrace();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
    }
}


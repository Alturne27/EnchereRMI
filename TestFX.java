package chat.com.chatrmi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.Naming;

public class TestFX extends Application {
    private Stage primaryStage;
    private ClientDistant client = new ClientDistant();

    public TestFX() throws Exception {}


    public static void main(String[] args) {

        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        afficherPageConnexion();


    }

    public void afficherPageConnexion(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("enchere-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Connexion");
            primaryStage.setScene(scene);
            primaryStage.show();

            EnchereController controller = loader.getController();
            controller.setMainApp(this);
            controller.setClient(client); //Partage de la référence du client

            client.setController(controller); //Partage de la référence du controller

            InterfaceServeur serveur = (InterfaceServeur) Naming.lookup("serveurVente");
            serveur.joinAuction(client);
            client.setServeur(serveur);

            client.startAuctionUpdates();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

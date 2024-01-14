package chat.com.chatrmi;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EnchereController {

    @FXML
    private TextField pseudoInput;

    @FXML
    private TextField enchereInput;

    @FXML
    private Label dernierPrix;

    @FXML
    private Label infosEnchere;

    @FXML
    private Label coordVendeur;

    @FXML
    private ImageView myImageView;

    @FXML
    private Button sendButton;

    private ClientDistant client;

    private TestFX testFX;





    public void setClient(ClientDistant client) {
        this.client = client;
    }

    public void setMainApp(TestFX testFX) {
        this.testFX = testFX;
    }

    public void setDernierPrix(String dernierPrix) {
        this.dernierPrix.setText(dernierPrix);
    }

    public void setInfosEnchere(String infosEnchere) {
        this.infosEnchere.setText(infosEnchere);
    }

    public void setCoordVendeur(String coordVendeur) {
        this.coordVendeur.setText(coordVendeur);
    }

    public void setMyImageView( Image image) {

        this.myImageView.setImage(image);
    }

    public void setLabelPrix(String prix) {
        this.dernierPrix.setText("Derniere enchere \n"+prix);
    }

    public void setLabelInfos(String infos) {
        this.infosEnchere.setText(infos);
    }

    public void setLabelCoord(String coord) {
        this.coordVendeur.setText(coord);
    }

    public void setLabelVendeurVisible( boolean visibilite) {
        this.coordVendeur.setVisible(visibilite);
    }

    public boolean getLabelVendeurVisible() {
        return this.coordVendeur.isVisible();
    }

    public void updateLabels(String coordVendeur, String infosEnchere, String dernierPrix) {
        setLabelCoord(coordVendeur);
        setLabelInfos(infosEnchere);
        setLabelPrix(dernierPrix);
    }


    public void onSendButton(javafx.event.ActionEvent event) {

        String pseudo = pseudoInput.getText();
        double enchere = Double.parseDouble(enchereInput.getText());

        if (pseudo.isEmpty() || Double.isNaN(enchere)) return;

        try {
            client.setPrice(enchere);
            client.AuctionService(pseudo, enchere);
            client.updateAuction(pseudo, enchere);

            client.setNameBidder(pseudo);

            client.getServeur().updateAuction(pseudo, enchere);

            enchereInput.clear();

            Platform.runLater(() -> {
                try {
                    this.updateLabels(
                            client.getSellerInfo(client.getServeur().getCurrentAuctionInfo().getSeller()),
                            client.getServeur().getCurrentAuctionInfo().getDescription(),
                            String.valueOf(client.getServeur().getCurrentAuctionInfo().getCurrentPrice())
                    );

                    System.out.println("L'adjudicataire : " + client.getServeur().getCurrentAuctionInfo().getLastBidder());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

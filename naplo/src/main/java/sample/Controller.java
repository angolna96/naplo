package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Controller {

    private Beolvas hallgato;

    private Hallgato biztonsagi_mentes;

    private int seged = 0;

    @FXML
    private Label atlag, hiba;

    @FXML
    private Pane paneHFel, paneHMod;

    @FXML
    private TextField ujnev, ujaz, ujd, ujk, ujtz;

    @FXML
    private TextField nevMod, azMod, dMod, kMod, tzMod;

    @FXML
    private ChoiceBox<String> valasztoka = new ChoiceBox<>();

    @FXML
    private ListView<String> jlista = new ListView<>();
    private ObservableList b = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        hallgato = new Beolvas();
        valasztoka.getItems().clear();
        valasztoka.getItems().addAll(hallgato.hallgatok_neve());
    }

    @FXML
    public void ok(ActionEvent e){
        jlista.getItems().clear();
        b.removeAll();
        b.add("dolgozat = "+ hallgato.adott_hallgato_djegye(valasztoka.getValue()).toString());
        b.add("témazáró = " + hallgato.adott_hallgato_tzjegye(valasztoka.getValue()).toString());
        b.add("kisötös: " + hallgato.adott_hallgato_kjegye(valasztoka.getValue()) + " db");
        jlista.setItems(b);

        List<Integer> n = hallgato.adott_hallgato_djegye(valasztoka.getValue());
        List<Integer> t = hallgato.adott_hallgato_tzjegye(valasztoka.getValue());
        int k = hallgato.adott_hallgato_kjegye(valasztoka.getValue());

        double a = Beolvas.atlag_szamol(n, t, k);

        if(a==-1){
            atlag.setText("Az adatok hibásak!");
            atlag.setTextFill(Paint.valueOf("#F56358"));
        }
        else if(a==0){
            atlag.setText("A hallgató teljesítménye nem értékelhető!");
            atlag.setTextFill(Paint.valueOf("#F56358"));
        }
        else{
            atlag.setText("Átlag = " + Beolvas.atlag_szamol(n, t, k));
            atlag.setTextFill(Paint.valueOf("#0D0D0D"));
        }
        if(a<2){
            atlag.setTextFill(Paint.valueOf("#F56358"));
        }
        else atlag.setTextFill(Paint.valueOf("#0D0D0D"));

        paneHFel.setVisible(false);
        paneHMod.setVisible(false);
    }

    @FXML
    public void HFel(ActionEvent e){
        atlag.setText("");
        jlista.getItems().clear();
        b.removeAll();
        jlista.setItems(b);
        if(paneHFel.isVisible())
            paneHFel.setVisible(false);
        else paneHFel.setVisible(true);

        if(paneHMod.isVisible())
            paneHMod.setVisible(false);
    }

    @FXML
    public void HFelOK(ActionEvent e){
        if(ujnev.getText().equals("") || ujaz.getText().equals("")){
                hiba.setText("A *-gal jelölt mező kitöltése kötelező");
                hiba.setTextFill(Paint.valueOf("#F56358"));
        }
        else {
            Hallgato h = new Hallgato();
            List<Integer> ld = new ArrayList<>();
            List<Integer> lt = new ArrayList<>();

            Scanner sc = new Scanner(ujd.getText());
            sc.useDelimiter(", |,");

            while (sc.hasNextInt()) {
                ld.add(sc.nextInt());
            }

            sc = new Scanner(ujtz.getText());
            sc.useDelimiter(", |,");

            while (sc.hasNextInt()) {
                lt.add(sc.nextInt());
            }

            h.setNev(ujnev.getText());
            h.sethAZ(ujaz.getText());
            h.setD(ld);
            h.setTz(lt);
            h.setK(Integer.parseInt(ujk.getText()));

            Beolvas.hallgato_felvetele(h);
            paneHFel.setVisible(false);
            initialize();

            hiba.setText("");
        }
    }

    @FXML
    public void torles(ActionEvent e){
        biztonsagi_mentes = hallgato.hallgato_kivalaszt(valasztoka.getValue());
        seged = 1;
        Beolvas.hallgato_torlese(valasztoka.getValue());
        initialize();
    }

    @FXML
    public void HMod(ActionEvent e){
        atlag.setText("");
        jlista.getItems().clear();
        b.removeAll();
        jlista.setItems(b);
        if(paneHMod.isVisible())
            paneHMod.setVisible(false);
        else paneHMod.setVisible(true);

        if(paneHFel.isVisible())
            paneHFel.setVisible(false);


        try{
            Hallgato kivalasztott = hallgato.hallgato_kivalaszt(valasztoka.getValue());
            nevMod.setText(kivalasztott.getNev());
            azMod.setText(kivalasztott.gethAZ());
            tzMod.setText(kivalasztott.getTz().toString());
            dMod.setText(kivalasztott.getD().toString());
            kMod.setText(Integer.toString(kivalasztott.getK()));
        }
        catch (Exception ex){}
    }

    @FXML
    public void HModOK(ActionEvent e){
        try {
            Hallgato modh = new Hallgato();

            List<Integer> ld = new ArrayList<>();
            List<Integer> lt = new ArrayList<>();

            Scanner sc = new Scanner(dMod.getText());
            sc.skip("\\[");
            sc.useDelimiter(", |]|,");

            while (sc.hasNextInt()) {
                ld.add(sc.nextInt());
            }

            sc = new Scanner(tzMod.getText());
            sc.skip("\\[");
            sc.useDelimiter(", |]|,");


            while (sc.hasNextInt()) {
                lt.add(sc.nextInt());
            }

            modh.setNev(nevMod.getText());
            modh.sethAZ(azMod.getText());
            modh.setD(ld);
            modh.setTz(lt);
            modh.setK(Integer.parseInt(kMod.getText()));

            Beolvas.hallgato_torlese(modh.getNev());
            Beolvas.hallgato_felvetele(modh);
            paneHMod.setVisible(false);
            initialize();
        }catch (Exception ex){}
    }

    @FXML
    public void vissza(ActionEvent e){
        if(seged==1) {
            Beolvas.hallgato_felvetele(biztonsagi_mentes);
            seged = 0;
            initialize();
        }
    }
}
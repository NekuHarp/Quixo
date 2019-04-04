package sample;

import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import sample.AI.IA;
import sample.AI.Test;

import java.io.File;

public class Controller {
    Main.data d;
    @FXML
    private Pane gamepane;
    @FXML
    private Pane optionpane;
    @FXML
    private Pane iapane;
    @FXML
    private GridPane grillemorpion;
    @FXML
    private ImageView ImgTopLeft;
    @FXML
    private ImageView ImgCenterLeft;
    @FXML
    private ImageView ImgBottomLeft;
    @FXML
    private ImageView ImgTopRight;
    @FXML
    private ImageView ImgCenterRight;
    @FXML
    private ImageView ImgBottomRight;
    @FXML
    private ImageView ImgTopCenter;
    @FXML
    private ImageView ImgCenterCenter;
    @FXML
    private ImageView ImgBottomCenter;
    @FXML
    private Button resetbtn;
    @FXML
    private Button optionbtn;
    @FXML
    private Label result;
    @FXML
    private Button osymbtn;
    @FXML
    private Button xsymbtn;
    @FXML
    private Button p2hbtn;
    @FXML
    private Button p2iabtn;
    @FXML
    private Button iaoptbtn;
    @FXML
    private Button valbtn;
    @FXML
    private ComboBox iatypecombo;
    @FXML
    private ComboBox iadifcombo;
    @FXML
    private Button iatrainbtn;
    @FXML
    private Button iavalbtn;
    @FXML
    private Label notif;
    @FXML
    private ProgressBar progtrain;
    @FXML
    private Button resettrainbtn;

    private double[] grille = new double[]{ 0,0,0, 0,0,0, 0,0,0 };
    private int joueur = 1;
    private int firstjoueur = 1;
    private int typejoueur = 1;
    private EventHandler<MouseEvent> eventH;
    private int resultat;
    private FadeTransition ft;
    private String iatype;
    private String iadif;
    private File fichierIA;
    private IA intelligence = new IA(2,0);
    public Controller control = this;

    public Controller()
    {
    }
    public void changeData(Main.data dio)
    {
        d=dio;
    }
    @FXML
    private void initialize()
    {
        resetbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                reecrire(0,0,0);
                joueur=firstjoueur;
                if(joueur==1){
                    result.setText("Tour de Joueur O");
                } else {
                    result.setText("Tour de Joueur X");
                }
            }
        });

        optionbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ft = new FadeTransition(Duration.millis(200), gamepane);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.play();
                ft = new FadeTransition(Duration.millis(200), optionpane);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                gamepane.setDisable(true);
                optionpane.setDisable(false);
            }
        });

        osymbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                firstjoueur=1;
            }
        });

        xsymbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                firstjoueur=2;
            }
        });

        p2hbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                typejoueur=1;
            }
        });

        p2iabtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                typejoueur=2;
            }
        });

        iaoptbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ft = new FadeTransition(Duration.millis(200), optionpane);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.play();
                ft = new FadeTransition(Duration.millis(200), iapane);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                optionpane.setDisable(true);
                iapane.setDisable(false);
            }
        });

        valbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                ft = new FadeTransition(Duration.millis(200), optionpane);
                ft.setFromValue(1);
                ft.setToValue(0);
                ft.play();
                ft = new FadeTransition(Duration.millis(200), gamepane);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                optionpane.setDisable(true);
                gamepane.setDisable(false);

                reecrire(0,0,0);
                joueur=firstjoueur;
                if(joueur==1){
                    result.setText("Tour de Joueur O");
                } else {
                    result.setText("Tour de Joueur X");
                }
            }
        });

        eventH = new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                Node node = (Node) e.getSource() ;
                String data = (String) node.getUserData();
                int x= Character.getNumericValue(data.charAt(0));
                int y= Character.getNumericValue(data.charAt(1));
                reecrire(joueur,x,y);
                if(typejoueur==1) {
                    if (joueur == 1) {
                        joueur++;
                    } else {
                        joueur--;
                    }
                    resultat = vresult();
                    ecrireresult();
                } else {
                    intelligence.addEnnemyTurn(grille,x,y);
                    resultat = vresult();
                    joueur++;
                    ecrireresult();
                    if(resultat==0){
                        gamepane.setDisable(true);
                        int[] coupia = intelligence.doTurn(grille);
                        //afficheboard();
                        reecrire(joueur,coupia[0],coupia[1]);
                        //afficheboard();
                        gamepane.setDisable(false);
                        joueur--;
                        resultat = vresult();
                        ecrireresult();
                    }
                }
                //System.out.println("X="+x+" Y="+y+" Joueur="+joueur);
            }
        };

        reecrire(0,0,0);

        iatypecombo.getItems().addAll("Vétéran","Bleu");
        iadifcombo.getItems().addAll("Facile","Moyen","Difficile");

        iatypecombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                iatype = t1;
                notif.setText("");
            }
        });

        iadifcombo.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                iadif = t1;
                notif.setText("");
            }
        });

        iatrainbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((iatypecombo.getValue() != null && !iatypecombo.getValue().toString().isEmpty())&&(iadifcombo.getValue() != null && !iadifcombo.getValue().toString().isEmpty())){
                    if(iatypecombo.getValue().toString().equals("Vétéran")){
                        notif.setText("IA incompatible pour entraînement");
                        /*Test t = new Test();
                        t.main(new String[0]);*/
                        // Pour régénérer Vétéran
                    } else {
                        if(iadifcombo.getValue().toString().equals("Facile")){
                            fichierIA = new File("src/sample/AI/IA/Bleu_Facile");
                            intelligence = new IA(2,0);
                        } else if(iadifcombo.getValue().toString().equals("Moyen")){
                            fichierIA = new File("src/sample/AI/IA/Bleu_Moyen");
                            intelligence = new IA(2,1);
                        } else {
                            fichierIA = new File("src/sample/AI/IA/Bleu_Difficile");
                            intelligence = new IA(2,2);
                        }
                        iavalbtn.setDisable(true);
                        iatrainbtn.setDisable(true);
                        iatypecombo.setDisable(true);
                        iadifcombo.setDisable(true);
                        resettrainbtn.setDisable(true);
                        progressbarupdate(0);

                        Task task = new Task<Void>() {
                            @Override public Void call() {
                                intelligence.doTraining(control);
                                iavalbtn.setDisable(false);
                                iatrainbtn.setDisable(false);
                                iatypecombo.setDisable(false);
                                iadifcombo.setDisable(false);
                                resettrainbtn.setDisable(false);
                                progtrain.setProgress(1);
                                //control.notif.setText("Entraînement terminé");
                                return null;
                            }
                        };
                        new Thread(task).start();




                    }
                } else {
                    notif.setText("Choisissez une IA à entraîner");
                }
            }
        });

        resettrainbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                intelligence.reset();
            }
        });

        iavalbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if ((iatypecombo.getValue() != null && !iatypecombo.getValue().toString().isEmpty())&&(iadifcombo.getValue() != null && !iadifcombo.getValue().toString().isEmpty())){
                    if(iatypecombo.getValue().toString().equals("Vétéran")){
                        if(iadifcombo.getValue().toString().equals("Facile")){
                            fichierIA = new File("src/sample/AI/IA/Veteran_Facile");
                            intelligence = new IA(1,0);
                        } else if(iadifcombo.getValue().toString().equals("Moyen")){
                            fichierIA = new File("src/sample/AI/IA/Veteran_Moyen");
                            intelligence = new IA(1,1);
                        } else {
                            fichierIA = new File("src/sample/AI/IA/Veteran_Difficile");
                            intelligence = new IA(1,2);
                        }
                    } else {
                        if(iadifcombo.getValue().toString().equals("Facile")){
                            fichierIA = new File("src/sample/AI/IA/Bleu_Facile");
                            intelligence = new IA(2,0);
                        } else if(iadifcombo.getValue().toString().equals("Moyen")){
                            fichierIA = new File("src/sample/AI/IA/Bleu_Moyen");
                            intelligence = new IA(2,1);
                        } else {
                            fichierIA = new File("src/sample/AI/IA/Bleu_Difficile");
                            intelligence = new IA(2,2);
                        }
                    }

                    if(fichierIA.isFile()){

                        ft = new FadeTransition(Duration.millis(200), iapane);
                        ft.setFromValue(1);
                        ft.setToValue(0);
                        ft.play();
                        ft = new FadeTransition(Duration.millis(200), optionpane);
                        ft.setFromValue(0);
                        ft.setToValue(1);
                        ft.play();
                        iapane.setDisable(true);
                        optionpane.setDisable(false);



                    } else {
                        notif.setText("Veuillez entraîner l'IA");
                    }
                } else {
                    notif.setText("Choisissez une IA / Difficulté");
                }
            }
        });

    }

    private void reecrire(int j, int x, int y){
        if(j == 1){
            if(x==0){
                if(y==0){
                    ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[0]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgTopLeft);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else if(y==1){
                    ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[1]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgTopCenter);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else {
                    ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[2]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgTopRight);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            } else if(x==1){
                if(y==0){
                    ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[3]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgCenterLeft);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else if(y==1){
                    ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[4]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgCenterCenter);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else {
                    ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[5]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgCenterRight);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            } else {
                if(y==0){
                    ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[6]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgBottomLeft);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else if(y==1){
                    ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[7]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgBottomCenter);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else {
                    ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O.png")));
                    ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[8]=1;
                    ft = new FadeTransition(Duration.millis(200), ImgBottomRight);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            }

        } else if(j == 2) {
            if(x==0){
                if(y==0){
                    ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[0]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgTopLeft);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else if(y==1){
                    ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[1]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgTopCenter);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else {
                    ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[2]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgTopRight);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            } else if(x==1){
                if(y==0){
                    ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[3]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgCenterLeft);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else if(y==1){
                    ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[4]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgCenterCenter);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else {
                    ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[5]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgCenterRight);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            } else {
                if(y==0){
                    ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[6]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgBottomLeft);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else if(y==1){
                    ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[7]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgBottomCenter);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                } else {
                    ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X.png")));
                    ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[8]=2;
                    ft = new FadeTransition(Duration.millis(200), ImgBottomRight);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            }
        } else {

            ft = new FadeTransition(Duration.millis(200), ImgTopLeft);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgTopCenter);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgTopRight);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgCenterLeft);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgCenterCenter);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgCenterRight);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgBottomLeft);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgBottomCenter);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft = new FadeTransition(Duration.millis(200), ImgBottomRight);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();

            ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));
            ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));
            ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));

            ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));
            ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));
            ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));

            ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));
            ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));
            ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/blank.png")));

            ImgTopLeft.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgTopCenter.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgTopRight.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

            ImgCenterLeft.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgCenterCenter.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgCenterRight.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

            ImgBottomLeft.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgBottomCenter.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgBottomRight.addEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

            if(joueur==1){
                result.setText("Tour de Joueur O");
            } else {
                result.setText("Tour de Joueur X");
            }

            for (int i = 0; i < grille.length; i++){
                grille[i]=0;
            }
        }
    }

    private void ecrireresult(){

        if(resultat!=0){
            if(resultat==1){
                deletehandler();
                result.setText("Joueur O gagne !");
            } else if(resultat==2){
                deletehandler();
                result.setText("Joueur X gagne !");
            } else {
                deletehandler();
                result.setText("Match nul !");
            }
        } else {
            if(joueur==1){
                result.setText("Tour de Joueur O");
            } else {
                result.setText("Tour de Joueur X");
            }
        }
    }

    private int vresult(){
        boolean full = true;
        for (int i = 0; i < grille.length; i++){
            if(grille[i]==0){
                full = false;
            }
        }
        if(     ((grille[0]==grille[1])&&(grille[1]==grille[2])&&grille[0]==1)||
                ((grille[0]==grille[3])&&(grille[3]==grille[6])&&grille[0]==1)||
                ((grille[0]==grille[4])&&(grille[4]==grille[8])&&grille[0]==1)||
                ((grille[6]==grille[7])&&(grille[7]==grille[8])&&grille[6]==1)||
                ((grille[2]==grille[5])&&(grille[5]==grille[8])&&grille[2]==1)||
                ((grille[2]==grille[4])&&(grille[4]==grille[6])&&grille[2]==1)||
                ((grille[3]==grille[4])&&(grille[4]==grille[5])&&grille[3]==1)||
                ((grille[1]==grille[4])&&(grille[4]==grille[7])&&grille[1]==1)){

            if((grille[0]==grille[1])&&(grille[1]==grille[2])&&(grille[0]==1)){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[0]==grille[3])&&(grille[3]==grille[6])&&(grille[0]==1)){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[0]==grille[4])&&(grille[4]==grille[8])&&(grille[0]==1)){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[6]==grille[7])&&(grille[7]==grille[8])&&(grille[6]==1)){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[2]==grille[5])&&(grille[5]==grille[8])&&(grille[2]==1)){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[2]==grille[4])&&(grille[4]==grille[6])&&(grille[2]==1)){
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[3]==grille[4])&&(grille[4]==grille[5])&&(grille[3]==1)){
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }
            if((grille[1]==grille[4])&&(grille[4]==grille[7])&&(grille[1]==1)){
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/O_win.png")));
            }

            if(typejoueur==2){
                intelligence.enemyWin();
            }
            return 1;
        }
        if(     ((grille[0]==grille[1])&&(grille[1]==grille[2])&&grille[0]==2)||
                ((grille[0]==grille[3])&&(grille[3]==grille[6])&&grille[0]==2)||
                ((grille[0]==grille[4])&&(grille[4]==grille[8])&&grille[0]==2)||
                ((grille[6]==grille[7])&&(grille[7]==grille[8])&&grille[6]==2)||
                ((grille[2]==grille[5])&&(grille[5]==grille[8])&&grille[2]==2)||
                ((grille[2]==grille[4])&&(grille[4]==grille[6])&&grille[2]==2)||
                ((grille[3]==grille[4])&&(grille[4]==grille[5])&&grille[3]==2)||
                ((grille[1]==grille[4])&&(grille[4]==grille[7])&&grille[1]==2)){

            if((grille[0]==grille[1])&&(grille[1]==grille[2])&&grille[0]==2){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[0]==grille[3])&&(grille[3]==grille[6])&&grille[0]==2){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[0]==grille[4])&&(grille[4]==grille[8])&&grille[0]==2){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[6]==grille[7])&&(grille[7]==grille[8])&&grille[6]==2){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[2]==grille[5])&&(grille[5]==grille[8])&&grille[2]==2){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[2]==grille[4])&&(grille[4]==grille[6])&&grille[2]==2){
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[3]==grille[4])&&(grille[4]==grille[5])&&grille[3]==2){
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            if((grille[1]==grille[4])&&(grille[4]==grille[7])&&grille[1]==2){
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/images/X_win.png")));
            }
            return 2;
        }
        if(full==true){
            return 3;
        }
        return 0;
    }

    private void deletehandler(){
        ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
        ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
        ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

        ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
        ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
        ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

        ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
        ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
        ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
    }

    public void progressbarupdate(double progpercent){
        progtrain.setProgress(progpercent);
        if(progpercent==1){
            notif.setText("Entraînement terminé");
        }
        if(progpercent==0){
            notif.setText(" ");
        }
    }

    private void afficheboard(){
        for (int i = 0; i < grille.length; i++){
            System.out.print(grille[i]+" ");
            if((i==2)||(i==5)){
                System.out.println(" ");
            }
        }
    }

}

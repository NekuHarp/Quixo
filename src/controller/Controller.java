package controller;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Coord;
import model.Coup;
import model.joueur.JoueurHumain;
import model.Partie;
import model.joueur.*;

public class Controller {

    @FXML
    private Pane gamepane;
    @FXML
    private ImageView Img00;
    @FXML
    private ImageView Img10;
    @FXML
    private ImageView Img20;
    @FXML
    private ImageView Img30;
    @FXML
    private ImageView Img40;
    @FXML
    private ImageView Img01;
    @FXML
    private ImageView Img11;
    @FXML
    private ImageView Img21;
    @FXML
    private ImageView Img31;
    @FXML
    private ImageView Img41;
    @FXML
    private ImageView Img02;
    @FXML
    private ImageView Img12;
    @FXML
    private ImageView Img22;
    @FXML
    private ImageView Img32;
    @FXML
    private ImageView Img42;
    @FXML
    private ImageView Img03;
    @FXML
    private ImageView Img13;
    @FXML
    private ImageView Img23;
    @FXML
    private ImageView Img33;
    @FXML
    private ImageView Img43;
    @FXML
    private ImageView Img04;
    @FXML
    private ImageView Img14;
    @FXML
    private ImageView Img24;
    @FXML
    private ImageView Img34;
    @FXML
    private ImageView Img44;

    @FXML
    private ImageView Case00;
    @FXML
    private ImageView Case10;
    @FXML
    private ImageView Case20;
    @FXML
    private ImageView Case30;
    @FXML
    private ImageView Case40;
    @FXML
    private ImageView Case01;
    @FXML
    private ImageView Case11;
    @FXML
    private ImageView Case21;
    @FXML
    private ImageView Case31;
    @FXML
    private ImageView Case41;
    @FXML
    private ImageView Case02;
    @FXML
    private ImageView Case12;
    @FXML
    private ImageView Case22;
    @FXML
    private ImageView Case32;
    @FXML
    private ImageView Case42;
    @FXML
    private ImageView Case03;
    @FXML
    private ImageView Case13;
    @FXML
    private ImageView Case23;
    @FXML
    private ImageView Case33;
    @FXML
    private ImageView Case43;
    @FXML
    private ImageView Case04;
    @FXML
    private ImageView Case14;
    @FXML
    private ImageView Case24;
    @FXML
    private ImageView Case34;
    @FXML
    private ImageView Case44;
    @FXML
    private SplitPane splitpaneforbtn;

    @FXML
    private Button resetbtn;

    @FXML
    private Button changebtn;

    @FXML
    private Label result;

    private boolean iajoueur;

    private ImageView[][] grilleimg = new ImageView[5][5];

    private ImageView[][] grillecase = new ImageView[5][5];

    private Partie game = new Partie(new JoueurHumain(1),new JoueurHumain(2));

    private int joueur;
    private EventHandler<MouseEvent> eventHFirstClick;
    private EventHandler<MouseEvent> eventHSecondClick;
    private EventHandler<MouseEvent> eventHCancelClick;
    private int resultat;
    private Coord selectedcoord = new Coord(0,0);

    public Controller() {
        getInstance();
    }

    // Singleton
    private static Controller instance = new Controller();

    private static Controller getInstance(){
        return instance;
    }

    // Fonction d'initialisation
    @FXML
    private void initialize(){

        // Afin d'éviter qu'on puisse changer la taille du SplitPane qui contient les boutons
        SplitPane.Divider divider = splitpaneforbtn.getDividers().get(0);
        divider.positionProperty().addListener((observable, oldvalue, newvalue) -> divider.setPosition(0.5));

        // On appelle defgrid qui va définir les grilles à leurs valeurs de base
        defgrid();

        // On met joueur 1 en tant que joueur courant
        joueur=game.getJoueur1().getNumber();

        // Par défaut, le joueur 2 n'est pas une IA
        iajoueur = false;

        // On met la fonction sur le bouton de remise à zéro
        resetbtn.setOnAction(e -> {
            reecrire(0,0,0,0);
            result.setText("Tour de Joueur O");
        });

        // On met la fonction sur le bouton de changement de type de joueur
        changebtn.setOnAction(e -> {
            iajoueur=!iajoueur;
            reecrire(0,0,0,0);
            result.setText("Tour de Joueur O");
        });

        // Event de clic de souris sur les cases dans le cas d'une première sélection
        eventHFirstClick = e -> {
            Node node = (Node) e.getSource() ;
            String data = (String) node.getUserData();
            int x = Character.getNumericValue(data.charAt(0));
            int y = Character.getNumericValue(data.charAt(1));
            reecrire(joueur,x,y,0);
        };

        // Event de clic de souris sur les cases dans le cas d'une seconde sélection, pour jouer le coup
        eventHSecondClick = e -> {
            Node node = (Node) e.getSource() ;
            String data = (String) node.getUserData();
            int x = Character.getNumericValue(data.charAt(0));
            int y = Character.getNumericValue(data.charAt(1));
            reecrire(joueur,x,y,1);
            if(!iajoueur) {
                if (joueur == 1) {
                    joueur++;
                } else {
                    joueur--;
                }
                resultat = vresult();
                ecrireresult();
            } else {
                joueur++;
                resultat = vresult();
                ecrireresult();
                if(resultat==0){
                    result.setText("Tour de l'IA X");
                    gamepane.setDisable(true);
                    Coup iamove = game.getJoueur2().determinerCoup(game);
                    gamepane.setDisable(false);
                    reecrire(joueur,iamove.getInitialPos().getX(),iamove.getInitialPos().getY(),0);
                    reecrire(joueur,iamove.getFinalPos().getX(),iamove.getFinalPos().getY(),1);
                    joueur--;
                    resultat = vresult();
                    ecrireresult();
                }
            }
        };

        // Event de clic de souris sur les images dans le cas d'une annulation de première sélection
        eventHCancelClick = e -> {
            Node node = (Node) e.getSource() ;
            String data = (String) node.getUserData();
            int x = Character.getNumericValue(data.charAt(0));
            int y = Character.getNumericValue(data.charAt(1));
            reecrire(joueur,x,y,2);
        };

        // On réécrit la grille sur l'interface
        reecrire(0,0,0, 0);

    }

    // Fonction qui met à jour la vue de la grille dans des cas (events) particuliers
    // Event 0 -> Durant un coup. jo = x -> après que joueur x ait sélectionné la case de base de son coup ; jo = 0 -> début de partie.
    // Event 1 -> jo x a joué son coup, on actualise.
    // Event 2 -> Affichage des coups disponibles au début de chaque tour.
    private void reecrire(int jo, int x, int y, int event){

        deletehandler();
        FadeTransition ft;
        if(event==0) {
            if (jo == 1) {
                resetbackground();
                grillecase[x][y].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_selected.png")));
                ft = new FadeTransition(Duration.millis(200), grillecase[x][y]);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                game.allMoves(new Coord(x,y),game.getJoueur1()).forEach((n) -> {
                    int a = n.getFinalPos().getX();
                    int b = n.getFinalPos().getY();
                    grillecase[a][b].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                    grilleimg[a][b].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHSecondClick);
                });
                grilleimg[x][y].removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHSecondClick);
                grilleimg[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHCancelClick);
                selectedcoord = new Coord(x,y);
            } else if (jo == 2) {
                resetbackground();
                game.allMoves(new Coord(x,y),game.getJoueur2()).forEach((n) -> {
                    int a = n.getFinalPos().getX();
                    int b = n.getFinalPos().getY();
                    grillecase[a][b].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                    grilleimg[a][b].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHSecondClick);
                });
                grillecase[x][y].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_selected.png")));
                grilleimg[x][y].removeEventHandler(MouseEvent.MOUSE_CLICKED, eventHSecondClick);
                grilleimg[x][y].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHCancelClick);

                ft = new FadeTransition(Duration.millis(200), grillecase[x][y]);
                ft.setFromValue(0);
                ft.setToValue(1);
                ft.play();
                selectedcoord = new Coord(x,y);
            } else {
                if(iajoueur){
                    game.setJoueur2(new JoueurIA(2));
                } else {
                    game.setJoueur2(new JoueurHumain(2));
                }
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        ft = new FadeTransition(Duration.millis(200), grilleimg[i][j]);
                        ft.setFromValue(1);
                        ft.setToValue(0);
                        ft.play();

                        grilleimg[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blank.png")));

                        ft = new FadeTransition(Duration.millis(200), grillecase[i][j]);
                        ft.setFromValue(1);
                        ft.setToValue(0);
                        ft.play();

                        grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka.png")));

                        game.getGrille()[i][j] = 0;

                        ft = new FadeTransition(Duration.millis(200), grillecase[i][j]);
                        ft.setFromValue(0);
                        ft.setToValue(1);
                        ft.play();

                        ft = new FadeTransition(Duration.millis(200), grilleimg[i][j]);
                        ft.setFromValue(0);
                        ft.setToValue(1);
                        ft.play();
                    }
                }

                if (joueur == 1) {
                    result.setText("Tour de Joueur O");
                } else {
                    result.setText("Tour de Joueur X");
                }

                joueur = game.getJoueur1().getNumber();

                game.allSelectablePieces(game.getJoueur1()).forEach((n) -> {
                    int i = n.getX();
                    int j = n.getY();
                    grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                    grilleimg[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHFirstClick);
                });


            }
        } else if(event==1){

            if(jo==1){
                game.getJoueur1().jouer(new Coup(selectedcoord,new Coord(x,y)),game);
            } else {
                game.getJoueur2().jouer(new Coup(selectedcoord,new Coord(x,y)),game);
            }
            actualiser();

        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    ft = new FadeTransition(Duration.millis(200), grilleimg[i][j]);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.play();

                    ft = new FadeTransition(Duration.millis(200), grillecase[i][j]);
                    ft.setFromValue(1);
                    ft.setToValue(0);
                    ft.play();

                    grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka.png")));

                    ft = new FadeTransition(Duration.millis(200), grillecase[i][j]);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();

                    ft = new FadeTransition(Duration.millis(200), grilleimg[i][j]);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                }
            }
            if(jo==1) {
                game.allSelectablePieces(game.getJoueur1()).forEach((n) -> {
                    int i = n.getX();
                    int j = n.getY();
                    grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                    grilleimg[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHFirstClick);
                });
            } else {
                game.allSelectablePieces(game.getJoueur2()).forEach((n) -> {
                    int i = n.getX();
                    int j = n.getY();
                    grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                    grilleimg[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHFirstClick);
                });
            }
        }
    }

    // Fonction d'affichage de résultat si victoire, ou d'affichage du tour en cour.
    private void ecrireresult(){

        if(resultat!=0){
            resetbackground();
            deletehandler();
            if(resultat==1){
                result.setText("Joueur O gagne !");
            } else if(resultat==2){
                result.setText("Joueur X gagne !");
            }
        } else {
            if(joueur==1){
                result.setText("Tour de Joueur O");
            } else {
                result.setText("Tour de Joueur X");
            }
        }
    }

    // Fonction de vérification des résultats appelée à chaque fin de tour, elle change aussi la couleur des cases qui ont permis la victoire.
    private int vresult(){

        double[][] grille = game.getGrille();

        if(game.checkwin()==1){

            if(((grille[0][0]==grille[0][1])&&(grille[0][1]==grille[0][2])&&(grille[0][2]==grille[0][3])&&(grille[0][3]==grille[0][4]))&&grille[0][0]==1){
                Img00.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img01.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img02.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img03.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img04.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[1][0]==grille[1][1])&&(grille[1][1]==grille[1][2])&&(grille[1][2]==grille[1][3])&&(grille[1][3]==grille[1][4]))&&grille[1][0]==1){
                Img10.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img11.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img12.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img13.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img14.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[2][0]==grille[2][1])&&(grille[2][1]==grille[2][2])&&(grille[2][2]==grille[2][3])&&(grille[2][3]==grille[2][4]))&&grille[2][0]==1){
                Img20.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img21.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img23.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img24.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[3][0]==grille[3][1])&&(grille[3][1]==grille[3][2])&&(grille[3][2]==grille[3][3])&&(grille[3][3]==grille[3][4]))&&grille[3][0]==1){
                Img30.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img31.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img32.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img33.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img34.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[4][0]==grille[4][1])&&(grille[4][1]==grille[4][2])&&(grille[4][2]==grille[4][3])&&(grille[4][3]==grille[4][4]))&&grille[4][0]==1){
                Img40.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img41.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img42.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img43.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img44.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }

            if(((grille[0][0]==grille[1][0])&&(grille[1][0]==grille[2][0])&&(grille[2][0]==grille[3][0])&&(grille[3][0]==grille[4][0]))&&grille[0][0]==1){
                Img00.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img10.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img20.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img30.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img40.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[0][1]==grille[1][1])&&(grille[1][1]==grille[2][1])&&(grille[2][1]==grille[3][1])&&(grille[3][1]==grille[4][1]))&&grille[0][1]==1){
                Img01.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img11.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img21.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img31.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img41.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[0][2]==grille[1][2])&&(grille[1][2]==grille[2][2])&&(grille[2][2]==grille[3][2])&&(grille[3][2]==grille[4][2]))&&grille[0][2]==1){
                Img02.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img12.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img32.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img42.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[0][3]==grille[1][3])&&(grille[1][3]==grille[2][3])&&(grille[2][3]==grille[3][3])&&(grille[3][3]==grille[4][3]))&&grille[0][3]==1){
                Img03.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img13.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img23.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img33.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img43.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[0][4]==grille[1][4])&&(grille[1][4]==grille[2][4])&&(grille[2][4]==grille[3][4])&&(grille[3][4]==grille[4][4]))&&grille[0][4]==1){
                Img04.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img14.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img24.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img34.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img44.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }

            if(((grille[0][0]==grille[1][1])&&(grille[1][1]==grille[2][2])&&(grille[2][2]==grille[3][3])&&(grille[3][3]==grille[4][4]))&&grille[0][0]==1){
                Img00.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img11.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img33.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img44.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
            if(((grille[0][4]==grille[1][3])&&(grille[1][3]==grille[2][2])&&(grille[2][2]==grille[3][1])&&(grille[3][1]==grille[4][0]))&&grille[0][4]==1){
                Img04.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img13.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img31.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
                Img40.setImage(new Image(getClass().getResourceAsStream("/view/images/O_win.png")));
            }
        }
        if(game.checkwin()==2){

            if(((grille[0][0]==grille[0][1])&&(grille[0][1]==grille[0][2])&&(grille[0][2]==grille[0][3])&&(grille[0][3]==grille[0][4]))&&grille[0][0]==2){
                Img00.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img01.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img02.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img03.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img04.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[1][0]==grille[1][1])&&(grille[1][1]==grille[1][2])&&(grille[1][2]==grille[1][3])&&(grille[1][3]==grille[1][4]))&&grille[1][0]==2){
                Img10.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img11.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img12.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img13.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img14.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[2][0]==grille[2][1])&&(grille[2][1]==grille[2][2])&&(grille[2][2]==grille[2][3])&&(grille[2][3]==grille[2][4]))&&grille[2][0]==2){
                Img20.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img21.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img23.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img24.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[3][0]==grille[3][1])&&(grille[3][1]==grille[3][2])&&(grille[3][2]==grille[3][3])&&(grille[3][3]==grille[3][4]))&&grille[3][0]==2){
                Img30.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img31.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img32.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img33.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img34.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[4][0]==grille[4][1])&&(grille[4][1]==grille[4][2])&&(grille[4][2]==grille[4][3])&&(grille[4][3]==grille[4][4]))&&grille[4][0]==2){
                Img40.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img41.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img42.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img43.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img44.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }

            if(((grille[0][0]==grille[1][0])&&(grille[1][0]==grille[2][0])&&(grille[2][0]==grille[3][0])&&(grille[3][0]==grille[4][0]))&&grille[0][0]==2){
                Img00.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img10.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img20.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img30.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img40.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[0][1]==grille[1][1])&&(grille[1][1]==grille[2][1])&&(grille[2][1]==grille[3][1])&&(grille[3][1]==grille[4][1]))&&grille[0][1]==2){
                Img01.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img11.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img21.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img31.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img41.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[0][2]==grille[1][2])&&(grille[1][2]==grille[2][2])&&(grille[2][2]==grille[3][2])&&(grille[3][2]==grille[4][2]))&&grille[0][2]==2){
                Img02.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img12.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img32.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img42.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[0][3]==grille[1][3])&&(grille[1][3]==grille[2][3])&&(grille[2][3]==grille[3][3])&&(grille[3][3]==grille[4][3]))&&grille[0][3]==2){
                Img03.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img13.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img23.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img33.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img43.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[0][4]==grille[1][4])&&(grille[1][4]==grille[2][4])&&(grille[2][4]==grille[3][4])&&(grille[3][4]==grille[4][4]))&&grille[0][4]==2){
                Img04.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img14.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img24.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img34.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img44.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }

            if(((grille[0][0]==grille[1][1])&&(grille[1][1]==grille[2][2])&&(grille[2][2]==grille[3][3])&&(grille[3][3]==grille[4][4]))&&grille[0][0]==2){
                Img00.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img11.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img33.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img44.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
            if(((grille[0][4]==grille[1][3])&&(grille[1][3]==grille[2][2])&&(grille[2][2]==grille[3][1])&&(grille[3][1]==grille[4][0]))&&grille[0][4]==2){
                Img04.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img13.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img22.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img31.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
                Img40.setImage(new Image(getClass().getResourceAsStream("/view/images/X_win.png")));
            }
        }

        return game.checkwin();
    }

    // Fonction pour supprimer les events de clic de souris sur les cases, par exemple quand une partie est terminée.
    private void deletehandler(){

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grilleimg[i][j].removeEventHandler(MouseEvent.MOUSE_CLICKED,eventHFirstClick);
                grilleimg[i][j].removeEventHandler(MouseEvent.MOUSE_CLICKED,eventHSecondClick);
                grilleimg[i][j].removeEventHandler(MouseEvent.MOUSE_CLICKED,eventHCancelClick);
            }
        }
    }

    // Fonction pour actualiser la grille visuelle, change aussi l'affichage pour que le joueur sache quelles cases sont sélectionnables.
    private void actualiser(){
        double[][] grille = game.getGrille();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if(grille[i][j]==0){
                    grilleimg[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blank.png")));
                } else if (grille[i][j]==1){
                    grilleimg[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/O.png")));
                } else {
                    grilleimg[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/X.png")));
                }
                grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka.png")));
            }
        }
        if(joueur==1){
            game.allSelectablePieces(game.getJoueur2()).forEach((n) -> {
                int i = n.getX();
                int j = n.getY();
                grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                grilleimg[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHFirstClick);
            });
        } else {
            game.allSelectablePieces(game.getJoueur1()).forEach((n) -> {
                int i = n.getX();
                int j = n.getY();
                grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka_available.png")));
                grilleimg[i][j].addEventHandler(MouseEvent.MOUSE_CLICKED, eventHFirstClick);
            });
        }

    }

    // Fonction qui remet toutes les cases du fond en gris, "non sélectionnables".
    private void resetbackground(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grillecase[i][j].setImage(new Image(getClass().getResourceAsStream("/view/images/blanka.png")));
            }
        }
    }

    // Fonction de définition des grilles.
    private void defgrid(){

        grilleimg[0][0]=Img00;
        grilleimg[0][1]=Img01;
        grilleimg[0][2]=Img02;
        grilleimg[0][3]=Img03;
        grilleimg[0][4]=Img04;
        grilleimg[1][0]=Img10;
        grilleimg[1][1]=Img11;
        grilleimg[1][2]=Img12;
        grilleimg[1][3]=Img13;
        grilleimg[1][4]=Img14;
        grilleimg[2][0]=Img20;
        grilleimg[2][1]=Img21;
        grilleimg[2][2]=Img22;
        grilleimg[2][3]=Img23;
        grilleimg[2][4]=Img24;
        grilleimg[3][0]=Img30;
        grilleimg[3][1]=Img31;
        grilleimg[3][2]=Img32;
        grilleimg[3][3]=Img33;
        grilleimg[3][4]=Img34;
        grilleimg[4][0]=Img40;
        grilleimg[4][1]=Img41;
        grilleimg[4][2]=Img42;
        grilleimg[4][3]=Img43;
        grilleimg[4][4]=Img44;

        grillecase[0][0]=Case00;
        grillecase[0][1]=Case01;
        grillecase[0][2]=Case02;
        grillecase[0][3]=Case03;
        grillecase[0][4]=Case04;
        grillecase[1][0]=Case10;
        grillecase[1][1]=Case11;
        grillecase[1][2]=Case12;
        grillecase[1][3]=Case13;
        grillecase[1][4]=Case14;
        grillecase[2][0]=Case20;
        grillecase[2][1]=Case21;
        grillecase[2][2]=Case22;
        grillecase[2][3]=Case23;
        grillecase[2][4]=Case24;
        grillecase[3][0]=Case30;
        grillecase[3][1]=Case31;
        grillecase[3][2]=Case32;
        grillecase[3][3]=Case33;
        grillecase[3][4]=Case34;
        grillecase[4][0]=Case40;
        grillecase[4][1]=Case41;
        grillecase[4][2]=Case42;
        grillecase[4][3]=Case43;
        grillecase[4][4]=Case44;
    }

}

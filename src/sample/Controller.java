package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Controller {
    Main.data d;
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
    private Label result;

    private int[] grille = new int[]{ 0,0,0, 0,0,0, 0,0,0 };
    private int joueur = 1;
    private EventHandler<MouseEvent> eventH;
    private int resultat;

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
        @Override public void handle(ActionEvent e) {
            reecrire(0,0,0);
            joueur=1;
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
                /*ScaleTransition st = new ScaleTransition(Duration.millis(200), ImgTopLeft);
                st.setFromX(1);
                st.setToX(1.2);
                st.play();*/
                String data = (String) node.getUserData();
                int x= Character.getNumericValue(data.charAt(0));
                int y= Character.getNumericValue(data.charAt(1));
                reecrire(joueur,x,y);
                if(joueur==1){
                    joueur++;
                } else {
                    joueur--;
                }
                System.out.println("X="+x+" Y="+y+" Joueur="+joueur);

                resultat = vresult();

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
        };

        reecrire(0,0,0);




    }

    private void reecrire(int j, int x, int y){
        if(j == 1){
            if(x==0){
                if(y==0){
                    ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[0]=1;
                } else if(y==1){
                    ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[1]=1;
                } else {
                    ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[2]=1;
                }
            } else if(x==1){
                if(y==0){
                    ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[3]=1;
                } else if(y==1){
                    ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[4]=1;
                } else {
                    ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[5]=1;
                }
            } else {
                if(y==0){
                    ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[6]=1;
                } else if(y==1){
                    ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[7]=1;
                } else {
                    ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[8]=1;
                }
            }

        } else if(j == 2) {
            if(x==0){
                if(y==0){
                    ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[0]=2;
                } else if(y==1){
                    ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[1]=2;
                } else {
                    ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[2]=2;
                }
            } else if(x==1){
                if(y==0){
                    ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[3]=2;
                } else if(y==1){
                    ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[4]=2;
                } else {
                    ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[5]=2;
                }
            } else {
                if(y==0){
                    ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[6]=2;
                } else if(y==1){
                    ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[7]=2;
                } else {
                    ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                    grille[8]=2;
                }
            }
        } else {

            deletehandler();

            ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));
            ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));
            ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));

            ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));
            ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));
            ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));

            ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));
            ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));
            ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/blank.png")));

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

    // 0 1 2
    // 3 4 5
    // 6 7 8


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

            if((grille[0]==grille[1])&&(grille[1]==grille[2])){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[0]==grille[3])&&(grille[3]==grille[6])){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[0]==grille[4])&&(grille[4]==grille[8])){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[6]==grille[7])&&(grille[7]==grille[8])){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[2]==grille[5])&&(grille[5]==grille[8])){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[2]==grille[4])&&(grille[4]==grille[6])){
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[3]==grille[4])&&(grille[4]==grille[5])){
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
            }
            if((grille[1]==grille[4])&&(grille[4]==grille[7])){
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O_win.png")));
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

            if((grille[0]==grille[1])&&(grille[1]==grille[2])){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[0]==grille[3])&&(grille[3]==grille[6])){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[0]==grille[4])&&(grille[4]==grille[8])){
                ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[6]==grille[7])&&(grille[7]==grille[8])){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[2]==grille[5])&&(grille[5]==grille[8])){
                ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[2]==grille[4])&&(grille[4]==grille[6])){
                ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[3]==grille[4])&&(grille[4]==grille[5])){
                ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
            }
            if((grille[1]==grille[4])&&(grille[4]==grille[7])){
                ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
                ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X_win.png")));
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

}

package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

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
    private int joueur = 1;
    private EventHandler<MouseEvent> eventH;

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
        }
    });
        eventH = new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                Node node = (Node) e.getSource() ;

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
            }
        };

        reecrire(0,0,0);




    }

    private void reecrire(int joueur, int x, int y){
        if(joueur == 1){
            if(x==0){
                if(y==0){
                    ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else if(y==1){
                    ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else {
                    ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                }
            } else if(x==1){
                if(y==0){
                    ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else if(y==1){
                    ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else {
                    ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                }
            } else {
                if(y==0){
                    ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else if(y==1){
                    ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else {
                    ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/O.png")));
                    ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                }
            }

        } else if(joueur == 2) {
            if(x==0){
                if(y==0){
                    ImgTopLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else if(y==1){
                    ImgTopCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else {
                    ImgTopRight.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                }
            } else if(x==1){
                if(y==0){
                    ImgCenterLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else if(y==1){
                    ImgCenterCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else {
                    ImgCenterRight.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                }
            } else {
                if(y==0){
                    ImgBottomLeft.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else if(y==1){
                    ImgBottomCenter.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                } else {
                    ImgBottomRight.setImage(new Image(getClass().getResourceAsStream("/sample/X.png")));
                    ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
                }
            }
        } else {
            ImgTopLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgTopCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgTopRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

            ImgCenterLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgCenterCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgCenterRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);

            ImgBottomLeft.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgBottomCenter.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);
            ImgBottomRight.removeEventHandler(MouseEvent.MOUSE_CLICKED,eventH);


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
        }
    }

}

package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
        EventHandler<MouseEvent> eventH = new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent e) {
                Node node = (Node) e.getSource() ;
                String data = (String) node.getUserData();
                int x= Character.getNumericValue(data.charAt(0));
                int y= Character.getNumericValue(data.charAt(1));
                System.out.println(x+" "+y);
                if(d.getPlayed(x,y)==2)
                {
                    System.out.println("Good !");
                }
            }
        };
        ImgTopLeft.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgTopCenter.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgTopRight.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));

        ImgCenterLeft.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgCenterCenter.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgCenterRight.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));

        ImgBottomLeft.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgBottomCenter.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgBottomRight.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));

        ImgTopLeft.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgTopCenter.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));
        ImgTopRight.setImage(new Image("http://s3rg3-36k.pagesperso-orange.fr/GAME/Morpion/img/case_croix.png"));

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

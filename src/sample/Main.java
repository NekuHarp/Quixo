package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sun.rmi.runtime.Log;


public class Main extends Application {
    data d=new data();
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "sample.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        primaryStage.setTitle("Morpion-kun");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.setResizable(false);

        d.test();
        System.out.println(controller==null);
        controller.changeData(d);
        d.test2();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
    class data
    {
        int[][] board = new int[3][3];
        data()
        {
           for(int i=0;i<board.length;i++)
            {
                for(int j=0;j<board[i].length;j++)
                {
                    board[i][j]=0;
                }
            }
            System.out.println("safe constructor");
        }
        public void test()
        {
            System.out.println("safe");
            board[0][0]=1;
            board[1][1]=1;
            board[2][2]=2;
        }
        public void test2()
        {
            System.out.println("safe");
            board[0][1]=2;
        }
        public int getPlayed(int x,int y)
        {
            if((x>2)||(y>2))
            {
                return -1;
            }
            return board[x][y];
        }
    }
}

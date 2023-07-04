package Controller;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
   
    //0XY
    private double x = 0, y = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginAndRegister.fxml"));

        Scene scene = new Scene(root);
       
        
       //stage.initStyle(StageStyle.UNDECORATED);
      
    
        root.setOnMousePressed((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8);
        });

        root.setOnMouseReleased((event) -> {
            stage.setOpacity(1);
        });
       
        stage.setScene(scene);
        stage.show(); 
    }
    

    public static void main(String[] args) {
        launch(args);
        
    }

}

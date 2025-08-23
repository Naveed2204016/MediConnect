package mediconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class
main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/welcome.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("MediConnect");
        Image image =new Image(getClass().getResource("/images/1583625.png").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


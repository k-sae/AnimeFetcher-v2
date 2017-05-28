package AnimeFetcher;

import AnimeFetcher.Grabber.AddAnime.AddAnimeGrabber;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new GridPane();
        primaryStage.setTitle("Anime Fetcher");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //TODO
        // Saver to test the grabber


    }

    public static void main(String[] args) {
        launch(args);
    }
}

package AnimeFetcher;

import AnimeFetcher.Grabber.AddAnime.AddAnimeGrabber;
import AnimeFetcher.Grabber.JSParser;
import AnimeFetcher.Grabber.Progress;
import AnimeFetcher.Grabber.ProgressListener;
import com.oracle.xmlns.internal.webservices.jaxws_databinding.JavaMethod;
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
        AddAnimeGrabber addAnimeGrabber = new AddAnimeGrabber();
        addAnimeGrabber.enQueueAnimeLink("http://add-anime.net/video/82AD3YD7ONBN/One-Piece-790");
        addAnimeGrabber.enQueueAnimeLink("http://add-anime.net/video/HOGXY9SMAXD4/One-Piece-399");
        addAnimeGrabber.getDownloader().addProgressListener(new ProgressListener() {
            @Override
            public void reportProgress(Progress progress) {
                System.out.println(progress.getPercentage());
            }

            @Override
            public void onFinish() {
            }
        });
        addAnimeGrabber.startGrabbing();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

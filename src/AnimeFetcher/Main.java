package AnimeFetcher;

import AnimeFetcher.Model.Config;
import AnimeFetcher.View.MainFrame;
import AnimeFetcher.View.ThemeManager.DarkTheme;
import AnimeFetcher.View.ThemeManager.ThemeManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static ThemeManager themeManager;
    public  static Config config;
    public static ThemeManager getThemeManager() {
        return themeManager;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = new MainFrame();
        primaryStage.setTitle("Anime Fetcher");
        primaryStage.setScene(new Scene(root, 900, 400));
        primaryStage.show();
        //TODO
        // Saver to test the grabber


    }

    public static void main(String[] args) {
        final String CONFIG_FILE = "config.txt";
        themeManager = new DarkTheme();
        config = new Config();
        config.importFromFile(CONFIG_FILE);
        config.exportToFile(CONFIG_FILE);
        launch(args);
    }
}

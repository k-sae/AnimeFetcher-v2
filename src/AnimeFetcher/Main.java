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

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        final String CONFIG_FILE = "config.txt";
        themeManager = new DarkTheme();
        Config config = Config.getInstance();
        if(args.length < 1)
        config.importFromFile(CONFIG_FILE);
        else
            config.importFromFile(args[0]);
        launch(args);
    }
}

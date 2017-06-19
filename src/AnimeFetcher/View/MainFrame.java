package AnimeFetcher.View;

import AnimeFetcher.Main;
import AnimeFetcher.View.AddAnime.AddAnimeViewer;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 30/05/2017.
 */

/** UI VISUAL TREE
 *                  VBox    | -> HBox (title bar)
 *                          | -> HBox (content) | -> vBox
 *                                              | -> Pane (anime Content pane)
 */
public class MainFrame extends VBox {
    private final int LEFT_TABS_SIZE = 200;
    private HBox animeContentPane;

    public MainFrame()
    {

         setBackground(new Background(new BackgroundFill(Color.valueOf("#000"), CornerRadii.EMPTY, Insets.EMPTY)));
         setTitleBar();
         setContent();
         addTabsPane();
         setAnimeContentPane(new AddAnimeViewer());
    }
    private void setTitleBar()
    {
        HBox hBox = new HBox();
        hBox.setBackground(new Background(new BackgroundFill(Color.valueOf(Main.getThemeManager().getTitleBarBackground()), CornerRadii.EMPTY, Insets.EMPTY)));
        hBox.setMinHeight(80);
        getChildren().add(hBox);
    }

    private void setContent()
    {
        animeContentPane = new HBox();
        VBox.setVgrow(animeContentPane, Priority.ALWAYS);
        getChildren().add(animeContentPane);
    }
    private void addTabsPane()
    {
        VBox vBox = new VBox();
        vBox.setBackground(new Background(new BackgroundFill(Color.valueOf("#0f0"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setMinWidth(LEFT_TABS_SIZE);
        animeContentPane.getChildren().add(vBox);
    }
    private void setAnimeContentPane(Pane content)
    {
        HBox.setHgrow(content, Priority.ALWAYS);
        animeContentPane.getChildren().add(content);
    }
}

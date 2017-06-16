package AnimeFetcher.View.AddAnime;

import AnimeFetcher.Grabber.AddAnime.AddAnimeGrabber;
import AnimeFetcher.Grabber.ProgressListener;
import AnimeFetcher.Main;
import AnimeFetcher.Model.AddAnimeAnime;
import AnimeFetcher.Model.Anime;
import AnimeFetcher.View.DownloadBar;
import AnimeFetcher.View.HelperUIComponents.AutoScrollTextArea;
import AnimeFetcher.View.HelperUIComponents.NumericTextField;
import AnimeFetcher.View.HelperUIComponents.SearchBar;
import com.sun.tracing.dtrace.FunctionName;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by kemo on 31/05/2017.
 */

/** UI VISUAL TREE
 *                  VBox    |  BorderPane   |  left    -> HBox     | -> ComboBox anime_list
 *                                                                          | -> NumericTextFiend startEp
 *                                                                          | -> NumericTextFiend endEp
 *
 *                                          |  Right   -> HBox     | -> Button Download_btn
                            |
 *
 */
public class AddAnimeViewer extends VBox implements EventHandler<MouseEvent>, ChangeListener<ArrayList<AddAnimeAnime>>{
    AddAnimeGrabber addAnimeGrabber;
    public AddAnimeViewer() {
        initializePrompts();
       setBackground(new Background(new BackgroundFill(Color.valueOf(Main.getThemeManager().getAnimeWebsiteContent()), CornerRadii.EMPTY, Insets.EMPTY)));
        addAnimeGrabber = new AddAnimeGrabber() {
            @Override
            public void onAnimeListUpdateFailure() {
                if (statusTextArea!= null)
                {
                    statusTextArea.addTextLine("Updating AnimeList failed\nretrying...");
                }
            }
        };
        addAnimeGrabber.setOnListChangeListener(this);
        addAnimeGrabber.updateAnimeList();
        addDownloadBar();
        addStatusBar();
    }
    private SearchBar<AddAnimeAnime> animeList;
    private NumericTextField startEp;
    private NumericTextField endEp;
    private final int MARGIN_VALUE = 10;
    private AutoScrollTextArea statusTextArea;
    private void initializePrompts()
    {
        animeList = new SearchBar<>();
        animeList.setMaxWidth(200);
        startEp = initNumericTextField();
        endEp = initNumericTextField();
        //Left part
        HBox lHBox = new HBox();
        lHBox.getChildren().addAll(animeList, initLabel("starting Ep: "),startEp, initLabel("ending Ep: "),endEp);
        //Right part
        HBox rHBox = new HBox();
        //init downloadButton with appropriate listeners
        Button  button  = new Button("Download");
        button.setOnMouseClicked(this);
        rHBox.getChildren().addAll(button);
        //assign them to borderPane
        BorderPane borderPane= new BorderPane();
        borderPane.setLeft(lHBox);
        borderPane.setRight(rHBox);
        getChildren().add(borderPane);
    }
    private Label initLabel(String text)
    {
        Label label = new Label(text);
        label.setTextFill(Color.valueOf(Main.getThemeManager().getForeground()));
        label.setPadding(new Insets(2,MARGIN_VALUE,0,MARGIN_VALUE));
        return label;
    }
    private NumericTextField initNumericTextField()
    {
        NumericTextField child = new NumericTextField("1");
        child.setPrefWidth(50);
        HBox.setMargin(child,new Insets(0,0,0,10));
        child.setBackground(new Background
                (new BackgroundFill(Color.valueOf(Main.getThemeManager().getInputPromptBackground()),
                        CornerRadii.EMPTY,
                        Insets.EMPTY)));
        child.setStyle("-fx-text-inner-color: " + Main.getThemeManager().getInputPromptForeground() + ";" );
        return child;
    }
    private void addDownloadBar()
    {
        DownloadBar downloadBar = new DownloadBar(addAnimeGrabber.getDownloader());
        getChildren().add(downloadBar);
    }
    private void addStatusBar()
    {
        statusTextArea = new AutoScrollTextArea();
        statusTextArea.setTextRec("Initializing...");
        statusTextArea.addTextLine("updating Anime lists...");
        VBox.setMargin(statusTextArea, new Insets(20));
        VBox.setVgrow(statusTextArea, Priority.ALWAYS);
        getChildren().add(statusTextArea);
    }

    @Override
    public void handle(MouseEvent event) {
        //TODO
//        AddAnimeAnime addAnimeAnime = animeList.getSelectedItem();
        int endEpNo = Integer.valueOf(endEp.getText());
        int startEpNo = Integer.valueOf(startEp.getText());
        for (int i = startEpNo; i <= endEpNo ; i++){
            //Remove this from here
//            addAnimeAnime.setUrl("http://add-anime.net/next_episode.php?last=" + getEpisodeNo(i - 1) + "&cat=" +addAnimeAnime.getId() + ",");
            addAnimeGrabber.enQueueAnimeLink(new Anime("asdasd", "http://add-anime.net/next_episode.php?last=" + getEpisodeNo(i - 1) + "&cat=" +"110" + ",", i +""));
        }
        addAnimeGrabber.startGrabbing();
    }
    private String getEpisodeNo(int num)
    {
        if (num < 10)
        {
            return "00" + num;
        }
        else if(num < 100)
        {
            return  "0" + num;
        }
        else return num + "";
    }

    @FunctionName("AnimeListChanged")
    @Override
    public void changed(ObservableValue<? extends ArrayList<AddAnimeAnime>> observable, ArrayList<AddAnimeAnime> oldValue, ArrayList<AddAnimeAnime> newValue) {
        animeList.setSearchItems(newValue);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statusTextArea.addTextLine("Anime list updated");
                }
            });
    }

}
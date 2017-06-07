package AnimeFetcher.View.AddAnime;

import AnimeFetcher.Grabber.AddAnime.AddAnimeGrabber;
import AnimeFetcher.Grabber.Downloader.Progress;
import AnimeFetcher.Grabber.Downloader.ProgressListener;
import AnimeFetcher.Main;
import AnimeFetcher.Model.AddAnimeAnime;
import AnimeFetcher.View.DownloadBar;
import AnimeFetcher.View.NumericTextField;
import AnimeFetcher.View.SearchBar;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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
public class AddAnimeViewer extends VBox implements EventHandler<MouseEvent> {
    AddAnimeGrabber addAnimeGrabber;
    public AddAnimeViewer() {
        initializePrompts();
       setBackground(new Background(new BackgroundFill(Color.valueOf(Main.getThemeManager().getAnimeWebsiteContent()), CornerRadii.EMPTY, Insets.EMPTY)));
        addAnimeGrabber = new AddAnimeGrabber();
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
        addDownloadBar();
    }
    private SearchBar<AddAnimeAnime> animeList;
    private NumericTextField startEp;
    private NumericTextField endEp;
    private final int MARGIN_VALUE = 10;
    private ArrayList<AddAnimeAnime> addAnimeAnimes;
    private boolean isSelectedAnime;
    private void initializePrompts()
    {
        addAnimeAnimes = new ArrayList<>();
        animeList = new SearchBar<>();
        addAnimeAnimes.add(new AddAnimeAnime("one", "124"));
        addAnimeAnimes.add(new AddAnimeAnime("one piece", "123"));
        addAnimeAnimes.add(new AddAnimeAnime("ran shit 1", "123"));
        addAnimeAnimes.add(new AddAnimeAnime("rand shit 2", "123"));
        addAnimeAnimes.add(new AddAnimeAnime("one", "124"));
        addAnimeAnimes.add(new AddAnimeAnime("one piece", "123"));
        addAnimeAnimes.add(new AddAnimeAnime("ran shit 1", "123"));
        addAnimeAnimes.add(new AddAnimeAnime("rand shit 2", "123"));
        animeList.setSearchItems(addAnimeAnimes);
        startEp = initNumericTextField();
        endEp = initNumericTextField();
        HBox lHBox = new HBox();
        lHBox.getChildren().addAll(animeList,startEp,endEp);
        VBox.setMargin(lHBox, new Insets(MARGIN_VALUE));
        HBox rHBox = new HBox();
        Button  button  = new Button("Download");
        button.setOnMouseClicked(this);
        rHBox.getChildren().addAll(button);
        BorderPane borderPane= new BorderPane();
        borderPane.setLeft(lHBox);
        borderPane.setRight(rHBox);
        getChildren().add(borderPane);
    }
    private NumericTextField initNumericTextField()
    {
        NumericTextField child = new NumericTextField();
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

    @Override
    public void handle(MouseEvent event) {
        //TODO
        System.out.println(animeList.getSelectedItem() != null ? animeList.getSelectedItem().getId() : "null :)");
    }
}
package AnimeFetcher.View.AddAnime;

import AnimeFetcher.Grabber.AddAnime.AddAnimeGrabber;
import AnimeFetcher.Main;
import AnimeFetcher.Model.AddAnimeAnime;
import AnimeFetcher.Model.Anime;
import AnimeFetcher.View.DownloadBar;
import AnimeFetcher.View.HelperUIComponents.NumericTextField;
import AnimeFetcher.View.HelperUIComponents.SearchBar;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
public class AddAnimeViewer extends VBox implements EventHandler<MouseEvent>, ChangeListener<ArrayList<AddAnimeAnime>> {
    AddAnimeGrabber addAnimeGrabber;
    public AddAnimeViewer() {
        initializePrompts();
       setBackground(new Background(new BackgroundFill(Color.valueOf(Main.getThemeManager().getAnimeWebsiteContent()), CornerRadii.EMPTY, Insets.EMPTY)));
        addAnimeGrabber = new AddAnimeGrabber();
        addAnimeGrabber.setOnListChangeListener(this);
        addAnimeGrabber.updateAnimeList();
        addDownloadBar();
    }
    private SearchBar<AddAnimeAnime> animeList;
    private NumericTextField startEp;
    private NumericTextField endEp;
    private final int MARGIN_VALUE = 10;
    private void initializePrompts()
    {
        animeList = new SearchBar<>();
        animeList.setMaxWidth(200);
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

    @Override
    public void handle(MouseEvent event) {
        //TODO
        AddAnimeAnime addAnimeAnime = animeList.getSelectedItem();
        int endEpNo = Integer.valueOf(endEp.getText());
        int startEpNo = Integer.valueOf(startEp.getText());
        for (int i = startEpNo; i <= endEpNo ; i++){
            //Remove this from her
            addAnimeAnime.setUrl("http://add-anime.net/next_episode.php?last=" + getEpisodeNo(i - 1) + "&cat=" +addAnimeAnime.getId() + ",");
            addAnimeGrabber.enQueueAnimeLink(new Anime(addAnimeAnime.getName(), addAnimeAnime.getUrl(), i +""));
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

    @Override
    public void changed(ObservableValue<? extends ArrayList<AddAnimeAnime>> observable, ArrayList<AddAnimeAnime> oldValue, ArrayList<AddAnimeAnime> newValue) {
        animeList.setSearchItems(newValue);
    }
}
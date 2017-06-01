package AnimeFetcher.View;

import AnimeFetcher.Model.AddAnimeAnime;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 31/05/2017.
 */

/** UI VISUAL TREE
 *                  GridPane    | row1 -> BorderPane |  left    -> HBox     | -> ComboBox anime_list
 *                                                                          | -> NumericTextFiend startEp
 *                                                                          | -> NumericTextFiend endEp
 *
 *                                                   |  Right   -> HBox     | -> Button Download_btn
 *                              | row2 ->
 *
 */
public class AddAnimeViewer extends GridPane implements EventHandler<MouseEvent> {
    public AddAnimeViewer() {
        setConstrains();
        initializePrompts();
    }
    private ComboBox<AddAnimeAnime> animeList;
    private NumericTextField startEp;
    private NumericTextField endEp;
    private final int MARGIN_VALUE = 5;
    private void setConstrains() {
        final int SIZE = 4;
        for (int i = 0; i < SIZE ; i++) {
            getRowConstraints().addAll(new RowConstraints());
        }
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        getColumnConstraints().add(columnConstraints);

    }
    private void setLayout()
    {

    }
    private void initializePrompts()
    {
//        final int NO_OF_ITEMS  = 4;
//        GridPane gridPane = new GridPane();
//        for (int i = 0; i < NO_OF_ITEMS; i++) {
//            ColumnConstraints columnConstraints = new ColumnConstraints();
//            columnConstraints.setPercentWidth(100 / NO_OF_ITEMS);
//            gridPane.getColumnConstraints().add(columnConstraints);
//        }

        animeList = new ComboBox<>();
        animeList.getItems().add(new AddAnimeAnime("one piece", "123"));
        animeList.getItems().add(new AddAnimeAnime("one", "123"));
        animeList.setEditable(true);
        animeList.setVisibleRowCount(5);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        animeList.show(); //call this from other thread :)
                    }
                });
            }
        }).start();
        animeList.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                animeList.getEditor().setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });

        animeList.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(animeList.getSelectionModel().getSelectedItem());
            }
        });
        startEp = initNumericTextField();
        endEp = initNumericTextField();
        HBox lHBox = new HBox();
        lHBox.getChildren().addAll(animeList,startEp,endEp);
        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(lHBox);
        GridPane.setMargin(borderPane, new Insets(MARGIN_VALUE));
        add(borderPane,0,0);
//        gridPane.add(animeList,0,0);
//        add(gridPane,0,0);
//        gridPane.add(startEp,1,0);
//        gridPane.add(endEp,2,0);
    }
    private NumericTextField initNumericTextField()
    {
        NumericTextField child = new NumericTextField();
        child.setPrefWidth(50);
        HBox.setMargin(child,new Insets(0,0,0,10));
        return child;
    }

    @Override
    public void handle(MouseEvent event) {
        //TODO
        System.out.println(":)");
    }
}
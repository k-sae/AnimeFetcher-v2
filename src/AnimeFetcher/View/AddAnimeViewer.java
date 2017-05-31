package AnimeFetcher.View;

import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 31/05/2017.
 */
public class AddAnimeViewer extends GridPane {
    public AddAnimeViewer() {
        setConstrains();
    }
    private void setConstrains() {
        final int SIZE = 4;
        RowConstraints[] rowConstraints = new RowConstraints[SIZE];
        for (int i = 0; i < SIZE ; i++) {
            rowConstraints[i] = new RowConstraints();
        }
        getRowConstraints().addAll( rowConstraints);
    }
}
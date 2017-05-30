package AnimeFetcher.View;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 30/05/2017.
 */
public class MainFrame extends GridPane {
    public MainFrame()
    {
         setBackground(new Background(new BackgroundFill(Color.valueOf("#000"), CornerRadii.EMPTY, Insets.EMPTY)));
         setConstrains();
         addTabsPane();
    }
    private void setConstrains(){
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        getColumnConstraints().addAll(new ColumnConstraints(150), columnConstraints);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        getRowConstraints().addAll(new RowConstraints(100), rowConstraints);
//        StackPane stackPane = new StackPane();
//        stackPane.setBackground(new Background(new BackgroundFill(Color.valueOf("#f00"), CornerRadii.EMPTY, Insets.EMPTY)));
//        StackPane stackPane2 = new StackPane();
//        stackPane2.setBackground(new Background(new BackgroundFill(Color.valueOf("#0f0"), CornerRadii.EMPTY, Insets.EMPTY)));

    }
    private void addTabsPane()
    {
        StackPane stackPane3 = new StackPane();
        stackPane3.setBackground(new Background(new BackgroundFill(Color.valueOf("#00f"), CornerRadii.EMPTY, Insets.EMPTY)));
        add(stackPane3, 0,1);
    }
}

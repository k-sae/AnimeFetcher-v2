package AnimeFetcher.View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 07/06/2017.
 * responsive customizable Progressbar instead of the shitty one provided with javafx
 */
public class ProgressBar extends HBox implements ChangeListener<Number> {
    private double progress;
    private Pane progressIndicator;
    public ProgressBar(double progress) {
        this();
        this.progress = progress;
    }
    public ProgressBar() {
        progressIndicator = new StackPane();
        widthProperty().addListener(this);
        progressIndicator.setBackground(new Background(new BackgroundFill(Color.valueOf("#f00"), CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().add(progressIndicator);
    }
    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
        progressIndicator.setPrefWidth(widthProperty().get() * progress);
    }
    public void setProgressIndicatorColor(Color color)
    {
        progressIndicator.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        progressIndicator.setPrefWidth(newValue.doubleValue() * progress);
    }
}

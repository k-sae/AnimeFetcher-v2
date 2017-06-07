package AnimeFetcher.View;

import AnimeFetcher.Grabber.Downloader.Downloader;
import AnimeFetcher.Grabber.Downloader.Progress;
import AnimeFetcher.Grabber.Downloader.ProgressListener;
import AnimeFetcher.Main;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 07/06/2017.
 */
public class DownloadBar extends HBox implements ProgressListener {
    private Downloader downloader;
    private ProgressBar progressBar;
    private Label percentage;
    private Label timeRemaining;
    private Label fileName;
    private Label speed;
    public DownloadBar(Downloader downloader )
    {
        this.downloader = downloader;
        initialize();
        setLayout();
        downloader.addProgressListener(this);
    }
    public void initialize()
    {
        progressBar = new ProgressBar();
        percentage = new Label("0%");
        timeRemaining = new Label("0m");
        fileName = new Label(downloader.getFileName());
        speed = new Label("0kb");
    }
    private void setLayout()
    {
        progressBar.setBackground(new Background(new BackgroundFill(Color.valueOf("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        getChildren().addAll(fileName, percentage, progressBar, timeRemaining, speed);
    }

    @Override
    public void reportProgress(Progress progress) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(Double.valueOf(progress.getPercentage().split("%")[0])/100);
                percentage.setText(progress.getPercentage());
            }
        });
    }

    @Override
    public void onFinish() {

    }
}

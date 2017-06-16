package AnimeFetcher.View;

import AnimeFetcher.Grabber.Downloader.Downloader;
import AnimeFetcher.Grabber.Downloader.Progress;
import AnimeFetcher.Grabber.Downloader.DownloadProgressListener;
import AnimeFetcher.View.HelperUIComponents.ProgressBar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by kemo on 07/06/2017.
 */
public class DownloadBar extends HBox implements DownloadProgressListener {
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
        speed = new Label("0kb/s");
    }
    private void setLayout()
    {
        progressBar.setBackground(new Background(new BackgroundFill(Color.valueOf("#fff"), CornerRadii.EMPTY, Insets.EMPTY)));
        HBox.setHgrow(progressBar, Priority.ALWAYS);
        timeRemaining.setPrefWidth(50);
        speed.setPrefWidth(50);
        getChildren().addAll(fileName, percentage, progressBar, timeRemaining, speed);
        for (Node node: getChildren()
             ) {
            HBox.setMargin(node, new Insets(5));
        }
    }

    @Override
    public void onStart() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fileName.setText(downloader.getFileName());
            }
        });
    }

    @Override
    public void reportProgress(Progress progress) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(Double.valueOf(progress.getPercentage().split("%")[0])/100);
                percentage.setText(progress.getPercentage());
                speed.setText(progress.getSpeed() + "/s");
                timeRemaining.setText(progress.getTimeRemaining());
            }
        });
    }

    @Override
    public void onFinish() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressBar.setProgress(100);
                percentage.setText("100%");
                timeRemaining.setText("0s");
            }
        });
    }

    @Override
    public void onFail() {

    }
}

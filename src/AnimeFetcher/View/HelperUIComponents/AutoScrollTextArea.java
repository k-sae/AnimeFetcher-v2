package AnimeFetcher.View.HelperUIComponents;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;

/**
 * Created by kemo on 09/06/2017.
 */
public class AutoScrollTextArea extends TextArea implements ChangeListener<String> {
    private StringProperty textRec = new SimpleStringProperty();
    public AutoScrollTextArea()
    {
        textProperty().bind(textRec);
        textRec.addListener(this);
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        setScrollTop(getLength());
                    }
                });

            }
        }).start();
    }
    public void addTextLine(String text)
    {
        textRec.setValue(textRec.getValue() +  "\n" + text);
    }
    public void setTextRec(String text)
    {
        textRec.setValue(text);
    }
}

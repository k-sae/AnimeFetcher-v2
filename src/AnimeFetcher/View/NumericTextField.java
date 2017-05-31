package AnimeFetcher.View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

/**
 * Created by kemo on 01/06/2017.
 */
public class NumericTextField extends TextField {
    public NumericTextField()
    {
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }
}

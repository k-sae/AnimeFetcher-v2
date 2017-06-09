package AnimeFetcher.View.HelperUIComponents;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

/**
 * Created by kemo on 01/06/2017.
 */
public class NumericTextField extends TextField {
    public NumericTextField(String text) {
        super(text);
        init();
    }

    public NumericTextField()
    {
     init();
    }
    public void init()
    {
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }
}

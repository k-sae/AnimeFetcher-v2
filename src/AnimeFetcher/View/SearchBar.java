package AnimeFetcher.View;

import com.sun.tracing.dtrace.FunctionName;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;

/**
 * Created by kemo on 03/06/2017.
 */
public class SearchBar<T> extends ComboBox<T> implements ChangeListener<String>, EventHandler<ActionEvent> {
    private ArrayList<T> searchItems;
    private boolean isChecked;
    private ArrayList<T> items;
    public SearchBar()
    {
        items = new ArrayList<>();
        setEditable(true);
        setVisibleRowCount(5);
        getEditor().textProperty().addListener(this);
        setOnAction(this);
        isChecked = false;
    }

    @FunctionName("textChanged")
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                updateSearchItemsWithTokens(newValue);
                if (!isChecked) {
                    getItems().clear();
                    getItems().addAll(items);
                } else isChecked = false;
                show();
            }
        });

    }

    public ArrayList<T> getSearchItems() {
        return searchItems;
    }

    public void setSearchItems(ArrayList<T> searchItems) {
        this.searchItems = searchItems;
    }

    @FunctionName("ItemChanged")
    @Override
    public void handle(ActionEvent event) {
        isChecked = true;
        System.out.println(getSelectionModel().getSelectedIndex());
    }

    private void updateSearchItemsWithTokens(String token)
    {
        items.clear();
        if (searchItems == null) return;
        for (int i = 0; i < searchItems.size() ; i++) {
            if (searchItems.get(i).toString().contains(token))
            {
                items.add(searchItems.get(i));
            }
        }
    }
    public T getSelectedItem()
    {
        try {
            return items.get( getSelectionModel().getSelectedIndex());
        }catch (Exception e){
            return null;
        }
    }
}

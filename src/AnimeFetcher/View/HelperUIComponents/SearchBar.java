package AnimeFetcher.View.HelperUIComponents;

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
    private boolean caseSensitive;
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
                if (!isChecked) {
                    updateSearchItemsWithTokens(newValue);
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
    }

    private void updateSearchItemsWithTokens(String token)
    {
        items.clear();
        if (searchItems == null) return;
        for (int i = 0; i < searchItems.size() ; i++) {
            if (compare(searchItems.get(i).toString(), token))
            {
                items.add(searchItems.get(i));
            }
        }
    }
    private boolean compare(String item, String token)
    {
        if (!caseSensitive)return item.toLowerCase().contains(token.toLowerCase());
        return item.contains(token);
    }
    public T getSelectedItem()
    {
        try {
            return items.get( getSelectionModel().getSelectedIndex());
        }catch (Exception e){
            return null;
        }
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
}

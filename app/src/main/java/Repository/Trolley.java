package Repository;

import android.util.Log;

import java.util.List;

import Model.TrolleyItem;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class Trolley {
    private List<TrolleyItem> items;

    public Trolley(List<TrolleyItem> items) {
        this.items = items;
    }

    public Trolley() {
    }

    public List<TrolleyItem> getItems() {
        return items;
    }

    public void setItems(List<TrolleyItem> items) {
        this.items = items;
    }

    public void add(TrolleyItem item) {
        items.add(item);
        Log.d("Trolley", "Added item" +item.getName());
    }

    public void remove(TrolleyItem item) {
        items.remove(item);
    }
}

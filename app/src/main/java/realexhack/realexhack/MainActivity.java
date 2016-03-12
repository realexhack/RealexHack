package realexhack.realexhack;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.TrolleyItem;
import Repository.Trolley;

public class MainActivity extends BaseActivity {
    public static Trolley trolley;
    public static Map<String, TrolleyItem> stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trolley = new Trolley(new ArrayList<TrolleyItem>());
        stock = new HashMap<String, TrolleyItem>();
        populateStocks();
        setContentView(R.layout.activity_main);
    }

    private void populateStocks() {
        stock.put("5449000028921", new TrolleyItem(1, "coke", 2,5));
    }
}

package Controller;
import java.util.ArrayList;
import android.app.Application;

import Model.TrolleyItem;
import Repository.Trolley;

/**
 * Created by ram on 12/03/2016.
 */

public class TrolleyController extends Application{

    private  ArrayList<TrolleyItem> trolleyItems = new ArrayList<TrolleyItem>();
    private Trolley myCart =  Trolley.getInstance();


    public TrolleyItem getTrolleyItem(int pPosition) {

        return trolleyItems.get(pPosition);
    }

    public void setTrolleyItem(TrolleyItem trolleyItem) {

        trolleyItems.add(trolleyItem);

    }

    public Trolley getTrolley() {

        return myCart;

    }

    public int getTrolleySize() {

        return trolleyItems.size();
    }

}
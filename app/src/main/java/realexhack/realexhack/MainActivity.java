package realexhack.realexhack;
//package com.realexpayments.hpp;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Model.TrolleyItem;
import Repository.Trolley;

import com.realexpayments.hpp.*;

public class MainActivity extends BaseActivity implements HPPManagerListener {
    public static Trolley trolley;
    public static Map<String, TrolleyItem> stock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        trolley = new Trolley(new ArrayList<TrolleyItem>());
        stock = new HashMap<String, TrolleyItem>();
        populateStocks();
        setContentView(R.layout.activity_main);
        TestRealex();
    }

    protected void TestRealex() {
        Bundle args = new Bundle();
        args.putString(HPPManager.HPPREQUEST_PRODUCER_URL, "https://realex.herokuapp.com/Request.php");
        args.putString(HPPManager.HPPRESPONSE_CONSUMER_URL, "https://realex.herokuapp.com/Response.php");
        args.putString(HPPManager.HPPURL, "https://hpp.test.realexpayments.com/pay");
        args.putString(HPPManager.MERCHANT_ID, "realexsandbox");
        args.putString(HPPManager.AMOUNT, "100");
        args.putString(HPPManager.CURRENCY, "EUR");
        args.putString(HPPManager.ACCOUNT, "internet");

        HPPManager hppManager = new HPPManager();
        hppManager = hppManager.createFromBundle(args);
        Fragment hppManagerFragment = hppManager.newInstance();
        getFragmentManager()
                .beginTransaction()
                .add(R.id.container, hppManagerFragment,"hppManagerFragment")
                .commit();


    }


    @Override
    public void hppManagerCompletedWithResult(Object o) {
        Log.d("completed","completed");
        Toast.makeText(getApplicationContext(),
                "Payment Successful", Toast.LENGTH_LONG).show();
        Fragment f = getFragmentManager().findFragmentByTag("hppManagerFragment");
        if(f!=null) getFragmentManager()
                .beginTransaction().remove(f).commit();



    }

    @Override
    public void hppManagerFailedWithError(HPPError error) {
        //something went wrong
    }

    @Override
    public void hppManagerCancelled() { //operation was canceled
    }

    private void populateStocks() {
        stock.put("5449000028921", new TrolleyItem(1, "coke", 2,5));
    }
}

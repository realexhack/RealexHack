package realexhack.realexhack;
//package com.realexpayments.hpp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import Model.TrolleyItem;
import Repository.Trolley;

import com.postquantum.pqcheck.android.PQCheckActivity;
import com.postquantum.pqcheck.clientlib.response.ApiKey;
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
        //TestRealex();
        enrol("enrol","012345");
    }

    private void enrol(String enrolmentLink, String transcript) {
        Intent intent = new Intent(this, PQCheckActivity.class);

        // Set the action ACTION_ENROL.
        intent.setAction(PQCheckActivity.ACTION_ENROL);

        // Set the enrolment link as the data URI.
        intent.setData(Uri.parse(enrolmentLink));

        intent.putExtra(PQCheckActivity.EXTRA_USER_IDENTIFIER, "627b197f-10ca-42b2-b733-d121948347b5");

        intent.putExtra(PQCheckActivity.EXTRA_REFERENCE, "string");

        // Add the transcript the user will have to read.
        intent.putExtra(PQCheckActivity.EXTRA_TRANSCRIPT, transcript);

        intent.putExtra(PQCheckActivity.EXTRA_API_KEY, new ApiKey(UUID.fromString("627b097f-10ca-42b2-b733-d121948347b5")
                , "ThCe8ggd4G0afu0Qw/hIJmnzriig3TNCfWNuvSB1jBgsFDbqcNV4qQepEmAjYFkXJ7m9pNdR1kR5Vo+OkFsR8g=="));

                // MY_ENROLMENT_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(intent, 1234);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1234) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // We have completed the enrolment process successfully.
                    Log.d("Enrolment completed","Enrolment completed");
                    Toast.makeText(getApplicationContext(),
                            "Enrolment Successful", Toast.LENGTH_LONG).show();
                    break;
                case PQCheckActivity.RESULT_CLIENT_ERROR:
                case PQCheckActivity.RESULT_SERVER_ERROR:
                    // An error ocurred. For more details, check EXTRA_RESULT_ERROR_MESSAGE.
                    Bundle extras = data.getExtras();
                    String error = extras.getString(PQCheckActivity.EXTRA_RESULT_ERROR_MESSAGE);
                    Log.d("error",error);
                    Toast.makeText(getApplicationContext(),
                            "Enrolment Error", Toast.LENGTH_LONG).show();
                    break;
            }
        }
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

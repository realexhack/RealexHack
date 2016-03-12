package realexhack.realexhack;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import Model.TrolleyItem;
import Repository.Trolley;
import com.postquantum.pqcheck.android.PQCheckActivity;
import com.postquantum.pqcheck.clientlib.response.ApiKey;
import com.realexpayments.hpp.*;

import java.util.UUID;

public class ListMainActivity extends BaseActivity  implements HPPManagerListener{

    ListView list;
    CustomAdapter adapter;
    public  ListMainActivity CustomListView = null;
    Trolley trolley = Trolley.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmainview);

        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        //setListData();

        Resources res =getResources();
        list= ( ListView )findViewById( R.id.list );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter( CustomListView, trolley.getItems(),res );
        list.setAdapter( adapter );

    }

   /* *//****** Function to set data in ArrayList *************//*
    public void setListData()
    {

        for (int i = 0; i < 11; i++) {

            final ListModel sched = new ListModel();

            *//******* Firstly take data in model object ******//*
            sched.setCompanyName("Company "+i);
            sched.setImage("image"+i);
            sched.setUrl("http:\\www."+i+".com");

            *//******** Take Model Object in ArrayList **********//*
            CustomListViewValuesArr.add( sched );
        }

    }*/

    public void checkout_btn_onClick(View view){
        enrol("enrol", "012345");
        //TestRealex();

        //MainActivity.enrol(this, "enrol", "012345");
    }

    private void enrol(String enrolmentLink, String transcript) {
        Intent intent = new Intent(this, PQCheckActivity.class);

        // Set the action ACTION_ENROL.
        intent.setAction(PQCheckActivity.ACTION_ENROL);

        // Set the enrolment link as the data URI.
        //intent.setData(Uri.parse(enrolmentLink));

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
                    Log.d("Enrolment completed", "Enrolment completed");
                    Toast.makeText(getApplicationContext(),
                            "Enrolment Successful", Toast.LENGTH_LONG).show();
                    //android.os.SystemClock.sleep(10000);
                    TestRealex();

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
                .add(R.id.container1, hppManagerFragment,"hppManagerFragment1")
                .commit();


    }


    @Override
    public void hppManagerCompletedWithResult(Object o) {
        Log.d("completed", "completed");
        Toast.makeText(getApplicationContext(),
                "Payment Successful", Toast.LENGTH_LONG).show();
        Fragment f = getFragmentManager().findFragmentByTag("hppManagerFragment1");
        if(f!=null) getFragmentManager()
                .beginTransaction().remove(f).commit();



    }

    @Override
    public void hppManagerFailedWithError(HPPError error) {
        //something went wrong
        Log.d("HPPManager","Error");
    }

    @Override
    public void hppManagerCancelled() { //operation was canceled
        Log.d("HPPManager","Cancelled");
    }
    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        TrolleyItem tempValues = ( TrolleyItem ) trolley.getItems().get(mPosition);


        // SHOW ALERT

        Toast.makeText(CustomListView,
                "" + tempValues.getName()
                       ,
        Toast.LENGTH_LONG)
        .show();
    }
}

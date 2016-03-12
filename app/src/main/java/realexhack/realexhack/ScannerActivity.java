package realexhack.realexhack;


import android.os.Bundle;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Model.TrolleyItem;
import Repository.Trolley;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class ScannerActivity extends BaseActivity implements OnClickListener {
    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    String scanContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(v.toString(), "onclick");
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
        if(v.getId() == R.id.scan_add) {
            addToTrolley();
        }
    }

    private void addToTrolley() {
        String quantityString = ((EditText) findViewById(R.id.scan_quantity)).getText().toString();
        TrolleyItem item = MainActivity.stock.get(scanContent);
        int quantity = Integer.parseInt(quantityString);
        item.setQuantity(quantity);
        Trolley trolley = Trolley.getInstance();
        trolley.add(item);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//retrieve scan result
        setIntent(intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
//we have a result
            scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);
            addToTrolley();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

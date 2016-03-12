package realexhack.realexhack;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.Utils;

import java.util.List;
import java.util.UUID;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class MapActivity extends BaseActivity {

    private String treasureX = "11692:10260";
    private BeaconManager beaconManager;
    private Region region;
    private Beacon oldBeacon;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        imageView = (ImageView)findViewById(R.id.imageView1);
        imageView.setBackgroundColor(Color.rgb(144, 1144, 144));
        Log.d("MapActivity", "In Map Activity");
        displayTemperature();
        region = new Region("ranged region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> list) {
                if (!list.isEmpty()) {
                    final Beacon nearestBeacon = list.get(0);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Just in case if there are multiple beacons with the same uuid, major, minor.
                            Beacon foundBeacon = null;
                            for (Beacon rangedBeacon : list) {
                                if (treasureX.equals(getId(rangedBeacon))) {
                                    foundBeacon = rangedBeacon;
                                }
                            }
                            if (foundBeacon != null) {
                                updateDistanceView(foundBeacon);
                            }
                        }
                    });
                }
            }
        });
    }

    private void updateDistanceView(Beacon foundBeacon) {
        double prevDistance = 0;
        if (oldBeacon != null) {
            prevDistance = Utils.computeAccuracy(oldBeacon)*100;
        }
        double currentDistance = Utils.computeAccuracy(foundBeacon)*100;
        oldBeacon = foundBeacon;

        TextView tv = (TextView)findViewById(R.id.distanceView);
        tv.setText("Distance "+ Utils.computeAccuracy(foundBeacon));
        if(prevDistance > currentDistance) {
            double diff = currentDistance - prevDistance;
            ColorDrawable drawable = (ColorDrawable) imageView.getBackground();
            int r,b,g,c, newR, newB;
            c = drawable.getColor();
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            newR = (int) (r+diff);
            newB = (int) (b-diff);
            imageView.setColorFilter(Color.rgb(newR, g, newB));
            imageView.setBackgroundColor(Color.rgb(newR, g, newB));


        } else {
//            goBlue();
            double diff = currentDistance - prevDistance;
            ColorDrawable drawable = (ColorDrawable) imageView.getBackground();
            int r,b,g,c, newR, newB;
            c = drawable.getColor();
            r = Color.red(c);
            g = Color.green(c);
            b = Color.blue(c);
            newR = (int) (r-diff);
            newB = (int) (b+diff);
            imageView.setColorFilter(Color.rgb(newR, g, newB));
            imageView.setBackgroundColor(Color.rgb(newR, g, newB));
        }
    }


    @Override
    protected void onStop() {
        beaconManager.disconnect();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SystemRequirementsChecker.checkWithDefaultDialogs(this);

        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });
    }

    @Override
    protected void onPause() {
        beaconManager.stopRanging(region);

        super.onPause();
    }

    private String getId(Beacon beacon) {
        String id = beacon.getMajor() + ":" + beacon.getMinor();
        Log.d("MapActivity", "id "+ id);
        return id;
    }

    private void displayTemperature() {

    }
}

package realexhack.realexhack;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.MacAddress;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;

import java.util.List;
import java.util.UUID;

import Util.MyBeacon;
import Util.MyNearable;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class MapActivity extends BaseActivity {

    private String treasureX = "11692:10260";
    private BeaconManager beaconManager;
    private Region region;

    public static final String EXTRAS_TARGET_ACTIVITY = "extrasTargetActivity";
    public static final String EXTRAS_BEACON = "extrasBeacon";
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ImageView view = (ImageView)findViewById(R.id.imageView1);
        view.setColorFilter(Color.RED);
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
        Beacon beacon = foundBeacon;
        float prevDistance = getDistanceFromColour();
        float currentDistance = beacon.getRssi()/beacon.getMeasuredPower();;
        if(prevDistance > currentDistance) {
//            goRed();
        } else {
//            goBlue();
        }
    }

    private float getDistanceFromColour() {
        return 0;
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

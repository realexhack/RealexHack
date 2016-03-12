package realexhack.realexhack;

import android.app.Application;

import com.estimote.sdk.BeaconManager;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class MyApplication extends Application {
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());

    }
}

package Util;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.MacAddress;

import java.util.UUID;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class MyBeacon extends Beacon {
    public MyBeacon(UUID proximityUUID, MacAddress macAddress, int major, int minor, int measuredPower, int rssi) {
        super(proximityUUID, macAddress, major, minor, measuredPower, rssi);
    }

    public float getDistance() {
       return getRssi()/getMeasuredPower();
    }
}

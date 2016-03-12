package Util;

import android.os.Parcel;

import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;
import com.estimote.sdk.cloud.model.BroadcastingPower;

/**
 * Created by josekalladanthyil on 12/03/16.
 */
public class MyNearable extends Nearable{
    public MyNearable(String identifier, Region region, FirmwareState firmwareState, String hardwareVersion, String firmwareVersion, String bootloaderVersion, double temperature, int rssi, boolean isMoving, double xAcceleration, double yAcceleration, double zAcceleration, long currentMotionStateDuration, long lastMotionStateDuration, BatteryLevel batteryLevel, BroadcastingPower power) {
        super(identifier, region, firmwareState, hardwareVersion, firmwareVersion, bootloaderVersion, temperature, rssi, isMoving, xAcceleration, yAcceleration, zAcceleration, currentMotionStateDuration, lastMotionStateDuration, batteryLevel, power);
    }

    protected MyNearable(Parcel in) {
        super(in);
    }

    public float getDistance() {
        return rssi/power.powerInDbm;
    }
}

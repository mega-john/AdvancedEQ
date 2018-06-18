package cs2c.EQ;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.cs2c.IEQService;
import android.util.Log;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

public class EQBroadcastReceiver extends BroadcastReceiver {
    private static int balanceDivisorX = 1;
    private static int balanceDivisorY = 1;
    private static int increaseValue = 0;
    private int backLight = 0;
    private int coordinateX;
    private int coordinateY;
    private int mainVolume = 0;
    private SharedPreferences preferences;
    private int lowFreqSBProgressValue = 0;
    private int middleFreqSBProgressValue = 0;
    private int highFreqSBProgressValue = 0;
    private int lowVoiceSBProgressValue = 0;
    private int middleVoiceSBProgressValue = 0;
    private int highVoiceSBProgressValue = 0;

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.preferences = context.getSharedPreferences(Constants.EQSettingsFileName, 2);
        EQServiceProxy.Initialize(context);

        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            int diffX;
            int diffY;
            this.lowFreqSBProgressValue = this.preferences.getInt("lowFreqSBProgressValue", 1);
            this.middleFreqSBProgressValue = this.preferences.getInt("middleFreqSBProgressValue", 1);
            this.highFreqSBProgressValue = this.preferences.getInt("highFreqSBProgressValue", 0);
            this.lowVoiceSBProgressValue = this.preferences.getInt("lowVoiceSBProgressValue", 7);
            this.middleVoiceSBProgressValue = this.preferences.getInt("middleVoiceSBProgressValue", 7);
            this.highVoiceSBProgressValue = this.preferences.getInt("highVoiceSBProgressValue", 7);
            initLimitValue();
            if (EQActivity.width > 800) {
                this.coordinateX = this.preferences.getInt("CoordinateX", 155);
                this.coordinateY = this.preferences.getInt("CoordinateY", 180);
                System.out.println("coordinateX  " + this.coordinateX);
                System.out.println("coordinateY  " + this.coordinateY);
                diffX = ((this.coordinateX * 10) - 100) / balanceDivisorX;
                diffY = (345 - this.coordinateY) / balanceDivisorY;
            } else {
                this.coordinateX = this.preferences.getInt("CoordinateX", 155);
                this.coordinateY = this.preferences.getInt("CoordinateY", 180);
                System.out.println("coordinateX  " + this.coordinateX);
                System.out.println("coordinateY  " + this.coordinateY);
                diffX = ((this.coordinateX * 10) - 100) / balanceDivisorX;
                diffY = (260 - this.coordinateY) / balanceDivisorY;
            }
            setXY(diffX, diffY);
            try {
                EQServiceProxy.setSound(Constants.cTrebleCommand, this.highVoiceSBProgressValue);
                EQServiceProxy.setSound(Constants.cMiddleCommand, this.middleVoiceSBProgressValue);
                EQServiceProxy.setSound(Constants.cBassCommand, this.lowVoiceSBProgressValue);
                EQServiceProxy.setSound(Constants.cHighFreqSB, this.highFreqSBProgressValue);
                EQServiceProxy.setSound(Constants.cMiddleFreqSB, this.middleFreqSBProgressValue);
                EQServiceProxy.setSound(Constants.cLowFreqSB, this.lowFreqSBProgressValue);
                increaseValue = this.preferences.getInt("increase_value_key", 2);
                System.out.println(increaseValue);
                System.out.println("preferences.getAll().size()  " + this.preferences.getAll().size());
                EQServiceProxy.setSound(Constants.cIncreaseValueCommand, increaseValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.equals("cn.com.cs2c.android.vehicle.action.EQ_KEY")) {
            Intent it = new Intent();
            it.setClass(context, EqualizerFragment.class);
            it.addFlags(FLAG_CANCEL_CURRENT);
            context.startActivity(it);
            Log.d("lzc", "receiver EQ key");
        }
    }

    private void initLimitValue() {
        if (EQActivity.width > 800) {
            balanceDivisorX = 116;
            balanceDivisorY = 13;
            return;
        }
        balanceDivisorX = 88;
        balanceDivisorY = 10;
    }

    public int checkItem(int value) {
        if (value > 24) {
            return 24;
        }
        if (value < 0) {
            return 0;
        }
        return value;
    }

    public void setXY(int x, int y) {
        try {
            EQServiceProxy.setSurround(checkItem(x), 24 - checkItem(y));
            System.out.println("-------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
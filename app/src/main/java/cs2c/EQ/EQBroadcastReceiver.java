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
    private static int increaseValue = 0;

    private SharedPreferences preferences;
    private int sb_lowFreqProgress = 0;
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
            this.sb_lowFreqProgress = this.preferences.getInt("sb_lowFreqProgress", 1);
            this.middleFreqSBProgressValue = this.preferences.getInt("middleFreqSBProgressValue", 1);
            this.highFreqSBProgressValue = this.preferences.getInt("highFreqSBProgressValue", 0);
            this.lowVoiceSBProgressValue = this.preferences.getInt("lowVoiceSBProgressValue", 7);
            this.middleVoiceSBProgressValue = this.preferences.getInt("middleVoiceSBProgressValue", 7);
            this.highVoiceSBProgressValue = this.preferences.getInt("highVoiceSBProgressValue", 7);

            try {
                EQServiceProxy.setSound(Constants.cTrebleCommand, this.highVoiceSBProgressValue);
                EQServiceProxy.setSound(Constants.cMiddleCommand, this.middleVoiceSBProgressValue);
                EQServiceProxy.setSound(Constants.cBassCommand, this.lowVoiceSBProgressValue);
                EQServiceProxy.setSound(Constants.cHighFreqSB, this.highFreqSBProgressValue);
                EQServiceProxy.setSound(Constants.cMiddleFreqSB, this.middleFreqSBProgressValue);
                EQServiceProxy.setSound(Constants.cLowFreqSB, this.sb_lowFreqProgress);
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
}
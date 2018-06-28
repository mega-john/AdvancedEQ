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

    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.preferences = context.getSharedPreferences(Constants.EQSettingsFileName, 2);
        EQServiceProxy.Initialize(context);

        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            int defGain = 7;
            int maxGain = 14;
            int sbPreampG = this.preferences.getInt(Constants.sb_preampGProgress, defGain);
            int sbBassG = this.preferences.getInt(Constants.sb_bassGProgress, defGain);
            int sbMiddleG = this.preferences.getInt(Constants.sb_middleGProgress, defGain);
            int sbTrebleG = this.preferences.getInt(Constants.sb_trebleGProgress, defGain);
            int sbLoudG = this.preferences.getInt(Constants.sb_LoudGProgress, defGain);
            int sbBassQ = this.preferences.getInt(Constants.sb_bassQProgress, 0);
            int sbBassF = this.preferences.getInt(Constants.sb_bassFoProgress, 0);
            int sbMiddleQ = this.preferences.getInt(Constants.sb_middleQProgress, 0);
            int sbMiddleF = this.preferences.getInt(Constants.sb_middleFoProgress, 0);
            int sbTrebleQ = this.preferences.getInt(Constants.sb_trebleQProgress, 0);
            int sbTrebleF = this.preferences.getInt(Constants.sb_trebleFoProgress, 0);
            boolean cbLoudOn = this.preferences.getBoolean(Constants.cb_LoudOn, true);

            try {
                int gainValue = (sbBassG > maxGain ? maxGain : sbBassG);
                EQServiceProxy.setSound(Constants.cBassCommand, gainValue);
                EQServiceProxy.setSound(Constants.cBassQFCommand, sbBassF << 4 + sbBassQ);

                gainValue = (sbMiddleG > maxGain ? maxGain : sbMiddleG);
                EQServiceProxy.setSound(Constants.cMiddleCommand, gainValue);
                EQServiceProxy.setSound(Constants.cMiddleQFCommand, sbMiddleF << 4 + sbMiddleQ);

                gainValue = (sbTrebleG > maxGain ? maxGain : sbTrebleG);
                EQServiceProxy.setSound(Constants.cTrebleCommand, gainValue);
                EQServiceProxy.setSound(Constants.cTrebleQFCommand, sbTrebleF << 4 + sbTrebleQ);

                EQServiceProxy.set_volume(Constants.cSubwooferGainCommand, sbLoudG);

                EQServiceProxy.set_volume(Constants.cLoudOnOffCommand, cbLoudOn ? 1 : 0);

                EQServiceProxy.setSound(Constants.cHighFreqSB, 7);
                EQServiceProxy.setSound(Constants.cMiddleFreqSB, 7);
                EQServiceProxy.setSound(Constants.cLowFreqSB, 14);

                int increaseValue = this.preferences.getInt("increase_value_key", 2);
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
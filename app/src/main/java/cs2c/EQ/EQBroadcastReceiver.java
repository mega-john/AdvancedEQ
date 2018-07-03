package cs2c.EQ;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.content.Context.MODE_WORLD_WRITEABLE;

public class EQBroadcastReceiver extends BroadcastReceiver {

    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.preferences = context.getSharedPreferences(Constants.EQSettingsFileName, MODE_WORLD_WRITEABLE);
        EQServiceProxy.Initialize(context);

        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            int defGain = context.getResources().getInteger(R.integer.eq_gain_default);
            int maxGain = context.getResources().getInteger(R.integer.eq_gain_max);
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
                EQServiceProxy.setSound(Commands.BassGain, gainValue);
                EQServiceProxy.setSound(Commands.BassQF, sbBassF << 4 + sbBassQ);

                gainValue = (sbMiddleG > maxGain ? maxGain : sbMiddleG);
                EQServiceProxy.setSound(Commands.MiddleGain, gainValue);
                EQServiceProxy.setSound(Commands.MiddleQF, sbMiddleF << 4 + sbMiddleQ);

                gainValue = (sbTrebleG > maxGain ? maxGain : sbTrebleG);
                EQServiceProxy.setSound(Commands.TrebleGain, gainValue);
                EQServiceProxy.setSound(Commands.TrebleQF, sbTrebleF << 4 + sbTrebleQ);

                EQServiceProxy.set_volume(Commands.SubwooferGain, sbLoudG);

                EQServiceProxy.set_volume(Commands.LoudOnOff, cbLoudOn ? 1 : 0);

                EQServiceProxy.setSound(Commands.HighFreqSB, defGain);
                EQServiceProxy.setSound(Commands.MiddleFreqSB, defGain);
                EQServiceProxy.setSound(Commands.LowFreqSB, maxGain);

                int increaseValue = this.preferences.getInt("increase_value_key", 2);
                EQServiceProxy.setSound(Commands.IncreaseValue, increaseValue);
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
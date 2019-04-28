package cs2c.EQ;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.UserHandle;
import android.util.Log;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.content.Context.MODE_WORLD_WRITEABLE;

public class EQBroadcastReceiver extends BroadcastReceiver {

    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.preferences = context.getSharedPreferences(cs2c.EQ.Constants.EQSettingsFileName, MODE_WORLD_WRITEABLE);
        cs2c.EQ.EQServiceProxy.Initialize(context);

        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            int defGain = context.getResources().getInteger(R.integer.eq_gain_default);
            int maxGain = context.getResources().getInteger(R.integer.eq_gain_max);
            int sbPreampG = this.preferences.getInt(cs2c.EQ.Constants.sb_preampGProgress, defGain);
            int sbBassG = this.preferences.getInt(cs2c.EQ.Constants.sb_bassGProgress, defGain);
            int sbMiddleG = this.preferences.getInt(cs2c.EQ.Constants.sb_middleGProgress, defGain);
            int sbTrebleG = this.preferences.getInt(cs2c.EQ.Constants.sb_trebleGProgress, defGain);
            int sbLoudG = this.preferences.getInt(cs2c.EQ.Constants.sb_LoudGProgress, defGain);
            int sbBassQ = this.preferences.getInt(cs2c.EQ.Constants.sb_bassQProgress, 0);
            int sbBassF = this.preferences.getInt(cs2c.EQ.Constants.sb_bassFoProgress, 0);
            int sbMiddleQ = this.preferences.getInt(cs2c.EQ.Constants.sb_middleQProgress, 0);
            int sbMiddleF = this.preferences.getInt(cs2c.EQ.Constants.sb_middleFoProgress, 0);
            int sbTrebleQ = this.preferences.getInt(cs2c.EQ.Constants.sb_trebleQProgress, 0);
            int sbTrebleF = this.preferences.getInt(cs2c.EQ.Constants.sb_trebleFoProgress, 0);
            boolean cbLoudOn = this.preferences.getBoolean(cs2c.EQ.Constants.cb_LoudOn, true);

            try {
                int gainValue = (sbBassG > maxGain ? maxGain : sbBassG);
                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.BassGain, gainValue);
                cs2c.EQ.EQServiceProxy.set_volume(SetVolumeCommands.BassQF, sbBassF << 4 + sbBassQ);

                gainValue = (sbMiddleG > maxGain ? maxGain : sbMiddleG);
                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.MiddleGain, gainValue);
                cs2c.EQ.EQServiceProxy.set_volume(SetVolumeCommands.MiddleQF, sbMiddleF << 4 + sbMiddleQ);

                gainValue = (sbTrebleG > maxGain ? maxGain : sbTrebleG);
                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.TrebleGain, gainValue);
                cs2c.EQ.EQServiceProxy.set_volume(SetVolumeCommands.TrebleQF, sbTrebleF << 4 + sbTrebleQ);

                cs2c.EQ.EQServiceProxy.set_volume(SetVolumeCommands.LoudnessGain, sbLoudG);

                cs2c.EQ.EQServiceProxy.set_volume(SetVolumeCommands.LoudOnOff, cbLoudOn ? 1 : 0);

                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.HighFreqSB, defGain);
                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.MiddleFreqSB, defGain);
                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.LowFreqSB, maxGain);

                int increaseValue = this.preferences.getInt("increase_value_key", 2);
                cs2c.EQ.EQServiceProxy.setSound(SetSoundCommands.IncreaseValue, increaseValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.equals("cn.com.cs2c.android.vehicle.action.EQ_KEY")) {
            Intent it = new Intent();
            it.setClass(context, cs2c.EQ.EqualizerFragment.class);
            it.addFlags(FLAG_CANCEL_CURRENT);
            context.startActivity(it);
            Log.d("lzc", "receiver EQ key");
        }
    }
}
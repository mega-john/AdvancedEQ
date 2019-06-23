package cs2c.EQ;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import cs2c.EQ.Service.EQServiceProxy;
import cs2c.EQ.Service.SetSoundCommands;
import cs2c.EQ.Service.SetVolumeCommands;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;
import static android.content.Context.MODE_WORLD_WRITEABLE;

public class EQBroadcastReceiver extends BroadcastReceiver {

    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        this.preferences = context.getSharedPreferences(Settings.EQSettingsFileName, MODE_WORLD_WRITEABLE);
        EQServiceProxy.Initialize(context);

        if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            Log.d(Settings.EQInterfaceName, "receiver BOOT_COMPLETED");

            int defGain = context.getResources().getInteger(R.integer.eq_gain_default);
            int maxGain = context.getResources().getInteger(R.integer.eq_gain_max);
            int sbPreampG = this.preferences.getInt(Settings.PreampGValue, defGain);
            int sbBassG = this.preferences.getInt(Settings.BassGValue, defGain);
            int sbMiddleG = this.preferences.getInt(Settings.MiddleGValue, defGain);
            int sbTrebleG = this.preferences.getInt(Settings.TrebleGValue, defGain);
            int sbLoudG = this.preferences.getInt(Settings.LoudGValue, defGain);
            int sbBassQ = this.preferences.getInt(Settings.BassQValue, 0);
            int sbBassF = this.preferences.getInt(Settings.BassFoValue, 0);
            int sbMiddleQ = this.preferences.getInt(Settings.MiddleQValue, 0);
            int sbMiddleF = this.preferences.getInt(Settings.MiddleFoValue, 0);
            int sbTrebleQ = this.preferences.getInt(Settings.TrebleQValue, 0);
            int sbTrebleF = this.preferences.getInt(Settings.TrebleFoValue, 0);
            boolean cbLoudOn = this.preferences.getBoolean(Settings.LoudOnValue, true);

            try {
                int gainValue = (sbBassG > maxGain ? maxGain : sbBassG);
                EQServiceProxy.setSound(SetSoundCommands.BassGain, gainValue);
                EQServiceProxy.set_volume(SetVolumeCommands.BassQF, sbBassF << 4 + sbBassQ);

                gainValue = (sbMiddleG > maxGain ? maxGain : sbMiddleG);
                EQServiceProxy.setSound(SetSoundCommands.MiddleGain, gainValue);
                EQServiceProxy.set_volume(SetVolumeCommands.MiddleQF, sbMiddleF << 4 + sbMiddleQ);

                gainValue = (sbTrebleG > maxGain ? maxGain : sbTrebleG);
                EQServiceProxy.setSound(SetSoundCommands.TrebleGain, gainValue);
                EQServiceProxy.set_volume(SetVolumeCommands.TrebleQF, sbTrebleF << 4 + sbTrebleQ);

                EQServiceProxy.set_volume(SetVolumeCommands.LoudnessGain, sbLoudG);

                EQServiceProxy.set_volume(SetVolumeCommands.LoudOnOff, cbLoudOn ? 1 : 0);

                EQServiceProxy.setSound(SetSoundCommands.HighFreqSB, defGain);
                EQServiceProxy.setSound(SetSoundCommands.MiddleFreqSB, defGain);
                EQServiceProxy.setSound(SetSoundCommands.LowFreqSB, maxGain);

                int increaseValue = this.preferences.getInt("increase_value_key", 2);
                EQServiceProxy.setSound(SetSoundCommands.IncreaseValue, increaseValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (action.equals("cn.com.cs2c.android.vehicle.action.EQ_KEY")) {
            Intent it = new Intent();
            it.setClass(context, cs2c.EQ.EqualizerFragment.class);
            it.addFlags(FLAG_CANCEL_CURRENT);
            context.startActivity(it);
            Log.d(Settings.EQInterfaceName, "receiver EQ key");
        } else if (action.equals("cn.com.cs2c.sys.MAIN_VOICE")) {
            Bundle b = intent.getExtras();
            int currVolume = b.getInt("int");
//            Intent it = new Intent();
//            it.setClass(context, cs2c.EQ.EqualizerFragment.class);
//            it.addFlags(FLAG_CANCEL_CURRENT);
//            context.startActivity(it);
            Log.d(Settings.EQInterfaceName, "receiver: cn.com.cs2c.sys.MAIN_VOICE currVolume=" + currVolume);
        }
    }
}
package cs2c.EQ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import cs2c.EQ.Controls.AfcChart;
import cs2c.EQ.Controls.HorizontalSeekBar;
import cs2c.EQ.Controls.VerticalSeekBar;

public class EqualizerFragment extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    //    private CheckBox muteOn, equalizerOn;
    private CheckBox cbLoudOn;

    private VerticalSeekBar sbPreampG, sbBassG, sbMiddleG, sbTrebleG, sbLoudG;
    private HorizontalSeekBar sbBassF, sbBassQ;
    private HorizontalSeekBar sbMiddleF, sbMiddleQ;
    private HorizontalSeekBar sbTrebleF, sbTrebleQ;
    private HorizontalSeekBar sbLoudF, sbLoudHC;

    private TextView preampV, bassV, middleV, trebleV, loudV, btBalance, btDefaults;
//    private TextView inputV;

    private AfcChart chart;
    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        setContentView(R.layout.fragment_equalizer);

        EQServiceProxy.Initialize(this);

        if (preferences == null) {
            preferences = getSharedPreferences(Constants.EQSettingsFileName, MODE_PRIVATE);
        }

        btBalance = (TextView) findViewById(R.id.bt_balance);
        btDefaults = (TextView) findViewById(R.id.bt_defaults);
//        muteOn = (CheckBox) findViewById(R.id.mute_on);
        cbLoudOn = (CheckBox) findViewById(R.id.loud_on);
//        equalizerOn = (CheckBox) findViewById(R.id.equalizer_on);

        btBalance.setOnClickListener(this);
        btDefaults.setOnClickListener(this);
//        muteOn.setOnClickListener(this);
        cbLoudOn.setOnClickListener(this);
//        equalizerOn.setOnClickListener(this);

        sbPreampG = (VerticalSeekBar) findViewById(R.id.seekBarPreamp);
        sbBassG = (VerticalSeekBar) findViewById(R.id.seekBarBassG);
        sbBassF = (HorizontalSeekBar) findViewById(R.id.seekBarBassF);
        sbBassQ = (HorizontalSeekBar) findViewById(R.id.seekBarBassQ);
        sbMiddleG = (VerticalSeekBar) findViewById(R.id.seekBarMiddleG);
        sbMiddleF = (HorizontalSeekBar) findViewById(R.id.seekBarMiddleF);
        sbMiddleQ = (HorizontalSeekBar) findViewById(R.id.seekBarMiddleQ);
        sbTrebleG = (VerticalSeekBar) findViewById(R.id.seekBarTrebleG);
        sbTrebleF = (HorizontalSeekBar) findViewById(R.id.seekBarTrebleF);
        sbTrebleQ = (HorizontalSeekBar) findViewById(R.id.seekBarTrebleQ);
        sbLoudG = (VerticalSeekBar) findViewById(R.id.seekBarSubG);
        sbLoudF = (HorizontalSeekBar) findViewById(R.id.seekBarLoudF);
        sbLoudHC = (HorizontalSeekBar) findViewById(R.id.seekBarLoudHC);

        sbPreampG.setOnSeekBarChangeListener(this);
        sbBassG.setOnSeekBarChangeListener(this);
        sbBassF.setOnSeekBarChangeListener(this);
        sbBassQ.setOnSeekBarChangeListener(this);
        sbMiddleG.setOnSeekBarChangeListener(this);
        sbMiddleF.setOnSeekBarChangeListener(this);
        sbMiddleQ.setOnSeekBarChangeListener(this);
        sbTrebleG.setOnSeekBarChangeListener(this);
        sbTrebleF.setOnSeekBarChangeListener(this);
        sbTrebleQ.setOnSeekBarChangeListener(this);
        sbLoudG.setOnSeekBarChangeListener(this);
        sbLoudF.setOnSeekBarChangeListener(this);
        sbLoudHC.setOnSeekBarChangeListener(this);

        sbBassG.setOnTouchListener(this);
        sbBassF.setOnTouchListener(this);
        sbBassQ.setOnTouchListener(this);
        sbMiddleG.setOnTouchListener(this);
        sbMiddleF.setOnTouchListener(this);
        sbMiddleQ.setOnTouchListener(this);
        sbTrebleG.setOnTouchListener(this);
        sbTrebleF.setOnTouchListener(this);
        sbTrebleQ.setOnTouchListener(this);

        preampV = (TextView) findViewById(R.id.preamp_v);
        bassV = (TextView) findViewById(R.id.bass_v);
        middleV = (TextView) findViewById(R.id.middle_v);
        trebleV = (TextView) findViewById(R.id.treble_v);
        loudV = (TextView) findViewById(R.id.loud_v);
//        inputV = (TextView) findViewById(R.id.current_input);

        chart = (AfcChart) findViewById(R.id.afcChart);
        chart.setGrid(false);

        loadEQValues();
        updateAll();
    }

    private void loadEQValues() {

        int storedValue;

        storedValue = this.preferences.getInt(Constants.sb_preampGProgress, 0);
        sbPreampG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_bassGProgress, 10);
        sbBassG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_middleGProgress, 10);
        sbMiddleG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_trebleGProgress, 10);
        sbTrebleG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_LoudGProgress, 10);
        sbLoudG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_bassQProgress, 0);
        sbBassQ.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_bassFoProgress, 0);
        sbBassF.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_middleQProgress, 0);
        sbMiddleQ.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_middleFoProgress, 0);
        sbMiddleF.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_trebleQProgress, 0);
        sbTrebleQ.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_trebleFoProgress, 0);
        sbTrebleF.setProgress(storedValue);

        boolean b = this.preferences.getBoolean(Constants.cb_LoudOn, true);
        cbLoudOn.setChecked(b);

        updateAll();
    }

    private void saveEQValues() {
        SharedPreferences.Editor e = this.preferences.edit();
        if (e != null) {
            try {
                e.putInt(Constants.sb_preampGProgress, sbPreampG.getProgress());
                e.apply();
                e.putInt(Constants.sb_bassGProgress, sbBassG.getProgress());
                e.apply();
                e.putInt(Constants.sb_middleGProgress, sbMiddleG.getProgress());
                e.apply();
                e.putInt(Constants.sb_trebleGProgress, sbTrebleG.getProgress());
                e.apply();
                e.putInt(Constants.sb_LoudGProgress, sbLoudG.getProgress());
                e.apply();

                e.putInt(Constants.sb_bassQProgress, sbBassQ.getProgress());
                e.apply();
                e.putInt(Constants.sb_bassFoProgress, sbBassF.getProgress());
                e.apply();
                e.putInt(Constants.sb_middleQProgress, sbMiddleQ.getProgress());
                e.apply();
                e.putInt(Constants.sb_middleFoProgress, sbMiddleF.getProgress());
                e.apply();
                e.putInt(Constants.sb_trebleQProgress, sbTrebleQ.getProgress());
                e.apply();
                e.putInt(Constants.sb_trebleFoProgress, sbTrebleF.getProgress());
                e.apply();

                e.putBoolean(Constants.cb_LoudOn, cbLoudOn.isChecked());
                e.apply();
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e(Constants.EQInterfaceName, ex.getMessage());
            }
        }
        updateAll();
    }

    private void WriteSettingsToSoundProcessor() {
        int gainValue;
        int maxGain = getResources().getInteger(R.integer.eq_gain_max);

        gainValue = (sbBassG.getProgress() > maxGain  ? maxGain  : sbBassG.getProgress());
        EQServiceProxy.setSound(Constants.cBassCommand, gainValue);
        EQServiceProxy.setSound(Constants.cBassQFCommand, sbBassF.getProgress() << 4 + sbBassQ.getProgress());
        updateChartBass();

        gainValue = (sbMiddleG.getProgress() > maxGain  ? maxGain  : sbMiddleG.getProgress());
        EQServiceProxy.setSound(Constants.cMiddleCommand, gainValue);
        EQServiceProxy.setSound(Constants.cMiddleQFCommand, sbMiddleF.getProgress() << 4 + sbMiddleQ.getProgress());
        updateChartMiddle();

        gainValue = (sbTrebleG.getProgress() > maxGain  ? maxGain  : sbTrebleG.getProgress());
        EQServiceProxy.setSound(Constants.cTrebleCommand, gainValue);
        EQServiceProxy.setSound(Constants.cTrebleQFCommand, sbTrebleF.getProgress() << 4 + sbTrebleQ.getProgress());
        updateChartTreble();

        EQServiceProxy.set_volume(Constants.cSubwooferGainCommand, sbLoudG.getProgress());

        EQServiceProxy.set_volume(Constants.cLoudOnOffCommand, cbLoudOn.isChecked() ? 1 : 0);

        EQServiceProxy.setSound(Constants.cHighFreqSB, 7);
        EQServiceProxy.setSound(Constants.cMiddleFreqSB, 7);
        EQServiceProxy.setSound(Constants.cLowFreqSB, 14);
    }

    public void updateAll() {
//        updateOnSwitches();
        updateValues();
        updateCharts();
        SetCombosEnabled();
        WriteSettingsToSoundProcessor();
    }

    private void SetCombosEnabled() {
        sbLoudG.setEnabled(cbLoudOn.isChecked());
    }

    private void updateValues() {
        int eqGain = 0;// getResources().getInteger(R.integer.eq_gain_default_progress);
        preampV.setText(formatGain(sbPreampG.getProgress()));
        bassV.setText(formatGain(sbBassG.getProgress() - eqGain));
        middleV.setText(formatGain(sbMiddleG.getProgress() - eqGain));
        trebleV.setText(formatGain(sbTrebleG.getProgress() - eqGain));
        loudV.setText(formatGain(sbLoudG.getProgress()));
    }

    private void updateCharts() {
        updateChartBass();
        updateChartMiddle();
        updateChartTreble();
    }

    private void updateChartBass() {
        float eqGain = getResources().getInteger(R.integer.eq_gain_default_progress);
        chart.bass.setFQG(bassF(sbBassF.getProgress()), bassQ(sbBassQ.getProgress()), sbBassG.getProgress() - eqGain);
    }

    private void updateChartMiddle() {
        int eqGain = getResources().getInteger(R.integer.eq_gain_default_progress);
        chart.middle.setFQG(middleF(sbMiddleF.getProgress()), middleQ(sbMiddleQ.getProgress()), sbMiddleG.getProgress() - eqGain);
    }

    private void updateChartTreble() {
        int eqGain = getResources().getInteger(R.integer.eq_gain_default_progress);
        chart.treble.setFQG(trebleF(sbTrebleF.getProgress()), trebleQ(sbTrebleQ.getProgress()), sbTrebleG.getProgress() - eqGain);
    }

    private String formatGain(int gain) {
        if (gain <= 0) {
            return "0";
        }
        if (gain >= 15) {
            return "+14";
        }
        return String.format("%+d", gain);
    }

    private float bassF(int i) {
        switch (i) {
            case 0:
                return 60;
            case 1:
                return 80;
            case 2:
                return 100;
            default:
                return 120;
        }
    }

    private float bassQ(int i) {
        switch (i) {
            case 0:
                return 0.5f;
            case 1:
                return 1.0f;
            case 2:
                return 1.5f;
            default:
                return 2.0f;
        }
    }

    private float middleF(int i) {
        switch (i) {
            case 0:
                return 500;
            case 1:
                return 1000;
            case 2:
                return 1500;
            default:
                return 2000;
        }
    }

    private float middleQ(int i) {
        switch (i) {
            case 0:
                return 0.75f;
            case 1:
                return 1.0f;
            case 2:
                return 1.25f;
            default:
                return 1.5f;
        }
    }

    private float trebleF(int i) {
        switch (i) {
            case 0:
                return 7500;
            case 1:
                return 10000;
            case 2:
                return 12500;
            default:
                return 15000;
        }
    }

    private float trebleQ(int i) {
        switch (i) {
            case 0:
                return 0.75f;
            default:
                return 1.25f;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_balance:
                Intent intent = new Intent();
                intent.setClass(this, BalanceFragment.class);
                startActivity(intent);
                break;
            case R.id.bt_defaults:
//                audioManager.setParameters("av_mute=" + (muteOn.isChecked() ? "true" : "false"));
                ResetToDefault();
                break;
            case R.id.loud_on:
//                audioManager.setParameters("av_lud=" + (loudOn.isChecked() ? "on" : "off"));
                SetCombosEnabled();
                EQServiceProxy.set_volume(Constants.cLoudOnOffCommand, cbLoudOn.isChecked() ? 1 : 0);
                break;
        }
    }

    private void ResetToDefault() {
        int value = getResources().getInteger(R.integer.eq_gain_default_progress);
        sbPreampG.setProgress(value);
        sbBassG.setProgress(value);
        sbMiddleG.setProgress(value);
        sbTrebleG.setProgress(value);
        sbLoudG.setProgress(value);
        sbBassQ.setProgress(0);
        sbBassF.setProgress(0);
        sbMiddleQ.setProgress(0);
        sbMiddleF.setProgress(0);
        sbTrebleQ.setProgress(0);
        sbTrebleF.setProgress(0);
        cbLoudOn.setChecked(true);
        saveEQValues();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (!fromUser) return;

//        int eqGain = 0;//getResources().getInteger(R.integer.eq_gain_default_progress);
//        int gainValue;
//        int maxGain = getResources().getInteger(R.integer.eq_gain_max);
//
//        switch (seekBar.getId()) {
//            case R.id.seekBarPreamp:
////                audioManager.setParameters(String.format("av_gain=%d", preampG.getProgress()));
//                break;
//            case R.id.seekBarBassG:
//            case R.id.seekBarBassF:
//            case R.id.seekBarBassQ:
//                gainValue = (sbBassG.getProgress() > maxGain ? maxGain : sbBassG.getProgress()) - eqGain;
//                EQServiceProxy.setSound(Constants.cBassCommand, gainValue);
//                EQServiceProxy.setSound(Constants.cBassQFCommand, sbBassF.getProgress() << 4 + sbBassQ.getProgress());
//                updateChartBass();
//                break;
//            case R.id.seekBarMiddleG:
//            case R.id.seekBarMiddleF:
//            case R.id.seekBarMiddleQ:
//                gainValue = (sbMiddleG.getProgress() > maxGain ? maxGain : sbMiddleG.getProgress()) - eqGain;
//                EQServiceProxy.setSound(Constants.cMiddleCommand, gainValue);
//                EQServiceProxy.setSound(Constants.cMiddleQFCommand, sbMiddleF.getProgress() << 4 + sbMiddleQ.getProgress());
//                updateChartMiddle();
//                break;
//            case R.id.seekBarTrebleG:
//            case R.id.seekBarTrebleF:
//            case R.id.seekBarTrebleQ:
//                gainValue = (sbTrebleG.getProgress() > maxGain ? maxGain : sbTrebleG.getProgress()) - eqGain;
//                EQServiceProxy.setSound(Constants.cTrebleCommand, gainValue);
//                EQServiceProxy.setSound(Constants.cTrebleQFCommand, sbTrebleF.getProgress() << 4 + sbTrebleQ.getProgress());
//                updateChartTreble();
//                break;
//            case R.id.seekBarSubG:
//            case R.id.seekBarLoudF:
//            case R.id.seekBarLoudHC:
//                EQServiceProxy.set_volume(Constants.cSubwooferGainCommand, sbLoudG.getProgress());
////                EQServiceProxy.set_volume(Constants.cLoudQFCommand, (sbLoudF.getProgress() << 4) + sbLoudHC.getProgress());
//                break;
//        }
//        EQServiceProxy.setSound(13, 7);
//        EQServiceProxy.setSound(12, 7);
//        EQServiceProxy.setSound(11, 14);

        saveEQValues();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (SeekBar.class.isInstance(v)) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    chart.setGrid(true);
                    break;
                case MotionEvent.ACTION_UP:
                    chart.setGrid(false);
                    break;
            }
            return false;
        }
        return false;
    }
}
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
import cs2c.EQ.Controls.EQServiceProxy;
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
    private EQServiceProxy mEQProxy;
    private SharedPreferences preferences;

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        setContentView(R.layout.fragment_equalizer);

        mEQProxy = new EQServiceProxy(this);

        if (preferences == null) {
            preferences = getSharedPreferences(Constants.EQSettingsFileName, MODE_PRIVATE);
        }

        btBalance = (TextView) findViewById(R.id.bt_balance);
        btDefaults = (TextView) findViewById(R.id.bt_defaults);
//        muteOn = (CheckBox) findViewById(R.id.mute_on);
        cbLoudOn= (CheckBox) findViewById(R.id.loud_on);
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
        sbLoudG = (VerticalSeekBar) findViewById(R.id.seekBarLoudG);
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
        update();
    }

    private void loadEQValues() {

        int storedValue;

        storedValue = this.preferences.getInt(Constants.sb_PreampGProgressValue, 0);
        sbPreampG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_lowFreqSBProgressValue, 10);
        sbBassG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_middleFreqSBProgressValue, 10);
        sbMiddleG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_highFreqSBProgressValue, 10);
        sbTrebleG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_LoudGProgressValue, 10);
        sbLoudG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sbQ_bassProgressValue, 0);
        sbBassQ.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sbFo_bassProgressValue, 0);
        sbBassF.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sbQ_middleProgressValue, 0);
        sbMiddleQ.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sbFo_middleProgressValue, 0);
        sbMiddleF.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sbQ_trebleProgressValue, 0);
        sbTrebleQ.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sbFo_trebleProgressValue, 0);
        sbTrebleF.setProgress(storedValue);

        boolean b = this.preferences.getBoolean(Constants.sb_LoudOn, true);
        cbLoudOn.setChecked(b);

        SetEnabled();
    }

    private void saveEQValues() {
        SharedPreferences.Editor e = this.preferences.edit();
        if (e != null) {
            try {
                e.putInt(Constants.sb_PreampGProgressValue, sbPreampG.getProgress());
                e.apply();
                e.putInt(Constants.sb_lowFreqSBProgressValue, sbBassG.getProgress());
                e.apply();
                e.putInt(Constants.sb_middleFreqSBProgressValue, sbMiddleG.getProgress());
                e.apply();
                e.putInt(Constants.sb_highFreqSBProgressValue, sbTrebleG.getProgress());
                e.apply();
                e.putInt(Constants.sb_LoudGProgressValue, sbLoudG.getProgress());
                e.apply();

                e.putInt(Constants.sbQ_bassProgressValue, sbBassQ.getProgress());
                e.apply();
                e.putInt(Constants.sbFo_bassProgressValue, sbBassF.getProgress());
                e.apply();
                e.putInt(Constants.sbQ_middleProgressValue, sbMiddleQ.getProgress());
                e.apply();
                e.putInt(Constants.sbFo_middleProgressValue, sbMiddleF.getProgress());
                e.apply();
                e.putInt(Constants.sbQ_trebleProgressValue, sbTrebleQ.getProgress());
                e.apply();
                e.putInt(Constants.sbFo_trebleProgressValue, sbTrebleF.getProgress());
                e.apply();

                e.putBoolean(Constants.sb_LoudOn, cbLoudOn.isChecked());
                e.apply();
            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("saveEQValues", ex.getMessage());
            }
        }
    }

    public void update() {
        updateOnSwitches();
        updateValues();
        updateChart();
        SetEnabled();
    }

    private void SetEnabled()
    {
        sbLoudG.setEnabled(cbLoudOn.isChecked());
    }

    private void updateOnSwitches() {
//        muteOn.setChecked("true".equalsIgnoreCase(audioManager.getParameters("av_mute=")));
//        loudOn.setChecked("on".equalsIgnoreCase(audioManager.getParameters("av_lud=")));
//        equalizerOn.setChecked("on".equalsIgnoreCase(audioManager.getParameters("av_eq_on=")));
    }

    private void updateValues() {
        preampV.setText(formatGain(sbPreampG.getProgress()));
        int eqGain = 0;// getResources().getInteger(R.integer.eq_gain_default_progress);
        bassV.setText(formatGain(sbBassG.getProgress() - eqGain));
        middleV.setText(formatGain(sbMiddleG.getProgress() - eqGain));
        trebleV.setText(formatGain(sbTrebleG.getProgress() - eqGain));
        loudV.setText(formatGain(sbLoudG.getProgress()));
    }

    private void updateChart() {
        updateChartBass();
        updateChartMiddle();
        updateChartTreble();
    }

    private void updateChartBass() {
        int eqGain = getResources().getInteger(R.integer.eq_gain_default_progress);
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
        if (gain == 0) {
            return "0";
        }
        if (gain >= 15) {
            return "+(15)";
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
//                audioManager.setParameters("av_mute=" + (muteOn.isChecked() ? "true" : "false"));
                Intent advancedIntent = new Intent();
                advancedIntent.setClass(this, BalanceFragment.class);
                startActivity(advancedIntent);
                return;
//                break;
            case R.id.bt_defaults:
//                audioManager.setParameters("av_mute=" + (muteOn.isChecked() ? "true" : "false"));
                ResetToDefault();
                return;
//                break;
//            case R.id.mute_on:
////                audioManager.setParameters("av_mute=" + (muteOn.isChecked() ? "true" : "false"));
//                break;
            case R.id.loud_on:
//                audioManager.setParameters("av_lud=" + (loudOn.isChecked() ? "on" : "off"));
                SetEnabled();
                mEQProxy.set_volume(Constants.cLoudOnOffCommand, cbLoudOn.isChecked() ? 1 : 0);
                break;
//            case R.id.equalizer_on:
////                audioManager.setParameters("av_eq_on=" + (equalizerOn.isChecked() ? "on" : "off"));
//                break;
        }
    }

    private void ResetToDefault() {
        sbPreampG.setProgress(10);
        sbBassG.setProgress(10);
        sbMiddleG.setProgress(10);
        sbTrebleG.setProgress(10);
        sbLoudG.setProgress(10);
        sbBassQ.setProgress(0);
        sbBassF.setProgress(0);
        sbMiddleQ.setProgress(0);
        sbMiddleF.setProgress(0);
        sbTrebleQ.setProgress(0);
        sbTrebleF.setProgress(0);
        cbLoudOn.setChecked(true);
        saveEQValues();
        update();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (!fromUser) return;

        int eqGain = 0;//getResources().getInteger(R.integer.eq_gain_default_progress);

        switch (seekBar.getId()) {
            case R.id.seekBarPreamp:
//                audioManager.setParameters(String.format("av_gain=%d", preampG.getProgress()));
                break;
            case R.id.seekBarBassG:
            case R.id.seekBarBassF:
            case R.id.seekBarBassQ:
//                audioManager.setParameters(String.format("av_eq_bass=%d,%d,%d", bassG.getProgress()-20, bassF.getProgress(), bassQ.getProgress()));
                mEQProxy.setSound(Constants.cBassCommand, sbBassG.getProgress() - eqGain);
//                mEQProxy.set_volume(Constants.cBassQFCommand, (sbBassF.getProgress() << 4) + sbBassQ.getProgress());
                updateChartBass();
                break;
            case R.id.seekBarMiddleG:
            case R.id.seekBarMiddleF:
            case R.id.seekBarMiddleQ:
//                audioManager.setParameters(String.format("av_eq_middle=%d,%d,%d", middleG.getProgress()-20, middleF.getProgress(), middleQ.getProgress()));
                mEQProxy.setSound(Constants.cMiddleCommand, sbMiddleG.getProgress() - eqGain);
//                mEQProxy.set_volume(Constants.cMiddleQFCommand, (sbMiddleF.getProgress() << 4) + sbMiddleQ.getProgress());
                updateChartMiddle();
                break;
            case R.id.seekBarTrebleG:
            case R.id.seekBarTrebleF:
            case R.id.seekBarTrebleQ:
//                audioManager.setParameters(String.format("av_eq_treble=%d,%d,%d", trebleG.getProgress()-20, trebleF.getProgress(), trebleQ.getProgress()));
                mEQProxy.setSound(Constants.cTrebleCommand, sbTrebleG.getProgress() - eqGain);
//                mEQProxy.set_volume(Constants.cTrebleQFCommand, (sbBassF.getProgress() << 4) + sbBassQ.getProgress());
                updateChartTreble();
                break;
            case R.id.seekBarLoudG:
            case R.id.seekBarLoudF:
            case R.id.seekBarLoudHC:
//                audioManager.setParameters(String.format("av_loudness=%d,%d,%d", loudG.getProgress(), loudF.getProgress(), loudHC.getProgress()));
                mEQProxy.set_volume(Constants.cLoudGainCommand, sbLoudG.getProgress());
//                mEQProxy.set_volume(Constants.cLoudQFCommand, (sbLoudF.getProgress() << 4) + sbLoudHC.getProgress());
                break;
        }
        updateValues();
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
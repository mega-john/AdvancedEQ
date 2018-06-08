package cs2c.EQ;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
//import android.cs2c.IEQService;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import cs2c.EQ.Controls.AfcChart;

public class EqualizerFragment extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {
    private CheckBox muteOn, loudOn, equalizerOn;

    private SeekBar sbPreampG;
    private SeekBar sbBassG, sbBassF, sbBassQ;
    private SeekBar sbMiddleG, sbMiddleF, sbMiddleQ;
    private SeekBar sbTrebleG, sbTrebleF, sbTrebleQ;
    private SeekBar sbLoudG, sbLoudF, sbLoudHC;

    private TextView preampV, bassV, middleV, trebleV, loudV, inputV;
    private AfcChart chart;
    private IEQService mEQService;
    private SharedPreferences preferences;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);

        setContentView(R.layout.fragment_equalizer);

        if (mEQService == null) {
            //noinspection WrongConstant
            mEQService = (IEQService) getSystemService(Constants.EQInterfaceName);
            Log.i("this.mEQService == null", String.valueOf(mEQService == null));
        }

        if (preferences == null) {
            preferences = getSharedPreferences("musicEQ", MODE_PRIVATE);
        }

        muteOn = (CheckBox) findViewById(R.id.mute_on);
        loudOn = (CheckBox) findViewById(R.id.loud_on);
        equalizerOn = (CheckBox) findViewById(R.id.equalizer_on);

        muteOn.setOnClickListener(this);
        loudOn.setOnClickListener(this);
        equalizerOn.setOnClickListener(this);

        sbPreampG = (SeekBar) findViewById(R.id.seekBarPreamp);
        sbBassG = (SeekBar) findViewById(R.id.seekBarBassG);
        sbBassF = (SeekBar) findViewById(R.id.seekBarBassF);
        sbBassQ = (SeekBar) findViewById(R.id.seekBarBassQ);
        sbMiddleG = (SeekBar) findViewById(R.id.seekBarMiddleG);
        sbMiddleF = (SeekBar) findViewById(R.id.seekBarMiddleF);
        sbMiddleQ = (SeekBar) findViewById(R.id.seekBarMiddleQ);
        sbTrebleG = (SeekBar) findViewById(R.id.seekBarTrebleG);
        sbTrebleF = (SeekBar) findViewById(R.id.seekBarTrebleF);
        sbTrebleQ = (SeekBar) findViewById(R.id.seekBarTrebleQ);
        sbLoudG = (SeekBar) findViewById(R.id.seekBarLoudG);
        sbLoudF = (SeekBar) findViewById(R.id.seekBarLoudF);
        sbLoudHC = (SeekBar) findViewById(R.id.seekBarLoudHC);

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
        inputV = (TextView) findViewById(R.id.current_input);

        chart = (AfcChart) findViewById(R.id.afcChart);
        chart.setGrid(false);

        loadEQValues();
        update();

    }

    private void loadEQValues() {

        int storedValue = 0;

        storedValue = this.preferences.getInt(Constants.sb_PreampGProgressValue, 0);
        sbPreampG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_lowFreqSBProgressValue, 20);
        sbBassG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_middleFreqSBProgressValue, 20);
        sbMiddleG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_highFreqSBProgressValue, 20);
        sbTrebleG.setProgress(storedValue);

        storedValue = this.preferences.getInt(Constants.sb_LoudGProgressValue, 0);
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
    }

    private void updateOnSwitches() {
//        muteOn.setChecked("true".equalsIgnoreCase(audioManager.getParameters("av_mute=")));
//        loudOn.setChecked("on".equalsIgnoreCase(audioManager.getParameters("av_lud=")));
//        equalizerOn.setChecked("on".equalsIgnoreCase(audioManager.getParameters("av_eq_on=")));
    }

    private void updateValues() {
        preampV.setText(formatGain(sbPreampG.getProgress()));
        bassV.setText(formatGain(sbBassG.getProgress() - 20));
        middleV.setText(formatGain(sbMiddleG.getProgress() - 20));
        trebleV.setText(formatGain(sbTrebleG.getProgress() - 20));
        loudV.setText(formatGain(sbLoudG.getProgress()));
    }

    private void updateChart() {
        updateChartBass();
        updateChartMiddle();
        updateChartTreble();
    }

    private void updateChartBass() {
        chart.bass.setG(sbBassG.getProgress() - 20);
        chart.bass.setF(bassF(sbBassF.getProgress()));
        chart.bass.setQ(bassQ(sbBassQ.getProgress()));
    }

    private void updateChartMiddle() {
        chart.middle.setG(sbMiddleG.getProgress() - 20);
        chart.middle.setF(middleF(sbMiddleF.getProgress()));
        chart.middle.setQ(middleQ(sbMiddleQ.getProgress()));
    }

    private void updateChartTreble() {
        chart.treble.setG(sbTrebleG.getProgress() - 20);
        chart.treble.setF(trebleF(sbTrebleF.getProgress()));
        chart.treble.setQ(trebleQ(sbTrebleQ.getProgress()));
    }

    private String formatGain(int gain) {
        if (gain == 0) return "0";
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
            case R.id.mute_on:
//                audioManager.setParameters("av_mute=" + (muteOn.isChecked() ? "true" : "false"));
                break;
            case R.id.loud_on:
//                audioManager.setParameters("av_lud=" + (loudOn.isChecked() ? "on" : "off"));
                break;
            case R.id.equalizer_on:
//                audioManager.setParameters("av_eq_on=" + (equalizerOn.isChecked() ? "on" : "off"));
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (!fromUser) return;

        switch (seekBar.getId()) {
            case R.id.seekBarPreamp:
//                audioManager.setParameters(String.format("av_gain=%d", preampG.getProgress()));
                break;
            case R.id.seekBarBassG:
            case R.id.seekBarBassF:
            case R.id.seekBarBassQ:
//                audioManager.setParameters(String.format("av_eq_bass=%d,%d,%d", bassG.getProgress()-20, bassF.getProgress(), bassQ.getProgress()));
                mEQService.setSound(Constants.cBassCommand, sbBassG.getProgress()-20);
                mEQService.set_volume(Constants.cBassQFCommand, (sbBassF.getProgress() << 4) + sbBassQ.getProgress());
                updateChartBass();
                break;
            case R.id.seekBarMiddleG:
            case R.id.seekBarMiddleF:
            case R.id.seekBarMiddleQ:
//                audioManager.setParameters(String.format("av_eq_middle=%d,%d,%d", middleG.getProgress()-20, middleF.getProgress(), middleQ.getProgress()));
                mEQService.setSound(Constants.cMiddleCommand, sbMiddleG.getProgress()-20);
                mEQService.set_volume(Constants.cMiddleQFCommand, (sbMiddleF.getProgress() << 4) + sbMiddleQ.getProgress());
                updateChartMiddle();
                break;
            case R.id.seekBarTrebleG:
            case R.id.seekBarTrebleF:
            case R.id.seekBarTrebleQ:
//                audioManager.setParameters(String.format("av_eq_treble=%d,%d,%d", trebleG.getProgress()-20, trebleF.getProgress(), trebleQ.getProgress()));
                mEQService.setSound(Constants.cTrebleCommand, sbBassG.getProgress()-20);
                mEQService.set_volume(Constants.cTrebleQFCommand, (sbBassF.getProgress() << 4) + sbBassQ.getProgress());
                updateChartTreble();
                break;
            case R.id.seekBarLoudG:
            case R.id.seekBarLoudF:
            case R.id.seekBarLoudHC:
//                audioManager.setParameters(String.format("av_loudness=%d,%d,%d", loudG.getProgress(), loudF.getProgress(), loudHC.getProgress()));
                mEQService.set_volume(Constants.cLoudCommand, sbLoudG.getProgress());
                mEQService.set_volume(Constants.cLoudQFCommand, (sbLoudF.getProgress() << 4) + sbLoudHC.getProgress());
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
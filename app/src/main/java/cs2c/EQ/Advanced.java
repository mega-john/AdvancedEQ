package cs2c.EQ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.cs2c.IEQService;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar;
import android.widget.TextView;

public class Advanced extends Activity implements OnClickListener, SeekBar.OnSeekBarChangeListener {

    private IEQService mEQService;
    private SharedPreferences preferences;

    private TextView txtBassQ;
    private SeekBar sbBassQ;
    private TextView txtBassF;
    private SeekBar sbBassF;
    private TextView txtMiddleQ;
    private SeekBar sbMiddleQ;
    private TextView txtMiddleF;
    private SeekBar sbMiddleF;
    private TextView txtTrebleQ;
    private SeekBar sbTrebleQ;
    private TextView txtTrebleF;
    private SeekBar sbTrebleF;

    private String[] bassQValues = new String[]{"0.5", "1.0", "1.5", "2.0", "---",};
    private String[] bassFValues = new String[]{"60 Hz", "80 Hz", "100 Hz", "120 Hz", "---",};
    private String[] middleQValues = new String[]{"0.75", "1.0", "1.25", "1.5", "---",};
    private String[] middleFValues = new String[]{"0.5 kHz", "1.0 kHz", "1.5 kHz", "2.5 kHz", "---",};
    private String[] trebleQValues = new String[]{"0.75", "1.25", "---",};
    private String[] trebleFValues = new String[]{"7.5 kHz", "10 kHz", "12.5 kHz", "15 kHz", "---",};

    @Override
    protected void onStop() {
        saveEQValues();
        super.onStop();
    }

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        requestWindowFeature(1);
        setContentView(R.layout.music_advanced);

        if (preferences == null) {
            preferences = getSharedPreferences("musicEQ", MODE_PRIVATE);
        }

        GetEQInterface();
        InitializeComponets();
        loadEQValues();
    }

    @SuppressLint("WrongConstant")
    private void GetEQInterface() {
        if (mEQService == null) {
            //noinspection WrongConstant
            mEQService = (IEQService) getSystemService("eq");
            Log.i("this.mEQService == null", String.valueOf(mEQService == null));
        }
    }

    private void InitializeComponets() {
        this.txtBassQ = (TextView) findViewById(R.id.txtBassQ);
        this.sbBassQ = (SeekBar) findViewById(R.id.sbBassQ);
        this.sbBassQ.setOnSeekBarChangeListener(this);
        this.txtBassF = (TextView) findViewById(R.id.txtBassF);
        this.sbBassF = (SeekBar) findViewById(R.id.sbBassF);
        this.sbBassF.setOnSeekBarChangeListener(this);

        this.txtMiddleQ = (TextView) findViewById(R.id.txtMiddleQ);
        this.sbMiddleQ = (SeekBar) findViewById(R.id.sbMiddleQ);
        this.sbMiddleQ.setOnSeekBarChangeListener(this);
        this.txtMiddleF = (TextView) findViewById(R.id.txtMiddleF);
        this.sbMiddleF = (SeekBar) findViewById(R.id.sbMiddleF);
        this.sbMiddleF.setOnSeekBarChangeListener(this);

        this.txtTrebleQ = (TextView) findViewById(R.id.txtTrebleQ);
        this.sbTrebleQ = (SeekBar) findViewById(R.id.sbTrebleQ);
        this.sbTrebleQ.setOnSeekBarChangeListener(this);
        this.txtTrebleF = (TextView) findViewById(R.id.txtTrebleF);
        this.sbTrebleF = (SeekBar) findViewById(R.id.sbTrebleF);
        this.sbTrebleF.setOnSeekBarChangeListener(this);
    }

    private void loadEQValues() {

        int storedValue =  0;

        storedValue = this.preferences.getInt(Constants.sbQ_bassProgressValue, 0);
        sbBassQ.setProgress(storedValue);
        txtBassQ.setText(String.format("%s%s", getString(R.string.BassQ), bassQValues[storedValue]));

        storedValue = this.preferences.getInt(Constants.sbFo_bassProgressValue, 0);
        sbBassF.setProgress(storedValue);
        txtBassF.setText(String.format("%s%s", getString(R.string.BassFo), bassFValues[storedValue]));

        storedValue = this.preferences.getInt(Constants.sbQ_middleProgressValue, 0);
        sbMiddleQ.setProgress(storedValue);
        txtMiddleQ.setText(String.format("%s%s", getString(R.string.MiddleQ), middleQValues[storedValue]));

        storedValue = this.preferences.getInt(Constants.sbFo_middleProgressValue, 0);
        sbMiddleF.setProgress(storedValue);
        txtMiddleF.setText(String.format("%s%s", getString(R.string.MiddleFo), middleFValues[storedValue]));

        storedValue = this.preferences.getInt(Constants.sbQ_trebleProgressValue, 0);
        sbTrebleQ.setProgress(storedValue);
        txtTrebleQ.setText(String.format("%s%s", getString(R.string.TrebleQ), trebleQValues[storedValue]));

        storedValue = this.preferences.getInt(Constants.sbFo_trebleProgressValue, 0);
        sbTrebleF.setProgress(storedValue);
        txtTrebleF.setText(String.format("%s%s", getString(R.string.TrebleFo), trebleFValues[storedValue]));
    }

    private void saveEQValues() {
        SharedPreferences.Editor e = this.preferences.edit();
        if (e != null) {
            try {
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

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

        int var1 = 0;
        int var2 = 0;

        switch (seekBar.getId()) {
            case R.id.sbBassQ:
                txtBassQ.setText(String.format("%s%s", getString(R.string.BassQ), bassQValues[progress]));
                txtBassF.setText(String.format("%s%s", getString(R.string.BassFo), bassFValues[sbBassF.getProgress()]));
                break;
            case R.id.sbBassF:
                txtBassQ.setText(String.format("%s%s", getString(R.string.BassQ), bassQValues[sbBassQ.getProgress()]));
                txtBassF.setText(String.format("%s%s", getString(R.string.BassFo), bassFValues[progress]));
                break;
            case R.id.sbMiddleQ:
                txtMiddleQ.setText(String.format("%s%s", getString(R.string.MiddleQ), middleQValues[progress]));
                txtMiddleF.setText(String.format("%s%s", getString(R.string.MiddleFo), middleFValues[sbMiddleF.getProgress()]));
                break;
            case R.id.sbMiddleF:
                txtMiddleQ.setText(String.format("%s%s", getString(R.string.MiddleQ), middleQValues[sbMiddleQ.getProgress()]));
                txtMiddleF.setText(String.format("%s%s", getString(R.string.MiddleFo), middleFValues[progress]));
                break;
            case R.id.sbTrebleQ:
                txtTrebleQ.setText(String.format("%s%s", getString(R.string.TrebleQ), trebleQValues[progress]));
                txtTrebleF.setText(String.format("%s%s", getString(R.string.TrebleFo), trebleFValues[sbTrebleF.getProgress()]));
                break;
            case R.id.sbTrebleF:
                txtTrebleQ.setText(String.format("%s%s", getString(R.string.TrebleQ), trebleQValues[sbTrebleQ.getProgress()]));
                txtTrebleF.setText(String.format("%s%s", getString(R.string.TrebleFo), trebleFValues[progress]));
                break;
        }

        switch (seekBar.getId()) {
            case R.id.sbBassQ:
                var1 = Constants.cBassCommand;
                var2 = progress;
                var2 += ((sbBassF.getProgress() << 4));
                break;
            case R.id.sbBassF:
                var1 = Constants.cBassCommand;
                var2 = progress << 4;
                var2 += sbBassQ.getProgress();
                break;
            case R.id.sbMiddleQ:
                var1 = Constants.cMiddleCommand;
                var2 = progress;
                var2 += ((sbMiddleF.getProgress() << 4));
                break;
            case R.id.sbMiddleF:
                var1 = Constants.cMiddleCommand;
                var2 = progress << 4;
                var2 += sbMiddleQ.getProgress();
                break;
            case R.id.sbTrebleQ:
                var1 = Constants.cTrebleCommand;
                var2 = progress;
                var2 += ((sbTrebleF.getProgress() << 4));
                break;
            case R.id.sbTrebleF:
                var1 = Constants.cTrebleCommand;
                var2 = progress << 4;
                var2 += sbTrebleQ.getProgress();
                break;
        }

        if (mEQService != null) {
            try {
                String s = "mEQService.set_volume(";
                s = s.concat(String.valueOf(var1));
                s = s.concat(", ");
                s = s.concat(String.valueOf(var2));
                s = s.concat(")");
                mEQService.set_volume(var1, var2);
                Log.e("onProgressChanged", s);

            } catch (Exception ex) {
                ex.printStackTrace();
                Log.e("onStopTrackingTouch", ex.getMessage());
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

}

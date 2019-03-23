package cs2c.EQ1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class AdvancedFragment extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView btEqualizer;
    private TextView btBalance;
    private TextView bt1,bt2,bt3;
    private cs2c.EQ1.Controls.HorizontalSeekBar sbParam1, sbParam2;
    private TextView txt1, txt2;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Log.d("AdvancedFragment", "onCreate AdvancedFragment");
        setContentView(R.layout.fragment_advanced);

        btEqualizer = (TextView) findViewById(R.id.bt_equalizer);
        btEqualizer.setOnClickListener(this);
        btBalance = (TextView) findViewById(R.id.bt_balance);
        btBalance.setOnClickListener(this);

        bt1 = (TextView) findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2 = (TextView) findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3 = (TextView) findViewById(R.id.bt3);
        bt3.setOnClickListener(this);

        sbParam1 = (cs2c.EQ1.Controls.HorizontalSeekBar) findViewById(R.id.param1);
        sbParam1.setOnSeekBarChangeListener(this);
        sbParam2 = (cs2c.EQ1.Controls.HorizontalSeekBar) findViewById(R.id.param2);
        sbParam2.setOnSeekBarChangeListener(this);

        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_equalizer:
                Intent equalizerIntent = new Intent();
                equalizerIntent.setClass(this, cs2c.EQ1.EqualizerFragment.class);
                startActivity(equalizerIntent);
                Log.i("AdvancedFragment", "bt_equalizer");
                break;
            case R.id.bt_balance:
                Intent balanceIntent = new Intent();
                balanceIntent.setClass(this, cs2c.EQ1.BalanceFragment.class);
                startActivity(balanceIntent);
                Log.i("AdvancedFragment", "bt_balance");
                break;
            case R.id.bt1:
                cs2c.EQ1.EQServiceProxy.setSound(sbParam1.getProgress(), sbParam2.getProgress());
                break;
            case R.id.bt2:
                cs2c.EQ1.EQServiceProxy.set_volume(sbParam1.getProgress(), sbParam2.getProgress());
                break;
            case R.id.bt3:
                cs2c.EQ1.EQServiceProxy.setSound(sbParam1.getProgress(), sbParam2.getProgress());
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (!fromUser) {
            return;
        }
        int gainValue = 0;

        int seekBarId = seekBar.getId();

        switch (seekBarId) {
            case R.id.param1:
                txt1.setText(String.format("%+d", sbParam1.getProgress()));
                break;
            case R.id.param2:
                txt2.setText(String.format("%+d", sbParam2.getProgress()));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

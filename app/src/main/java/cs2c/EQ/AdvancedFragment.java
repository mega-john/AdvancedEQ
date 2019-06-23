package cs2c.EQ;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import cs2c.EQ.Controls.VerticalSeekBar;

public class AdvancedFragment extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private TextView btEqualizer;
    private TextView btBalance;
    private TextView bt1, bt2, bt3;
    private cs2c.EQ.Controls.HorizontalSeekBar sbParam1, sbParam2;
    private TextView txt1, txt2;
    private int width = 0;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Log.d("AdvancedFragment", "onCreate AdvancedFragment");
        setContentView(R.layout.fragment_advanced);

        btEqualizer = (TextView) findViewById(R.id.bt_equalizer);
        btEqualizer.setOnClickListener(this);
        btBalance = (TextView) findViewById(R.id.bt_balance);
        btBalance.setOnClickListener(this);

        LinearLayout lm = (LinearLayout) findViewById(R.id.seekBarsLayout);

        lm.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                // Preventing extra work because method will be called many times.
                if(width == (right - left))
                    return;

                width = (right - left);
                // do something here...
                createContols();
            }
        });

    }

    private void createContols() {
        LinearLayout lm = (LinearLayout) findViewById(R.id.seekBarsLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        Resources r = getResources();

        int w = width / 20;
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25.0f, r.getDisplayMetrics());
//        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, w, r.getDisplayMetrics());
        params.setMargins(0, 80, 0, 80);

        //Create four
        for (int j = 0; j < 10; j++) {
            // Create LinearLayout
            VerticalSeekBar vsb = new VerticalSeekBar(this);
            vsb.setId(j);
            vsb.setLayoutParams(params);
            vsb.setMax(40);
            vsb.setProgress(20);

            lm.addView(vsb);


            // Create Button
//            final Button btn = new Button(this);
//            // Give button an ID
//            btn.setId(j+1);
//            btn.setText("Add To Cart");
//            // set the layoutParams on the button
////            btn.setLayoutParams(params);
//
//            final int index = j;
//            // Set click listener for button
//            btn.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//
//                    Log.i("TAG", "index :" + index);
//
//                    Toast.makeText(getApplicationContext(),
//                            "Clicked Button Index :" + index,
//                            Toast.LENGTH_LONG).show();
//
//                }
//            });
//
//            //Add button to LinearLayout
//            ll.addView(btn);
//            //Add button to LinearLayout defined in XML
//            lm.addView(ll);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_equalizer:
                Intent equalizerIntent = new Intent();
                equalizerIntent.setClass(this, cs2c.EQ.EqualizerFragment.class);
                startActivity(equalizerIntent);
                Log.i("AdvancedFragment", "bt_equalizer");
                break;
            case R.id.bt_balance:
                Intent balanceIntent = new Intent();
                balanceIntent.setClass(this, cs2c.EQ.BalanceFragment.class);
                startActivity(balanceIntent);
                Log.i("AdvancedFragment", "bt_balance");
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
//            case R.id.param1:
//                txt1.setText(String.format("%+d", sbParam1.getProgress()));
//                break;
//            case R.id.param2:
//                txt2.setText(String.format("%+d", sbParam2.getProgress()));
//                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

package cs2c.EQ1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AdvancedFragment extends Activity implements View.OnClickListener {
    private TextView btEqualizer;
    private TextView btBalance;

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
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Log.d("AdvancedFragment", "onCreate AdvancedFragment");
        setContentView(R.layout.fragment_advanced);

        btEqualizer = (TextView) findViewById(R.id.bt_equalizer);
        btEqualizer.setOnClickListener(this);
        btBalance = (TextView) findViewById(R.id.bt_balance);
        btBalance.setOnClickListener(this);
    }
}

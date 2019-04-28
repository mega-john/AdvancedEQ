package cs2c.EQ;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cs2c.EQ.Controls.BalanceCross;

public class BalanceFragment extends Activity implements View.OnClickListener, BalanceCross.OnBalanceChangeListener {

    private BalanceCross balance;
    private TextView btEqualizer;
    private TextView btAdvanced;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Log.d("EQ", "user handle" + android.os.Process.myUserHandle());
        setContentView(R.layout.fragment_balance);

        balance = (BalanceCross) findViewById(R.id.balanceCross);
//        balance.setOnBalanceChangeListener(this);
//        btEqualizer = (TextView) findViewById(R.id.bt_equalizer);
//        btEqualizer.setOnClickListener(this);
//        btAdvanced = (TextView) findViewById(R.id.bt_advanced);
//        btAdvanced.setOnClickListener(this);

        findViewById(R.id.bt_equalizer).setOnClickListener(this);
        findViewById(R.id.bt_advanced).setOnClickListener(this);

        findViewById(R.id.balanceReset).setOnClickListener(this);
        findViewById(R.id.balanceFront).setOnClickListener(this);
        findViewById(R.id.balanceLeft).setOnClickListener(this);
        findViewById(R.id.balanceRight).setOnClickListener(this);
        findViewById(R.id.balanceRear).setOnClickListener(this);

//        return view;
    }

//    @Override
//    protected void update() {
//        updateBalance();
//    }

    public void updateBalance() {
//        int[] parts = parseList(audioManager.getParameters("av_balance="));
//        if (parts.length == 2)
//        {
//            balance.setBalance(parts[0], 28 - parts[1]);
//        }
//        else
//        {
//            balance.setBalance(14, 14);
//        }
    }

    @Override
    public void onBalanceChange(int balanceX, int balanceY, int byUser) {
//        audioManager.setParameters(String.format("av_balance=%d,%d", balanceX, 28 - balanceY));
//        Log.d(Constants.EQInterfaceName, String.format("onBalanceChange(%+d,%+d)", balanceX, balanceY));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.balanceReset:
                balance.setBalance(7, 7);
                break;
            case R.id.balanceFront:
                balance.balanceYdown();
                break;
            case R.id.balanceLeft:
                balance.balanceXdown();
                break;
            case R.id.balanceRight:
                balance.balanceXup();
                break;
            case R.id.balanceRear:
                balance.balanceYup();
                break;
            case R.id.bt_equalizer:
                Intent equalizerIntent = new Intent();
                equalizerIntent.setClass(this, cs2c.EQ.EqualizerFragment.class);
                startActivity(equalizerIntent);
                break;
            case R.id.bt_advanced:
                Intent advancedIntent = new Intent();
                advancedIntent.setClass(this, cs2c.EQ.AdvancedFragment.class);
                startActivity(advancedIntent);
                break;
        }
    }
}


package cs2c.EQ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.cs2c.IEQService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import cs2c.EQ.Controls.VerticalSeekBar;
import cs2c.EQ.Controls.VerticalSeekBar.OnSeekBarChangeListener;

public class EQActivity extends Activity implements OnClickListener, OnSeekBarChangeListener {
    private static int eqValue = 6;
    public static int height = 0;
    private static boolean isSystemWallperSettings = false;
    private static int vbProgress = 0;
    private static int vbProgress1 = 0;
    private static int vbProgress2 = 0;
    private static int vbProgress3 = 0;
    private static int vbProgress4 = 0;
    private static int vbProgress5 = 0;
    private static int vbProgress6 = 0;
    private static int vbProgressValue = 0;
    private static int vbProgressValue1 = 0;
    private static int vbProgressValue2 = 0;
    private static int vbProgressValue3 = 0;
    private static int vbProgressVariable1 = 0;
    private static int vbProgressVariable2 = 0;
    private static int vbProgressVariable3 = 0;
    private static int vbProgressVariable4 = 0;
    private static int vbProgressVariable5 = 0;
    private static int vbProgressVariable6 = 0;
    public static int width = 0;
    private TextView mBalancerTextView;
    DispearA mDispearA;
    private IEQService mEQService;
    private VerticalSeekBar mHighFVSB;
    private VerticalSeekBar mHighVoiceVSB;
    private TextView mHighVoiceValue;
    private VerticalSeekBar mLoudSeekBar;
    private TextView mLoudValue;
    private VerticalSeekBar mLowFVSB;
    private VerticalSeekBar mLowVoiceVSB;
    private TextView mLowVoiceValue;
    private VerticalSeekBar mMiddleFVSB;
    private VerticalSeekBar mMiddleVoiceVSB;
    private TextView mMiddleVoiceValue;
    private OnClickListener mOnClickListener = new OnClickListener() {
        public void onClick(View v) {
            Editor editor = EQActivity.this.preferences.edit();
            if (EQActivity.this.mPWRock == v) {
                EQActivity.eqValue = 1;
            } else if (EQActivity.this.mPWClasses == v) {
                EQActivity.eqValue = 2;
            } else if (EQActivity.this.mPWJazz == v) {
                EQActivity.eqValue = 3;
            } else if (EQActivity.this.mPWFashion == v) {
                EQActivity.eqValue = 4;
            } else if (EQActivity.this.mPWFlat == v) {
                EQActivity.eqValue = 5;
            } else if (EQActivity.this.mPWUser == v) {
                EQActivity.eqValue = 6;
            }
            editor.putInt("eq_value", EQActivity.eqValue);
            EQActivity.this.changeBottomSeekBar(EQActivity.eqValue);
            editor.apply();
            EQActivity.this.displayEQ();
            EQActivity.this.displayEQValue();
            EQActivity.this.setEQValue();
            EQActivity.this.window.dismiss();
        }
    };
    private OnLongClickListener mOnLongClickListener = new OnLongClickListener() {
        public boolean onLongClick(View v) {
            if (EQActivity.width > 800) {
                EQActivity.this.showBigPopWindow();
            } else {
                EQActivity.this.showPopWindow();
            }
            EQActivity.this.mDispearA = new DispearA();
            EQActivity.this.mDispearA.sendEmptyMessageDelayed(1, 5000);
            return true;
        }
    };
    private TextView mPWClasses;
    private TextView mPWFashion;
    private TextView mPWFlat;
    private TextView mPWJazz;
    private TextView mPWRock;
    private TextView mPWUser;
    private TextView mResetTextView;
    private TextView mAdvancedTextView;
    private TextView mRockTextView;
    Switch mSwitch;
    private SharedPreferences preferences;
    private PopupWindow window;

    class DispearA extends Handler {
        DispearA() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    EQActivity.this.window.dismiss();
                    return;
                default:
            }
        }
    }

    @SuppressLint("WrongConstant")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.bt_frame_music_eq);
        if (this.mEQService == null) {
            this.mEQService = (IEQService) getSystemService("eq");
            System.out.println("------------------------------------------------");
        }
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();
        this.preferences = getSharedPreferences("musicEQ", 2);
        initCommonent();
        setOnClick();
        initSeekBarMax();
        this.mRockTextView.setSelected(true);
        eqValue = this.preferences.getInt("eq_value", 5);
        displayEQ();
        displayEQValue();
        changeBottomSeekBar(eqValue);
        LoadAndSetAdvancedValues();
    }

    protected void onResume() {
        super.onResume();
        @SuppressLint("WrongConstant") SharedPreferences settings = getApplicationContext().getSharedPreferences("musicEQ", 2);
        if (this.preferences.getInt("eq_loud", 0) == 1) {
            this.mSwitch.setChecked(true);
        } else {
            this.mSwitch.setChecked(false);
        }
        int progress = this.preferences.getInt("loud_bar", 50);
        this.mLoudValue.setText(String.valueOf(((progress * 14) / 100) - 7));
        this.mLoudSeekBar.setProgress(progress);
        isSystemWallperSettings = settings.getBoolean("musicEQ", false);
        Log.d("lzc", "Mode=2");
        Log.d("lzc", "isSystemWallperSettings=" + isSystemWallperSettings);
    }

    public void displayEQ() {
        switch (eqValue) {
            case 1:
                this.mRockTextView.setText(R.string.rock);
                return;
            case 2:
                this.mRockTextView.setText(R.string.classes);
                return;
            case 3:
                this.mRockTextView.setText(R.string.jazz);
                return;
            case 4:
                this.mRockTextView.setText(R.string.fashion);
                return;
            case 5:
                this.mRockTextView.setText(R.string.flat);
                return;
            case 6:
                this.mRockTextView.setText(R.string.user);
                return;
            default:
                Log.d("lzc", "eqValue=" + eqValue);
        }
    }

    private void setVoiceValue(int[] value) {
        this.mLowVoiceValue.setText(value[0]);
        this.mMiddleVoiceValue.setText(value[1]);
        this.mHighVoiceValue.setText(value[2]);
    }

    private void displayEQValue() {
        switch (eqValue) {
            case 1:
                setVoiceValue(new int[]{R.string.three, R.string.zero, R.string.three, R.string.eighty, R.string.one_khz, R.string.ten_khz});
                return;
            case 2:
                setVoiceValue(new int[]{R.string.three, R.string.negative_four, R.string.one, R.string.eighty, R.string.one_khz, R.string.ten_khz});
                return;
            case 3:
                setVoiceValue(new int[]{R.string.negative_there, R.string.two, R.string.negative_one, R.string.eighty, R.string.one_khz, R.string.ten_khz});
                return;
            case 4:
                setVoiceValue(new int[]{R.string.zero, R.string.five, R.string.three, R.string.eighty, R.string.one_khz, R.string.ten_khz});
                return;
            case 5:
                setVoiceValue(new int[]{R.string.zero, R.string.zero, R.string.zero, R.string.eighty, R.string.one_khz, R.string.ten_khz});
                return;
            case 6:
                vbProgressValue1 = this.preferences.getInt("lowVoiceSBProgressValue", 7) - 7;
                vbProgressValue2 = this.preferences.getInt("middleVoiceSBProgressValue", 7) - 7;
                vbProgressValue3 = this.preferences.getInt("highVoiceSBProgressValue", 7) - 7;
                this.mLowVoiceValue.setText(String.valueOf(vbProgressValue1));
                this.mMiddleVoiceValue.setText(String.valueOf(vbProgressValue2));
                this.mHighVoiceValue.setText(String.valueOf(vbProgressValue3));
                return;
            default:
                Log.d("lzc", "eqValue=" + eqValue);
        }
    }

    private void setEQValue() {
        if (this.mEQService == null) {
            return;
        }
        try {
            switch (eqValue) {
                case 1:
                    this.mEQService.setSound(3, 10);
                    this.mEQService.setSound(2, 7);
                    this.mEQService.setSound(1, 10);
                    this.mEQService.setSound(13, 0);
                    this.mEQService.setSound(12, 1);
                    this.mEQService.setSound(11, 1);
                    return;
                case 2:
                    this.mEQService.setSound(3, 8);
                    this.mEQService.setSound(2, 3);
                    this.mEQService.setSound(1, 10);
                    this.mEQService.setSound(13, 0);
                    this.mEQService.setSound(12, 1);
                    this.mEQService.setSound(11, 1);
                    return;
                case 3:
                    this.mEQService.setSound(3, 6);
                    this.mEQService.setSound(2, 9);
                    this.mEQService.setSound(1, 4);
                    this.mEQService.setSound(13, 0);
                    this.mEQService.setSound(12, 1);
                    this.mEQService.setSound(11, 1);
                    return;
                case 4:
                    this.mEQService.setSound(3, 10);
                    this.mEQService.setSound(2, 12);
                    this.mEQService.setSound(1, 7);
                    this.mEQService.setSound(13, 0);
                    this.mEQService.setSound(12, 1);
                    this.mEQService.setSound(11, 1);
                    return;
                case 5:
                    this.mEQService.setSound(3, 7);
                    this.mEQService.setSound(2, 7);
                    this.mEQService.setSound(1, 7);
                    this.mEQService.setSound(13, 0);
                    this.mEQService.setSound(12, 1);
                    this.mEQService.setSound(11, 1);
                    return;
                case 6:
                    vbProgressVariable1 = this.preferences.getInt("lowFreqSBProgressValue", 1);
                    vbProgressVariable2 = this.preferences.getInt("middleFreqSBProgressValue", 1);
                    vbProgressVariable3 = this.preferences.getInt("highFreqSBProgressValue", 0);
                    vbProgressVariable4 = this.preferences.getInt("lowVoiceSBProgressValue", 7);
                    vbProgressVariable5 = this.preferences.getInt("middleVoiceSBProgressValue", 7);
                    vbProgressVariable6 = this.preferences.getInt("highVoiceSBProgressValue", 7);
                    Log.i("1", String.valueOf(vbProgressVariable4));
                    Log.i("2", String.valueOf(vbProgressVariable5));
                    Log.i("3", String.valueOf(vbProgressVariable6));
                    this.mEQService.setSound(3, vbProgressVariable6);
                    this.mEQService.setSound(2, vbProgressVariable5);
                    this.mEQService.setSound(1, vbProgressVariable4);
                    this.mEQService.setSound(13, vbProgressVariable3);
                    this.mEQService.setSound(12, vbProgressVariable2);
                    this.mEQService.setSound(11, vbProgressVariable1);
                    return;
                default:
                    Log.d("lzc", "eqValue=" + eqValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCommonent() {
        this.mSwitch = (Switch) findViewById(R.id.loud_sw);
        this.mRockTextView = (TextView) findViewById(R.id.bt_music_rock);
        this.mBalancerTextView = (TextView) findViewById(R.id.bt_music_balancer);
        this.mResetTextView = (TextView) findViewById(R.id.bt_music_reset);
        this.mAdvancedTextView = (TextView) findViewById(R.id.bt_music_advanced);
        this.mLowVoiceVSB = (VerticalSeekBar) findViewById(R.id.bt_music_vseekbar1);
        this.mMiddleVoiceVSB = (VerticalSeekBar) findViewById(R.id.bt_music_vseekbar2);
        this.mHighVoiceVSB = (VerticalSeekBar) findViewById(R.id.bt_music_vseekbar3);
        this.mLowFVSB = (VerticalSeekBar) findViewById(R.id.bt_music_vseekbar4);
        this.mMiddleFVSB = (VerticalSeekBar) findViewById(R.id.bt_music_vseekbar5);
        this.mHighFVSB = (VerticalSeekBar) findViewById(R.id.bt_music_vseekbar6);
        this.mLoudSeekBar = (VerticalSeekBar) findViewById(R.id.bt_loud_vseekbar);
        this.mLowVoiceValue = (TextView) findViewById(R.id.bt_music_text_low_voice_value);
        this.mMiddleVoiceValue = (TextView) findViewById(R.id.bt_music_text_middle_voice_value);
        this.mHighVoiceValue = (TextView) findViewById(R.id.bt_music_text_high_voice_value);
        this.mLoudValue = (TextView) findViewById(R.id.bt_loud_text_tottom);
    }

    private void setOnClick() {
        try {
            this.mRockTextView.setOnClickListener(this);
            this.mRockTextView.setOnLongClickListener(this.mOnLongClickListener);
            this.mBalancerTextView.setOnClickListener(this);
            this.mResetTextView.setOnClickListener(this);
            this.mAdvancedTextView.setOnClickListener(this);
            this.mLowVoiceVSB.setOnSeekBarChangeListener(this);
            this.mMiddleVoiceVSB.setOnSeekBarChangeListener(this);
            this.mHighVoiceVSB.setOnSeekBarChangeListener(this);
            this.mLowFVSB.setOnSeekBarChangeListener(this);
            this.mMiddleFVSB.setOnSeekBarChangeListener(this);
            this.mHighFVSB.setOnSeekBarChangeListener(this);
            this.mLoudSeekBar.setOnSeekBarChangeListener(this);
            this.mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Editor editor = EQActivity.this.preferences.edit();
                    if (isChecked) {
                        try {
                            EQActivity.this.mEQService.set_volume(22, 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        editor.putInt("eq_loud", 1);
                        editor.apply();
                        Log.d("EQ", "switch LOUD 1");
                        return;
                    }
                    try {
                        EQActivity.this.mEQService.set_volume(22, 0);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    editor.putInt("eq_loud", 0);
                    editor.commit();
                    Log.d("EQ", "switch LOUD 0");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d("EQ", ex.getMessage() + " ");
        }
    }

    public void showBigPopWindow() {
        @SuppressLint("WrongConstant") View v = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popwindow_frame, null);
        Log.d("TAG", "gggggggggggggggggggggggggggggggggggg");
        this.window = new PopupWindow(v, 942, 160);
        this.mPWRock = (TextView) v.findViewById(R.id.eq_music_rock);
        this.mPWClasses = (TextView) v.findViewById(R.id.eq_music_class);
        this.mPWJazz = (TextView) v.findViewById(R.id.eq_music_jazz);
        this.mPWFashion = (TextView) v.findViewById(R.id.eq_music_fashion);
        this.mPWFlat = (TextView) v.findViewById(R.id.eq_music_flat);
        this.mPWUser = (TextView) v.findViewById(R.id.eq_music_option);
        displayEQSelected();
        diaplayEQColor();
        this.mPWRock.setOnClickListener(this.mOnClickListener);
        this.mPWClasses.setOnClickListener(this.mOnClickListener);
        this.mPWJazz.setOnClickListener(this.mOnClickListener);
        this.mPWFashion.setOnClickListener(this.mOnClickListener);
        this.mPWFlat.setOnClickListener(this.mOnClickListener);
        this.mPWUser.setOnClickListener(this.mOnClickListener);
        this.window.setFocusable(true);
        this.window.showAtLocation(this.mRockTextView, 0, 38, 275);
    }

    public void showPopWindow() {
        @SuppressLint("WrongConstant") View v = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.popwindow_frame, null);
        Log.d("TAG", "ddddddddddddddddddddddddddddddddd");
        this.window = new PopupWindow(v, 702, 125);
        this.mPWRock = (TextView) v.findViewById(R.id.eq_music_rock);
        this.mPWClasses = (TextView) v.findViewById(R.id.eq_music_class);
        this.mPWJazz = (TextView) v.findViewById(R.id.eq_music_jazz);
        this.mPWFashion = (TextView) v.findViewById(R.id.eq_music_fashion);
        this.mPWFlat = (TextView) v.findViewById(R.id.eq_music_flat);
        this.mPWUser = (TextView) v.findViewById(R.id.eq_music_option);
        displayEQSelected();
        diaplayEQColor();
        this.mPWRock.setOnClickListener(this.mOnClickListener);
        this.mPWClasses.setOnClickListener(this.mOnClickListener);
        this.mPWJazz.setOnClickListener(this.mOnClickListener);
        this.mPWFashion.setOnClickListener(this.mOnClickListener);
        this.mPWFlat.setOnClickListener(this.mOnClickListener);
        this.mPWUser.setOnClickListener(this.mOnClickListener);
        this.window.setFocusable(true);
        this.window.showAtLocation(this.mRockTextView, 0, 45, 275);
    }

    public void displayEQSelected() {
        switch (eqValue) {
            case 1:
                this.mPWRock.setSelected(true);
                this.mPWClasses.setSelected(false);
                this.mPWJazz.setSelected(false);
                this.mPWFashion.setSelected(false);
                this.mPWFlat.setSelected(false);
                this.mPWUser.setSelected(false);
                return;
            case 2:
                this.mPWRock.setSelected(false);
                this.mPWClasses.setSelected(true);
                this.mPWJazz.setSelected(false);
                this.mPWFashion.setSelected(false);
                this.mPWFlat.setSelected(false);
                this.mPWUser.setSelected(false);
                return;
            case 3:
                this.mPWRock.setSelected(false);
                this.mPWClasses.setSelected(false);
                this.mPWJazz.setSelected(true);
                this.mPWFashion.setSelected(false);
                this.mPWFlat.setSelected(false);
                this.mPWUser.setSelected(false);
                return;
            case 4:
                this.mPWRock.setSelected(false);
                this.mPWClasses.setSelected(false);
                this.mPWJazz.setSelected(false);
                this.mPWFashion.setSelected(true);
                this.mPWFlat.setSelected(false);
                this.mPWUser.setSelected(false);
                return;
            case 5:
                this.mPWRock.setSelected(false);
                this.mPWClasses.setSelected(false);
                this.mPWJazz.setSelected(false);
                this.mPWFashion.setSelected(false);
                this.mPWFlat.setSelected(true);
                this.mPWUser.setSelected(false);
                return;
            case 6:
                this.mPWRock.setSelected(false);
                this.mPWClasses.setSelected(false);
                this.mPWJazz.setSelected(false);
                this.mPWFashion.setSelected(false);
                this.mPWFlat.setSelected(false);
                this.mPWUser.setSelected(true);
                return;
            default:
                Log.d("lzc", "eqValue=" + eqValue);
        }
    }

    public void diaplayEQColor() {
        switch (eqValue) {
            case 1:
                this.mPWRock.setTextColor(getResources().getColor(R.color.blue));
                return;
            case 2:
                this.mPWClasses.setTextColor(getResources().getColor(R.color.blue));
                return;
            case 3:
                this.mPWJazz.setTextColor(getResources().getColor(R.color.blue));
                return;
            case 4:
                this.mPWFashion.setTextColor(getResources().getColor(R.color.blue));
                return;
            case 5:
                this.mPWFlat.setTextColor(getResources().getColor(R.color.blue));
                return;
            case 6:
                this.mPWUser.setTextColor(getResources().getColor(R.color.blue));
                return;
            default:
                Log.d("lzc", "eqValue=" + eqValue);
        }
    }

    private void initSeekBarMax() {
        this.mLowVoiceVSB.setMax(100);
        this.mMiddleVoiceVSB.setMax(100);
        this.mHighVoiceVSB.setMax(100);
        this.mLowFVSB.setMax(100);
        this.mMiddleFVSB.setMax(100);
        this.mHighFVSB.setMax(100);
        this.mLoudSeekBar.setMax(100);
    }

    private void changeBottomSeekBar(int paramInt) {
        switch (paramInt) {
            case 1:
                this.mLowVoiceVSB.setProgress(66);
                this.mMiddleVoiceVSB.setProgress(46);
                this.mHighVoiceVSB.setProgress(66);
                this.mLowFVSB.setProgress(25);
                this.mMiddleFVSB.setProgress(25);
                this.mHighFVSB.setProgress(0);
                return;
            case 2:
                this.mLowVoiceVSB.setProgress(67);
                this.mMiddleVoiceVSB.setProgress(20);
                this.mHighVoiceVSB.setProgress(53);
                this.mLowFVSB.setProgress(25);
                this.mMiddleFVSB.setProgress(25);
                this.mHighFVSB.setProgress(0);
                return;
            case 3:
                this.mLowVoiceVSB.setProgress(26);
                this.mMiddleVoiceVSB.setProgress(60);
                this.mHighVoiceVSB.setProgress(40);
                this.mLowFVSB.setProgress(25);
                this.mMiddleFVSB.setProgress(25);
                this.mHighFVSB.setProgress(0);
                return;
            case 4:
                this.mLowVoiceVSB.setProgress(46);
                this.mMiddleVoiceVSB.setProgress(80);
                this.mHighVoiceVSB.setProgress(67);
                this.mLowFVSB.setProgress(25);
                this.mMiddleFVSB.setProgress(25);
                this.mHighFVSB.setProgress(0);
                return;
            case 5:
                this.mLowVoiceVSB.setProgress(50);
                this.mMiddleVoiceVSB.setProgress(50);
                this.mHighVoiceVSB.setProgress(50);
                this.mLowFVSB.setProgress(25);
                this.mMiddleFVSB.setProgress(25);
                this.mHighFVSB.setProgress(0);
                return;
            case 6:
                vbProgress1 = this.preferences.getInt("lowVoiceSBProgress", 50);
                vbProgress2 = this.preferences.getInt("middleVoiceSBProgress", 50);
                vbProgress3 = this.preferences.getInt("highVoiceSBProgress", 50);
                vbProgress4 = this.preferences.getInt("lowFreqSBProgress", 25);
                vbProgress5 = this.preferences.getInt("middleFreqSBProgress", 25);
                vbProgress6 = this.preferences.getInt("highFreqSBProgress", 0);
                this.mLowVoiceVSB.setProgress(vbProgress1);
                this.mMiddleVoiceVSB.setProgress(vbProgress2);
                this.mHighVoiceVSB.setProgress(vbProgress3);
                this.mLowFVSB.setProgress(vbProgress4);
                this.mMiddleFVSB.setProgress(vbProgress5);
                this.mHighFVSB.setProgress(vbProgress6);
                return;
            default:
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_music_rock:
                changePlayEQ();
                return;
            case R.id.bt_music_balancer:
                Intent balancerIntent = new Intent();
                balancerIntent.setClass(this, Balancer.class);
                startActivity(balancerIntent);
                return;
            case R.id.bt_music_advanced:
                Intent advancedIntent = new Intent();
                advancedIntent.setClass(this, Advanced.class);
                startActivity(advancedIntent);
                return;
            case R.id.bt_music_reset:
                eqValue = 6;
                Editor editor = this.preferences.edit();
                editor.putInt("lowVoiceSBProgress", 50);
                editor.putInt("middleVoiceSBProgress", 50);
                editor.putInt("highVoiceSBProgress", 50);
                editor.putInt("lowFreqSBProgress", 25);
                editor.putInt("middleFreqSBProgress", 25);
                editor.putInt("highFreqSBProgress", 0);
                editor.putInt("lowVoiceSBProgressValue", 7);
                editor.putInt("middleVoiceSBProgressValue", 7);
                editor.putInt("highVoiceSBProgressValue", 7);
                editor.putInt("lowFreqSBProgressValue", 1);
                editor.putInt("middleFreqSBProgressValue", 1);
                editor.putInt("highFreqSBProgressValue", 0);
                editor.putInt("eq_value", eqValue);
                editor.putInt("loud_bar", 50);
                editor.putInt("eq_loud", 0);
                editor.apply();
                this.mLoudValue.setText("0");
                this.mLoudSeekBar.setProgress(50);
                this.mSwitch.setChecked(false);
                try {
                    if (this.mEQService != null) {
                        this.mEQService.set_volume(22, 0);
                        this.mEQService.set_volume(6, 7);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                displayEQ();
                displayEQValue();
                setEQValue();
                changeBottomSeekBar(eqValue);
                return;
            default:
        }
    }

    public void changePlayEQ() {
        eqValue++;
        if (eqValue > 6) {
            eqValue = 1;
        }
        Editor editor = this.preferences.edit();
        editor.putInt("eq_value", eqValue);
        editor.apply();
        displayEQ();
        displayEQValue();
        setEQValue();
        changeBottomSeekBar(eqValue);
    }

    public void jumpUserEQ() {
        eqValue = 6;
        Editor editor = this.preferences.edit();
        editor.putInt("eq_value", eqValue);
        editor.apply();
        displayEQ();
        displayEQValue();
        setEQValue();
        changeBottomSeekBar(eqValue);
    }

    public void onProgressChanged(VerticalSeekBar VerticalSeekBar, int progress, boolean fromUser) {
        vbProgress = progress;
        Log.i("royu", String.valueOf(vbProgress));
    }

    public void onStartTrackingTouch(VerticalSeekBar VerticalSeekBar) {
    }

    public void onStopTrackingTouch(VerticalSeekBar VerticalSeekBar) {
        VerticalSeekBar.setMax(100);
        VerticalSeekBar.setProgress(vbProgress);
        Log.i("EQ", "onStopTrackingTouch: " + String.valueOf(vbProgress));
        Editor editor;
        switch (VerticalSeekBar.getId()) {
            case R.id.bt_music_vseekbar1:
                vbProgressValue = (vbProgress * 14) / 100;
                Log.i("EQ", "R.id.bt_music_vseekbar1:" + String.valueOf(vbProgressValue));
                editor = this.preferences.edit();
                editor.putInt("lowVoiceSBProgress", vbProgress);
                editor.putInt("lowVoiceSBProgressValue", vbProgressValue);
                editor.apply();
                break;
            case R.id.bt_music_vseekbar2:
                vbProgressValue = (vbProgress * 14) / 100;
                Log.i("vbProgressValue=", String.valueOf(vbProgressValue));
                Editor editor1 = this.preferences.edit();
                editor1.putInt("middleVoiceSBProgress", vbProgress);
                editor1.putInt("middleVoiceSBProgressValue", vbProgressValue);
                editor1.apply();
                break;
            case R.id.bt_music_vseekbar3:
                vbProgressValue = (vbProgress * 14) / 100;
                Editor editor2 = this.preferences.edit();
                editor2.putInt("highVoiceSBProgress", vbProgress);
                editor2.putInt("highVoiceSBProgressValue", vbProgressValue);
                editor2.apply();
                break;
            case R.id.bt_music_vseekbar4:
                vbProgress = swapToFV(vbProgress);
                vbProgressValue = vbProgress / 25;
                if (vbProgressValue > 3) {
                    vbProgressValue = 3;
                }
                Editor editor3 = this.preferences.edit();
                editor3.putInt("lowFreqSBProgress", vbProgress);
                editor3.putInt("lowFreqSBProgressValue", vbProgressValue);
                editor3.apply();
                break;
            case R.id.bt_music_vseekbar5:
                vbProgress = swapToFV(vbProgress);
                vbProgressValue = vbProgress / 25;
                if (vbProgressValue > 3) {
                    vbProgressValue = 3;
                }
                Editor editor4 = this.preferences.edit();
                editor4.putInt("middleFreqSBProgress", vbProgress);
                editor4.putInt("middleFreqSBProgressValue", vbProgressValue);
                editor4.apply();
                break;
            case R.id.bt_music_vseekbar6:
                vbProgress = swapToFV(vbProgress);
                vbProgressValue = vbProgress / 25;
                if (vbProgressValue > 3) {
                    vbProgressValue = 3;
                }
                Editor editor5 = this.preferences.edit();
                editor5.putInt("highFreqSBProgress", vbProgress);
                editor5.putInt("highFreqSBProgressValue", vbProgressValue);
                editor5.apply();
                break;
            case R.id.bt_loud_vseekbar:
                vbProgressValue = ((vbProgress * 14) / 100) - 7;
                editor = this.preferences.edit();
                this.mLoudValue.setText(String.valueOf(vbProgressValue));
                try {
                    this.mEQService.set_volume(6, vbProgressValue + 7);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                editor.putInt("loud_bar", vbProgress);
                editor.commit();
                Log.i("EQ", "R.id.bt_loud_vseekbar" + vbProgressValue);
                return;
        }
        jumpUserEQ();
    }

    private int swapToFV(int value) {
        if (value <= 25) {
            return 0;
        }
        if (value <= 50) {
            return 33;
        }
        if (value > 75) {
            return 100;
        }
        return 66;
    }


    public void LoadAndSetAdvancedValues() {
        int var2;
        int v1 = this.preferences.getInt(Constants.sbQ_bassProgressValue, 0);
        int v2 = this.preferences.getInt(Constants.sbFo_bassProgressValue, 0);
        var2 = (v2 << 4) + v1;
        try {
            mEQService.set_volume(0x17, var2);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("LoadAndSetAdvancedValue", ex.getMessage() + " ");
        }

        v1 = this.preferences.getInt(Constants.sbQ_middleProgressValue, 0);
        v2 = this.preferences.getInt(Constants.sbFo_middleProgressValue, 0);
        var2 = (v2 << 4) + v1;
        try {
            mEQService.set_volume(0x18, var2);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("LoadAndSetAdvancedValue", ex.getMessage() + " ");
        }

        v1 = this.preferences.getInt(Constants.sbQ_trebleProgressValue, 0);
        v2 = this.preferences.getInt(Constants.sbFo_trebleProgressValue, 0);
        var2 = (v2 << 4) + v1;
        try {
            mEQService.set_volume(0x19, var2);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e("LoadAndSetAdvancedValue", ex.getMessage() + " ");
        }
    }
}
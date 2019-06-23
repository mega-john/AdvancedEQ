package cs2c.EQ.Service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.cs2c.IEQService;
import android.util.Log;

import cs2c.EQ.Settings;

public final class EQServiceProxy {

    private static IEQService mEQService;

    @SuppressLint("WrongConstant")
    public static void Initialize(Activity activity) {
        if (mEQService == null) {
            mEQService = (IEQService) activity.getSystemService(Settings.EQInterfaceName);
        }
    }

    @SuppressLint("WrongConstant")
    public static void Initialize(Context context) {
        if (mEQService == null) {
            mEQService = (IEQService) context.getSystemService(Settings.EQInterfaceName);
        }
    }

    public static void setSound(int register, int value) {
        try {
            Log.d(Settings.EQInterfaceName, String.format("setSound(%d, %d)", register, value));
            mEQService.setSound(register, value);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    //используется для всех настроек баланса
    public static void setSurround(int register, int value) {
        try {
            Log.d(Settings.EQInterfaceName, String.format("setSurround(%d, %d)", register, value));
            mEQService.setSurround(register, value);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }


    //используется для loud on/off и новых настроек эквалайзера
    public static void set_volume(int register, int value) {
        try {
            Log.d(Settings.EQInterfaceName, String.format("set_volume(%d, %d)", register, value));
            mEQService.set_volume(register, value);
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}

//регулировка низких, средних и высоких частот
//bass seek:
//min setSound(1, 0)
//max setSound(1, 14)
//middle seek:
//min setSound(2, 0)
//max setSound(2, 14)
//treble seek:
//min setSound(3, 0)
//max setSound(3, 14)
//при любом изменении этих прогресов, так же вызывается:
//setSound(13, 0)
//setSound(12, 1)
//setSound(11, 1)
//регулировка сабвуфера
//sub seek
//min set_volume(6, 0)
//max set_volume(6, 14)
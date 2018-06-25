package cs2c.EQ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

//import android.cs2c.IEQService;
//import android.os.RemoteException;

public final class EQServiceProxy {

    private static IEQService mEQService;

    @SuppressLint("WrongConstant")
    public static void Initialize(Activity activity) {
        if (mEQService == null) {
            mEQService = (IEQService) activity.getSystemService(Constants.EQInterfaceName);
        }
    }

    @SuppressLint("WrongConstant")
    public static void Initialize(Context context) {
        if (mEQService == null) {
            mEQService = (IEQService) context.getSystemService(Constants.EQInterfaceName);
        }
    }

    public static void setSound(int var1, int var2) {
        try {
            Log.d(Constants.EQInterfaceName, String.format("setSound(%d, %d)", var1, var2));
            mEQService.setSound(var1, var2);
        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(Constants.EQInterfaceName, String.format("setSound exception:", e.toString()));
        }
    }

    public static void setSurround(int var1, int var2) {
        try {
            Log.d(Constants.EQInterfaceName, String.format("setSurround(%d, %d)", var1, var2));
            mEQService.setSurround(var1, var2);
        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(Constants.EQInterfaceName, String.format("setSurround exception:", e.toString()));
        }
    }

    public static void set_volume(int var1, int var2) {
        try {
//            Log.d(Constants.EQInterfaceName, String.format("set_volume(%d, %d)", var1, var2));
            mEQService.set_volume(var1, var2);
        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e(Constants.EQInterfaceName, String.format("set_volume exception:", e.toString()));
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
//setSound(13, 7)
//setSound(12, 7)
//setSound(11, 14)
//регулировка сабвуфера
//sub seek
//min set_volume(6, 0)
//max set_volume(6, 14)
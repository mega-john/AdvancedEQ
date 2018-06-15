package cs2c.EQ;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

//import android.cs2c.IEQService;
//import android.os.RemoteException;

public class EQServiceProxy {

    private IEQService mEQService;

    @SuppressLint("WrongConstant")
    public EQServiceProxy(Activity activity) {
        if (mEQService == null) {
            mEQService = (IEQService) activity.getSystemService(Constants.EQInterfaceName);
        }
    }

    public void setSound(int var1, int var2) {
        try {
            Log.d(Constants.EQInterfaceName, String.format("setSound(%d, %d)", var1, var2));
//            mEQService.setSound(var1, var2);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.EQInterfaceName, String.format("setSound exception:", e.toString()));
        }
    }

    public void setSurround(int var1, int var2) {
        try {
            Log.d(Constants.EQInterfaceName, String.format("setSurround(%d, %d)", var1, var2));
//            mEQService.setSurround(var1, var2);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.EQInterfaceName, String.format("setSurround exception:", e.toString()));
        }
    }

    public void set_volume(int var1, int var2) {
        try {
            Log.d(Constants.EQInterfaceName, String.format("set_volume(%d, %d)", var1, var2));
//            mEQService.set_volume(var1, var2);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(Constants.EQInterfaceName, String.format("set_volume exception:", e.toString()));
        }
    }
}

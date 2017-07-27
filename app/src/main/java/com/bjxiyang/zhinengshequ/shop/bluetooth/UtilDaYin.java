package com.bjxiyang.zhinengshequ.shop.bluetooth;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.gprinter.aidl.GpService;
import com.gprinter.service.GpPrintService;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class UtilDaYin {
    private GpService mGpService = null;
    public static final String CONNECT_STATUS = "connect.status";
    private static final String DEBUG_TAG = "MainActivity";
    private PrinterServiceConnection conn = null;
    private int mPrinterIndex = 0;
    private int mTotalCopies = 0;

    class PrinterServiceConnection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i("ServiceConnection", "onServiceDisconnected() called");
            mGpService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mGpService = GpService.Stub.asInterface(service);
        }
    }
    public void connection1(Context context) {
        conn = new PrinterServiceConnection();
        Intent intent = new Intent(context, GpPrintService.class);
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE); // bindService
        try {
            mGpService.isUserExperience(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}

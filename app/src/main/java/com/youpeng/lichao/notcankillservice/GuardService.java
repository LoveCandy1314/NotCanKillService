package com.youpeng.lichao.notcankillservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * @author Created by lichao
 * @desc
 * @time 2017/11/21 15:00
 * 邮箱：lichao@voole.com
 */

public class GuardService extends Service {
    private static final  String TAG = GuardService.class.getName();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {};
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    Log.d(TAG,"in GuardService is runining...");
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //绑定建立连接
        bindService(new Intent(GuardService.this,WorkService.class),mServiceConnect, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    ServiceConnection mServiceConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"in GuardService is 建立连接.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"in GuardService is 取消连接");
            //断开连接后需要重新启动,再去绑定
            startService(new Intent(GuardService.this,WorkService.class));
            bindService(new Intent(GuardService.this,WorkService.class),mServiceConnect, Context.BIND_IMPORTANT);
        }
    };
}

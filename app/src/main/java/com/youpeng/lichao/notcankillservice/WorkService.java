package com.youpeng.lichao.notcankillservice;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

/**
 * @author lichao
 */
public class WorkService extends Service {
    private static final  String TAG = WorkService.class.getName();
    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    Log.d(TAG,"in WorkService is runining...");
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    ServiceConnection mServiceConnect = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"in WorkService is 建立连接.");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"in WorkService is 取消连接");
            //断开连接后需要重新启动,再去绑定
            startService(new Intent(WorkService.this,GuardService.class));
            bindService(new Intent(WorkService.this,GuardService.class),mServiceConnect,Context.BIND_IMPORTANT);
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bindService(new Intent(this,GuardService.class),mServiceConnect, Context.BIND_IMPORTANT);
        //粘性启动
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return  new ProcessConnection.Stub() {};
    }
}

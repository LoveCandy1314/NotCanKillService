package com.youpeng.lichao.notcankillservice;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //启动两个service,相互绑定
        startService(new Intent(this,WorkService.class));
        startService(new Intent(this,GuardService.class));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            //必须大于5.0
            startService(new Intent(this,JobWakeUpService.class));
        }
    }
}

package com.youpeng.lichao.notcankillservice;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.List;

/**
 * @author Created by lichao
 * @desc
 * @time 2017/11/21 15:16
 * 邮箱：lichao@voole.com
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeUpService extends JobService {
    private static final  String TAG = JobWakeUpService.class.getName();
    private final int jobWakeUpId = 1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //开启一个轮寻
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo.Builder jobInfo = new JobInfo.Builder(jobWakeUpId,new ComponentName(this,JobWakeUpService.class));
        jobInfo.setPeriodic(2000);
        jobScheduler.schedule(jobInfo.build());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        boolean workServiceAlive = serviceAlive(WorkService.class.getName());
        Log.d(TAG,"in JobWakeUpService ..onStartJob  .workServiceAlive is " + workServiceAlive);
        if (!workServiceAlive){
            startService(new Intent(this,WorkService.class));
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
    /**
     * 判断某个服务是否正在运行的方法
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean serviceAlive(String serviceName){
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList =  myAM.getRunningServices(100);
        if (myList.size()<=0){
            return  false;
        }
        for (ActivityManager.RunningServiceInfo runningServiceInfo : myList) {
            String mName = runningServiceInfo.service.getClassName().toString();
            if (mName.equals(serviceName)){
                isWork =true;
                break;
            }
        }
        return isWork;
    }
}

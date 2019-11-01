package com.android.todayinformation;

import android.os.Handler;

public class CustomCountDownTimer implements Runnable{

    private int time;
    private int countDowntime;
    private final ICountDownHandler countDownHandler;
    private boolean isRun;
    private final Handler handler;
    //1.时时取回点 这个时候是什么时间 倒计时到几秒 观察者设计模式
    //2.支持动态传入时间
    //3.每过一秒总秒数减一
    //4.总时间倒计时为0时，要回调完成的状态

    public CustomCountDownTimer(int time,ICountDownHandler countDownHandler){
        handler = new Handler();
        this.time = time;
        this.countDowntime = time;
        this.countDownHandler = countDownHandler;


    }

    //实现具体逻辑
    @Override
    public void run() {
        if (isRun){
            if (countDownHandler != null){
                countDownHandler.onTicker(countDowntime);
            }
            if (countDowntime == 0){
                cancel();
                if (countDownHandler != null){
                    countDownHandler.onFinish();
                }
            }else {
                countDowntime = time--;
                handler.postDelayed(this,1000);
            }
        }
    }
    //开始循环
    public void start(){
        isRun = true;
        handler.post(this);
    }
    //跳出循环 终止
    public void cancel(){
        isRun = false;
        handler.removeCallbacks(this);
    }
//观察者回调接口（IOC 数据回调）
    public interface ICountDownHandler{
        //倒计时回调
        void onTicker(int time);
        //完成时回调
        void onFinish();
    }
}

package com.devedroy.serviceapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MyService extends Service {

    private int mRandomNumber;
    private boolean mIsRandomNumberGeneratorOn;

    private final int MIN = 0;
    private final int MAX = 100;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MyService", "in onStartCommand " + Thread.currentThread().getId());
        mIsRandomNumberGeneratorOn = true;
        new Thread(() -> {
            startRandomNumberGenerator();
        }).start();

        //see flags
        return START_STICKY;
    }

    class MyServiceBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private IBinder mIBinder = new MyServiceBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.i("MyService", "Service destroyed.");
    }

    private void startRandomNumberGenerator() {
        while (mIsRandomNumberGeneratorOn) {
            try {
                Thread.sleep(1000);
                if (mIsRandomNumberGeneratorOn) {
                    mRandomNumber = new Random().nextInt(MAX) + MIN;
                    Log.i("MyService", "in onStartCommand " + Thread.currentThread().getId() + "Random number" + mRandomNumber);
                }
            } catch (InterruptedException e) {
                Log.i("MyService", "Thread Interrupted");
            }
        }
    }

    private void stopRandomNumberGenerator() {
        mIsRandomNumberGeneratorOn = false;
    }

    public int getRandomNumber() {
        return mRandomNumber;
    }
}

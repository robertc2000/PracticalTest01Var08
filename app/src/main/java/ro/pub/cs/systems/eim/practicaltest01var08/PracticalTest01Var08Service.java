package ro.pub.cs.systems.eim.practicaltest01var08;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Date;
import java.util.Random;

public class PracticalTest01Var08Service extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String answer = intent.getStringExtra("answer");
        ProcessingThread t = new ProcessingThread(this, answer);
        t.start();
        return START_REDELIVER_INTENT;
    }

}

/*class MyBroadcastReceiver extends BroadcastReceiver {
    int sum;

    public MyBroadcastReceiver(int sum) {
        this.sum = sum;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            // handle the intent here
            int s = intent.getIntExtra("sum", -1);
            if (s != -1) {
                this.sum = s;
                Log.d("From service", "Received data: " + this.sum);
            }
        }
    }
}*/

class ProcessingThread extends Thread {

    private Context context;
    private String data;

    public ProcessingThread(Context context, String data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public void run() {
        Log.d("From service", "starting now");

       /* receiver = new MyBroadcastReceiver(sum);

        IntentFilter filter = new IntentFilter();
        filter.addAction("Default");

        context.registerReceiver(receiver, filter);*/

        while(true) {
            sleep();
            sendMessage();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void sendMessage() {
        Random rand = new Random();
        int randomNum = rand.nextInt((data.length()));

        String first = "", second = "";
        for(int i = 0; i < randomNum-1; i++) {
            first = first + "*";
        }

        for(int i = randomNum+1; i < data.length(); i++) {
            second = second + "*";
        }

        String result = first + new String(String.valueOf(data.charAt(randomNum))) + second;

        Intent intent = new Intent();
        intent.setAction("default");
        intent.putExtra("data", result);

        context.sendBroadcast(intent);

        Log.d("From service", "Sending data: " + result);
    }
}
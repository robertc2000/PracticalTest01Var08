package ro.pub.cs.systems.eim.practicaltest01var08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var02PlayActivity extends AppCompatActivity {
    String riddle, answer;
    Intent intent;
    IntentFilter intentFilter = new IntentFilter();

    BroadcastReceiverFromService broadcastReceiver;

    private ButtonListener buttonListener = new ButtonListener();

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.check_button:
                    process_check_button();
                    break;
                case R.id.back_button:
                    process_back_button();
                    break;

            }
        }
    }

    private void process_check_button() {
        String myAnswer = ((EditText) findViewById(R.id.edit_text)).getText().toString();
        if (answer.equals(myAnswer)) {
            Toast.makeText(this, "Correct", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Wrong", Toast.LENGTH_LONG).show();
        }

    }

    private void process_back_button() {
        /*intent.putExtra("riddle", riddle);
        intent.putExtra("answer", answer);*/
        setResult(0, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var02_play);

        findViewById(R.id.check_button).setOnClickListener(buttonListener);
        findViewById(R.id.back_button).setOnClickListener(buttonListener);

        broadcastReceiver = new BroadcastReceiverFromService(this);
        intentFilter.addAction("default");

        intent = getIntent();
        if (intent != null) {
            riddle = intent.getStringExtra("riddle");
            answer = intent.getStringExtra("answer");
        }

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01var08",
                "ro.pub.cs.systems.eim.practicaltest01var08.PracticalTest01Var08Service"));
        intent.putExtra("answer", answer);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("ro.pub.cs.systems.eim.practicaltest01var08",
                "ro.pub.cs.systems.eim.practicaltest01var08.PracticalTest01Var08Service"));
        stopService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("From activity", "registering receiver");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("From activity", "unregistering receiver");
        unregisterReceiver(broadcastReceiver);
    }
}

class BroadcastReceiverFromService extends BroadcastReceiver {
    PracticalTest01Var02PlayActivity a;
    public BroadcastReceiverFromService(PracticalTest01Var02PlayActivity a) {
        this.a = a;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        String data = "";
        if (action.equals("default")) {
            data = intent.getStringExtra("data");
        }

        Toast.makeText(this.a, data, Toast.LENGTH_LONG).show();
        Log.d("Received from service:", data);
    }
}
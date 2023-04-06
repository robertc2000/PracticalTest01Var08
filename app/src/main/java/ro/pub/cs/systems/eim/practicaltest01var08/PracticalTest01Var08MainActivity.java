package ro.pub.cs.systems.eim.practicaltest01var08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class PracticalTest01Var08MainActivity extends AppCompatActivity {

    private ButtonListener buttonListener = new ButtonListener();

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.play:
                    process_play_button();
                    break;
                /*case R.id.add_button:
                    process_add_button();
                    break;*/

            }
        }
    }

    private void process_play_button() {
        EditText t = (EditText) findViewById(R.id.riddle);
        String riddle = t.getText().toString();

        t = (EditText) findViewById(R.id.answer);
        String answer = t.getText().toString();

        if (riddle.length() == 0 || answer.length() == 0) {
            return;
        }

        Intent intent = new Intent("android.intent.action.SECONDARY");
        intent.putExtra("riddle", riddle);
        intent.putExtra("answer", answer);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var08_main);

        findViewById(R.id.play).setOnClickListener(buttonListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        String riddle = intent.getStringExtra("riddle");
        String answer = intent.getStringExtra("answer");

        EditText t = (EditText) findViewById(R.id.riddle);
        t.setText(riddle);

        t = (EditText) findViewById(R.id.answer);
        t.setText(answer);
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        System.out.println("App is saved");

        EditText t = (EditText) findViewById(R.id.riddle);
        String riddle = t.getText().toString();

        t = (EditText) findViewById(R.id.answer);
        String answer = t.getText().toString();

        savedInstanceState.putString("riddle", riddle);
        savedInstanceState.putString("answer", answer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        System.out.println("App is restored");

        String riddle = savedInstanceState.getString("riddle");
        String answer = savedInstanceState.getString("answer");

        EditText t = (EditText) findViewById(R.id.riddle);
        if (t.isSaveEnabled()) {
            t.setText(riddle);
        }


        t = (EditText) findViewById(R.id.answer);
        if (t.isSaveEnabled()) {
            t.setText(answer);
        }
    }
}
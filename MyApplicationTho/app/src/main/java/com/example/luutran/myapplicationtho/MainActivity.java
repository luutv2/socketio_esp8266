package com.example.luutran.myapplicationtho;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    Switch onOffSwitch;
    TextView textView;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.109:3000");
        } catch (URISyntaxException e) {}
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.textView);

        mSocket.connect();
        mSocket.on("Led on off", onNewMessage);

        onOffSwitch = (Switch)findViewById(R.id.on_off_Switch);
        onOffSwitch.setOnCheckedChangeListener(this);
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            // do something when check is selected
            mSocket.emit("android send", 1);
        } else {
            //do something when unchecked
            mSocket.emit("android send", 0);
        }
    }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String onoff;
                    try {
                        onoff = data.getString("onoff");
                        if(onoff == "LOW"){
                            textView.setText("led off");
                        }else{
                            textView.setText("led on");
                        }
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
}

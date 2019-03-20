package com.example.basicapp;

import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;



import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSpeak = findViewById(R.id.button_speak);
//        Spinner mySpinner = (Spinner) findViewById(R.id.drop);
//        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(MainActivity.this,
//                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.drop_list));
//        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mySpinner.setAdapter(myadapter);
//        final String spinnerText = mySpinner.getSelectedItem().toString();
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
//                    Set<Voice> v = mTTS.getVoices();
                    int flag = 0;
                    Log.e("TTS","Default voice:" + mTTS.getVoice().getName());
//                    if (spinnerText.equals("male")){
                        for (Voice tmpVoice : mTTS.getVoices()) {
                            Log.e("TTS", "voice: " + tmpVoice.toString());
                            // en-us-x-sfg#male_2-local
                            if (tmpVoice.getName().equals("pt-br-x-afs#male_2-local")) {
                                mTTS.setVoice(tmpVoice);
                                flag = 1;
                                break;
                            }
                        }
//                    }
                    if(flag==0)
                        System.out.print("Default voice");
                    else{
                        System.out.print("Success");
                        Log.e("TTS", "Voice supported");
                    }
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mEditText = findViewById(R.id.edit_text);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
    }

    private void speak() {
        String text = mEditText.getText().toString();
        float pitch = (float) mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        System.out.print(text);
        Log.e("TTS", text);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }
}
package org.dronix.servocardboard;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static final int REQUEST_OK_REC_VOICE = 1;
    private TextToSpeech mTts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTts = new TextToSpeech(getBaseContext(), this);
        mTts.setOnUtteranceProgressListener(utteranceProgressListener);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            mTts.setLanguage(Locale.ENGLISH);
            HashMap<String, String> myHashAlarm = new HashMap<>();
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "INIT");
            mTts.speak(getString(R.string.start), TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        } else {
            Toast.makeText(this, "TTS Initialization failed", Toast.LENGTH_SHORT).show();
        }
    }

    private UtteranceProgressListener utteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {

        }

        @Override
        public void onDone(String utteranceId) {
            Intent recVoiceIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recVoiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
            recVoiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            recVoiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ITALIAN);
            try {
                startActivityForResult(recVoiceIntent, REQUEST_OK_REC_VOICE);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onError(String utteranceId) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OK_REC_VOICE && resultCode == Activity.RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            for (String s : thingsYouSaid) {
                if (s.equalsIgnoreCase("start")) {
                    startActivity(new Intent(null, ServoCardBoardActivity.class));
                    break;
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTts.shutdown();
    }
}

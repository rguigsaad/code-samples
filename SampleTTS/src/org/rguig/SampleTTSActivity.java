package org.rguig;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class SampleTTSActivity extends Activity implements OnClickListener, OnInitListener {

	private static final int CHECK_DATA = 0;
	private TextToSpeech tts;
	private boolean isInit = false;
	private View speakButton;
	private final String tag = "SampleTTSActivity ";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Identify buttons and add click listeners
		speakButton = findViewById(R.id.speakButton);
		speakButton.setOnClickListener(this);

		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, CHECK_DATA);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHECK_DATA) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {

				// success, create the TTS instance
				tts = new TextToSpeech(this,  this);

				// List available locales on device
				Locale locales [] = Locale.getAvailableLocales();
				Log.i(tag,"Locales Available on Device:");
				for(int i=0; i<locales.length; i++){
					String stringLocales = "Locale "+i+": "
											+locales[i]+" Language="
											+locales[i].getDisplayLanguage();
					if(locales[i].getDisplayCountry() != "") {
						stringLocales += " Country="+locales[i].getDisplayCountry();
					}
					Log.i(tag, stringLocales);
				}

			} else {
				// missing data, so install it on the device
				Log.i("onActivityResult ", "Missing Data; Install it");
				Intent intenInstaller = new Intent();
				intenInstaller.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(intenInstaller);
			}
		}
	}
	public void onClick(View v) {
		String textToSay = "Hi There check that! it's working";
		sayText(textToSay,true);
	}

	public void onInit(int status) {
		if(status == TextToSpeech.SUCCESS){
			isInit = true;
			speakButton.setEnabled(true);

			// load and set the language to the default langage
			Log.i(tag, "Language available= "+tts.isLanguageAvailable(Locale.getDefault()));
			tts.setLanguage(Locale.getDefault());
			
		} else {
			isInit = false;
			Log.i(tag, "Failure: TextToSpeech instance tts was not properly initialized");
		}		
	}
	
	public void sayText(String text, boolean flush){
		if(isInit){
			if(flush){
				tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
			} else {
				tts.speak(text, TextToSpeech.QUEUE_ADD, null);
			}
		} else {
			Log.i(tag, "Could not initialize TextToSpeech.");
		}
	}
    // Release TTS resources
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	Log.i(tag,"Release TTS resources");
    	tts.shutdown();
    }
}
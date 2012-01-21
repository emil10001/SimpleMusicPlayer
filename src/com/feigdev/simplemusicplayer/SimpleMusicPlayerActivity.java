package com.feigdev.simplemusicplayer;

import com.feigdev.backgroundsound.android.SoundHandler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SimpleMusicPlayerActivity extends Activity implements OnClickListener {
	SoundHandler sh;
	TextView songName;
	TextView status;
	ImageButton play;
	ImageButton stop;
	boolean playing = false;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        songName = (TextView)findViewById(R.id.songName);
        status = (TextView)findViewById(R.id.statusMessage);
        status.setText("not loaded");
        play = (ImageButton)findViewById(R.id.playButton);
        play.setOnClickListener(this);
        stop = (ImageButton)findViewById(R.id.stopButton);
        stop.setOnClickListener(this);
        init();
    }
    
    private void init(){
    	sh = new SoundHandler();
		String path = "http://files.feigdev.com/test.ogg";
    	songName.setText(path);
		sh.netInit(path);
		status.setText("stopped");
    }
    
    private void play(){
    	if (!playing){
	    	playing = true;
	    	sh.startMusic();
	    	status.setText("playing");
    	}
    }
    
    private void stop(){
    	if (playing){
	    	playing = false;
	    	sh.stopMusic();
	    	status.setText("stopped");
    	}
    }
    
    public void onDestroy(){
    	sh.killPlayers();
    	super.onDestroy();
    }

	@Override
	public void onClick(View v) {
		if (v.getId() == stop.getId()){
			stop();
		}
		if (v.getId() == play.getId()){
			play();
		}
		
	}
    
    
}
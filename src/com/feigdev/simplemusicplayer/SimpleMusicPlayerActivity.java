package com.feigdev.simplemusicplayer;

import com.feigdev.backgroundsound.android.SoundHandler;
import com.lamerman.FileDialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

// The file selection dialog was shamelessly stolen from SO
// http://stackoverflow.com/a/3645048/974800

public class SimpleMusicPlayerActivity extends Activity implements OnClickListener {
	private SoundHandler sh;
	private TextView songName;
	private TextView status;
	private ImageButton play;
	private ImageButton stop;
	private ImageButton one;
	private ImageButton two;
	private ImageButton three;
	private EditText path;
	private boolean playing = false;
	private final String TAG = "SimpleMusicPlayer";
	private static final int REQUEST_SAVE = 1021132;
	private String fileName;
	
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
        path = (EditText)findViewById(R.id.editPath);
        path.setOnClickListener(this);
        one = (ImageButton)findViewById(R.id.sfx1Button);
        one.setOnClickListener(this);
        two = (ImageButton)findViewById(R.id.sfx2Button);
        two.setOnClickListener(this);
        three = (ImageButton)findViewById(R.id.sfx3Button);
        three.setOnClickListener(this);
        init();
    }
        
    private void init(){
    	sh = new SoundHandler();
		String path = "http://files.feigdev.com/test.ogg";
    	songName.setText(path);
		sh.netInit(path);
//		sh.localInitSfx(this, file, SoundHandler.SFX1);
		sh.initSfx(this,R.raw.one,SoundHandler.SFX1);
		sh.initSfx(this,R.raw.two,SoundHandler.SFX2);
		sh.initSfx(this,R.raw.three,SoundHandler.SFX3);
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
    
    private void fileGrab(){
    	Intent intent = new Intent(this.getBaseContext(), FileDialog.class);
		intent.putExtra(FileDialog.START_PATH, "/sdcard");
		this.startActivityForResult(intent, REQUEST_SAVE);
    }
    
    private void loadFile(){
    	stop();
    	sh.localInit(this, Uri.parse(fileName));
    	songName.setText(fileName);
    }
    
    public void onDestroy(){
    	sh.killPlayers();
    	super.onDestroy();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.stopButton:
			stop();
			break;
		case R.id.playButton:
			play();
			break;
		case R.id.editPath:
			fileGrab();
			break;
		case R.id.sfx1Button:
			sh.playSfx(SoundHandler.SFX1);
			break;
		case R.id.sfx2Button:
			sh.playSfx(SoundHandler.SFX2);
			break;
		case R.id.sfx3Button:
			sh.playSfx(SoundHandler.SFX3);
			break;
		}
	}
	
	 public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {

             if (resultCode == Activity.RESULT_OK) {
             	String filePath = data.getStringExtra(FileDialog.RESULT_PATH);
             	path.setText(filePath);
             	fileName = filePath;
             	loadFile();
             } else if (resultCode == Activity.RESULT_CANCELED) {
                     Log.w(TAG, "file not selected");
             }

     }
    
    
}
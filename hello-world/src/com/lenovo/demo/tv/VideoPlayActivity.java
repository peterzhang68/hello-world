/*
 * Copyright (C) 2013 SHARP Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lenovo.demo.tv;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlayActivity extends Activity {

    private TextView videoPath;
    private VideoView videoview;
    private String strVideoPath = "";
    private String TAG = "HIPPO_VIDEOVIEW";

    private boolean bIfSDExist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	setContentView(R.layout.video_play);

	videoview = (VideoView) findViewById(R.id.videoview);

	// check sdcard if exist
	checkTheSdCard();

	// videoPath = (TextView) findViewById(R.id.videoPath);

	/* test video file path */
	strVideoPath = "file:///sdcard/test.mp4";
	playVideo(strVideoPath);

	videoview
		.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

		    @Override
		    public void onCompletion(MediaPlayer mp) {
			// videoview.setVideoURI(Uri.parse(strVideoPath));
			// videoview.setVideoPath(strVideoPath);
			// videoview.start();
			playVideo(strVideoPath);

		    }
		});

    }

    /* play the video */
    private void playVideo(String strPath) {
	if (strPath != "") {
	    /* set video url */
	    try {
		videoview.setVideoURI(Uri.parse("android.resource://"
			+ getPackageName() + "/" + R.raw.lenovo_video));
	    } catch (Exception e) {
		// TODO: handle exception
	    }

	    /*  */
	    // videoview.setMediaController(new MediaController(this));

	    videoview.requestFocus();

	    /* play the video */
	    try {
		videoview.start();
	    } catch (Exception e) {
		// TODO: handle exception
	    }

	    if (videoview.isPlaying()) {
		videoPath.setText("Now Playing:" + strPath);
		Log.i(TAG, strPath);
	    }
	}
    }

    /*
     * 
     */
    public void checkTheSdCard() {

	if (android.os.Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED)) {
	    bIfSDExist = true;
	} else {
	    bIfSDExist = false;
	    // mMakeTextToast(getResources().getText(R.string.str_err_nosd)
	    // .toString(), true);
	}
    }

    //
    public void mMakeTextToast(String str, boolean isLong) {
	if (isLong == true) {
	    Toast.makeText(this, str, Toast.LENGTH_LONG).show();
	} else {
	    Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
    }

}

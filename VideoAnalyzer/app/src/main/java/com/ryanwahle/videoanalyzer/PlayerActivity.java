package com.ryanwahle.videoanalyzer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;


public class PlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        //String videoURLString = "android.resource://com.ryanwahle.videoanalyzer/" + R.raw.testvideo;
        String videoURLString = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";

        VideoView playerVideoView = (VideoView) findViewById(R.id.playerVideoView);
        playerVideoView.setVideoURI(Uri.parse(videoURLString));

        MediaController playerMediaController = new MediaController(this);
        playerMediaController.setAnchorView(playerVideoView);

        playerVideoView.setMediaController(playerMediaController);

        playerVideoView.start();

    }
}

package com.benimoyunum.tictocteo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ticFirstPage extends AppCompatActivity {

    public static MediaPlayer menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_tic_first_page);

        menu=MediaPlayer.create (getApplicationContext (),R.raw.menu);
        menu.setLooping (false);
        menu.start ();
    }

    public void exit_game(View view) {
        finish ();
        menu.stop ();
    }

    public void goTo3x3(View view) {

        Intent intent=new Intent (getApplicationContext (),playerMode.class);
        startActivity (intent);
        menu.stop ();
    }

    public void goAİ(View view) {
        Intent intent=new Intent (getApplicationContext (), computerPlayerMode.class);
        startActivity (intent);
        menu.stop ();

    }


}

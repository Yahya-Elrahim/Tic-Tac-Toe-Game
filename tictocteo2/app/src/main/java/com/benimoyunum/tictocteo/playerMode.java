package com.benimoyunum.tictocteo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class playerMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_player_mode);
    }


    public void goToboard4x4(View view) {
        Intent intent=new Intent (getApplicationContext (),boardTow.class);
        startActivity (intent);

    }

    public void goToBoard5x5(View view) {
        Intent intent=new Intent (getApplicationContext (),boardThree.class);
        startActivity (intent);

    }

    public void backToMenuTic(View view) {
        Intent intent=new Intent (getApplicationContext (),ticFirstPage.class);
        startActivity (intent);
    }

    public void goBoardOnee(View view) {
        Intent intent=new Intent (getApplicationContext (),boardOne.class);
        startActivity (intent);

    }
}

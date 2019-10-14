package com.benimoyunum.tictocteo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class computerPlayerMode extends AppCompatActivity {
    public static Boolean buttonE, buttonM, buttonH;
    Button easy, medium, hard;
    Boolean gameStat=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_computer_player_mode);

        Toast.makeText (getApplicationContext (),"Please Choose The Mode Of Game",Toast.LENGTH_LONG);
        final Button btn3x3=findViewById (R.id.btn3x3);
        final Button btn4x4=findViewById (R.id.btn4x4);
        btn3x3.setEnabled (false);
        btn4x4.setEnabled (false);

        easy = findViewById (R.id.button_easy);
        medium = findViewById (R.id.button_medium);
        hard = findViewById (R.id.button_hard);
        easy.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                easy.setBackgroundResource (R.drawable.button_dificulty);
                medium.setBackgroundResource (R.drawable.button_state);
                hard.setBackgroundResource (R.drawable.button_state);
                buttonE = true;
                buttonM = false;
                buttonH = false;
                gameStat=true;
                btn3x3.setEnabled (true);
                btn4x4.setEnabled (true);

            }
        });
        medium.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                easy.setBackgroundResource (R.drawable.button_state);
                medium.setBackgroundResource (R.drawable.button_dificulty);
                hard.setBackgroundResource (R.drawable.button_state);
                buttonE = false;
                buttonM = true;
                buttonH = false;
                gameStat=true;
                btn3x3.setEnabled (true);
                btn4x4.setEnabled (true);

            }
        });
        hard.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                easy.setBackgroundResource (R.drawable.button_state);
                medium.setBackgroundResource (R.drawable.button_state);
                hard.setBackgroundResource (R.drawable.button_dificulty);
                buttonE = false;
                buttonM = false;
                buttonH = true;
                gameStat=true;
                btn3x3.setEnabled (true);
                btn4x4.setEnabled (true);

            }
        });
    }



    public void goAiBoardOne(View view) {
        if (gameStat) {
            Intent intent = new Intent (getApplicationContext (), playerWithComputerOne.class);
            intent.putExtra ("difficultyE", buttonE);
            intent.putExtra ("difficultyM", buttonM);
            intent.putExtra ("difficultyH", buttonH);
            startActivity (intent);
        }

    }

    public void backToMenu(View view) {
        Intent intent=new Intent (getApplicationContext (),ticFirstPage.class);
        startActivity (intent);
    }


}

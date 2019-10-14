package com.benimoyunum.tictocteo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class boardOne extends AppCompatActivity implements View.OnClickListener {


    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;
    private int roundCount;
    private int player1Points;
    private int player2Points;
    TextView textViewPlayer1,viewX,viewO;
    TextView textViewPlayer2;
    Button button;
    public   MediaPlayer x,o,gameOver,resetGame,x_wins,o_wins;
    Handler handler=new Handler ();
    Animation animation,animationWinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_board_one);

        animation= AnimationUtils.loadAnimation (getApplicationContext (),R.anim.scale);
        animationWinner= AnimationUtils.loadAnimation (getApplicationContext (),R.anim.alpha);
        x=MediaPlayer.create (getApplicationContext (),R.raw.x);
        o=MediaPlayer.create (getApplicationContext (),R.raw.o);
        viewX=findViewById (R.id.x);
        viewO=findViewById (R.id.o);
        viewX.setBackgroundResource (R.drawable.button_state);

        gameOver=MediaPlayer.create (getApplicationContext (),R.raw.player_wins);
        resetGame=MediaPlayer.create (getApplicationContext (),R.raw.resetgame);
        x_wins=MediaPlayer.create (getApplicationContext (),R.raw.gameover);
        o_wins=MediaPlayer.create (getApplicationContext (),R.raw.player_lost);

        textViewPlayer1 = findViewById (R.id.txt_p1);
        textViewPlayer2 = findViewById (R.id.txt_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                String buttonId = "button_" + i + j;
                int resId = getResources ().getIdentifier (buttonId, "id", getPackageName ());
                buttons[i][j] = findViewById (resId);
                buttons[i][j].setOnClickListener (this);
            }


        }
        button=findViewById (R.id.button_reset);
        button.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                resetGame.start ();
                player1Points=0;
                player2Points=0;
                resetBoard ();
                updatePlayerPoints ();
            }
        });


    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText ().equals ("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText ("X");
            v.startAnimation (animation);
            x.start ();
            v.setBackgroundResource (R.drawable.button_board_pressed);
            viewX.setBackgroundResource (R.drawable.button_board_pressed);
            viewO.setBackgroundResource (R.drawable.button_state);

        } else {
            ((Button) v).setText ("O");
            v.startAnimation (animation);
            v.setBackgroundResource (R.drawable.button_board_pressed);
            o.start ();
            viewX.setBackgroundResource (R.drawable.button_state);
            viewO.setBackgroundResource (R.drawable.button_board_pressed);
        }
        roundCount++;

        if (checkForWin ()){

            if (player1Turn){
               handler.postDelayed (player1Wins,1500);
            }
            else {
                handler.postDelayed (player2wins,1500);
            }
        }

        else if (roundCount==9){
            handler.postDelayed (draw,1500);
        }
        else {
            player1Turn=!player1Turn;
        }
    }

    public boolean checkForWin() {

        String[][] fields = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j]=buttons[i][j].getText ().toString ();
            }
        }
        for (int i=0; i < 3; i++){

            if (fields[i][0].equals (fields[i][1])
                    && fields[i][0].equals (fields[i][2])
                    && !fields[i][0].equals ("")) {
                buttons[i][0].startAnimation (animationWinner);
                buttons[i][1].startAnimation (animationWinner);
                buttons[i][2].startAnimation (animationWinner);
                return true;
            }
        }
        for (int i=0; i < 3; i++){

            if (fields[0][i].equals (fields[1][i])
                    && fields[0][i].equals (fields[2][i])
                    && !fields[0][i].equals ("")) {
                buttons[0][i].startAnimation (animationWinner);
                buttons[1][i].startAnimation (animationWinner);
                buttons[2][i].startAnimation (animationWinner);
                return true;
            }
        }

        if (fields[0][0].equals (fields[1][1])
                && fields[0][0].equals (fields[2][2])
                && !fields[0][0].equals ("")) {
            buttons[0][0].startAnimation (animationWinner);
            buttons[1][1].startAnimation (animationWinner);
            buttons[2][2].startAnimation (animationWinner);
            return true;
        }


       else if (fields[0][2].equals (fields[1][1])
                && fields[0][2].equals (fields[2][0])
                && !fields[0][2].equals ("")) {
            buttons[0][2].startAnimation (animationWinner);
            buttons[1][1].startAnimation (animationWinner);
            buttons[2][0].startAnimation (animationWinner);
            return true;
        }
        return false;
    }

   Runnable player1Wins=new Runnable () {
       @Override
       public void run() {
           x_wins.start ();
           toastMessageX ();
           player1Points++;
           updatePlayerPoints();
           resetBoard();
           changeColor ();
       }
   };

    Runnable player2wins=new Runnable () {
        @Override
        public void run() {

            o_wins.start ();
            toastMessagey ();
            player2Points++;
            updatePlayerPoints();
            resetBoard();
            changeColor ();
        }
    };
    Runnable draw=new Runnable () {
        @Override
        public void run() {
            gameOver.start ();
            toastMessageDraw ();
            changeColor ();
            resetBoard ();
        }
    };
    private void updatePlayerPoints(){

        textViewPlayer1.setText (String.valueOf (player1Points));
        textViewPlayer2.setText (String.valueOf (player2Points));

    }
    private void resetBoard(){
        for (int i=0; i < 3; i++){
            for (int j=0; j < 3; j++){
                buttons[i][j].setText ("");
            }
        }
        changeColor ();
        roundCount=0;
        player1Turn=true;
        viewX.setBackgroundResource (R.drawable.button_state);
        viewO.setBackgroundResource (R.color.bg);
    }

    private void changeColor(){

        for (int i=0; i < 3; i++){
            for (int j=0; j < 3; j++){
                buttons[i][j].setBackgroundResource (R.drawable.button_state);
            }
        }

    }

    public void toastMessageX(){

        LayoutInflater myInflater=LayoutInflater.from (this);
        View view=myInflater.inflate (R.layout.player1_wins,null);
        Toast message=new Toast (this);
        message.setView (view);
        message.setDuration (Toast.LENGTH_SHORT);
        message.setGravity (Gravity.CENTER,0,0);
        message.show ();
    }
    public void toastMessagey(){

        LayoutInflater myInflater=LayoutInflater.from (this);
        View view=myInflater.inflate (R.layout.player2_wins,null);
        Toast message=new Toast (this);
        message.setView (view);
        message.setDuration (Toast.LENGTH_SHORT);
        message.setGravity (Gravity.CENTER,0,0);
        message.show ();
    }
    public void toastMessageDraw(){

        LayoutInflater myInflater=LayoutInflater.from (this);
        View view=myInflater.inflate (R.layout.draw,null);
        Toast message=new Toast (this);
        message.setView (view);
        message.setDuration (Toast.LENGTH_SHORT);
        message.setGravity (Gravity.CENTER,0,0);
        message.show ();
    }

   public void backMenu(View view){
     Intent intent=new Intent (getApplicationContext (),playerMode.class);
     startActivity (intent);
   }

}




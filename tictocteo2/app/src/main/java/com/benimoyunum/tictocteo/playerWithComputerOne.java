package com.benimoyunum.tictocteo;


import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class playerWithComputerOne extends AppCompatActivity {
    int turn = 1;
    int win = 0;
    int gamov = 0;
    int flagEndGame=0;
    int flag;
    Button playBoard[][] = new Button[3][3];
    int boardMatrix[][] = new int[3][3];
    double probMatrix[][] = new double[3][3];
    int moveNumber=1;
    int counter = 0;
    int player1Win = 0, player2Win = 0, draw = 0;
    int flipValue=0;
    public int index,playerPoints=0,computerPoints=0;
    public Boolean firstGame=true,state=true,buttonE,buttonM,buttonH;
    Handler handler=new Handler ();
    TextView playerResult,computerResult;
    Animation animationForClick,animationForWon;
    public MediaPlayer xClick,oClick,playerWinsS,computerWinsS,gameDrawS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_with_computer_one);


        playerResult=findViewById (R.id.txt_p1);
        computerResult=findViewById (R.id.txt_p2);
        animationForClick= AnimationUtils.loadAnimation (this,R.anim.scale);
        animationForWon= AnimationUtils.loadAnimation (this,R.anim.alpha);
        xClick=MediaPlayer.create (this,R.raw.x);
        oClick=MediaPlayer.create (this,R.raw.o);
        playerWinsS=MediaPlayer.create (this,R.raw.player_wins);
        computerWinsS=MediaPlayer.create (this,R.raw.player_lost);
        gameDrawS=MediaPlayer.create (this,R.raw.gameover);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button_" + i + j;
                int resId = getResources ().getIdentifier (buttonId, "id", getPackageName ());
                playBoard[i][j] = findViewById (resId);
                boardMatrix[i][j]=0;
            }
        }
        if(flipValue==1){
            computerPlay();

            turn=2;
        }



    }

    public void playMove(View view) {

        switch (view.getId ()){
            case R.id.button_00:
                index=0;
                break;
            case R.id.button_01:
                index=1;
                break;
            case R.id.button_02:
                index=2;
                break;
            case R.id.button_10:
                index=3;
                break;
            case R.id.button_11:
                index=4;
                break;
            case R.id.button_12:
                index=5;
                break;
            case R.id.button_20:
                index=6;
                break;
            case R.id.button_21:
                index=7;
                break;
            case R.id.button_22:
                index=8;
                break;
        }
        int i = index / 3;
        int j = index % 3;
        flag = 0;
        if (turn == 1 && gamov == 0 && !(playBoard[i][j].getText().toString().equals("X")) && !(playBoard[i][j].getText().toString().equals("O"))) {


            if(flipValue==0){
                xClick.start ();
                playBoard[i][j].setText("X");
                playBoard[i][j].setBackgroundResource (R.drawable.button_board_pressed);
                playBoard[i][j].startAnimation (animationForClick);
                boardMatrix[i][j]=1;
                turn = 2;
                moveNumber++;
                firstGame = false;
                turn = 1;
                moveNumber++;
                computerPlay ();
                turn = 1;
                moveNumber++;


            }



        } else if (turn == 2 && gamov == 0 && !(playBoard[i][j].getText().toString().equals("X")) && !(playBoard[i][j].getText().toString().equals("O"))) {

            if(flipValue==1){
                oClick.start ();
                playBoard[i][j].setText("O");
                playBoard[i][j].setBackgroundResource (R.drawable.button_board_pressed);
                playBoard[i][j].startAnimation (animationForClick);
                boardMatrix[i][j]=1;
                turn = 1;
                moveNumber++;
                computerPlay ();
                turn = 2;
                moveNumber++;


            }

        }

        checkWin();
        if (gamov == 1) {
            if (win == 1) {
                handler.postDelayed (playerWins,1500);
                if(flagEndGame==0){
                    player1Win++;
                    counter++;
                }


            } else if (win == 2) {
                handler.postDelayed (computerWon,1500);
                if(flagEndGame==0){
                    player2Win++;
                    counter++;
                }

            }
            flagEndGame=1;


        }
        if (gamov == 0) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (!playBoard[i][j].getText().toString().equals("X") && !playBoard[i][j].getText().toString().equals("O")) {
                        flag = 1;
                        break;

                    }
                }
            }
            if (flag == 0) {
                handler.postDelayed (drawing,1500);
                if(flagEndGame==0){
                    counter++;
                    draw++;
                }
                flagEndGame=1;


            }


        }


    }
    int level=0;
    public void randomPlay(){
        int random = (int)(Math.random()*9);
        int i=random/3;
        int j=random%3;
           playBoard[i][j].setBackgroundResource (R.drawable.button_board_pressed);
           playBoard[i][j].setText ("O");
           playBoard[i][j].startAnimation (animationForClick);
        boardMatrix[i][j]=2;
    }





    public void computerPlay(){
        int currentTurn = turn;
        int currentMove = moveNumber;
        int i=0,j=0;
        int moveChoice=0;
        int flag=0;
        int flagGameNotOver=0;

        int counter=0;
        double sum=0;
        if(turn==1){
            turn=2;
        }
        else{
            turn=1;
        }
        for(int c=0;c<9;c++){
            i=c/3;
            j=c%3;
            //  tempBoard[i][j].setText(playBoard[i][j].getText().toString());
            probMatrix[i][j]=0;
        }

        for(int c=0; c<9;c++) {
            i = c / 3;
            j = c % 3;
            if (boardMatrix[i][j] == 0) {
                flagGameNotOver=1;
                // Log.e("INCP", "I got "+String.valueOf(i)+" "+String.valueOf(j));
                boardMatrix[i][j] = 1;
                if (flipValue == 1) {
                    playBoard[i][j].setText ("X");
                }

                else {
                    playBoard[i][j].setText ("O");
                }
                if (checkWinComp() == 2 && flipValue == 0) {
                    flag=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                } else if (checkWinComp() == 2 && flipValue == 1) {
                    flag=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                }
                if (checkWinComp() == 1 && flipValue == 1) {
                    playBoard[i][j].setText(" ");
                    //  Log.v("CP","I came to the first if");
                    boardMatrix[i][j] = 0;
                    continue;
                } else if (checkWinComp() == 1 && flipValue == 0) {
                    playBoard[i][j].setText(" ");
                    //   Log.v("CP","I came to the second if");
                    boardMatrix[i][j] = 0;
                    continue;

                } else {
                    level++;
                    probMatrix[i][j]=computerAnalyze();
                    //    double value = computerAnalyze();
//                    sum+=value;
//                    counter++;
                    level--;
                    //   Log.v("CP","Analysis has been done! " + String.valueOf(i)+" "+String.valueOf(j));

                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j] = 0;
                //probMatrix[i][j]=sum;

            }
        }
        if(flagGameNotOver==0){
            return;
        }
        double maxProb=0;
        if(flag==0){
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb<probMatrix[p][q]){
                        maxProb=probMatrix[p][q];
                    }
                }
            }
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb==probMatrix[p][q] && boardMatrix[p][q]==0){
                        moveChoice=3*p+q;
                        break;
                    }
                }
            }
        }
        else{
            moveChoice=3*i+j;
        }
        turn = currentTurn;
        moveNumber = currentMove;
        int xCoord=moveChoice/3;
        int yCoord=moveChoice%3;
        boardMatrix[xCoord][yCoord]=1;
        if(flipValue==0){
            playBoard[xCoord][yCoord].setText("O");
            playBoard[xCoord][yCoord].setBackgroundResource (R.drawable.button_board_pressed);
            oClick.start ();
            playBoard[xCoord][yCoord].startAnimation (animationForClick);


        }
        else{
            playBoard[xCoord][yCoord].setText("X");
            playBoard[xCoord][yCoord].setBackgroundResource (R.drawable.button_board_pressed);
            xClick.start ();
            playBoard[xCoord][yCoord].startAnimation (animationForClick);

        }

        //  Log.v("CP","I have moved!!! "+ String.valueOf(xCoord)+" "+String.valueOf(yCoord)+" "+String.valueOf(boardMatrix[xCoord][yCoord])+" "+String.valueOf(probMatrix[xCoord][yCoord]));
        //  Log.v("CP",String.valueOf(probMatrix[0][0])+" "+String.valueOf(probMatrix[0][1])+" "+String.valueOf(probMatrix[0][2])+" "+String.valueOf(probMatrix[1][0])+" "+String.valueOf(probMatrix[1][1])+" "+String.valueOf(probMatrix[1][2])+" "+String.valueOf(probMatrix[2][0])+" "+String.valueOf(probMatrix[2][1])+" "+String.valueOf(probMatrix[2][2]));

    }

    public double computerAnalyze() {
        double sum=0;
        int counter=0;
        int flagCheckGameNotOver=0;
        for(int c=0;c<9;c++){
            int i=c/3;
            int j=c%3;

            if(boardMatrix[i][j]==0){
                flagCheckGameNotOver=1;
                boardMatrix[i][j]=1;

                if(turn==1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if(checkWinComp()==2 && flipValue==0){
                    sum=1;
                    //    Log.v("INCA","First If "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinComp()==2 && flipValue==1){
                    sum=1;
                    //    Log.v("INCA","Second If "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinComp()==1 && flipValue==1){
                    sum=0;
                    //    Log.v("INCA","Third Iff "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinComp()==1 && flipValue==0){
                    sum=0;
                    //    Log.v("INCA","Fourth If "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else {
                    //    Log.v("INCA","In Else "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(level)+" "+String.valueOf(turn));
                    counter++;
                    if(turn==1){
                        turn=2;
                    }
                    else{
                        turn=1;
                    }
                    level++;
                    double value=computerAnalyze();
                    level--;
                    sum+=value;
                    //   Log.v("INCA",String.valueOf(sum));

                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j]=0;
                if(turn==1){
                    turn=2;
                }
                else{
                    turn=1;
                }
            }

        }
        //  Log.v("SUMC",String.valueOf(sum)+" "+String.valueOf(counter));
        if(flagCheckGameNotOver==0){
            return 0.5;
        }
        double average = ((double) sum)/ ((double) counter);
        return average;
    }


    public void checkWin() {
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString()) && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 1;
                    else if(flipValue==1)
                        win=2;


                } else if (playBoard[i][0].getText().toString().equals("O")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 2;
                    else if(flipValue==1)
                        win=1;

                }
                if (!playBoard[i][0].getText().toString().equals(" ")) {
                    playBoard[i][0].setTextColor(Color.RED);
                    playBoard[i][1].setTextColor(Color.RED);
                    playBoard[i][2].setTextColor(Color.RED);
                    playBoard[i][0].startAnimation (animationForWon);
                    playBoard[i][1].startAnimation (animationForWon);
                    playBoard[i][2].startAnimation (animationForWon);

                }

            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString()) && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 1;
                    else if(flipValue==1)
                        win=2;


                } else if (playBoard[0][i].getText().toString().equals("O")) {
                    gamov = 1;
                    if(flipValue==0)
                        win = 2;
                    else if(flipValue==1)
                        win=1;

                }
                if (!playBoard[0][i].getText().toString().equals(" ")) {
                    playBoard[0][i].setTextColor(Color.RED);
                    playBoard[1][i].setTextColor(Color.RED);
                    playBoard[2][i].setTextColor(Color.RED);
                    playBoard[0][i].startAnimation (animationForWon);
                    playBoard[1][i].startAnimation (animationForWon);
                    playBoard[2][i].startAnimation (animationForWon);
                }

            }


        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {
                gamov = 1;
                if(flipValue==0)
                    win = 1;
                else if(flipValue==1)
                    win=2;


            } else if (playBoard[0][0].getText().toString().equals("O")) {
                gamov = 1;
                if(flipValue==0)
                    win = 2;
                else if(flipValue==1)
                    win=1;

            }
            if (!playBoard[0][0].getText().toString().equals(" ")) {
                playBoard[0][0].setTextColor(Color.RED);
                playBoard[1][1].setTextColor(Color.RED);
                playBoard[2][2].setTextColor(Color.RED);
                playBoard[0][0].startAnimation (animationForWon);
                playBoard[1][1].startAnimation (animationForWon);
                playBoard[2][2].startAnimation (animationForWon);
            }


        }
        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())) {
            if (playBoard[0][2].getText().toString().equals("X")) {
                gamov = 1;
                if(flipValue==0)
                    win = 1;
                else if(flipValue==1)
                    win=2;


            } else if (playBoard[0][2].getText().toString().equals("O")) {
                gamov = 1;
                if(flipValue==0)
                    win = 2;
                else if(flipValue==1)
                    win=1;

            }
            if (!playBoard[2][0].getText().toString().equals(" ")) {
                playBoard[2][0].setTextColor(Color.RED);
                playBoard[1][1].setTextColor(Color.RED);
                playBoard[0][2].setTextColor(Color.RED);
                playBoard[2][0].startAnimation (animationForWon);
                playBoard[1][1].startAnimation (animationForWon);
                playBoard[0][2].startAnimation (animationForWon);
            }


        }
    }

    public int checkWinComp() {
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString()) && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;


                } else if (playBoard[i][0].getText().toString().equals("O")) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;

                }


            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString()) && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;


                } else if (playBoard[0][i].getText().toString().equals("O")) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;

                }


            }


        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;


            } else if (playBoard[0][0].getText().toString().equals("O")) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;

            }



        }
        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())) {
            if (playBoard[0][2].getText().toString().equals("X")) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;


            } else if (playBoard[0][2].getText().toString().equals("O")) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;

            }



        }
        return 0;
    }
    public void newGame(View view) {
       resetBoard ();
       firstGame=true;
       playerPoints=0;
       computerPoints=0;
       updatePlayerPoints ();

    }

    Runnable playerWins=new Runnable () {
        @Override
        public void run() {
            playerWinsS.start ();
            playerWins ();
            resetBoard ();
            playerPoints++;
            updatePlayerPoints();

        }
    };

    Runnable computerWon=new Runnable () {
        @Override
        public void run() {
            computerWinsS.start ();
            computerWins ();
            resetBoard ();
            computerPoints++;
            updatePlayerPoints();

        }
    };
    Runnable drawing=new Runnable () {
        @Override
        public void run() {
            gameDrawS.start ();
            resetBoard ();
            gameIsDrawing ();
        }
    };
    private void updatePlayerPoints(){

      playerResult.setText (String.valueOf (playerPoints));
      computerResult.setText (String.valueOf (computerPoints));

    }
    private void resetBoard(){

        win = 0;
        gamov = 0;
        turn=1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j].setText(" ");
                playBoard[i][j].setTextColor(Color.WHITE);
                playBoard[i][j].setBackgroundResource (R.drawable.button_state);
                boardMatrix[i][j]=0;
            }
        }

        if(flipValue==0){
            if(flagEndGame==1){
                flipValue=1;

            }


        }
        else if(flipValue==1 ){
            if(flagEndGame==1){
                flipValue=0;
            }




        }
        flagEndGame=0;
        if(flipValue==1){
            //    Log.e("INNEW","I am here to create a new game with computer x!");
            randomPlay();
            //    Log.e("INNEW","I am out!");
            turn=2;
        }

    }


    private void playerWins(){

        LayoutInflater myInflater=LayoutInflater.from (this);
        View view=myInflater.inflate (R.layout.player1_wins,null);
        Toast message=new Toast (this);
        message.setView (view);
        message.setDuration (Toast.LENGTH_SHORT);
        message.setGravity (Gravity.CENTER,0,0);
        message.show ();
    }
    private void computerWins(){

        LayoutInflater myInflater=LayoutInflater.from (this);
        View view=myInflater.inflate (R.layout.computer,null);
        Toast message=new Toast (this);
        message.setView (view);
        message.setDuration (Toast.LENGTH_SHORT);
        message.setGravity (Gravity.CENTER,0,0);
        message.show ();
    }
    private void gameIsDrawing(){

        LayoutInflater myInflater=LayoutInflater.from (this);
        View view=myInflater.inflate (R.layout.draw,null);
        Toast message=new Toast (this);
        message.setView (view);
        message.setDuration (Toast.LENGTH_SHORT);
        message.setGravity (Gravity.CENTER,0,0);
        message.show ();
    }



    public void newMatch(View view){
        Intent intent = new Intent(this,computerPlayerMode.class);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
            finish();
        }
    }

    public void randomO(){
        Random rnd=new Random ();
        int x,y;
        for(int i=0; i < 3; i++){
            for (int j=0; j < 3; j++){
                x=rnd.nextInt (3);   y=rnd.nextInt (3);
                if (playBoard[x][y].getText ().toString ().equals ("")){
                    playBoard[x][y].setText ("O");
                    playBoard[x][y].setBackgroundResource (R.drawable.button_board_pressed);
                    playBoard[x][y].startAnimation (animationForClick);
                    oClick.start ();
                }
            }
        }
    }
    public void randomX(){
        Random rnd=new Random ();
        int x,y;
        for(int i=0; i < 3; i++){
            for (int j=0; j < 3; j++){
                x=rnd.nextInt (3);   y=rnd.nextInt (3);
                if (playBoard[x][y].getText ().toString ().equals ("")){
                    playBoard[x][y].setText ("X");
                    playBoard[x][y].setBackgroundResource (R.drawable.button_board_pressed);
                    playBoard[x][y].startAnimation (animationForClick);
                    xClick.start ();
                }
            }
        }
    }
}




































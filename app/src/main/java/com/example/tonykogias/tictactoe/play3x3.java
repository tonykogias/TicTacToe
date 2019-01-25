package com.example.tonykogias.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

public class play3x3 extends AppCompatActivity implements View.OnClickListener {

    ImageButton im[][] = new ImageButton[3][3];

    TextView p1score;
    TextView p2score;
    int counter1;
    int counter2;

    char grid[][] = new char[3][3];
    char currentPlayer;
    int fullGrid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play3x3);


        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.rsz_taskbaricon);
        }

        p1score = findViewById(R.id.player1Score);
        p2score = findViewById(R.id.player2Score);
        p1score.setText("Player 1:   "+counter1);
        p2score.setText("Player 2:   "+counter2);

        //Initialize image views
        im[0][0] = findViewById(R.id.imageButton11);
        im[0][1] = findViewById(R.id.imageButton12);
        im[0][2] = findViewById(R.id.imageButton13);
        im[1][0] = findViewById(R.id.imageButton21);
        im[1][1] = findViewById(R.id.imageButton22);
        im[1][2] = findViewById(R.id.imageButton23);
        im[2][0] = findViewById(R.id.imageButton31);
        im[2][1] = findViewById(R.id.imageButton32);
        im[2][2] = findViewById(R.id.imageButton33);

        currentPlayer = 'o';

        im[0][0].setOnClickListener(this);
        im[0][1].setOnClickListener(this);
        im[0][2].setOnClickListener(this);
        im[1][0].setOnClickListener(this);
        im[1][1].setOnClickListener(this);
        im[1][2].setOnClickListener(this);
        im[2][0].setOnClickListener(this);
        im[2][1].setOnClickListener(this);
        im[2][2].setOnClickListener(this);


    }

    //change player's turn
    public void changePlayer() {
        currentPlayer = (currentPlayer == 'x' ? 'o' : 'x');
    }

    //show message who's turn is to play
    public void alertPlayerTurn() {

        AlertDialog.Builder builder = new AlertDialog.Builder(play3x3.this);
        Intent intent = getIntent();
        String name1 = intent.getStringExtra("player1");
        String name2 = intent.getStringExtra("player2");

        //Set up custom dialog
        View view = LayoutInflater.from(play3x3.this).inflate(R.layout.dialognobutt, null);

        TextView title = view.findViewById(R.id.dialogTitle);
        title.setText("TicTacToe");

        TextView message = view.findViewById(R.id.dialogMessage);

        if(currentPlayer == 'o') {
            message.setText("It is "+name1+" turn!");
        }
        if(currentPlayer == 'x'){
            message.setText("It is "+name2+" turn!");
        }

        builder.setView(view);
        builder.show();

    }

    //Each players move
    public void playerMove(int i, int j) {


        alertPlayerTurn();

        if(currentPlayer == 'o'){
            im[i][j].setImageResource(R.drawable.rsz_player1);
            grid[i][j] = 'o';
            fullGrid++;
        }
        if(currentPlayer == 'x'){
            im[i][j].setImageResource(R.drawable.rsz_player2);
            grid[i][j] = 'x';
            fullGrid++;
        }


        if(isWin()){

            winner(currentPlayer);
        }

        if(isGridFull()) {
            currentPlayer = 'd';
            winner(currentPlayer);
        }

        changePlayer();

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.imageButton11:
                playerMove(0,0);
                break;
            case R.id.imageButton12:
                playerMove(0,1);
                break;
            case R.id.imageButton13:
                playerMove(0,2);
                break;
            case R.id.imageButton21:
                playerMove(1,0);
                break;
            case R.id.imageButton22:
                playerMove(1,1);
                break;
            case R.id.imageButton23:
                playerMove(1,2);
                break;
            case R.id.imageButton31:
                playerMove(2,0);
                break;
            case R.id.imageButton32:
                playerMove(2,1);
                break;
            case R.id.imageButton33:
                playerMove(2,2);
                break;
        }
    }

    //check if grid is full (means it's a draw)
    public boolean isGridFull() {
        if(fullGrid==9) {
            return true;
        } else {
            return false;
        }
    }

    //check if someone won
    public boolean isWin(){

        if (checkRow()) return true;
        if (checkColumn()) return true;
        if (checkDiagonal()) return true;
        return false;

    }

    public boolean checkRow(){
        for (int i = 0; i < 3; i++){
            if (grid[i][0] == grid [i][1] && grid [i][0] == grid[i][2]){
                if (grid[i][0] != '\0') return true; //because char '-' is empty
            }
        }

        return false;
    }

    public boolean checkColumn(){
        for (int i = 0; i < 3; i++){
            if (grid[0][i] == grid [1][i] && grid [0][i] == grid[2][i]){
                if (grid[0][i] != '\0') return true;
            }
        }

        return false;
    }

    public boolean checkDiagonal(){

        if ((grid[1][1] == grid[0][0] && grid[1][1] == grid[2][2]) ||
                (grid[1][1] == grid[0][2] && grid [1][1] == grid[2][0])){
            if (grid[1][1] != '\0') return true;
        }

        return false;
    }


    public void winner(char player){

        final AlertDialog.Builder builder = new AlertDialog.Builder(play3x3.this);



        //Set up custom dialog
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.mydialog, null);
        builder.setView(view);


        TextView title = view.findViewById(R.id.dialogTitle);
        title.setText("TicTacToe");

        TextView message = view.findViewById(R.id.dialogMessage);


        if (player != 'd'){
            message.setText("Winner is Player "+player+"\nStart New Game?");

            if(player == 'o') {
                p1score.setText("Player 1:   "+(++counter1));
            } else {
                p2score.setText("Player 2:   "+(++counter2));
            }

        } else {
            message.setText("It's a Draw !\nStarte New Game?");
        }

        Button quitButt = view.findViewById(R.id.quitButtDialog);
        Button resttButt = view.findViewById(R.id.restartButtonDialog);

        quitButt.setText("Quit");
        resttButt.setText("Yes");

        final AlertDialog dialog = builder.create();

        quitButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(play3x3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        resttButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartGame();
                dialog.dismiss();
            }
        });


        dialog.show();

    }


    public void restartGame() {

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                im[i][j].setImageResource(R.drawable.rsz_blank);
                grid[i][j] = '\0';
            }
        }

        fullGrid = 0;
        currentPlayer = 'o';
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putChar("currentPlayer", currentPlayer);
        outState.putInt("score1",counter1);
        outState.putInt("score2",counter2);

        outState.putChar("grid00", grid[0][0]);
        outState.putChar("grid01", grid[0][1]);
        outState.putChar("grid02", grid[0][2]);
        outState.putChar("grid10", grid[1][0]);
        outState.putChar("grid11", grid[1][1]);
        outState.putChar("grid12", grid[1][2]);
        outState.putChar("grid20", grid[2][0]);
        outState.putChar("grid21", grid[2][1]);
        outState.putChar("grid22", grid[2][2]);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentPlayer = savedInstanceState.getChar("currentPlayer");
        counter1 = savedInstanceState.getInt("score1");
        counter2 = savedInstanceState.getInt("score2");

        grid[0][0] = savedInstanceState.getChar("grid00");
        grid[0][1] = savedInstanceState.getChar("grid01");
        grid[0][2] = savedInstanceState.getChar("grid02");
        grid[1][0] = savedInstanceState.getChar("grid10");
        grid[1][1] = savedInstanceState.getChar("grid11");
        grid[1][2] = savedInstanceState.getChar("grid12");
        grid[2][0] = savedInstanceState.getChar("grid20");
        grid[2][1] = savedInstanceState.getChar("grid21");
        grid[2][2] = savedInstanceState.getChar("grid22");

        setSavedImg(0,0);
        setSavedImg(0,1);
        setSavedImg(0,2);
        setSavedImg(1,0);
        setSavedImg(1,1);
        setSavedImg(1,2);
        setSavedImg(2,0);
        setSavedImg(2,1);
        setSavedImg(2,2);
    }

    //Sets the saved image (used when change orientation)
    public void setSavedImg(int i, int j) {

        if(grid[i][j] == 'o'){
            im[i][j].setImageResource(R.drawable.rsz_player1);
        }
        if(grid[i][j] == 'x'){
            im[i][j].setImageResource(R.drawable.rsz_player2);
        }
    }
}

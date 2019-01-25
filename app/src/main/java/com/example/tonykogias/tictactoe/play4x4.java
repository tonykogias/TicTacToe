package com.example.tonykogias.tictactoe;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class play4x4 extends AppCompatActivity implements View.OnClickListener {


    ImageButton im[][] = new ImageButton[4][4];

    TextView p1score;
    TextView p2score;
    int counter1 = 0;
    int counter2 = 0;

    char grid[][] = new char[4][4];
    char currentPlayer;
    int fullGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play4x4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.rsz_taskbaricon);
        }

        p1score = findViewById(R.id.player1Score);
        p2score = findViewById(R.id.player2Score);
        p1score.setText("Player 1:   " + counter1);
        p2score.setText("Player 2:   " + counter2);

        //Initialize image views
        im[0][0] = findViewById(R.id.imageButton11);
        im[0][1] = findViewById(R.id.imageButton12);
        im[0][2] = findViewById(R.id.imageButton13);
        im[0][3] = findViewById(R.id.imageButton14);
        im[1][0] = findViewById(R.id.imageButton21);
        im[1][1] = findViewById(R.id.imageButton22);
        im[1][2] = findViewById(R.id.imageButton23);
        im[1][3] = findViewById(R.id.imageButton24);
        im[2][0] = findViewById(R.id.imageButton31);
        im[2][1] = findViewById(R.id.imageButton32);
        im[2][2] = findViewById(R.id.imageButton33);
        im[2][3] = findViewById(R.id.imageButton34);
        im[3][0] = findViewById(R.id.imageButton41);
        im[3][1] = findViewById(R.id.imageButton42);
        im[3][2] = findViewById(R.id.imageButton43);
        im[3][3] = findViewById(R.id.imageButton44);

        currentPlayer = 'o';

        im[0][0].setOnClickListener(this);
        im[0][1].setOnClickListener(this);
        im[0][2].setOnClickListener(this);
        im[0][3].setOnClickListener(this);
        im[1][0].setOnClickListener(this);
        im[1][1].setOnClickListener(this);
        im[1][2].setOnClickListener(this);
        im[1][3].setOnClickListener(this);
        im[2][0].setOnClickListener(this);
        im[2][1].setOnClickListener(this);
        im[2][2].setOnClickListener(this);
        im[2][3].setOnClickListener(this);
        im[3][0].setOnClickListener(this);
        im[3][1].setOnClickListener(this);
        im[3][2].setOnClickListener(this);
        im[3][3].setOnClickListener(this);
    }


    //change player's turn
    public void changePlayer() {
        currentPlayer = (currentPlayer == 'x' ? 'o' : 'x');
    }

    //show message who's turn is to play
    public void alertPlayerTurn() {

        AlertDialog.Builder builder = new AlertDialog.Builder(play4x4.this);
        Intent intent = getIntent();
        String name1 = intent.getStringExtra("player1");
        String name2 = intent.getStringExtra("player2");

        //Set up custom dialog
        View view = LayoutInflater.from(play4x4.this).inflate(R.layout.dialognobutt, null);

        TextView title = view.findViewById(R.id.dialogTitle);
        title.setText("TicTacToe");

        TextView message = view.findViewById(R.id.dialogMessage);

        if (currentPlayer == 'o') {
            message.setText("It is " + name1 + " turn!");
        }
        if (currentPlayer == 'x') {
            message.setText("It is " + name2 + " turn!");
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


        if(isWin(grid, currentPlayer)){

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
            case R.id.imageButton14:
                playerMove(0,3);
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
            case R.id.imageButton24:
                playerMove(1,3);
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
            case R.id.imageButton34:
                playerMove(2,3);
                break;
            case R.id.imageButton41:
                playerMove(3,0);
                break;
            case R.id.imageButton42:
                playerMove(3,1);
                break;
            case R.id.imageButton43:
                playerMove(3,2);
                break;
            case R.id.imageButton44:
                playerMove(3,3);
                break;
        }
    }

    //check if grid is full (means it's a draw)
    public boolean isGridFull() {
        if(fullGrid==16) {
            return true;
        } else {
            return false;
        }
    }

    //check if someone won
    public boolean isWin(char grid[][], char player){

        if (checkRow(grid, player)) return true;
        if (checkColumn(grid, player)) return true;
        if (checkDiagonal(grid, player)) return true;
        return false;

    }

    public boolean checkRow(char grid[][], char player){
        for (int i = 0; i < 3; i++){
            if (grid[i][0] == player && grid [i][1] == player && grid[i][2] == player && grid[i][3] == player){
                return true;
            }
        }

        return false;
    }

    public boolean checkColumn(char grid[][], char player){
        for (int i = 0; i < 3; i++){
            if (grid[0][i] == player && grid [1][i] == player && grid[2][i] == player && grid[3][i] == player){
                return true;
            }
        }

        return false;
    }



    public boolean checkDiagonal(char grid[][], char player){

        if(grid[0][0] == player && grid[1][1] == player && grid[2][2] == player && grid[3][3] == player)
            return true;
        if(grid[0][3] == player && grid[1][2] == player && grid[2][1] == player && grid[3][0] == player)
            return true;


        return false;
    }



    public void winner(char player){

        final AlertDialog.Builder builder = new AlertDialog.Builder(play4x4.this);



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
                Intent intent = new Intent(play4x4.this, MainActivity.class);
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

        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
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
        outState.putChar("grid03", grid[0][3]);
        outState.putChar("grid10", grid[1][0]);
        outState.putChar("grid11", grid[1][1]);
        outState.putChar("grid12", grid[1][2]);
        outState.putChar("grid13", grid[1][3]);
        outState.putChar("grid20", grid[2][0]);
        outState.putChar("grid21", grid[2][1]);
        outState.putChar("grid22", grid[2][2]);
        outState.putChar("grid23", grid[2][3]);
        outState.putChar("grid30", grid[3][0]);
        outState.putChar("grid31", grid[3][1]);
        outState.putChar("grid32", grid[3][2]);
        outState.putChar("grid33", grid[3][3]);
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
        grid[0][3] = savedInstanceState.getChar("grid03");
        grid[1][0] = savedInstanceState.getChar("grid10");
        grid[1][1] = savedInstanceState.getChar("grid11");
        grid[1][2] = savedInstanceState.getChar("grid12");
        grid[1][3] = savedInstanceState.getChar("grid13");
        grid[2][0] = savedInstanceState.getChar("grid20");
        grid[2][1] = savedInstanceState.getChar("grid21");
        grid[2][2] = savedInstanceState.getChar("grid22");
        grid[2][3] = savedInstanceState.getChar("grid23");
        grid[3][0] = savedInstanceState.getChar("grid30");
        grid[3][1] = savedInstanceState.getChar("grid31");
        grid[3][2] = savedInstanceState.getChar("grid32");
        grid[3][3] = savedInstanceState.getChar("grid33");

        setSavedImg(0,0);
        setSavedImg(0,1);
        setSavedImg(0,2);
        setSavedImg(0,3);
        setSavedImg(1,0);
        setSavedImg(1,1);
        setSavedImg(1,2);
        setSavedImg(1,3);
        setSavedImg(2,0);
        setSavedImg(2,1);
        setSavedImg(2,2);
        setSavedImg(2,3);
        setSavedImg(3,0);
        setSavedImg(3,1);
        setSavedImg(3,2);
        setSavedImg(3,3);
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

package com.example.tonykogias.tictactoe;

import java.util.List;
import java.util.ArrayList;
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

public class play4x4bot extends AppCompatActivity implements View.OnClickListener {

    ImageButton im[][] = new ImageButton[4][4];
    TextView p1score;
    TextView botscore;

    int counter1;
    int counterbot;

    char grid[][] = new char[4][4];
    char player1 = 'o';
    char playerbot = 'x';
    int fullGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play4x4bot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.rsz_taskbaricon);
        }

        p1score = findViewById(R.id.player1Score);
        botscore = findViewById(R.id.player2Score);
        p1score.setText("Player 1:   "+counter1);
        botscore.setText("Bot:   "+counterbot);

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

    public int[] findAIMove(int depth, char player) {

        List<int[]> nextMoves = generateMoves();

        int bestScore = (player == playerbot) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if(nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            bestScore = evaluate();
        } else {

            for (int[] move : nextMoves) {
                grid[move[0]][move[1]] = player;
                if(player == playerbot) {
                    currentScore = findAIMove(depth - 1, player1)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = findAIMove(depth - 1, playerbot)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }

                // Undo move
                grid[move[0]][move[1]] = '\0';
            }
        }

        return new int[] {bestScore, bestRow, bestCol};
    }

    //Return List of moves in int[2] of {row, col}
    public List<int[]> generateMoves() {

        List<int[]> nextMoves = new ArrayList<int[]>();

        // If gameover, i.e., no next move
        if(hasWon(playerbot) || hasWon(player1)) {
            return nextMoves;
        }

        for(int i=0;i<4;i++) {
            for (int j = 0; j<4; j++) {
                if(grid[i][j] == '\0') {
                    nextMoves.add(new int[] {i, j});
                }
            }
        }

        return nextMoves;
    }

    public int evaluate() {
        int score = 0;
        // Evaluate score for each of the 10 lines (4 rows, 4 columns, 2 diagonals)
        score += evaluateLine(0, 0, 0, 1, 0, 2, 0, 3);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2, 1, 3);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2, 2, 3);  // row 2
        score += evaluateLine(3, 0, 3, 1, 3, 2, 3, 3);  // row 3
        score += evaluateLine(0, 0, 1, 0, 2, 0, 3, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1, 3, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2, 3, 2);  // col 2
        score += evaluateLine(0, 3, 1, 3, 2, 3, 3, 3);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2, 3, 3);  // diagonal
        score += evaluateLine(0, 3, 1, 2, 2, 1, 3, 0);  // alternate diagonal
        return score;
    }

    public int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3, int row4, int col4) {
        int score = 0;

        // First cell
        if (grid[row1][col1] == playerbot) {
            score = 1;
        } else if (grid[row1][col1] == player1) {
            score = -1;
        }

        // Second cell
        if (grid[row2][col2] == playerbot) {
            if (score == 1) { //cell1 is bot
                score = 10;
            } else if (score == -1) { //cell1 is player
                return 0;
            } else {  //cell1 empty
                score = 1;
            }
        } else if (grid[row2][col2] == player1) {
            if (score == -1) { //cell1 is player
                score = -10;
            } else if (score == 1) { //cell1 is bot
                return 0;
            } else {  //cell1 is empty
                score = -1;
            }
        }

        // Third cell
        if (grid[row3][col3] == playerbot) {
            if (score == 10) { //cell1 and cell2 is bot
                score = 100;
            } else if (score == 1) { //cell1 or cell2 is bot
                score = 10;
            } else if (score == -1){ //cell1 or cell2 is player
                return 0;
            } else if (score == -10) { //cell1 and cell2 is player
                score = -100;
            } else {  //cell1 and cell2 is empty
                score = 1;
            }
        } else if (grid[row3][col3] == player1) {
            if (score == -10) { //cell1 and cell2 is player
                score = -100;
            } else if (score == -1) { //cell1 or cell2 is player
                score = -10;
            } else if (score == 1){ //cell1 or cell2 is bot
                return 0;
            } else if (score == 10) { //cell1 and cell2 is bot
                score = 100;
            } else {    //cell1 and cell2 is empty
                score = -1;
            }
        }

        // Four cell
        if(grid[row4][col4] == playerbot) {
            if(score >= 100) { //cell1 and cell2 and cell3 is bot
                score *= 100;
            } else if(score <= -100) { //cell1 and cell2 and cell3 is player
                return 0;
            } else {
                score = 1;
            }
        } else if(grid[row4][col4] == player1) {
            if(score <= -100) { //cell1 and cell2 and cell3 is player
                score *=100;
            } else if(score >= 100) {
                return 0;
            } else {
                score = -1;
            }
        }

        return score;
    }

    public int[] winningPatterns = {
            0b1111000000000000, 0b0000111100000000, 0b0000000011110000, 0b0000000000001111, // rows
            0b1000100010001000, 0b0100010001000100, 0b0010001000100010, 0b0001000100010001, // cols
            0b1000010000100001, 0b0001001001001000               // diagonals
    };

    //return true if player wins
    public boolean hasWon(char player) {

        int pattern = 0b000000000000000;
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                if(grid[i][j] == player) {
                    pattern |= (1 << (i * 4 + j));
                }
            }
        }

        for (int winningPattern : winningPatterns) {
            if ((pattern & winningPattern) == winningPattern) return true;
        }
        return false;
    }

    public void playerMove(int i, int j) {

        grid[i][j] = player1;
        im[i][j].setImageResource(R.drawable.rsz_player1);
        fullGrid++;

        if(isWin(grid,player1)) {
            winner(player1);
        } else {

            if(isGridFull()) {
                winner('d');
            } else {
                botMove(grid);
            }
        }

    }

    public void botMove(char board[][]) {

        int move[] = findAIMove(3, playerbot);

        grid[move[1]][move[2]] = playerbot;
        im[move[1]][move[2]].setImageResource(R.drawable.rsz_player2);
        fullGrid++;

        if(isWin(grid,playerbot)) {
            winner(playerbot);
        } else {

            if (isGridFull()) {
                winner('d');
            }
        }


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

    public void winner(char player){

        final AlertDialog.Builder builder = new AlertDialog.Builder(play4x4bot.this);


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
                botscore.setText("Bot:   "+(++counterbot));
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
                Intent intent = new Intent(play4x4bot.this, MainActivity.class);
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

    public void restartGame() {

        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                im[i][j].setImageResource(R.drawable.rsz_blank);
                grid[i][j] = '\0';
            }
        }

        fullGrid = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putChar("currentPlayer", player1);
        outState.putInt("score1",counter1);
        outState.putInt("score2",counterbot);

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

        player1 = savedInstanceState.getChar("currentPlayer");
        counter1 = savedInstanceState.getInt("score1");
        counterbot = savedInstanceState.getInt("score2");

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


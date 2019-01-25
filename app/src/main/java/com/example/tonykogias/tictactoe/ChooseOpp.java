package com.example.tonykogias.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import org.w3c.dom.Text;

public class ChooseOpp extends AppCompatActivity {

    CheckBox player2butt;
    TextView player2;
    EditText player2edit, player1edit;
    Button startButt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_opp);


        Toolbar toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.rsz_taskbaricon);
        }

        player2butt = findViewById(R.id.checkIFplayer2);

        player2 = findViewById(R.id.textP2);
        player2edit = findViewById(R.id.editP2);
        player1edit = findViewById(R.id.editP1);

        //Checks if there is a 2nd player or vs computer
        player2butt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player2butt.isChecked()) {
                    player2.setVisibility(View.VISIBLE);
                    player2edit.setVisibility(View.VISIBLE);
                } else {
                    player2.setVisibility(View.INVISIBLE);
                    player2edit.setVisibility(View.INVISIBLE);
                }
            }
        });

        //which button was pressed in previous activity (3x3 or 4x4)
        Intent intent = getIntent();
        final int idButt = intent.getIntExtra("buttonID", 0);

        //Start the game
        startButt = findViewById(R.id.startGame);
        startButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idButt == 0) {
                    //Error handling
                } else {
                    //3x3
                    if (idButt == R.id.button_3x3) {
                        if(player2butt.isChecked()) {
                            //Play vs opponent
                            Intent intent = new Intent(ChooseOpp.this,play3x3.class);
                            intent.putExtra("player1", player1edit.getText().toString());
                            intent.putExtra("player2", player2edit.getText().toString());
                            startActivity(intent);
                        } else {
                            //Play vs computer
                            Intent intent = new Intent(ChooseOpp.this, play3x3bot.class);
                            intent.putExtra("player1", player1edit.getText().toString());
                            startActivity(intent);
                        }
                    }
                    //4x4
                    if (idButt == R.id.button_4x4) {
                        if(player2butt.isChecked()) {
                            //Play vs opponent
                            //Play vs opponent
                            Intent intent = new Intent(ChooseOpp.this,play4x4.class);
                            intent.putExtra("player1", player1edit.getText().toString());
                            intent.putExtra("player2", player2edit.getText().toString());
                            startActivity(intent);
                        } else {
                            //Play vs computer
                            Intent intent = new Intent(ChooseOpp.this, play4x4bot.class);
                            intent.putExtra("player1", player1edit.getText().toString());
                            startActivity(intent);
                        }
                    }
                }

            }
        });

    }
}

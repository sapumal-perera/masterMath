package com.emperia.isurup.mastermath;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import android.os.Bundle;

public class GameScore extends AppCompatActivity {
    /**
     * declare and initialize variables
     */

    private ArrayList<Integer> pointArr = new ArrayList<>();
    private TextView scoreScreen;
    private int quesNo = 0;
    private int totScore;

    /**
     * override onCreate
     * @param savedInstanceState ()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button toGame;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent gameplay = getIntent();
        pointArr = gameplay.getIntegerArrayListExtra("times"); //get times Array list from mathGame
        toGame = (Button) findViewById(R.id.togame);
        scoreScreen = (TextView) findViewById(R.id.scorelist);
        calculateScore();
        toGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // onclick cont
                Intent newGame = new Intent(GameScore.this, SelectLevel.class);
                scoreScreen.setText("");
                startActivity(newGame);
            }
        });
    }

    /**
     * method for calculate scores for each and toyal
     */
    public void calculateScore() {
        for (int time : pointArr) {
            quesNo++;
            if (time == 99) {
                scoreScreen.append("quest no " + quesNo + " score:   0");
                scoreScreen.append(" \n");
                scoreScreen.append(" \n");
            } else {
                int usersTempSco = 0;
                usersTempSco = (100 / (10 - time));
                totScore = totScore + usersTempSco;
                scoreScreen.setTextColor(Color.rgb(255, 111, 241));
                scoreScreen.append("quest no " + quesNo + " score:   " + usersTempSco);
                scoreScreen.append(" \n");
                scoreScreen.append(" \n");
            }
        }
        scoreScreen.append("\t\t\t Total points: " + totScore);
        totScore = 0;
    }

    /**
     * override onBackPress
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backMenu = new Intent(this, MainMenu.class);
        backMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backMenu);
        finish();
    }
}

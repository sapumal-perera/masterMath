package com.emperia.isurup.mastermath;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;


public class SelectLevel extends AppCompatActivity implements View.OnClickListener {
    /**
     * override onCreate method
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btnNovice, btnEasy, btnMedium, btnGuru;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_level);
        btnNovice = (Button) findViewById(R.id.btnNovice);
        btnEasy = (Button) findViewById(R.id.btnEasy);
        btnMedium = (Button) findViewById(R.id.btnMedium);
        btnGuru = (Button) findViewById(R.id.btnGuru);
        btnNovice.setOnClickListener(this);
        btnEasy.setOnClickListener(this);
        btnMedium.setOnClickListener(this);
        btnGuru.setOnClickListener(this);
    }

    /**
     * method for handling views
     * @param v
     */
    public void onClick(View v) {

        Intent objNewMathGame = new Intent(this, MathGame.class);

        switch (v.getId()) {
            case (R.id.btnGuru):
                objNewMathGame.putExtra("gameLevel", "btnGuru");
                objNewMathGame.putExtra("qNo", 0);
                startActivity(objNewMathGame);
                break;
            case (R.id.btnMedium):
                objNewMathGame.putExtra("gameLevel", "btnMedium");
                objNewMathGame.putExtra("qNo", 0);
                startActivity(objNewMathGame);
                break;
            case (R.id.btnNovice):
                objNewMathGame.putExtra("gameLevel", "btnNovice");
                objNewMathGame.putExtra("qNo", 0);
                startActivity(objNewMathGame);
                break;
            case (R.id.btnEasy):
                objNewMathGame.putExtra("gameLevel", "btnEasy");
                objNewMathGame.putExtra("qNo", 0);
                startActivity(objNewMathGame);
                break;
        }
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

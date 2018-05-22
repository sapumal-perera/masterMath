package com.emperia.isurup.mastermath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    /**
     * declare and initialize variables
     */
    private PopupWindow aboutGame;
    private RelativeLayout relativeLayout;
    private int quNO = 30;

    /**
     * override onCreate method
     * @param savedInstanceState (savedInstanceState)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btnNewGame, btnCont, btnAbout, btnExit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_start_menu);
        btnNewGame = (Button) findViewById(R.id.btnNewGame);
        btnNewGame.setOnClickListener(this);
        btnCont = (Button) findViewById(R.id.btnContinue);
        btnCont.setOnClickListener(this);
        btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(this);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        Intent getGameStatus = new Intent();
        quNO = getGameStatus.getIntExtra("qNo", 30);
    }

    /**
     * method for handling views
     * @param v (view)
     */
    public void onClick(View v) {
        SharedPreferences sharedPref;
        LayoutInflater inflaterLayout;
        switch (v.getId()) {
            case R.id.btnNewGame:
                Intent objNewMathGame = new Intent(MainMenu.this, SelectLevel.class);
                objNewMathGame.putExtra("isCont",false);
                startActivity(objNewMathGame);
                break;
            case R.id.btnContinue:
                sharedPref = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);
                String setLevel = sharedPref.getString("gameLevel", "btnNovice");
                int quisNo = sharedPref.getInt("question", 1);
                String contExp = sharedPref.getString("currentExp", "");
                Intent continueGame = new Intent(MainMenu.this, MathGame.class);
                continueGame.putExtra("gameLevel", setLevel);
                continueGame.putExtra("qNo", quisNo);
                continueGame.putExtra("contExp", contExp);
                continueGame.putExtra("isCont", true);
                startActivity(continueGame);
                break;
            case R.id.btnExit:
                AlertDialog.Builder mbuild = new AlertDialog.Builder(MainMenu.this);
                View mView = getLayoutInflater().inflate(R.layout.exit_game, null);
                final Button save = (Button) mView.findViewById(R.id.save);
                final Button exit = (Button) mView.findViewById(R.id.directexit);
                exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        System.exit(1);
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (quNO == 30) {
                            finish();
                            System.exit(1);
                        }
                    }
                });
                mbuild.setView(mView);
                AlertDialog dialog = mbuild.create();
                dialog.show();
                break;
            case R.id.btnAbout:
                inflaterLayout = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) inflaterLayout.inflate(R.layout.about_game, null);
                aboutGame = new PopupWindow(container, 1080, 1250, true);
                aboutGame.showAtLocation(relativeLayout, Gravity.CENTER, 0, 70);
                Button done = (Button) container.findViewById(R.id.done);
                done.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        aboutGame.dismiss();
                    }
                });
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        aboutGame.dismiss();
                        return true;
                    }
                });
                break;
        }
    }

    /**
     * override onBackPress
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        System.exit(0);// exit
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//    }


}




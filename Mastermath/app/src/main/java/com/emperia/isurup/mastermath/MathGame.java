package com.emperia.isurup.mastermath;

import android.widget.ToggleButton;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import java.util.Random;
import java.util.ArrayList;

import static android.graphics.Color.*;

public class MathGame extends AppCompatActivity implements View.OnClickListener {

    /**
     * declare and initialize variables
     */
    private ImageView resultImage;
    private int hintNum = 0;
    private ToggleButton btnHint;
    private int valPair1, valPair2, valPair3, valPair4, valPair5, questNum;
    private boolean willContinue = false;
    private boolean enterSwitch, minusVal;
    private String currentExp;
    private int trmNum;
    private ArrayList<Integer> times = new ArrayList<>();
    private String gmLevel;
    private int mathAnswer = 0, minumumTrms = 0, maximunTrms = 0;
    private Random randNum = new Random();
    private final String[] operatorArray = {"+", "-", "/", "*"};
    private CountDownTimer timerCount;
    private long remTime;
    private boolean isContine;
    private String expCont;
    private TextView quesDisplay, ansDisplay, timeDispaly, resultDispaly/*,debug*/, hintDispaly, hintNumDispaly;

    /**
     * override onCreate
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button btnEnter, btnMinus, btnDel, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_game);
        Intent gameplay = getIntent();//get game level from main menu
        gmLevel = gameplay.getStringExtra("gameLevel");
        isContine = gameplay.getBooleanExtra("isCont", false);
        expCont = gameplay.getStringExtra("contExp");
        switch (gmLevel) { //set levels calculations number
            case ("btnNovice"):
                maximunTrms = 2;
                minumumTrms = 2;
                break;
            case ("btnEasy"):
                maximunTrms = 3;
                minumumTrms = 2;
                break;
            case ("btnMedium"):
                maximunTrms = 4;
                minumumTrms = 2;
                break;
            case ("btnGuru"):
                maximunTrms = 6;
                minumumTrms = 4;
                break;
        }
        questNum = gameplay.getIntExtra("qNo", 0);
        if (questNum != 0) {
            willContinue = true;
        }
        ansDisplay = (TextView) findViewById(R.id.displayanswer);
        quesDisplay = (TextView) findViewById(R.id.displayquestion);
        timeDispaly = (TextView) findViewById(R.id.displaytime);
        resultDispaly = (TextView) findViewById(R.id.result);
        resultImage = (ImageView) findViewById(R.id.resultImg);
        hintDispaly = (TextView) findViewById(R.id.hinttext);
        hintNumDispaly = (TextView) findViewById(R.id.hintNo);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3 = (Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4 = (Button) findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5 = (Button) findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6 = (Button) findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7 = (Button) findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8 = (Button) findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
        btn9 = (Button) findViewById(R.id.btn9);
        btn9.setOnClickListener(this);
        btn0 = (Button) findViewById(R.id.btn0);
        btn0.setOnClickListener(this);
        btnEnter = (Button) findViewById(R.id.btnenter);
        btnEnter.setOnClickListener(this);
        btnDel = (Button) findViewById(R.id.btndel);
        btnDel.setOnClickListener(this);
        btnMinus = (Button) findViewById(R.id.btnminus);
        btnMinus.setOnClickListener(this);
        btnHint = (ToggleButton) findViewById(R.id.hint);
        genQuestion();// gen random questions
    }

    /**
     * method for handling views
     * @param v (view)
     */
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btndel):
                switch (ansDisplay.getText().toString()) {
                    case "":
                        break;
                    case "=- ":
                        ansDisplay.setText("= ");
                        break;
                    default:
                        if (!((ansDisplay.getText().toString().endsWith("?")) || (ansDisplay.getText().toString().endsWith("=")) || (ansDisplay.getText().toString().endsWith(" ")))) {
                            String text = ansDisplay.getText().toString();
                            ansDisplay.setText(text.substring(0, text.length() - 1));
                            break;
                        }
                }
                break;
            case (R.id.btnminus):
                if ((ansDisplay.getText().toString().endsWith("?")) || (ansDisplay.getText().toString().endsWith("=")) || (ansDisplay.getText().toString().endsWith(" ")))
                    ansDisplay.setText("= -");
                minusVal = true;
                break;

            case (R.id.btnenter):
                if (enterSwitch) {
                    ansDisplay.setText("= ?");
                    resultDispaly.setText("");
                    enterSwitch = false;
                    resultImage.setVisibility(View.INVISIBLE);
                    hintNum = 0;
                    genQuestion();
                } else {
                    if (!((ansDisplay.getText().toString().endsWith("-")) || (ansDisplay.getText().toString().endsWith("?")) || (ansDisplay.getText().toString().endsWith(" ")))) {
                        int subString = 2;
                        if (minusVal) {
                            subString = 3;
                        }
                        String answerview = ansDisplay.getText().toString();
                        if (!answerview.endsWith("?")) {
                            int userAnswer = Integer.parseInt(answerview.substring(subString));
                            if (minusVal) {
                                userAnswer = userAnswer * -1;
                                minusVal = false;
                            }
                            if ((btnHint.getText().toString().equals("Hint Off")) || (hintNum >= 3)) {
                                timerCount.cancel();
                                resultImage.setVisibility(View.VISIBLE);
                                if (userAnswer == mathAnswer) {
                                    resultDispaly.setText(R.string.correct);
                                    resultDispaly.setTextColor(rgb(0, 230, 118));
                                    resultImage.setImageResource(R.drawable.correct);
                                    times.add((int) remTime);
                                } else {
                                    resultDispaly.setText(R.string.wrong);
                                    resultDispaly.setTextColor(rgb(244, 67, 54));
                                    times.add(99);
                                    resultImage.setImageResource(R.drawable.wrong);
                                }
                                valPair1 = 0;
                                valPair2 = 0;
                                valPair3 = 0;
                                valPair4 = 0;
                                valPair5 = 0;
                                mathAnswer = 0;
                                enterSwitch = true;
                            } else if ((btnHint.getText().toString().equals("Hint On")) || hintNum < 3) {
                                hintDispaly.setVisibility(View.VISIBLE);
                                hintNumDispaly.setVisibility(View.VISIBLE);
                                resultImage.setVisibility(View.VISIBLE);
                                if (userAnswer == mathAnswer) {
                                    resultDispaly.setText(R.string.correct);
                                    resultDispaly.setTextColor(rgb(0, 230, 118));
                                    resultImage.setImageResource(R.drawable.correct);
                                    times.add((int) remTime);
                                    valPair1 = 0;
                                    valPair2 = 0;
                                    valPair3 = 0;
                                    valPair4 = 0;
                                    valPair5 = 0;
                                    mathAnswer = 0;
                                    timerCount.cancel();
                                    enterSwitch = true;
                                } else if (userAnswer < mathAnswer) {
                                    hintNum++;
                                    hintNumDispaly.setText(String.valueOf(4 - hintNum));
                                    resultDispaly.setText(R.string.greater);
                                    resultDispaly.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                    resultImage.setImageResource(R.drawable.greaterthan);
                                    resultImage.setVisibility(View.VISIBLE);
                                    resultDispaly.setTextColor(rgb(255, 152, 0));
                                    ansDisplay.setText("= ?");
                                } else if (userAnswer > mathAnswer) {
                                    hintNum++;
                                    hintNumDispaly.setText(String.valueOf(4 - hintNum));
                                    resultDispaly.setTextColor(rgb(255, 152, 0));
                                    resultImage.setImageResource(R.drawable.lessthan);
                                    resultImage.setVisibility(View.VISIBLE);
                                    resultDispaly.setGravity(View.TEXT_ALIGNMENT_CENTER);
                                    resultDispaly.setText(R.string.less);
                                    ansDisplay.setText("= ?");
                                }
                            }
                        }
                    }
                }
                break;
            default:
                if (!enterSwitch) {
                    int enteredNum = Integer.parseInt(v.getTag().toString());
                    if (ansDisplay.getText().toString().endsWith("?"))
                        ansDisplay.setText("= " + enteredNum);
                    else
                        ansDisplay.append("" + enteredNum);
                }
                break;
        }
    }

    /**
     * method for generate mathematical questions
     */
    public void genQuestion() {
        final long strtCntTime = 11 * 1000;
        final long intCntTime = 1000;
        ArrayList<String> expList = new ArrayList<>();
        ArrayList<Integer> numList = new ArrayList<>();
        ArrayList<Integer> opList = new ArrayList<>();
        resultDispaly.setText("");
        String mathexp = "";

        if (questNum == 10) {
            questNum = 0;
            Intent score = new Intent(MathGame.this, GameScore.class);
            score.putExtra("times", times);
            startActivity(score);
        } else {
            hintDispaly.setVisibility(View.INVISIBLE);
            hintNumDispaly.setVisibility(View.INVISIBLE);
            resultImage.setVisibility(View.INVISIBLE);
            btnHint.setChecked(false);
            timerCount = new TimeCounter(strtCntTime, intCntTime);
            timeDispaly.setText(getString(R.string.time_remainning) + String.valueOf(strtCntTime / 1000));
            timerCount.start();
            quesDisplay.setText("");
            ansDisplay.setText("= ?");
            Toast.makeText(getBaseContext(), "Question:" + (String.valueOf(questNum + 1)),
                    Toast.LENGTH_SHORT).show();
            if (willContinue) {
                trmNum = 0;
                willContinue = false;
            } else {
                questNum++;
                trmNum = randNum.nextInt((maximunTrms + 1) - minumumTrms) + minumumTrms;
                for (int i = 1; i <= trmNum; i++) {
                    int randomInt = randNum.nextInt(50 - 1) + 1;
                    numList.add(randomInt);
                    expList.add(String.valueOf(randomInt));
                    if (i != trmNum) {
                        int rndOp = new Random().nextInt(operatorArray.length);
                        opList.add(rndOp);//oplist
                        expList.add(operatorArray[rndOp]);
                    }
                }
            }

            switch (trmNum) {
                case (2):
                    mathCalculation(1, numList.get(0), numList.get(1), opList.get(0));
                    mathAnswer = valPair1;
                    break;
                case (3):
                    mathCalculation(1, numList.get(0), numList.get(1), opList.get(0));
                    mathCalculation(2, valPair1, numList.get(2), opList.get(1));
                    mathAnswer = valPair2;
                    break;
                case (4):
                    mathCalculation(1, numList.get(0), numList.get(1), opList.get(0));
                    mathCalculation(2, valPair1, numList.get(2), opList.get(1));
                    mathCalculation(3, valPair2, numList.get(3), opList.get(2));
                    mathAnswer = valPair3;
                    break;
                case (5):
                    mathCalculation(1, numList.get(0), numList.get(1), opList.get(0));
                    mathCalculation(2, valPair1, numList.get(2), opList.get(1));
                    mathCalculation(3, valPair2, numList.get(3), opList.get(2));
                    mathCalculation(4, valPair3, numList.get(4), opList.get(3));
                    mathAnswer = valPair4;
                    break;
                case (6):

                    mathCalculation(1, numList.get(0), numList.get(1), opList.get(0));
                    mathCalculation(2, valPair1, numList.get(2), opList.get(1));
                    mathCalculation(3, valPair2, numList.get(3), opList.get(2));
                    mathCalculation(4, valPair3, numList.get(4), opList.get(3));
                    mathCalculation(5, valPair4, numList.get(5), opList.get(4));
                    mathAnswer = valPair5;
                    break;
            }
            if (isContine) {
                isContine = false;
                quesDisplay.append(expCont);
                currentExp = expCont;
            } else {
                for (String genNumbers : expList) {
                    quesDisplay.append("" + String.valueOf(genNumbers));
                    mathexp += String.valueOf(genNumbers);
                }
                currentExp = mathexp;
            }
        }
    }

    /**
     * count remainning time and display
     */
    private class TimeCounter extends CountDownTimer {
        public TimeCounter(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override //override onFinish
        public void onFinish() {
            timeDispaly.setText(R.string.timesover);
            Toast.makeText(getBaseContext(), (R.string.timesover),
                    Toast.LENGTH_SHORT).show();
            times.add(99);
            genQuestion();
            //}
        }

        @Override //override onTick
        public void onTick(long millisUntilFinished) {
            remTime = (millisUntilFinished / 1000);
            timeDispaly.setText(getString(R.string.time_remaining) + remTime);
            timeDispaly.setTextColor(rgb(45, 245, 85));
            if (remTime <= 3) {
                timeDispaly.setTextColor(rgb(242, 12, 20));
            }
        }
    }

    /**
     * override oncBackpress
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timerCount.cancel();
        finish();//on finish
        SharedPreferences sharedPref = getSharedPreferences("gameinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("question", questNum);
        editor.putString("gameLevel", gmLevel);
        editor.putString("currentExp", currentExp);
        editor.commit();
        Intent backMenu = new Intent(this, MainMenu.class);
        backMenu.putExtra("gameLevel", gmLevel);
        backMenu.putExtra("qNo", questNum);
        backMenu.putExtra("trmNum", trmNum);
        backMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backMenu);
    }

    /**
     * this method calculates the correct answer
     * @param mathpair
     * @param value1
     * @param value2
     * @param operatr
     */
    public void mathCalculation(int mathpair, int value1, int value2, int operatr) {
        final int ADD_NUM_OP = 0, MULT_NUM_OP = 3, SUBT_NUM_OP = 1, DIV_NUM_OP = 2;
        int result = 0;
        switch (operatr) {
            case ADD_NUM_OP:
                result = (value1 + value2);
                break;
            case SUBT_NUM_OP:
                result = (value1 - value2);
                break;
            case DIV_NUM_OP:
                result = (value1 / value2);
                break;
            case MULT_NUM_OP:
                result = (value1 * value2);
                break;
            default:
                break;
        }
        switch (mathpair) {
            case 1:
                valPair1 = result;
                break;
            case 2:
                valPair2 = result;
                break;
            case 3:
                valPair3 = result;
                break;
            case 4:
                valPair4 = result;
                break;
            case 5:
                valPair5 = result;
                break;
        }
    }
}
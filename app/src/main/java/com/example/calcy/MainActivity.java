package com.example.calcy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Method;
import java.util.zip.InflaterInputStream;

public class MainActivity extends AppCompatActivity {
    EditText screen;
    TextView previousResult;
    Boolean focus1 = true, focus2= false;
    Boolean add = false, sub = false, multi = false, div = false, mod = false;
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0,
            buttonAdd, buttonSub, buttonMulti, buttonDiv, buttonMod, buttonDec, buttonC, buttonEquals,
            buttonDelete, buttonBrackets, buttonSin, buttonCos, buttonTan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitializeFields();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            screen.setShowSoftInputOnFocus(false);
        } else {
            try {
                final Method method = EditText.class.getMethod(
                        "setShowSoftInputOnFocus"
                        , new Class[]{boolean.class});
                method.setAccessible(true);
                method.invoke(screen, false);
            } catch (Exception e) {
                // ignore
            }
        }
        screen.setOnClickListener(v -> {
            screen.setHint(null);
        });
        button0.setOnClickListener(v -> updateText("0"));
        button1.setOnClickListener(v -> updateText("1"));
        button2.setOnClickListener(v -> updateText("2"));
        button3.setOnClickListener(v -> updateText("3"));
        button4.setOnClickListener(v -> updateText("4"));
        button5.setOnClickListener(v -> updateText("5"));
        button6.setOnClickListener(v -> updateText("6"));
        button7.setOnClickListener(v -> updateText("7"));
        button8.setOnClickListener(v -> updateText("8"));
        button9.setOnClickListener(v -> updateText("9"));
        buttonSin.setOnClickListener(v -> updateTrigoText("sin("));
        buttonCos.setOnClickListener(v -> updateTrigoText("cos("));
        buttonTan.setOnClickListener(v -> updateTrigoText("tan("));
        buttonAdd.setOnClickListener(v -> updateText("+"));
        buttonSub.setOnClickListener(v -> updateText("-"));
        buttonMulti.setOnClickListener(v -> updateText("*"));
        buttonDiv.setOnClickListener(v -> updateText("/"));
        buttonDec.setOnClickListener(v -> updateText("."));
        buttonMod.setOnClickListener(v -> updateText("%"));
        buttonDelete.setOnClickListener(v -> {
            int cursorPos = screen.getSelectionStart();
            int textLength = screen.getText().length();
            if (cursorPos!=0 && textLength!=0){
                SpannableStringBuilder stringBuilder = (SpannableStringBuilder) screen.getText();
                stringBuilder.replace(cursorPos-1, cursorPos, "");
                screen.setText(stringBuilder);
                screen.setSelection(cursorPos-1);
            }
        });
        buttonEquals.setOnClickListener(v -> {
            try {
                String lastExpression = screen.getText().toString();
                Expression expression = new ExpressionBuilder(lastExpression).build();
                double result = expression.evaluate();
                if (String.valueOf(result) != lastExpression) {
                    Animation animation = new AlphaAnimation(0.0f, 1.0f);
                    animation.setDuration(500);
                    previousResult.setText(lastExpression + " = ");
                    previousResult.startAnimation(animation);
                    Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim1);
                    anim.setDuration(500);
                    screen.startAnimation(anim);
                    screen.setText(String.valueOf(result));
                    screen.setSelection(screen.getText().length());
                }
                else {
                    Toast.makeText(this, "Please enter a valid input", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e){

            }

        });
        buttonC.setOnClickListener(v -> {
            screen.setText(null);
            previousResult.setText(null);
        });
        buttonBrackets.setOnClickListener(v -> {
            int cursorPos = screen.getSelectionStart();
            int opnPar = 0;
            int closedPar = 0;
            int textLength = screen.getText().length();
            for (int i=0; i<cursorPos; i++){
                if (screen.getText().toString().substring(i,i+1).equals("(")){
                    opnPar++;
                }
                if (screen.getText().toString().substring(i,i+1).equals(")")){
                    closedPar++;
                }
            }

            if (opnPar == closedPar || screen.getText().toString().substring(textLength-1, textLength).equals("(")){
                updateText("(");
            }
            else if (opnPar > closedPar && !screen.getText().toString().substring(textLength-1, textLength).equals("(")){
                updateText(")");
            }
            screen.setSelection(cursorPos + 1);
        });

    }
     private void updateText(String strToAdd){
        String oldStr = screen.getText().toString();
        int cursorPos = screen.getSelectionStart();
        String leftStr = oldStr.substring(0,cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        screen.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        screen.setSelection(cursorPos + 1);
     }
     private void updateTrigoText(String strToAdd){
        String oldStr = screen.getText().toString();
        int cursorPos = screen.getSelectionStart();
        String leftStr = oldStr.substring(0,cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        screen.setText(String.format("%s%s%s", leftStr, strToAdd, rightStr));
        screen.setSelection(cursorPos + 4);
     }

    private void InitializeFields() {
        previousResult = findViewById(R.id.previousResult);
        screen = findViewById(R.id.userInput);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonSin = findViewById(R.id.buttonSin);
        buttonCos = findViewById(R.id.buttonCos);
        buttonTan = findViewById(R.id.buttonTan);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonSub = findViewById(R.id.buttonSub);
        buttonMulti = findViewById(R.id.buttonMul);
        buttonDiv = findViewById(R.id.buttonDiv);
        buttonMod = findViewById(R.id.buttonModulo);
        buttonDec = findViewById(R.id.buttonDec);
        buttonEquals = findViewById(R.id.buttonEquals);
        buttonC = findViewById(R.id.buttonC);
        buttonDelete = findViewById(R.id.buttonDel);
        buttonBrackets = findViewById(R.id.buttonBrackets);
    }
}
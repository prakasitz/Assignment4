package com.prakasit.a59050083.assignment4;

import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CalulatorMain extends AppCompatActivity implements
        View.OnClickListener {
    Button btnZero, btnDot, btnEqual,
            btnOne, btnTwo, btnThree,
            btnFour, btnFive, btnSix,
            btnSeven, btnEight, btnNine,
            btnClear, btnDel, btnPlus,
            btnMinus, btnMutiple, btnDevide;
    TextView textMain, textSub;
    int cnt = 1;
    private String str = "";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        cnt++;
        int idOfItem = item.getItemId();
        switch (idOfItem){
            case R.id.menuItem1: {
                Toast.makeText(this, "Item1 clicked:" + cnt,
                        Toast.LENGTH_LONG).show();
                    if(cnt%2 == 0) {
                        setContentView(R.layout.activity_calulator_main_1);
                        a();
                        textMain.setText(str);
                    } else {
                        setContentView(R.layout.activity_calulator_main);
                        a();
                        textMain.setText(str);
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calulator_main);
        a();
    }

    public void a() {
        btnClear = findViewById(R.id.buttonClear); //Clear
        btnDel = findViewById(R.id.buttonDel); //Delete
        btnPlus = findViewById(R.id.buttonPlus); // +
        btnMinus = findViewById(R.id.buttonMinus); // -
        btnMutiple = findViewById(R.id.buttonMutiple);// *
        btnDevide = findViewById(R.id.buttonDivide);// /
        btnEqual = findViewById(R.id.buttonEqual);// =
        btnDot = findViewById(R.id.buttonDot);// .

        btnClear.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMutiple.setOnClickListener(this);
        btnDevide.setOnClickListener(this);
        btnEqual.setOnClickListener(this);
        btnDot.setOnClickListener(this);

        btnZero = findViewById(R.id.buttonZero);
        btnOne = findViewById(R.id.buttonOne);
        btnTwo = findViewById(R.id.buttonTwo);
        btnThree = findViewById(R.id.buttonThree);
        btnFour = findViewById(R.id.buttonFour);
        btnFive = findViewById(R.id.buttonFIve);
        btnSix = findViewById(R.id.buttonSix);
        btnSeven = findViewById(R.id.buttonSeven);
        btnEight = findViewById(R.id.buttonEight);
        btnNine = findViewById(R.id.buttonNine);



        btnZero.setOnClickListener(this);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        btnFour.setOnClickListener(this);
        btnFive.setOnClickListener(this);
        btnSix.setOnClickListener(this);
        btnSeven.setOnClickListener(this);
        btnEight.setOnClickListener(this);
        btnNine.setOnClickListener(this);


        textMain = findViewById(R.id.MainText);
        textSub = findViewById(R.id.SubText);
    }



    @Override
    public void onClick(View v) {
        if(v == btnEqual) {
            if(str == "") { //chk ตัวแรกต้องไม่เป็น 0 ไม่งั้นมัน error
                textMain.setText("");
            } else { //ไม่เป็น ""
                for(int i = 0; i < str.length() ; i++) { //เช็กแต่ละตัวว่า ตัวที่ติดกันจากซ้ายไปขวา เป็น เครื่องหมายหรือไม่ ถ้าเป็น ให้ error
                    if(!(str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                        if(i+1 < str.length() && !(str.charAt(i+1) >= '0' && str.charAt(i+1) <= '9')) {
                            String errorTxt = Character.toString(str.charAt(i+1));
                            textMain.setText("Invalid:" + errorTxt);
                            str = "";
                            break;
                        } else if(i == str.length()) { //ถ้า i เป็นตัวสุดท้าย
                            textMain.setText("Invalid:Subtraction");
                            str = "";
                        }
                    }
                } //end for

                if(textMain.getText().toString() != "Invalid+") { //ถ้า ไม่ error ให้เอาค่าใส่ฟังชั่น
                    double a = eval(str);
                    textMain.setText(String.valueOf(a));
                }
            }
        } else if (v == btnClear) { //ถ้าเป็นปุ่ม clear
            str = "";
            textMain.setText(str);
        }  else if (v == btnDel) { //ถ้าเป็นปุ่ม Del
            if(str.length() > 0) {
                str = str.substring(0, str.length() - 1);
                textMain.setText(str);
            }
        } else { //ปุ่มอื่นๆ จะทำการบวกเรื่อยๆ
            Button btn = (Button) v;
            str += btn.getText().toString();
            if(str.charAt(0)  >= '0' && str.charAt(0) <= '9') { //check ตัวแรกต้องเป็น 0-9
                textMain.setText(str);
            } else { //ไม่ใช่ให้ขึ้น error
                textMain.setText("Invalid");
                str= "";
            }
        }

    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;
            void nextChar() {
                ch = (++pos < str.length()) ? (str.charAt(pos)) : -1;
            }
            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }
            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            // | number | functionName factor | factor `^` factor
            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }
            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }
            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }
                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation
                return x;
            }
        }.parse(); //end obj()
    } //end eval
}

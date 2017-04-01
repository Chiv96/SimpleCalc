package com.example.chivo.simplecalc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends Activity implements View.OnClickListener {

    TextView display;
    Button ac, zero, one, two, three, four, five, six, seven, eight, nine, plus, minus, div, mul, plusminus, percent, equal, dot;

    String input = null;
    Integer output = 0;

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = (TextView) findViewById(R.id.display_screen);
        ac = (Button) findViewById(R.id.ac);
        percent = (Button) findViewById(R.id.percentage);
        equal = (Button) findViewById(R.id.equal);
        dot = (Button) findViewById(R.id.dot);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        div = (Button) findViewById(R.id.divide);
        mul = (Button) findViewById(R.id.multiply);
        zero = (Button) findViewById(R.id.zero);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);

        ac.setOnClickListener(this);
        percent.setOnClickListener(this);
        equal.setOnClickListener(this);
        dot.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        TextView d = (TextView) findViewById(R.id.display_screen);
        Button button = (Button) v;

        switch (v.getId()) {
            case R.id.ac:
                input = null;
                output = 0;
                d.setText("0");
                break;

            case R.id.equal:
                output = eval(input);
                d.setText(String.valueOf(output));
                break;

            default:
                if(input==null)
                    input=button.getText().toString();
                else
                    input += button.getText().toString();
                d.setText(input);
                break;

        }
    }

    public int checkPrec(char op)
    {
        if(op=='%' || op=='*' || op=='/')
            return 2;
        else if(op=='+' || op=='-')
            return 1;
        return 0;
    }

    public int applyOp(char op, Integer n1, Integer n2)
    {
        switch(op)
        {
            case '%':
                return n1%n2;
            case '*':
                return n1*n2;
            case '/':
                return n1/n2;
            case '+':
                return n1+n2;
            case '-':
                return n1-n2;
        }
        return 0;
    }

    public int eval(String input)
    {
        //char[] digit = new char[10];
        List<String> digits = new ArrayList<>();
        char getop;
        Integer n1,n2,output;
        char[] tokens= input.toCharArray();
        Stack<Integer> values= new Stack<Integer>();
        Stack<Character> ops= new Stack<Character>();
        for(int i=0;i<tokens.length;i++)
        {
            Log.i(TAG, "eval: " + i + " token val: " + tokens[i]);
            if(tokens[i]==' ') {
                Log.i(TAG, "eval: tokens[i]==' '");
            } else if(Character.isDigit(tokens[i])) {
                Log.i(TAG, "eval: tokens[i]>=0 && tokens[i]<=9");
                int k=0;
                int counter = i;
                while(Character.isDigit(tokens[counter])) {
                    //digit[k++] = tokens[counter++];
                    Log.i(TAG, "eval: checking: " + tokens[counter]);
                    digits.add(String.valueOf((tokens[counter])));
                    if (tokens.length - counter == 1) {
                        break;
                    }
                    counter++;
                    //Log.i(TAG, "eval: " + digit.toString());
                }
                //digit[k]='\
                Log.i(TAG, "eval: digit: " + digits.toString());
                String number = "";
                for (String string : digits) {
                    number = number + string;
                }
                values.push(Integer.valueOf(number));
            } else {
                Log.i(TAG, "eval: else");
                 if(!ops.empty() && ((checkPrec(ops.peek())>checkPrec(tokens[i])) || (checkPrec(ops.peek())==checkPrec(tokens[i])))) {
                     getop=ops.pop();
                     ops.push(tokens[i]);
                     n1=values.pop();
                     n2=values.pop();
                     values.push(applyOp(getop,n1,n2));
                 } else
                     ops.push(tokens[i]);

            }

        }
        while(!ops.empty())
            values.push(applyOp(ops.pop(),values.pop(),values.pop()));
        output=values.pop();
        return output;
    }
}

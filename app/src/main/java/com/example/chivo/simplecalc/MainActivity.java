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
    Button ac, zero, one, two, three, four, five, six, seven, eight, nine, plus, minus, div, mul, percent, equal, dot, clr;
    String input = null;
    Integer flag=0;
    double output = 0.0;
    private static final String TAG= MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        clr= (Button) findViewById(R.id.clr);

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
        clr.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        TextView d = (TextView) findViewById(R.id.display_screen);
        TextView d2 = (TextView) findViewById(R.id.display_screen2);

        Button button = (Button) v;
        switch (v.getId()) {
            case R.id.ac:
                input = null;
                output = 0.0;
                d.setText("");
                d2.setText("0");
                break;

            case R.id.clr:
                if(input==null)
                    d2.setText("0");
                else
                {
                    input = input.substring(0, input.length() - 1);
                    d2.setText(input);
                }
                break;


            case R.id.equal:
                output = eval(input);
                d.setText(input);
                if (output=='0' || flag==1)
                    d2.setText("<ERROR>");
                else                //CHECKKK!!!!!!
                {

                    int whole=(int)output;
                    double dec=output-whole;
                    if(dec==0.0)
                        output=whole;
                    d2.setText(String.valueOf(output));
                }

                break;

            default:
                if(input==null)
                    input=button.getText().toString();
                else
                    input += button.getText().toString();
                d2.setText(input);
                break;

        }
    }

    public int checkPrec(char op)
    {
        if(op=='%' || op=='x' || op=='/')
            return 2;
        else if(op=='+' || op=='-')
            return 1;
        return 0;
    }

    public Double applyOp(char op, Double n2, Double n1)
    {
        switch(op)
        {
            case '%':
                return n1%n2;
            case 'x':
                return n1*n2;
            case '/':
                if(n2==0)
                    flag=1;
                else
                    return n1/n2;
            case '+':
                return n1+n2;
            case '-':
                return n1-n2;
        }
        return 0.0;
    }

    public double eval(String input)
    {


        List<String> digits = new ArrayList<>();
        char getop;
        Double n1,n2,output;
        char[] tokens= input.toCharArray();

        Stack<Double> values= new Stack<Double>();
        Stack<Character> ops= new Stack<Character>();
        for(int i=0;i<tokens.length;i++)
        {
             if(Character.isDigit(tokens[i]) || tokens[i]=='.') {
                int counter = i;
                while(Character.isDigit(tokens[counter]) || tokens[counter]=='.')
                {

                    digits.add(String.valueOf((tokens[counter])));
                    if (tokens.length - counter == 1)
                        break;

                    counter++;

                }
                if(counter-i>1)
                      i=counter-1;

                String number = "";
                for (String string : digits) {
                    number = number + string;
                }

                values.push(Double.valueOf(number));
                digits.clear();
                flag=0;

            }


            else {

                if(flag==1)
                {
                    return '0';
                }

                 if(!ops.empty() && ((checkPrec(ops.peek())>checkPrec(tokens[i])) || (checkPrec(ops.peek())==checkPrec(tokens[i]))))
                 {

                     getop=ops.pop();
                     ops.push(tokens[i]);
                     flag=1;

                     n1=values.pop();
                     n2=values.pop();
                     values.push(applyOp(getop,n1,n2));

                 }
                 else {
                     ops.push(tokens[i]);
                     flag=1;

                 }
            }

        }
        while(!ops.empty())
            values.push(applyOp(ops.pop(),values.pop(),values.pop()));
        output=values.pop();
        return output;
    }
}

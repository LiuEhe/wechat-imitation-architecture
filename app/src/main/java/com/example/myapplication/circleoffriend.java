package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class circleoffriend extends Fragment implements View.OnClickListener{

    //初始化变量
    EditText et;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0,
            btnadd, btnsub, btnmul, btndot, btndiv, btnequ, btnc,btnback;
    double num1 = 0, num2 = 0;
    double result = 0;
    int op = 0;
    boolean isClickEqu = false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        //使用View绑定页面
        View view = inflater.inflate(R.layout.circleoffriend, container, false);

        //绑定id
        et =  view.findViewById(R.id.et);
        btn0 =  view.findViewById(R.id.btn0);
        btn1 =  view.findViewById(R.id.btn1);
        btn2 =  view.findViewById(R.id.btn2);
        btn3 =  view.findViewById(R.id.btn3);
        btn4 =  view.findViewById(R.id.btn4);
        btn5 =  view.findViewById(R.id.btn5);
        btn6 =  view.findViewById(R.id.btn6);
        btn7 =  view.findViewById(R.id.btn7);
        btn8 =  view.findViewById(R.id.btn8);
        btn9 =  view.findViewById(R.id.btn9);
        btnadd =  view.findViewById(R.id.btnadd);
        btnsub =  view.findViewById(R.id.btnsub);
        btnmul =  view.findViewById(R.id.btnmul);
        btndiv =  view.findViewById(R.id.btndiv);
        btndot =  view.findViewById(R.id.btndot);
        btnequ =  view.findViewById(R.id.btnequ);
        btnc =  view.findViewById(R.id.btnc);
        btnback =  view.findViewById(R.id.back);

        // 设置按钮点击事件
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnadd.setOnClickListener(this);
        btnsub.setOnClickListener(this);
        btnmul.setOnClickListener(this);
        btndiv.setOnClickListener(this);
        btndot.setOnClickListener(this);
        btnequ.setOnClickListener(this);
        btnc.setOnClickListener(this);
        //btnback.setOnClickListener(this);

        return view;
        //return inflater.inflate(R.layout.circleoffriend,container,false);
    }
    public void onClick(View v) {
        switch (v.getId()) {

//            case  R.id.back:
//                Intent intent = new Intent();
//                intent.setClass(circleoffriend.this,MainActivity.class);
//                startActivity(intent);
//                finish();

            case R.id.btn0:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str0 = et.getText().toString();
                str0 += "0";
                et.setText(str0);
                break;
            case R.id.btn1:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str1 = et.getText().toString();
                str1 += "1";
                et.setText(str1);
                break;
            case R.id.btn2:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str2 = et.getText().toString();
                str2 += "2";
                et.setText(str2);
                break;
            case R.id.btn3:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str3 = et.getText().toString();
                str3 += "3";
                et.setText(str3);
                break;
            case R.id.btn4:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str4 = et.getText().toString();
                str4 += "4";
                et.setText(str4);
                break;
            case R.id.btn5:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str5 = et.getText().toString();
                str5 += "5";
                et.setText(str5);
                break;
            case R.id.btn6:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str6 = et.getText().toString();
                str6 += "6";
                et.setText(str6);
                break;
            case R.id.btn7:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str7 = et.getText().toString();
                str7 += "7";
                et.setText(str7);
                break;
            case R.id.btn8:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str8 = et.getText().toString();
                str8 += "8";
                et.setText(str8);
                break;
            case R.id.btn9:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String str9 = et.getText().toString();
                str9 += "9";
                et.setText(str9);
                break;
            case R.id.btndot:
                if (isClickEqu) {
                    et.setText(null);
                    isClickEqu = false;
                }
                String strdot = et.getText().toString();
                strdot += ".";
                et.setText(strdot);
                break;

            case R.id.btnc:
                et.setText(null);
                break;

            case R.id.btnadd:
                String stradd = et.getText().toString();
                if (stradd.equals(null))
                    return;
                num1 = Double.valueOf(stradd);
                et.setText(null);
                op = 1;
                isClickEqu = false;
                break;
            case R.id.btnsub:
                String strsub = et.getText().toString();
                if (strsub.equals(null))
                    return;
                num1 = Double.valueOf(strsub);
                et.setText(null);
                op = 2;
                isClickEqu = false;
                break;
            case R.id.btnmul:
                String strmul = et.getText().toString();
                if (strmul.equals(null))
                    return;
                num1 = Double.valueOf(strmul);
                et.setText(null);
                op = 3;
                isClickEqu = false;
                break;
            case R.id.btndiv:
                String strdiv = et.getText().toString();
                if (strdiv.equals(null))
                    return;
                num1 = Double.valueOf(strdiv);
                et.setText(null);
                op = 4;
                isClickEqu = false;
                break;

            case R.id.btnequ:
                String strequ = et.getText().toString();
                if (strequ.equals(null))
                    return;
                num2 = Double.valueOf(strequ);
                et.setText(null);
                switch (op) {
                    case 0:
                        result = num2;
                        break;
                    case 1:
                        result = num1 + num2;
                        break;
                    case 2:
                        result = num1 - num2;
                        break;
                    case 3:
                        result = num1 * num2;
                        break;
                    case 4:
                        result = num1 / num2;
                        break;
                    default:
                        result = 0;
                        break;
                }
                et.setText(String.valueOf(result));
                isClickEqu = true;
                break;
            default:
                break;
        }
    }

}
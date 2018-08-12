package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.fraction_util.FractionClass;
import com.example.laher.learnfractions.R;

public class MixedConverterDialog extends Dialog {
    TextView txtWholeNum, txtNum1, txtNum2, txtDenom1, txtDenom2, txtEquation;
    Button btnConvert;
    FractionClass mixedFraction;


    public MixedConverterDialog(Context context){
        super(context);
        setGui();
    }
    public MixedConverterDialog(Context context, FractionClass mixedFraction){
        super(context);
        if (mixedFraction.getContext()== FractionClass.MIXED){
            setGui();

            txtWholeNum.setText(String.valueOf(mixedFraction.getWholeNum()));
            txtNum1.setText(String.valueOf(mixedFraction.getNumerator()));
            txtDenom1.setText(String.valueOf(mixedFraction.getDenominator()));
        }
    }
    public void setGui(){
        setTitle("Converter");
        setContentView(R.layout.layout_mixed_convert);
        txtWholeNum = findViewById(R.id.mcn_txtWholeNum1);
        txtNum1 = findViewById(R.id.mcn_txtNum1);
        txtNum2 = findViewById(R.id.mcn_txtNum2);
        txtDenom1 = findViewById(R.id.mcn_txtDenom1);
        txtDenom2 = findViewById(R.id.mcn_txtDenom2);
        txtEquation = findViewById(R.id.mcn_txtEquation);
        btnConvert = findViewById(R.id.mcn_btnConvert);
    }
}

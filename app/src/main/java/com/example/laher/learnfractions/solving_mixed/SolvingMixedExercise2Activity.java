package com.example.laher.learnfractions.solving_mixed;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.laher.learnfractions.Fraction;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.util_layout.MixedConverterDialog;

public class SolvingMixedExercise2Activity extends AppCompatActivity {
    Fraction mixedFraction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraction_equation_mixed);

        mixedFraction = new Fraction(Fraction.MIXED);
        Dialog mixedConverterDialog =  new MixedConverterDialog(this, mixedFraction);
        mixedConverterDialog.show();
    }
}

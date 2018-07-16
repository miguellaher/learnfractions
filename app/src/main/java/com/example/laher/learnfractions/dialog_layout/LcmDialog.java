package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.fraction_util.Question;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.util.Styles;

public class LcmDialog extends Dialog {
    TextView txtNum1, txtNum2, txtNum3;
    EditText inputLcm;
    Button btnCheck;
    int num1, num2, num3;
    int lcm;

    public int getLcm() {
        return lcm;
    }

    public LcmDialog(Context context, int num1, int num2) {
        super(context);
        setGui();
        this.num1 = num1;
        this.num2 = num2;

        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
        txtNum3.setText("");
    }
    public LcmDialog(Context context, int num1, int num2, int num3) {
        super(context);
        setGui();
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;

        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
        txtNum3.setText(String.valueOf(num3));
    }
    public void setGui(){
        setTitle("Converter");
        setContentView(R.layout.layout_dialog_lcm);

        txtNum1 = findViewById(R.id.lcm_txtNum1);
        txtNum2 = findViewById(R.id.lcm_txtNum2);
        txtNum3 = findViewById(R.id.lcm_txtNum3);
        inputLcm = findViewById(R.id.lcm_inputLcm);
        inputLcm.setOnEditorActionListener(new InputListener());
        btnCheck = findViewById(R.id.lcm_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        resetVars();
    }
    public void resetVars(){
        num1 = 0;
        num2 = 0;
        num3 = 0;
        lcm = 0;
    }
    public class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!inputLcm.getText().toString().matches("")) {
                if (num1 != 0 && num2 != 0) {
                    int nums[] = {num1, num2};
                    lcm = (int) Question.getLCM(nums);
                    if (Integer.valueOf(String.valueOf(inputLcm.getText())) == lcm) {
                        dismiss();
                    }
                }
            } else {
                Styles.shakeAnimate(inputLcm);
            }
        }
    }
    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        inputLcm.setText("");
    }
    private class InputListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnCheck.performClick();
            }
            return false;
        }
    }
}

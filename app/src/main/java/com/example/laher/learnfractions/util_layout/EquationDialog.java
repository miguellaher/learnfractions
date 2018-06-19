package com.example.laher.learnfractions.util_layout;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.util.Styles;

public class EquationDialog extends Dialog {
    TextView txtNum1, txtNum2, txtSign;
    EditText inputAns;
    Button btnCheck;

    int num1, num2, answer;
    String type, hint;
    boolean isCorrect;
    public final static String ADDITION = "ADDITION";
    public final static String SUBTRACTION = "SUBTRACTION";
    public final static String MULTIPLICATION = "MULTIPLICATION";
    public final static String DIVISION = "DIVISION";

    public int getAnswer() {
        return answer;
    }
    public boolean isCorrect() {
        return isCorrect;
    }
    public EditText getInputAns() {
        return inputAns;
    }
    public String getHint() {
        return hint;
    }

    public EquationDialog(Context context, int num1, int num2, String type) {
        super(context);
        setGui();
        this.num1 = num1;
        this.num2 = num2;
        this.type = type;

        txtNum1.setText(String.valueOf(num1));
        txtNum2.setText(String.valueOf(num2));
        if (ADDITION.equals(type)) {
            answer = num1 + num2;
            txtSign.setText("+");
        } else if (SUBTRACTION.equals(type)) {
            answer = num1 - num2;
            txtSign.setText("-");
        } else if (MULTIPLICATION.equals(type)) {
            answer = num1 * num2;
            txtSign.setText("x");
            if (num1>10 && num2>1 || num2>10 && num1>1){
                inputAns.setHint(String.valueOf(answer));
                hint = String.valueOf(answer);
            }
        } else if (DIVISION.equals(type)) {
            answer = num1 / num2;
            txtSign.setText("÷");
        }
    }


    private void setGui(){
        setContentView(R.layout.layout_dialog_equation);

        txtNum1 = findViewById(R.id.md_txtMultiplicand);
        txtNum2 = findViewById(R.id.md_txtMultiplier);
        txtSign = findViewById(R.id.md_txtSign);
        inputAns = findViewById(R.id.md_inputProduct);
        inputAns.setOnKeyListener(new InputListener());
        btnCheck = findViewById(R.id.md_btnCheck);
        btnCheck.setOnClickListener(new BtnCheckListener());
        resetVars();
    }

    private void resetVars(){
        num1 = 0;
        num2 = 0;
        answer = -1;
        type = null;
        isCorrect = false;
        hint = "";
    }

    private class BtnCheckListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!inputAns.getText().toString().matches("")){
                if (Integer.valueOf(String.valueOf(inputAns.getText())) ==
                        answer){
                    isCorrect = true;
                    dismiss();
                } else {
                    Styles.paintRed(inputAns);
                    Styles.shakeAnimate(inputAns);
                }
            } else {
                Styles.shakeAnimate(inputAns);
            }
        }
    }


    private class InputListener implements EditText.OnKeyListener{
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            Styles.paintBlack(inputAns);
            return false;
        }
    }
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        inputAns.setText("");
        inputAns.setHint("");
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        super.setOnShowListener(listener);
        inputAns.setHintTextColor(Color.rgb(128,128,128));
    }
}

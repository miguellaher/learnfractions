package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.laher.learnfractions.R;

public class AdminVerificationDialog extends Dialog {
    EditText inputAccessCode;
    Button btnVerify;
    private final String ACCESS_CODE = "FRITSLOVE";
    boolean isLegit;

    public boolean isLegit() {
        return isLegit;
    }
    public AdminVerificationDialog(@NonNull Context context) {
        super(context);
        setGui();
        btnVerify.setOnClickListener(new ButtonVerifyListener());
        isLegit = false;
    }
    private void setGui(){
        setContentView(R.layout.layout_dialog_admin_create);
        inputAccessCode = findViewById(R.id.dialog_admin_create_inputAccessCode);
        btnVerify = findViewById(R.id.dialog_admin_create_btnVerify);
    }
    private class ButtonVerifyListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!inputAccessCode.getText().toString().trim().matches("")){
                if (inputAccessCode.getText().toString().matches(ACCESS_CODE)){
                    isLegit = true;
                    dismiss();
                }
            }
        }
    }
}

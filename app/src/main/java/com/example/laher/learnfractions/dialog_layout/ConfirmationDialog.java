package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;

public class ConfirmationDialog extends Dialog {
    private TextView txtMessage;
    private Button btnOk, btnCanel;
    private boolean confirmed;

    public ConfirmationDialog(@NonNull Context context, String message) {
        super(context);
        setGui();
        txtMessage.setText(message);
        confirmed=false;
        btnOk.setOnClickListener(new BtnListener());
        btnCanel.setOnClickListener(new BtnListener());
    }
    private void setGui(){
        setContentView(R.layout.dialog_confirmation);
        txtMessage = findViewById(R.id.dialog_confirmation_txtMessage);
        btnOk = findViewById(R.id.dialog_confirmation_btnOk);
        btnCanel = findViewById(R.id.dialog_confirmation_btnCancel);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    private class BtnListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            Button b = (Button) v;
            if (b.getId()==R.id.dialog_confirmation_btnOk){
                confirmed=true;
            } else if (b.getId()==R.id.dialog_confirmation_btnCancel){
                confirmed=false;
            }
            dismiss();
        }
    }
}

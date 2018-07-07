package com.example.laher.learnfractions.dialog_layout;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.util.Styles;

public class MessageDialog extends Dialog {
    TextView txtMessage;
    Button btnOk;
    String message;

    public MessageDialog(Context context, String message) {
        super(context);
        this.message = message;
        setGui();
        txtMessage.setText(this.message);
    }
    private void setGui(){
        setContentView(R.layout.dialog_message);
        txtMessage = findViewById(R.id.dialog_message_txtMessage);
        btnOk = findViewById(R.id.dialog_message_btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}

package com.example.laher.learnfractions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laher.learnfractions.dialog_layout.AdminVerificationDialog;
import com.example.laher.learnfractions.model.Admin;
import com.example.laher.learnfractions.service.AdminService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Context context = this;

    TextView txtTitle, txtError, txtMessage;
    EditText inputUserName, inputPassword, inputConfirmPassword, inputAge, inputSecurityAnswer, inputTeacherCode;
    RadioGroup radioGroupGender;
    RadioButton radioMale, radioFemale;
    Spinner spinnerSecurityQuestion;
    Button btnRegister;

    //FOR REGISTERING AN ADMIN
    int easterEggClicks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtTitle = findViewById(R.id.rTxtTitle);
        easterEggClicks = 0;
        txtTitle.setOnClickListener(new TextViewTitleListener());
        txtError = findViewById(R.id.rTxtError);
        txtError.setVisibility(TextView.INVISIBLE);
        txtMessage = findViewById(R.id.rTxtMessage);
        txtMessage.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,
                        LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        inputUserName = findViewById(R.id.rInputUsername);
        inputPassword = findViewById(R.id.rInputPassword);
        inputConfirmPassword = findViewById(R.id.rInputConfirm);
        inputAge = findViewById(R.id.rInputAge);
        radioGroupGender = findViewById(R.id.rRadioGroupGender);
        radioMale = findViewById(R.id.rRadioMale);
        radioFemale = findViewById(R.id.rRadioFemale);
        spinnerSecurityQuestion = findViewById(R.id.rSpinnerSecurityQuestion);
        inputSecurityAnswer = findViewById(R.id.rInputSecurityAnswer);
        inputTeacherCode = findViewById(R.id.rInputTeacherCode);
        btnRegister = findViewById(R.id.rBtnRegister);
    }
    private void showTxtError(String errorMessage){
        txtError.setText(errorMessage);
        Styles.paintRed(txtError);
        txtError.setVisibility(View.VISIBLE);
    }
    private void setAdminRegistration(){
        txtTitle.setText("Admin Registration");
        inputAge.setVisibility(View.INVISIBLE);
        radioGroupGender.setVisibility(View.INVISIBLE);
        spinnerSecurityQuestion.setVisibility(View.INVISIBLE);
        inputSecurityAnswer.setVisibility(View.INVISIBLE);
        inputTeacherCode.setVisibility(View.INVISIBLE);
        btnRegister.setOnClickListener(new AdminRegistrationButtonListener());
    }
    private void register(Admin admin){
        Service service = new Service("Registering admin", RegisterActivity.this, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (response.getString("message").equals("Admin created.")){
                        Util.toast(context, "Admin created.");
                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Util.toast(context, response.getString("message"));
                    }
                } catch (Exception e){}
            }
        });
        AdminService.register(admin,service);
    }
    private class AdminRegistrationButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (!inputUserName.getText().toString().trim().matches("") &&
                    !inputPassword.getText().toString().trim().matches("") &&
                    !inputConfirmPassword.getText().toString().trim().matches("")){
                if (inputUserName.getText().length()>1){
                    if (inputPassword.getText().length()>5) {
                        if (inputPassword.getText().toString().trim().matches(inputConfirmPassword.getText().toString().trim())) {
                            Admin admin = new Admin();
                            admin.setUsername(String.valueOf(inputUserName.getText()));
                            admin.setPassword(String.valueOf(inputPassword.getText()));
                            register(admin);
                        } else {
                            showTxtError("Password and Confirm Password do not match.");
                        }
                    } else {
                        showTxtError("Passwords should be 6 or more characters.");
                    }
                } else {
                    showTxtError("Username should be 2 or more characters.");
                }
            } else {
                showTxtError("Some field/s are missing.");
            }
        }
    }
    private class TextViewTitleListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            easterEggClicks++;
            if (easterEggClicks==5){
                final AdminVerificationDialog adminVerificationDialog = new AdminVerificationDialog(context);
                adminVerificationDialog.show();
                adminVerificationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (adminVerificationDialog.isLegit()){
                            setAdminRegistration();
                        }
                    }
                });
            }
        }
    }
}

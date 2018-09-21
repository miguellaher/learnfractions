package com.example.laher.learnfractions;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laher.learnfractions.dialog_layout.ConfirmationDialog;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.service.StudentService;
import com.example.laher.learnfractions.service.TeacherService;
import com.example.laher.learnfractions.service.UserService;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.teacher.TeacherMainActivity;
import com.example.laher.learnfractions.user_activities.UserMainActivity;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import pl.droidsonroids.gif.GifImageView;

public class LoginActivity extends AppCompatActivity {
    Context mContext = this;
    public final String TAG = "LOGIN_ACTIVITY";

    TextView txtError;
    TextView txtRegister;
    Spinner spinnerUserType;
    EditText inputUsername;
    EditText inputPassword;
    Button btnLogin;
    ImageView imgLogo;
    GifImageView gifAvatar;

    String userType;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!isNetworkAvailable()){
            Storage.logout(mContext);
            Intent intent = new Intent(LoginActivity.this,
                    LessonsMenuActivity.class);
            startActivity(intent);
        }
        if (Storage.load(mContext,Storage.USER_ID)!=null){
            Intent intent = new Intent(LoginActivity.this,
                    UserMainActivity.class);
            startActivity(intent);
        }
        if (Storage.load(mContext,Storage.STUDENT_ID)!=null){
            Intent intent = new Intent(LoginActivity.this,
                    StudentMainActivity.class);
            startActivity(intent);
        }
        if (Storage.load(mContext,Storage.TEACHER_ID)!=null){
            Intent intent = new Intent(LoginActivity.this,
                    TeacherMainActivity.class);
            startActivity(intent);
        }
        txtError = findViewById(R.id.txtError);
        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new TxtRegisterListener());
        spinnerUserType = findViewById(R.id.spinnerUserType);
        inputUsername = findViewById(R.id.inputUserName);
        inputPassword = findViewById(R.id.inputPassword);
        inputPassword.setOnEditorActionListener(new InputPasswordOnEditorActionListener());
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new BtnLoginListener());
        btnLogin.setBackgroundResource(R.drawable.main_colors);
        txtError.setVisibility(TextView.INVISIBLE);
        inputUsername.requestFocus();

        imgLogo = findViewById(R.id.imgLogo);
        imgLogo.setImageResource(R.drawable.logo);

        gifAvatar = findViewById(R.id.gifAvatar);
        int gifID = R.drawable.snooping_frits;
        gifAvatar.setImageResource(gifID);

        adapter = ArrayAdapter.createFromResource(this,R.array.user_types,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);
        spinnerUserType.setOnItemSelectedListener(new UserTypeSpinnerListener());

        ActivityUtil.stopBgMusicMediaPlayer();
    }
    private String checkErrors(){
        if (inputUsername.getText().toString().matches("")){
            Styles.shakeAnimate(inputUsername);
            return "Input username.";
        }
        if (inputPassword.getText().toString().matches("")){
            Styles.shakeAnimate(inputPassword);
            return "Input password.";
        }
        return null;
    }
    private void login(String userType){
        if (userType.trim().matches(AppConstants.TEACHER)){
            Service service = new Service("Signing in...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try{
                        if (response.optString("message") != null && response.optString("message").equals("Incorrect username or password.")){
                            showTxtError(String.valueOf(response.optString("message")));
                        } else {
                            final Teacher teacher = new Teacher();
                            teacher.setId(response.optString("id"));
                            teacher.setUsername(response.optString("username"));
                            teacher.setTeacher_code(response.optString("teacher_code"));
                            Storage.save(mContext, teacher);
                            Intent intent = new Intent(LoginActivity.this,
                                    TeacherMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        Util.toast(mContext, AppConstants.SERVER_DOWN);
                    }
                }
            });
            Teacher teacher = new Teacher();
            teacher.setUsername(String.valueOf(inputUsername.getText()));
            teacher.setPassword(String.valueOf(inputPassword.getText()));
            TeacherService.login(teacher, service);
        }

        if (userType.trim().matches(AppConstants.STUDENT)){
            Service service = new Service("Signing in...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try{
                        if (response.optString("message") != null && response.optString("message").equals("Incorrect username or password.")){
                            showTxtError(String.valueOf(response.optString("message")));
                        } else {
                            final Student student = new Student();
                            student.setId(response.optString("id"));
                            student.setUsername(response.optString("username"));
                            student.setTeacher_code(response.optString("teacher_code"));
                            Storage.save(mContext, student);
                            Intent intent = new Intent(LoginActivity.this,
                                    StudentMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        Util.toast(mContext, AppConstants.SERVER_DOWN);
                    }
                }
            });
            Student student = new Student();
            student.setUsername(String.valueOf(inputUsername.getText()));
            student.setPassword(String.valueOf(inputPassword.getText()));
            StudentService.login(student, service);
        }

        if (userType.trim().matches(AppConstants.USER)){
            Service service = new Service("Signing in...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try{
                        if (response.optString("message") != null && response.optString("message").equals("Incorrect username or password.")){
                            showTxtError(String.valueOf(response.optString("message")));
                        } else {
                            User user = new User();

                            String id = response.optString("id");
                            String username = response.optString("username");
                            String password = response.optString("password");

                            user.setId(id);
                            user.setUsername(username);
                            user.setPassword(password);

                            Storage.save(mContext, user);

                            Intent intent = new Intent(LoginActivity.this,
                                    UserMainActivity.class);
                            startActivity(intent);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                        Util.toast(mContext, AppConstants.SERVER_DOWN);
                    }
                }
            });
            User user = new User();

            String username = inputUsername.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();

            user.setUsername(username);
            user.setPassword(password);

            UserService.login(user, service);
        }
    }
    private void showTxtError(String errorMsg){
        txtError.setText(errorMsg);
        Styles.paintRed(txtError);
        txtError.setVisibility(TextView.VISIBLE);
        Styles.shakeAnimate(txtError);
    }
    private class BtnLoginListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (checkErrors()==null){
                login(userType);
            } else {
                showTxtError(checkErrors());
            }
        }
    }
    private class TxtRegisterListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,
                    RegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
    private class UserTypeSpinnerListener implements Spinner.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            userType = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    private class InputPasswordOnEditorActionListener implements TextView.OnEditorActionListener{
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId== EditorInfo.IME_ACTION_DONE){
                btnLogin.performClick();
            }
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        final ConfirmationDialog confirmationDialog = new ConfirmationDialog(mContext, "Are you sure you want to exit app?");
        confirmationDialog.show();
        confirmationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (confirmationDialog.isConfirmed()){
                    finishAffinity();
                }
            }
        });
    }


}

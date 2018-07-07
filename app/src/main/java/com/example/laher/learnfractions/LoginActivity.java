package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laher.learnfractions.admin_activities.AdminMainActivity;
import com.example.laher.learnfractions.model.Admin;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.service.AdminService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.service.StudentService;
import com.example.laher.learnfractions.service.TeacherService;
import com.example.laher.learnfractions.student_activities.StudentMainActivity;
import com.example.laher.learnfractions.teacher_activities.TeacherMainActivity;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Storage;
import com.example.laher.learnfractions.util.Styles;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    Context context = this;
    public final String TAG = "LOGIN_ACTIVITY";

    TextView txtError, txtRegister;
    Spinner spinnerUserType;
    EditText inputUsername, inputPassword;
    Button btnLogin;

    String userType;
    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (!isNetworkAvailable()){
            Intent intent = new Intent(LoginActivity.this,
                    TopicsMenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        txtError = findViewById(R.id.txtError);
        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new TxtRegisterListener());
        spinnerUserType = findViewById(R.id.spinnerUserType);
        inputUsername = findViewById(R.id.inputUserName);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new BtnLoginListener());
        txtError.setVisibility(TextView.INVISIBLE);
        inputUsername.requestFocus();

        adapter = ArrayAdapter.createFromResource(this,R.array.user_types,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);
        spinnerUserType.setOnItemSelectedListener(new UserTypeSpinnerListener());

    }
    private String checkErrors(){
        if (inputUsername.getText().toString().matches("")){
            return "Input username.";
        }
        if (inputPassword.getText().toString().matches("")){
            return "Input password.";
        }
        return null;
    }
    private void login(String userType){
        if (userType.trim().matches(AppConstants.ADMIN)){
            Service service = new Service("Signing in...", context, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        if (response.optString("message") != null && response.optString("message").equals("Incorrect username or password.")) {
                            showTxtError(String.valueOf(response.optString("message")));
                        }else{
                            final Admin admin = new Admin();
                            admin.setId(response.optString("id"));
                            Intent intent = new Intent(LoginActivity.this,
                                    AdminMainActivity.class);
                            intent.putExtra("id", admin.getId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
            });
            Admin admin = new Admin();
            admin.setUsername(String.valueOf(inputUsername.getText()));
            admin.setPassword(String.valueOf(inputPassword.getText()));
            AdminService.login(admin, service);
        }

        if (userType.trim().matches(AppConstants.TEACHER)){
            Service service = new Service("Signing in...", context, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try{
                        if (response.optString("message") != null && response.optString("message").equals("Incorrect username or password.")){
                            showTxtError(String.valueOf(response.optString("message")));
                        } else {
                            final Teacher teacher = new Teacher();
                            teacher.setId(response.optString("id"));
                            teacher.setTeacher_code(response.optString("teacher_code"));
                            Storage.save(context, teacher);
                            Intent intent = new Intent(LoginActivity.this,
                                    TeacherMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            Teacher teacher = new Teacher();
            teacher.setUsername(String.valueOf(inputUsername.getText()));
            teacher.setPassword(String.valueOf(inputPassword.getText()));
            TeacherService.login(teacher, service);
        }

        if (userType.trim().matches(AppConstants.STUDENT)){
            Service service = new Service("Signing in...", context, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try{
                        if (response.optString("message") != null && response.optString("message").equals("Incorrect username or password.")){
                            showTxtError(String.valueOf(response.optString("message")));
                        } else {
                            final Student student = new Student();
                            student.setId(response.optString("id"));
                            Log.d(TAG, response.optString("id"));
                            student.setTeacher_code(response.optString("teacher_code"));
                            Log.d(TAG, response.optString("teacher_code"));
                            Storage.save(context, student);
                            Intent intent = new Intent(LoginActivity.this,
                                    StudentMainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            Student student = new Student();
            student.setUsername(String.valueOf(inputUsername.getText()));
            student.setPassword(String.valueOf(inputPassword.getText()));
            StudentService.login(student, service);
        }
    }
    private void showTxtError(String errorMsg){
        txtError.setText(errorMsg);
        Styles.paintRed(txtError);
        txtError.setVisibility(TextView.VISIBLE);
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
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}

package com.example.laher.learnfractions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.laher.learnfractions.classes.SecurityQuestionAdapter;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.service.StudentService;
import com.example.laher.learnfractions.service.TeacherService;
import com.example.laher.learnfractions.service.UserService;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    Context context = this;

    TextView txtTitle;
    TextView txtError;
    TextView txtMessage;
    Spinner spinnerUserType;
    Spinner spinnerSecurityQuestion;
    EditText inputUserName;
    EditText inputPassword;
    EditText inputConfirmPassword;
    EditText inputAge;
    EditText inputSecurityAnswer;
    EditText inputTeacherCode;
    RadioGroup radioGroupGender;
    RadioButton radioMale;
    RadioButton radioFemale;
    Button btnRegister;

    String userType;
    ArrayAdapter<CharSequence> adapter;

    ArrayList<String> security_questions_list;
    SecurityQuestionAdapter security_questions_adapter;
    String security_question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtTitle = findViewById(R.id.rTxtTitle);
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

        spinnerUserType = findViewById(R.id.rSpinnerUserType);
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

        adapter = ArrayAdapter.createFromResource(this,R.array.user_types_registration,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(adapter);
        spinnerUserType.setOnItemSelectedListener(new UserTypeSpinnerListener());

        security_questions_list = AppConstants.getSecurityQuestions();

        security_questions_adapter = new SecurityQuestionAdapter(this, R.id.security_question_dropdown_item_txtView1, security_questions_list);

        spinnerSecurityQuestion.setAdapter(security_questions_adapter);
        spinnerSecurityQuestion.setOnItemSelectedListener(new SecurityQuestionSpinnerListener());
    }
    private void showTxtError(String errorMessage){
        txtError.setText(errorMessage);
        Styles.paintRed(txtError);
        txtError.setVisibility(View.VISIBLE);
        Styles.shakeAnimate(txtError);
    }
    private void setUserRegistration(){
        String title = "User Registration";
        txtTitle.setText(title);
        inputAge.setVisibility(View.VISIBLE);
        radioGroupGender.setVisibility(View.VISIBLE);
        inputTeacherCode.setVisibility(View.INVISIBLE);
        btnRegister.setOnClickListener(new UserRegistrationButtonListener());
    }

    private boolean checkErrorsBase(){
        String strInputUserName = inputUserName.getText().toString().trim();
        String strInputPassword = inputPassword.getText().toString().trim();
        String strInputConfirmPassword = inputConfirmPassword.getText().toString().trim();
        String strInputSecurityAnswer = inputSecurityAnswer.getText().toString().trim();

        if (!strInputUserName.matches("") &&
                !strInputPassword.matches("") &&
                !strInputConfirmPassword.matches("") &&
                !strInputSecurityAnswer.matches("")){
            if (strInputUserName.length()>1){
                if (strInputPassword.length()>5) {
                    if (strInputPassword.matches(strInputConfirmPassword)) {
                        if (strInputSecurityAnswer.length()<4){
                            showTxtError("Security answers should be 4 or more characters.");
                            return false;
                        }
                        return true;
                    } else {
                        showTxtError("Password and Confirm Password do not match.");
                        return false;
                    }
                } else {
                    showTxtError("Passwords should be 6 or more characters.");
                    return false;
                }
            } else {
                showTxtError("Username should be 2 or more characters.");
                return false;
            }
        } else {
            showTxtError("There are missing field/s.");
            return false;
        }
    }

    private void register(User user){
        Service service = new Service("Registering user...", RegisterActivity.this, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (response.getString("message").equals("User registered.")){
                        Util.toast(context, response.getString("message"));
                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        showTxtError(response.getString("message"));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        UserService.register(user, service);
    }

    private class UserRegistrationButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (checkErrorsBase()){
                String strInputAge = inputAge.getText().toString().trim();

                if(!strInputAge.matches("")) {
                    if (radioGroupGender.getCheckedRadioButtonId() != -1) {
                        User user = new User();

                        String strInputUserName = inputUserName.getText().toString().trim();
                        String strInputPassword = inputPassword.getText().toString().trim();
                        String strAge = inputAge.getText().toString().trim();
                        String strInputSecuritAnswer = inputSecurityAnswer.getText().toString().trim();

                        if (Util.isNumeric(strAge)) {
                            int age = Integer.valueOf(strAge);

                            user.setUsername(strInputUserName);
                            user.setPassword(strInputPassword);
                            user.setAge(age);

                            if (radioMale.isChecked()) {
                                user.setGender(AppConstants.MALE);
                            } else if (radioFemale.isChecked()) {
                                user.setGender(AppConstants.FEMALE);
                            }

                            user.setSecurity_question(security_question);
                            user.setSecurity_answer(strInputSecuritAnswer);

                            register(user);
                        }
                    } else {
                        showTxtError("Input gender.");
                    }
                } else {
                    showTxtError("There are missing field/s.");
                }
            }
        }
    }
    private void setTeacherRegistration(){
        String title = "Teacher Registration";
        txtTitle.setText(title);
        inputAge.setVisibility(View.INVISIBLE);
        radioGroupGender.setVisibility(View.INVISIBLE);
        inputTeacherCode.setVisibility(View.VISIBLE);
        btnRegister.setOnClickListener(new TeacherRegistrationButtonListener());
    }
    private void register(Teacher teacher){
        Service service = new Service("Registering teacher...", RegisterActivity.this, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (response.getString("message").equals("Teacher created.")){
                        Util.toast(context, response.getString("message"));
                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        showTxtError(response.getString("message"));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        TeacherService.register(teacher, service);
    }
    private class TeacherRegistrationButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (checkErrorsBase()){
                if (!inputTeacherCode.getText().toString().trim().matches("")){
                    if (inputTeacherCode.getText().length()>3){
                        Teacher teacher = new Teacher();
                        teacher.setUsername(String.valueOf(inputUserName.getText()));
                        teacher.setPassword(String.valueOf(inputPassword.getText()));
                        teacher.setSecurity_question(String.valueOf(security_question));
                        teacher.setSecurity_answer(String.valueOf(inputSecurityAnswer.getText()));
                        teacher.setTeacher_code(String.valueOf(inputTeacherCode.getText()));
                        register(teacher);
                    } else {
                        showTxtError("Teacher codes should be 4 or more characters.");
                    }
                } else {
                    showTxtError("Input teacher code.");
                }
            }
        }
    }
    private void setStudentRegistration(){
        String title = "Student Registration";
        txtTitle.setText(title);
        inputAge.setVisibility(View.VISIBLE);
        radioGroupGender.setVisibility(View.VISIBLE);
        inputTeacherCode.setVisibility(View.VISIBLE);
        btnRegister.setOnClickListener(new StudentRegistrationButtonListener());
    }
    private void checkTeacherCode(final Student student){
        Service service = new Service("Checking teacher code...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (response.optString("message") != null && response.optString("message").equals("Incorrect teacher code.")) {
                        showTxtError(String.valueOf(response.optString("message")));
                    }else{
                        register(student);
                    }
                }catch (Exception e){e.printStackTrace();}
            }
        });
        StudentService.checkTeacherCode(student.getTeacher_code(), service);
    }
    private void register(Student student){
        Service service = new Service("Registering student...", RegisterActivity.this, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (response.getString("message").equals("Student created.")){
                        Util.toast(context, response.getString("message"));
                        Intent intent = new Intent(RegisterActivity.this,
                                LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        showTxtError(response.getString("message"));
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        StudentService.register(student, service);
    }
    private class StudentRegistrationButtonListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (checkErrorsBase()){
                if(!inputAge.getText().toString().trim().matches("")) {
                    if (radioGroupGender.getCheckedRadioButtonId() != -1) {
                        if (!inputTeacherCode.getText().toString().trim().matches("")){
                            if (inputTeacherCode.getText().length()>3){
                                Student student = new Student();
                                student.setUsername(String.valueOf(inputUserName.getText()));
                                student.setPassword(String.valueOf(inputPassword.getText()));
                                student.setAge(Integer.valueOf(String.valueOf(inputAge.getText())));
                                if (radioMale.isChecked()) {
                                    student.setGender(AppConstants.MALE);
                                } else if (radioFemale.isChecked()) {
                                    student.setGender(AppConstants.FEMALE);
                                }
                                student.setSecurity_question(security_question);
                                student.setSecurity_answer(String.valueOf(inputSecurityAnswer.getText()));
                                student.setTeacher_code(String.valueOf(inputTeacherCode.getText()));
                                checkTeacherCode(student);
                            } else {
                                showTxtError("Teacher codes should be 4 or more characters.");
                            }
                        } else {
                            showTxtError("Input teacher code.");
                        }
                    } else {
                        showTxtError("Input gender.");
                    }
                } else {
                    showTxtError("There are missing field/s.");
                }
            }
        }
    }
    private class UserTypeSpinnerListener implements Spinner.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            userType = parent.getItemAtPosition(position).toString();
            if (userType.trim().matches(AppConstants.USER)){
                setUserRegistration();
            } else if (userType.trim().matches(AppConstants.STUDENT)){
                setStudentRegistration();
            } else if (userType.trim().matches(AppConstants.TEACHER)){
                setTeacherRegistration();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
    private class SecurityQuestionSpinnerListener implements Spinner.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            security_question = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    @Override
    public void onBackPressed() {
        txtMessage.performClick();
    }
}

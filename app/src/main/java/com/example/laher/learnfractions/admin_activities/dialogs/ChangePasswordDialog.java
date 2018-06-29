package com.example.laher.learnfractions.admin_activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
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

public class ChangePasswordDialog extends Dialog {
    Context mContext;
    EditText inputNewPassword;
    Button btnChange;
    TextView txtUsername;

    private final String mUsername;
    private final String mUserType;

    public ChangePasswordDialog(@NonNull final Context context, final String username, final String userType) {
        super(context);
        mContext = context;
        mUsername = username;
        mUserType = userType;
        setGui();

        txtUsername.setText(username);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputNewPassword.getText().toString().trim().matches("")){
                    if (inputNewPassword.getText().toString().length()>5){
                        changePassword();
                    } else {
                        Util.toast(context, "New password should have 6 or more characters.");
                        Styles.shakeAnimate(inputNewPassword);
                    }
                } else {
                    Styles.shakeAnimate(inputNewPassword);
                }
            }
        });

    }

    public void changePassword(){
        if (mUserType == AppConstants.USER) {
            User user = new User();
            user.setUsername(mUsername);
            user.setPassword(String.valueOf(inputNewPassword.getText()));
            Service service = new Service("Changing password...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        Util.toast(mContext, response.optString("message"));
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            UserService.changeUserPassword(user, service);
        } else if (mUserType == AppConstants.STUDENT) {
            Student student = new Student();
            student.setUsername(mUsername);
            student.setPassword(String.valueOf(inputNewPassword.getText()));
            Service service = new Service("Changing password...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        Util.toast(mContext, response.optString("message"));
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            StudentService.changeUserPassword(student, service);
        } else if (mUserType == AppConstants.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setUsername(mUsername);
            teacher.setPassword(String.valueOf(inputNewPassword.getText()));
            Service service = new Service("Changing password...", mContext, new ServiceResponse() {
                @Override
                public void postExecute(JSONObject response) {
                    try {
                        Util.toast(mContext, response.optString("message"));
                        dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            TeacherService.changeUserPassword(teacher, service);
        }
    }
    public void setGui(){
        setContentView(R.layout.admin_dialog_change_password);
        inputNewPassword = findViewById(R.id.admin_dialog_change_password_inputNewPassword);
        btnChange = findViewById(R.id.admin_dialog_change_password_btnChange);
        txtUsername = findViewById(R.id.admin_dialog_change_password_txtUsername);

    }
}

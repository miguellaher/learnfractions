package com.example.laher.learnfractions.admin_activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.admin_activities.dialogs.ChangePasswordDialog;
import com.example.laher.learnfractions.admin_activities.list_adapters.StudentListAdapter;
import com.example.laher.learnfractions.admin_activities.list_adapters.TeacherListAdapter;
import com.example.laher.learnfractions.admin_activities.list_adapters.UserListAdapter;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.model.Teacher;
import com.example.laher.learnfractions.model.User;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.service.StudentService;
import com.example.laher.learnfractions.service.TeacherService;
import com.example.laher.learnfractions.service.UserService;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    Context mcontext = this;
    ListView userListView;
    ArrayList<User> users;
    ArrayList<Student> students;
    ArrayList<Teacher> teachers;

    Button btnBack;
    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        userListView = findViewById(R.id.user_list_userListView);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserListActivity.this,
                        ChangeUserPasswordActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            String user_type = extras.getString("user_type");
            Util.toast(mcontext, user_type);
            if (user_type.trim().matches(AppConstants.USER)) {
                userType = AppConstants.USER;
                getOnlineUsers();
            } else if (user_type.trim().matches(AppConstants.STUDENT)) {
                userType = AppConstants.STUDENT;
                getOnlineStudents();
            } else if (user_type.trim().matches(AppConstants.TEACHER)) {
                userType = AppConstants.TEACHER;
                getOnlineTeachers();
            }
        }
    }

    public void getOnlineUsers(){
        Service service = new Service("Loading Users...", mcontext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    users = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        User user = new User();
                        user.setUsername(String.valueOf(response.optString(i + "username")));
                        user.setAge(Integer.valueOf(response.getInt(i + "age")));
                        user.setGender(String.valueOf(response.optString(i + "gender")));
                        users.add(user);
                    }

                    UserListAdapter userListAdapter = new UserListAdapter(mcontext, R.layout.layout_user_item, users);
                    userListView.setAdapter(userListAdapter);
                    userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            User user = (User) userListView.getItemAtPosition(position);
                            Util.toast(mcontext,user.getUsername());
                            ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(mcontext, user.getUsername(), AppConstants.USER);
                            changePasswordDialog.show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        UserService.getAllUsers(service);
    }

    public void getOnlineStudents(){
        Service service = new Service("Loading Students...", mcontext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    students = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        Student student = new Student();
                        student.setUsername(String.valueOf(response.optString(i + "username")));
                        student.setAge(Integer.valueOf(response.optString(i + "age")));
                        student.setGender(String.valueOf(response.optString(i + "gender")));
                        students.add(student);
                    }
                    StudentListAdapter studentListAdapter = new StudentListAdapter(mcontext, R.layout.layout_user_item, students);
                    userListView.setAdapter(studentListAdapter);
                    userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Student student = (Student) userListView.getItemAtPosition(position);
                            Util.toast(mcontext,student.getUsername());
                            ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(mcontext, student.getUsername(), AppConstants.STUDENT);
                            changePasswordDialog.show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        StudentService.getAllUsers(service);
    }

    public void getOnlineTeachers(){
        Service service = new Service("Loading Teachers...", mcontext, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    teachers = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        Teacher teacher = new Teacher();
                        teacher.setUsername(String.valueOf(response.optString(i + "username")));
                        teachers.add(teacher);
                    }
                    TeacherListAdapter teacherListAdapter = new TeacherListAdapter(mcontext, R.layout.layout_user_item, teachers);
                    userListView.setAdapter(teacherListAdapter);
                    userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Teacher teacher = (Teacher) userListView.getItemAtPosition(position);
                            Util.toast(mcontext,teacher.getUsername());
                            ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog(mcontext, teacher.getUsername(), AppConstants.TEACHER);
                            changePasswordDialog.show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        TeacherService.getAllTeachers(service);
    }
}




















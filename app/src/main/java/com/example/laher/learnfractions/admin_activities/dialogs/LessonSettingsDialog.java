package com.example.laher.learnfractions.admin_activities.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.archive.LessonArchive;
import com.example.laher.learnfractions.model.Lesson;
import com.example.laher.learnfractions.util.AppConstants;
import com.example.laher.learnfractions.util.Util;

public class LessonSettingsDialog extends Dialog {
    String title;

    TextView txtLessonTitle;
    Button btnEnable, btnEdit;
    public LessonSettingsDialog(@NonNull Context context, String lessonTitle) {
        super(context);
        title = lessonTitle;

        setGui();

        txtLessonTitle.setText(lessonTitle);
        setBtnEnableTxt();
    }
    private void setBtnEnableTxt(){
        if (LessonArchive.getLesson(title).isEnabled()){
            btnEnable.setText(AppConstants.DISABLE);
        } else {
            btnEnable.setText(AppConstants.ENABLE);
        }
    }

    private void setGui(){
        setContentView(R.layout.layout_dialog_lesson_settings);

        txtLessonTitle = findViewById(R.id.dialog_lesson_settings_txtLessonTitle);
        btnEnable = findViewById(R.id.dialog_lesson_settings_btnEnable);
        btnEnable.setOnClickListener(new ButtonEnableListener());
        btnEdit = findViewById(R.id.dialog_lesson_settings_btnEdit);
    }
    private class ButtonEnableListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            if (LessonArchive.getLesson(title).isEnabled()){
                LessonArchive.getLesson(title).setEnabled(false);
                Util.toast(getContext(),"Lesson disabled");
            } else {
                LessonArchive.getLesson(title).setEnabled(true);
                Util.toast(getContext(),"Lesson enabled");
            }
            setBtnEnableTxt();
            dismiss();
        }
    }

    @Override
    public void setOnShowListener(@Nullable OnShowListener listener) {
        super.setOnShowListener(listener);
        setBtnEnableTxt();
    }
}

package com.example.laher.learnfractions.parent_activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.util.ActivityUtil;
import com.example.laher.learnfractions.util.Styles;

public class MainFrame extends AppCompatActivity {//TOOLBAR
    private Context mContext = this;

    protected Button buttonBack;
    protected Button buttonNext;
    protected TextView txtTitle;

    public Button getButtonNext() {
        return buttonNext;
    }

    public void setButtonBack(final Context context, final Class classBack){
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, classBack);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void setButtonNext(final Context context, final Class classNext){
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, classNext);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    public void setTitle(String title){
        txtTitle.setText(title);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonBack = findViewById(R.id.btnBack);
        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setEnabled(false);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(buttonBack);

        txtTitle = findViewById(R.id.txtTitle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityUtil.muteBgMusicMediaPlayer();
        ActivityUtil.playMediaPlayer(mContext, R.raw.clicked);
    }

    @Override
    protected void onResume() {
        ActivityUtil.unmuteBgMusicMediaPlayer();
        super.onResume();
    }

    @Override
    protected void onStart() {
        ActivityUtil.playBgMusicMediaPlayer(mContext);
        super.onStart();
    }
}

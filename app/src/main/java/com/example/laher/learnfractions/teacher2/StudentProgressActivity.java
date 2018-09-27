package com.example.laher.learnfractions.teacher2;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.laher.learnfractions.ChapterExamListActivity;
import com.example.laher.learnfractions.LessonsMenuActivity;
import com.example.laher.learnfractions.R;
import com.example.laher.learnfractions.SeatWorkListActivity;
import com.example.laher.learnfractions.classes.CustomViewPager;
import com.example.laher.learnfractions.classes.Range;
import com.example.laher.learnfractions.dialog_layout.MessageDialog;
import com.example.laher.learnfractions.model.ChapterExam;
import com.example.laher.learnfractions.model.Student;
import com.example.laher.learnfractions.parent_activities.LessonExercise;
import com.example.laher.learnfractions.parent_activities.MainFrame;
import com.example.laher.learnfractions.parent_activities.SeatWork;
import com.example.laher.learnfractions.service.ExamService;
import com.example.laher.learnfractions.service.ExamStatService;
import com.example.laher.learnfractions.service.ExerciseService;
import com.example.laher.learnfractions.service.ExerciseStatService;
import com.example.laher.learnfractions.service.SeatWorkService;
import com.example.laher.learnfractions.service.SeatWorkStatService;
import com.example.laher.learnfractions.service.Service;
import com.example.laher.learnfractions.service.ServiceResponse;
import com.example.laher.learnfractions.service.TeacherService;
import com.example.laher.learnfractions.teacher.TeacherMainActivity;
import com.example.laher.learnfractions.teacher.fragments.StatListFragment;
import com.example.laher.learnfractions.teacher.fragments.StatOptionsFragment;
import com.example.laher.learnfractions.teacher.fragments.StudentListFragment;
import com.example.laher.learnfractions.teacher.list_adapters.ExamStatAdapter;
import com.example.laher.learnfractions.teacher.list_adapters.ExerciseStatAdapter;
import com.example.laher.learnfractions.teacher.list_adapters.SeatworkStatAdapter;
import com.example.laher.learnfractions.teacher.list_adapters.ViewPagerAdapter;
import com.example.laher.learnfractions.util.Styles;
import com.example.laher.learnfractions.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;

public class StudentProgressActivity extends MainFrame {
    private Context context = this;

    private TextView txtHeader;
    private TextView txtBack;
    private CustomViewPager viewPager;
    ViewPagerAdapter mAdapter;

    ArrayList<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_progress);

        //TOOLBAR
        buttonBack = findViewById(R.id.btnBack);
        buttonNext = findViewById(R.id.btnNext);
        buttonNext.setEnabled(false);

        ConstraintLayout toolbar = findViewById(R.id.constraintLayoutToolbar);
        Styles.bgPaintMainYellow(toolbar);

        Styles.bgPaintMainBlue(buttonBack);

        txtTitle = findViewById(R.id.txtTitle);

        setButtonBack(context,TeacherMainActivity.class);
        Button buttonNext = getButtonNext();
        buttonNext.setVisibility(View.INVISIBLE);
        final String TITLE = "Student Progress";
        setTitle(TITLE);

        //ACTIVITY GUI
        ConstraintLayout constraintLayout = findViewById(R.id.constraintLayout);
        Styles.bgPaintMainOrange(constraintLayout);

        txtHeader = findViewById(R.id.student_progress_txtHeader);
        Styles.paintBlack(txtHeader);
        String header = "Students";
        txtHeader.setText(header);
        txtBack = findViewById(R.id.student_progress_txtBack);
        Styles.paintBlack(txtBack);
        txtBack.setVisibility(View.INVISIBLE);
        viewPager = findViewById(R.id.student_progress_viewPager);
        getStudents();
    }

    private void getStudents(){
        Service service = new Service("Getting students' information...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                if (!response.optString("item_count").matches("")) {
                    try {
                        int item_count = Integer.valueOf(response.optString("item_count"));
                        students = new ArrayList<>();
                        for (int i = 1; i <= item_count; i++) {
                            String id = response.optString(i + "student_id");
                            String username = response.optString(i + "username");
                            String strAge = response.optString(i + "age");
                            String gender = response.optString(i + "gender");

                            Student student = new Student();
                            student.setId(id);
                            student.setUsername(username);
                            if (Util.isNumeric(strAge)) {
                                int age = Integer.valueOf(strAge);
                                student.setAge(age);
                            }
                            student.setGender(gender);

                            students.add(student);
                        }
                        mAdapter = new ViewPagerAdapter(getSupportFragmentManager());

                        StudentListFragment studentListFragment = new StudentListFragment();
                        studentListFragment.setStudents(students);

                        mAdapter.addFragment(studentListFragment);

                        viewPager.setAdapter(mAdapter);

                        listViewStudents = findViewById(R.id.list_students);
                        listViewStudents.setOnItemClickListener(new StudentListListener());
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                } else {
                    String message = response.optString("message");
                    MessageDialog messageDialog = new MessageDialog(context, message);
                    messageDialog.show();
                }
            }
        });
        TeacherService.getAllStudents(context, service);
    }

    private ListView listViewStudents;

    private void showOptions(Student student){
        StatOptionsFragment statOptionsFragment = new StatOptionsFragment();
        mAdapter.addFragment(statOptionsFragment);
        viewPager.setAdapter(mAdapter);

        viewPager.setCurrentItem(1,true);

        String username = student.getUsername();
        txtHeader.setText(username);

        currentStudent = student;

        txtBack.setOnClickListener(new BtnBackOnOptionsListener());
        txtBack.setVisibility(View.VISIBLE);

        Button btnExercise = findViewById(R.id.list_options_btnExercises);
        Styles.bgPaintMainBlueGreen(btnExercise);
        btnExercise.setOnClickListener(new BtnViewExercisesStatListener());

        Button btnSeatwork = findViewById(R.id.list_options_btnSeatworks);
        Styles.bgPaintMainBlue(btnSeatwork);
        btnSeatwork.setOnClickListener(new BtnViewSeatworksStatListener());

        Button btnExam = findViewById(R.id.list_options_btnExams);
        Styles.bgPaintMainYellow(btnExam);
        btnExam.setOnClickListener(new BtnViewExamsStatListener());
    }

    private Student currentStudent;

    private class StudentListListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Student student = students.get(position);
            showOptions(student);
        }
    }

    private class BtnBackOnOptionsListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            mAdapter.removeLastFragment();
            mAdapter.notifyDataSetChanged();

            viewPager.setCurrentItem(0,true);
            String header = "Students";
            txtHeader.setText(header);
            txtBack.setVisibility(View.INVISIBLE);

            viewPager.setAdapter(mAdapter);

            listViewStudents = findViewById(R.id.list_students);
            listViewStudents.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            listViewStudents.setOnItemClickListener(new StudentListListener());
        }
    }

    ArrayList<LessonExercise> lessonExercises;

    private class BtnViewExercisesStatListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            lessonExercises = LessonsMenuActivity.getLessonExercises();
            updateExercises();
        }
    }

    ArrayList<SeatWork> seatWorks;

    private class BtnViewSeatworksStatListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            seatWorks = SeatWorkListActivity.getSeatWorks();
            updateSeatworks();
        }
    }

    ArrayList<ChapterExam> chapterExams;

    private class BtnViewExamsStatListener implements Button.OnClickListener{
        @Override
        public void onClick(View v) {
            chapterExams = ChapterExamListActivity.getChapterExams();
            updateExams();
        }
    }











    private void updateExams(){
        Service service = new Service("Getting exams...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));

                    ArrayList<SeatWork> builtInSeatWorks = SeatWorkListActivity.getSeatWorks(); // "ARCHIVE OF SEATWORKS"

                    for (int i = 1; i <= item_count; i++) {
                        ChapterExam onlineChapterExam = new ChapterExam();
                        String strExamNumber = response.optString(i + "exam_number");
                        if (Util.isNumeric(strExamNumber)) {
                            int examNumber = Integer.valueOf(strExamNumber);
                            String examTitle = response.optString(i + "exam_title");
                            String compiledSeatWorks = response.optString(i + "seat_works");

                            ArrayList<SeatWork> downloadedSeatWorks = ChapterExam.decompileSeatWorks(compiledSeatWorks);
                            ArrayList<SeatWork> examSeatWorks = new ArrayList<>();
                            for (SeatWork downloadedSeatWork : downloadedSeatWorks) {
                                for (SeatWork builtInSeatWork : builtInSeatWorks) {
                                    if (builtInSeatWork.equals(downloadedSeatWork)) {
                                        builtInSeatWork.getValuesFrom(downloadedSeatWork);
                                        examSeatWorks.add(builtInSeatWork);
                                    }
                                }
                            }

                            onlineChapterExam.setExamNumber(examNumber);
                            onlineChapterExam.setExamTitle(examTitle);
                            onlineChapterExam.setSeatWorks(examSeatWorks);
                            int i1 = 0;
                            for (ChapterExam mChapterExam : chapterExams) {
                                if (mChapterExam.equals(onlineChapterExam)) {
                                    mChapterExam = onlineChapterExam;

                                    chapterExams.set(i1, mChapterExam);
                                }
                                i1++;
                            }
                        }
                    }
                    getStudentExamStats();
                } catch (Exception e){
                    e.printStackTrace();
                    getStudentExamStats();
                }
            }
        });
        ExamService.getExams(context, service);
    }

    private void getStudentExamStats(){
        Service service = new Service("Loading stats...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (!response.optString("item_count").matches("")) {
                        int item_count = Integer.valueOf(response.optString("item_count"));
                        ArrayList<ChapterExam> onlineChapterExamStats = new ArrayList<>();
                        for (int i = 1; i <= item_count; i++) {
                            ChapterExam chapterExam = new ChapterExam();

                            String strExamNumber = response.optString(i + "exam_number");
                            if (Util.isNumeric(strExamNumber)) {
                                int examNumber = Integer.valueOf(strExamNumber);
                                String compiledSeatWorksStats = response.optString(i + "seat_works_stats");
                                String compiledSeatWorks = response.optString(i + "seatworks");

                                chapterExam.setExamNumber(examNumber);
                                chapterExam.setCompiledSeatWorksStats(compiledSeatWorksStats);
                                chapterExam.setCompiledSeatWorks(compiledSeatWorks);
                            }
                            onlineChapterExamStats.add(chapterExam);
                        }

                        ArrayList<ChapterExam> updatedChapterExamsStats = new ArrayList<>();
                        for (ChapterExam onlineChapterExamStat : onlineChapterExamStats) {
                            for (ChapterExam mChapterExam : chapterExams) {
                                if (mChapterExam.equals(onlineChapterExamStat)) {
                                    String mCompiledSeatWorks = mChapterExam.getCompiledSeatWorks();
                                    String onlineCompiledSeatWorks = onlineChapterExamStat.getCompiledSeatWorks();
                                    if (mCompiledSeatWorks.equals(onlineCompiledSeatWorks)) {
                                        String onlineCompiledSeatWorksStats = onlineChapterExamStat.getCompiledSeatWorksStats();
                                        ArrayList<SeatWork> onlineSeatWorksStats = ChapterExam.decompileSeatWorksStats(onlineCompiledSeatWorksStats);

                                        mChapterExam.setSeatWorksStats(onlineSeatWorksStats);
                                        mChapterExam.setAnswered(true);
                                        updatedChapterExamsStats.add(mChapterExam);
                                    }
                                }
                            }
                        }
                        chapterExams = updatedChapterExamsStats;

                        ExamStatAdapter exerciseStatAdapter = new ExamStatAdapter(context, R.layout.layout_user_item2, chapterExams);

                        setStatFragment(exerciseStatAdapter);
                    } else {
                        MessageDialog messageDialog = new MessageDialog(context, "No exams' stats found.");
                        messageDialog.show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        String studentID = currentStudent.getId();
        ExamStatService.getStudentStats(context, studentID, service);
    }

    private void updateSeatworks(){
        Service service = new Service("Updating seat works...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<SeatWork> downloadedSeatWorks = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        SeatWork seatWork = new SeatWork();

                        String id = response.optString(i + "sw_id");
                        String topic_name = response.optString(i + "topic_name");
                        String strItemSize = response.optString(i + "item_size");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");

                        int itemSize = Integer.valueOf(strItemSize);

                        int minimum = Integer.valueOf(strMinimum);
                        int maximum = Integer.valueOf(strMaximum);
                        Range range = new Range(minimum,maximum);

                        seatWork.setId(id);
                        seatWork.setTopicName(topic_name);
                        seatWork.setItems_size(itemSize);
                        seatWork.setRange(range);

                        downloadedSeatWorks.add(seatWork);
                    }
                    for (SeatWork downloadedSeatWork : downloadedSeatWorks){
                        int i = 0;
                        for (SeatWork seatWork : seatWorks){
                            if (downloadedSeatWork.equals(seatWork)){
                                seatWork.getValuesFrom(downloadedSeatWork);
                                seatWorks.set(i, seatWork);
                            }
                            i++;
                        }
                    }
                    getStudentSeatworkStats();
                } catch (Exception e) {
                    e.printStackTrace();
                    getStudentSeatworkStats();
                }
            }
        });
        SeatWorkService.getUpdates(context, service);
    }

    private void getStudentSeatworkStats(){
        Service getStatsService = new Service("Getting stats...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try {
                    if (!response.optString("item_count").matches("")) {
                        int item_count = Integer.valueOf(response.optString("item_count"));
                        ArrayList<SeatWork> downloadedSeatworkStats = new ArrayList<>();
                        for (int i = 1; i <= item_count; i++) {
                            String seatworkID = response.optString(i + "seatwork_id");
                            String strItemsSize = response.optString(i + "items_size");
                            String strMinimum = response.optString(i + "r_minimum");
                            String strMaximum = response.optString(i + "r_maximum");
                            String topic_name = response.optString(i + "topic_name");
                            String strScore = response.optString(i + "score");
                            String strTimeSpent = response.optString(i + "time_spent");

                            int itemsSize = Integer.valueOf(strItemsSize);

                            int minimum = Integer.valueOf(strMinimum);
                            int maximum = Integer.valueOf(strMaximum);
                            Range range = new Range(minimum, maximum);

                            int score = Integer.valueOf(strScore);
                            long timeSpent = Long.valueOf(strTimeSpent);

                            SeatWork downloadedSeatworkStat = new SeatWork();
                            downloadedSeatworkStat.setId(seatworkID);
                            downloadedSeatworkStat.setItems_size(itemsSize);
                            downloadedSeatworkStat.setRange(range);
                            downloadedSeatworkStat.setTopicName(topic_name);
                            downloadedSeatworkStat.setCorrect(score);
                            downloadedSeatworkStat.setTimeSpent(timeSpent);

                            downloadedSeatworkStats.add(downloadedSeatworkStat);
                        }
                        ArrayList<SeatWork> updatedSeatworkStats = new ArrayList<>();
                        for (SeatWork downloadedSeatworkStat : downloadedSeatworkStats) {
                            for (SeatWork seatWork : seatWorks) {
                                if (seatWork.isUpdatedWith(downloadedSeatworkStat)) {
                                    seatWork.getStatsFrom(downloadedSeatworkStat);
                                    seatWork.setAnswered(true);
                                    updatedSeatworkStats.add(seatWork);
                                }
                            }
                        }
                        seatWorks = updatedSeatworkStats;

                        SeatworkStatAdapter adapter = new SeatworkStatAdapter(context, R.layout.layout_user_item2, seatWorks);

                        setStatFragment(adapter);
                    } else {
                        MessageDialog messageDialog = new MessageDialog(context, "No seatworks' stats found.");
                        messageDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String studentID = currentStudent.getId();
        SeatWorkStatService.getStats(context, studentID, getStatsService);
    }



    private void updateExercises(){
        Service service = new Service("Updating exercise list...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    int item_count = Integer.valueOf(response.optString("item_count"));
                    ArrayList<LessonExercise> exercises = new ArrayList<>();
                    for (int i = 1; i <= item_count; i++) {
                        String exercise_id = response.optString(i + "exercise_id");
                        String title = response.optString(i + "title");
                        String strItemsSize = response.optString(i + "items_size");
                        String strMaxItemsSize = response.optString(i + "max_items");
                        String strRCC = response.optString(i + "rc_consecutive");
                        String strMaxWrong = response.optString(i + "max_wrong");
                        String strMEC = response.optString(i + "me_consecutive");
                        String strRangeEditable = response.optString(i + "r_editable");
                        String strMinimum = response.optString(i + "r_minimum");
                        String strMaximum = response.optString(i + "r_maximum");

                        if (Util.isNumeric(strItemsSize) && Util.isNumeric(strMaxWrong) && Util.isNumeric(strMaxItemsSize)
                                && Util.isNumeric(strMinimum) && Util.isNumeric(strMaximum)) {
                            int itemsSize = Integer.valueOf(strItemsSize);
                            int maxItemsSize = Integer.valueOf(strMaxItemsSize);
                            boolean isCorrectsShouldBeConsecutive = false;
                            if (strRCC.equals("1")) {
                                isCorrectsShouldBeConsecutive = true;
                            }
                            int maxWrong = Integer.valueOf(strMaxWrong);
                            boolean isWrongsShouldBeConsecutive = true;
                            if (strMEC.equals("0")) {
                                isWrongsShouldBeConsecutive = false;
                            }
                            boolean isRangeEditable = false;
                            if (strRangeEditable.equals("1")){
                                isRangeEditable = true;
                            }
                            int minimum = Integer.valueOf(strMinimum);
                            int maximum = Integer.valueOf(strMaximum);
                            Range range = new Range(minimum,maximum);

                            LessonExercise exercise = new LessonExercise();
                            exercise.setId(exercise_id);
                            exercise.setExerciseTitle(title);
                            exercise.setItemsSize(itemsSize);
                            exercise.setMaxItemSize(maxItemsSize);
                            exercise.setCorrectsShouldBeConsecutive(isCorrectsShouldBeConsecutive);
                            exercise.setMaxWrong(maxWrong);
                            exercise.setWrongsShouldBeConsecutive(isWrongsShouldBeConsecutive);
                            exercise.setRangeEditable(isRangeEditable);
                            exercise.setRange(range);
                            exercises.add(exercise);
                        }
                    }
                    updateList(exercises);
                    getStudentsExerciseStats(currentStudent);
                } catch (Exception e) {
                    e.printStackTrace();
                    getStudentsExerciseStats(currentStudent);
                }
            }
        });
        ExerciseService.getUpdates(context,service);
    }

    private void updateList(ArrayList<LessonExercise> downloadedExercises){
        int i = 0;
        for (LessonExercise mExercise : lessonExercises){
            for (LessonExercise downloadedExercise : downloadedExercises){
                if (mExercise.equals(downloadedExercise)){
                    lessonExercises.set(i, downloadedExercise);
                }
            }
            i++;
        }
    }

    private void retainExercisesWithStats(ArrayList<LessonExercise> downloadedExercisesStats){
        ArrayList<LessonExercise> retainedLessonExercises = new ArrayList<>();
        for (LessonExercise mExercise : lessonExercises){
            for (LessonExercise downloadedExercise : downloadedExercisesStats){
                if (mExercise.equals(downloadedExercise)){
                    if (mExercise.hasTheSameAttributesWith(downloadedExercise)) {
                        String mExerciseTitle = mExercise.getExerciseTitle();
                        downloadedExercise.setExerciseTitle(mExerciseTitle);
                        retainedLessonExercises.add(downloadedExercise);
                    }
                }
            }
        }
        lessonExercises = retainedLessonExercises;
    }

    private void getStudentsExerciseStats(Student student){
        Service service = new Service("Getting student's exercise stats...", context, new ServiceResponse() {
            @Override
            public void postExecute(JSONObject response) {
                try{
                    if (!response.optString("item_count").matches("")) {
                        int item_count = Integer.valueOf(response.optString("item_count"));
                        ArrayList<LessonExercise> downloadedLessonExercisesStats = new ArrayList<>();
                        for (int i = 1; i <= item_count; i++) {
                            String exerciseID = response.optString(i + "exercise_id");
                            String strTimeSpent = response.optString(i + "time_spent");
                            String strErrors = response.optString(i + "errors");
                            String strRequiredCorrects = response.optString(i + "required_corrects");
                            String strRCConsecutive = response.optString(i + "rc_consecutive");
                            String strMaxErrors = response.optString(i + "max_errors");
                            String strMEConsecutive = response.optString(i + "me_consecutive");
                            String strMinimum = response.optString(i + "r_minimum");
                            String strMaximum = response.optString(i + "r_maximum");


                            if (Util.isNumeric(strTimeSpent) && Util.isNumeric(strErrors) && Util.isNumeric(strRequiredCorrects) &&
                                    Util.isNumeric(strRCConsecutive) && Util.isNumeric(strMaxErrors) &&
                                    Util.isNumeric(strMEConsecutive) && Util.isNumeric(strMinimum) &&
                                    Util.isNumeric(strMaximum)) {
                                long timeSpent = Long.valueOf(strTimeSpent);
                                int errors = Integer.valueOf(strErrors);

                                int requiredCorrects = Integer.valueOf(strRequiredCorrects);
                                int intRCConsecutive = Integer.valueOf(strRCConsecutive);
                                boolean rcShouldBeConsecutive = false;
                                if (intRCConsecutive == 1) {
                                    rcShouldBeConsecutive = true;
                                }

                                int maxErrors = Integer.valueOf(strMaxErrors);

                                int intMEConsecutive = Integer.valueOf(strMEConsecutive);
                                boolean eShouldBeConsecutive = true;
                                if (intMEConsecutive == 0) {
                                    eShouldBeConsecutive = false;
                                }

                                int minimum = Integer.valueOf(strMinimum);
                                int maximum = Integer.valueOf(strMaximum);
                                Range range = new Range(minimum, maximum);

                                LessonExercise lessonExercise = new LessonExercise();

                                lessonExercise.setId(exerciseID);
                                lessonExercise.setTimeSpent(timeSpent);
                                lessonExercise.setTotalWrongs(errors);
                                lessonExercise.setItemsSize(requiredCorrects);
                                lessonExercise.setCorrectsShouldBeConsecutive(rcShouldBeConsecutive);
                                lessonExercise.setMaxWrong(maxErrors);
                                lessonExercise.setWrongsShouldBeConsecutive(eShouldBeConsecutive);
                                lessonExercise.setRange(range);

                                downloadedLessonExercisesStats.add(lessonExercise);
                            }
                        }

                        retainExercisesWithStats(downloadedLessonExercisesStats);
                        ExerciseStatAdapter adapter = new ExerciseStatAdapter(context, R.layout.layout_user_item2, lessonExercises);

                        setStatFragment(adapter);
                    } else {
                        MessageDialog messageDialog = new MessageDialog(context, "No exercises' stats found.");
                        messageDialog.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        String studentId = student.getId();
        ExerciseStatService.getStudentStats(context,studentId,service);
    }

    private void setStatFragment(ArrayAdapter adapter){
        StatListFragment statListFragment = new StatListFragment();
        statListFragment.setAdapter(adapter);

        mAdapter.addFragment(statListFragment);
        mAdapter.notifyDataSetChanged();
        viewPager.setAdapter(mAdapter);

        viewPager.setCurrentItem(2, true);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeLastFragment();
                mAdapter.notifyDataSetChanged();
                viewPager.setCurrentItem(1, true);
                String username = currentStudent.getUsername();
                txtHeader.setText(username);

                Button btnExercise = findViewById(R.id.list_options_btnExercises);
                Styles.bgPaintMainBlueGreen(btnExercise);
                btnExercise.setOnClickListener(new BtnViewExercisesStatListener());

                Button btnSeatwork = findViewById(R.id.list_options_btnSeatworks);
                Styles.bgPaintMainBlue(btnSeatwork);
                btnSeatwork.setOnClickListener(new BtnViewSeatworksStatListener());

                Button btnExam = findViewById(R.id.list_options_btnExams);
                Styles.bgPaintMainYellow(btnExam);
                btnExam.setOnClickListener(new BtnViewExamsStatListener());
                txtBack.setOnClickListener(new BtnBackOnOptionsListener());
            }
        });
    }
}

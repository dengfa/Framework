package com.hyena.framework;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

public class QuestionTextViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_text_view);
        mQtv = (QuestionTextView) findViewById(R.id.qtv);
        mSelectsLayout = (LinearLayout) findViewById(R.id.ll_question_select);
    }
}

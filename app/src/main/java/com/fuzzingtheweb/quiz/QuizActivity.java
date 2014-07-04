package com.fuzzingtheweb.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class QuizActivity extends Activity {

    private Button mFirstButton;
    private Button mSecondButton;
    private Button mThirdButton;
    private Button mFourthButton;

    private TextView mQuestionTextView;
    private Question[] mQuestionList;
    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        Question q_0 = new Question(getResources().getStringArray(R.array.q_0));
        Question q_1 = new Question(getResources().getStringArray(R.array.q_1));
        Question q_2 = new Question(getResources().getStringArray(R.array.q_2));
        mQuestionList = new Question[]{ q_0, q_1, q_2 };

        renderQuestion();

        // Make this nicer!
        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList[mCurrentIndex].checkQuestion(1));
                renderNextQuestion();
            }
        });

        mSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList[mCurrentIndex].checkQuestion(2));
                renderNextQuestion();
            }
        });

        mThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList[mCurrentIndex].checkQuestion(3));
                renderNextQuestion();
            }
        });

        mFourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList[mCurrentIndex].checkQuestion(4));
                renderNextQuestion();
            }
        });
    }

    private void renderQuestion() {
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        Question question = mQuestionList[mCurrentIndex];
        mQuestionTextView.setText(question.getQuestion());

        List<String> options = question.getOptions();
        mFirstButton = (Button) findViewById(R.id.first_button);
        mFirstButton.setText(options.get(0));
        mSecondButton = (Button) findViewById(R.id.second_button);
        mSecondButton.setText(options.get(1));
        mThirdButton = (Button) findViewById(R.id.third_button);
        mThirdButton.setText(options.get(2));
        mFourthButton = (Button) findViewById(R.id.fourth_button);
        mFourthButton.setText(options.get(3));
    }

    private void renderNextQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionList.length;
        renderQuestion();
    }

    public void makeToast(boolean correct) {
        int toastResult = (correct == true) ? R.string.correct_toast : R.string.incorrect_toast;
        Toast.makeText(QuizActivity.this,
                toastResult,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quizz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

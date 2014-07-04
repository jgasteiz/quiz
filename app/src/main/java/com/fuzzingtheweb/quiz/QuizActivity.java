package com.fuzzingtheweb.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class QuizActivity extends Activity {

    private final static int mNumQuestions = 5;

    private Button mFirstButton;
    private Button mSecondButton;
    private Button mThirdButton;
    private Button mFourthButton;

    private List<Question> mQuestionList;
    private int mCurrentIndex = 0;

    private int mCorrectAnswers = 0;
    private int mIncorrectAnswers = 0;

    private TextView mFeedbackMessage;
    private LinearLayout mUserFeedback;

    private LinearLayout mGameOverLayout;
    private TextView mStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        // Initialize Layout objects
        mUserFeedback = (LinearLayout) findViewById(R.id.user_feedback);
        mFeedbackMessage = (TextView) findViewById(R.id.feedback_message);
        mGameOverLayout = (LinearLayout) findViewById(R.id.game_over_layout);
        mStats = (TextView) findViewById(R.id.stats);

        // Initialize buttons
        initializeButtons();

        // Initialize question list
        mQuestionList = getQuestionList(mNumQuestions);

        // Render the first question
        renderQuestion();
    }

    /**
     * Initialize all buttons and their listeners
     */
    private void initializeButtons() {
        mFirstButton = (Button) findViewById(R.id.first_button);
        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList.get(mCurrentIndex).checkQuestion(1));
            }
        });

        mSecondButton = (Button) findViewById(R.id.second_button);
        mSecondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList.get(mCurrentIndex).checkQuestion(2));
            }
        });

        mThirdButton = (Button) findViewById(R.id.third_button);
        mThirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList.get(mCurrentIndex).checkQuestion(3));
            }
        });

        mFourthButton = (Button) findViewById(R.id.fourth_button);
        mFourthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast(mQuestionList.get(mCurrentIndex).checkQuestion(4));
            }
        });

        Button mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFeedbackMessage.setText("");
                mUserFeedback.setVisibility(View.INVISIBLE);
                mCurrentIndex++;
                if (mCurrentIndex < mQuestionList.size()) {
                    renderQuestion();
                } else {
                    String correct = Integer.toString(mCorrectAnswers) + " correct, ";
                    String incorrect = Integer.toString(mIncorrectAnswers) + " incorrect.";
                    String statsMessage = mStats.getText() + " " + correct + incorrect;
                    mGameOverLayout.setVisibility(View.VISIBLE);
                    mStats.setText(statsMessage);
                }
            }
        });

        Button mRestart = (Button) findViewById(R.id.restart_button);
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    /**
     * Creates a list of random questions based on all existing ones.
     *
     * @param numQuestions - number of questions to return
     * @return - list of questions
     */
    private ArrayList<Question> getQuestionList(int numQuestions) {

        String resourceName;
        int resourceId;
        Question q;
        ArrayList<Question> questionsList = new ArrayList<Question>();

        // Get all the questions in questionsList
        int counter = 0;
        while(true) {
            resourceName = "q_" + Integer.toString(counter);
            resourceId = getResources().getIdentifier(resourceName, "array", getPackageName());
            if (resourceId == 0) {
                break;
            }
            q = new Question(getResources().getStringArray(resourceId));
            questionsList.add(q);
            counter++;
        }

        // Shuffle the questions
        Collections.shuffle(questionsList, new Random(System.nanoTime()));
        return new ArrayList<Question>(questionsList.subList(0, numQuestions));
    }

    /**
     * Renders the question for the current index and it's possible answers.
     */
    private void renderQuestion() {
        TextView mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        Question question = mQuestionList.get(mCurrentIndex);
        mQuestionTextView.setText(question.getQuestion());

        List<String> options = question.getOptions();
        mFirstButton.setText(options.get(0));
        mSecondButton.setText(options.get(1));
        mThirdButton.setText(options.get(2));
        mFourthButton.setText(options.get(3));
    }

    /**
     * Shows if the answer is correct or not and displays the `next` button.
     *
     * @param correct - boolean which determines if an answer has been correct or not
     */
    public void makeToast(boolean correct) {
        int toastResult;
        if (correct) {
            mCorrectAnswers++;
            toastResult = R.string.correct_toast;
        } else {
            mIncorrectAnswers++;
            toastResult = R.string.incorrect_toast;
        }
        mFeedbackMessage.setText(toastResult);
        mUserFeedback.setVisibility(View.VISIBLE);
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

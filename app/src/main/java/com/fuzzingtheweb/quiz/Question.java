package com.fuzzingtheweb.quiz;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javiman on 04/07/2014.
 */
public class Question {

    private String mQuestion;
    private List<String> mOptions;
    private int mAnswerIndex;

    public Question(String[] resourceArray) {
        this.mQuestion = resourceArray[0];
        this.mOptions = new ArrayList<String>();
        mOptions.add(resourceArray[1]);
        mOptions.add(resourceArray[2]);
        mOptions.add(resourceArray[3]);
        mOptions.add(resourceArray[4]);
        this.mAnswerIndex = Integer.parseInt(resourceArray[5]);
    }

    public boolean checkQuestion(int answerIndex) {
        return (answerIndex == mAnswerIndex) ? true : false;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        this.mQuestion = question;
    }

    public List<String> getOptions() {
        return mOptions;
    }

    public void setOptions(List<String> options) {
        this.mOptions = options;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setmAnswerIndex(int answerIndex) {
        this.mAnswerIndex = answerIndex;
    }
}

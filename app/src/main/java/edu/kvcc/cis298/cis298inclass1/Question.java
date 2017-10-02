package edu.kvcc.cis298.cis298inclass1;

/**
 * Created by Evan on 9/18/2017.
 */

public class Question {
    private int mTextResId;
    //Resource id of the correct answer radio button
    private int mCorrectAnswerResId;
    //Resource id of all of the possible answers for the question
    private int[] mChoiceResId;
    private boolean mAnswerTrue;

    public Question(int textResId, int  correctAnswerResId, int[] choiceResId){
        mTextResId = textResId;
        mCorrectAnswerResId = correctAnswerResId;
        mChoiceResId = choiceResId;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public int getCorrectAnswerResId() {
        return mCorrectAnswerResId;
    }

    public void setCorrectAnswerResId(int correctAnswerResId) {
        mCorrectAnswerResId = correctAnswerResId;
    }

    public int[] getChoiceResId() {
        return mChoiceResId;
    }

    public void setChoiceResId(int[] choiceResId) {
        mChoiceResId = choiceResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}


package edu.kvcc.cis298.cis298inclass1;

/**
 * Created by Evan on 9/18/2017.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue) {
        //This is an int because it will reference the memory address
        //in the R.Java file that points to the string resource in the
        //strings.xml for the text of this particular question.
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}

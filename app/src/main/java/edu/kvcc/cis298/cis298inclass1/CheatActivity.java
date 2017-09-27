package edu.kvcc.cis298.cis298inclass1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    //This hey is used to store data on the intent that is created
    //to get this activity started.
    private static final String EXTRA_ANSWER_IS_TRUE = "edu.kvcc.cis298.cis298inclass1.answer_is_true";
    //This key is used to store the return data on the return intent
    //that is created to send data back to the quiz activity.
    private static final String EXTRA_ANSWER_SHOWN = "edu.kvcc.cis298.cis298inclass1.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;
    private Button mShowAnswer;


    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext,CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return i;
    }

    //This method will be called by QuizActivity to chedk ans see if a user
    // cheated or not, QuizActivity will send the Intent that contains the result
    //data into this method, and this method will 'decode' the intent and return
    //a bool to let us know whether the person cheated or not.
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    //Method to create and set the return data for this activity
    private void setAnswerShownResult(boolean isAnswerShown){
        //Make a new intent that will be used to hold the return data.
        //It will not be used to start a new activity.
        //Intent now has double duty.
        Intent data = new Intent();
        //Put an extra just like when we are creating an intent to start an activity.
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        //Call activity's setResult method to attach the intent as the return data.
        //The first parameter is a constant that says that everything finished okay here.
        ///There are other RESULT constants for when other things happen.
        setResult(RESULT_OK, data);
    }
}

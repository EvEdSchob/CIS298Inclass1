package edu.kvcc.cis298.cis298inclass1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static edu.kvcc.cis298.cis298inclass1.R.id.question_text_view;

public class QuizActivity extends AppCompatActivity {

    //Used for logging to the logcat
    private static final String TAG = "QuizActivity";

    //This will be used at the key in a key => value object
    //called the bundle to save information between screen rotations
    private static final String KEY_INDEX = "index";

    //Declare a request code integer that can be sent with the
    //startactivityforresult method. This way when we return
    //to this activity we can check this request code to see if
    //it is the one that matches the one we sent when we started
    //the cheat activity.
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    //Array of questions. We send over the resource id from
    //R.java as the first parameter of the constructor.
    //We will use this stored ResourceId
    //(Which references a string in the strings.xml) later.
    //If we were to have a string variable on the Question model
    //and try to pass the string value, it would work, but it
    //goes against the convention of android development.
    private Question[] mQuestions = new Question[] {
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    //Index of the current question we are on.
    private int mCurrentIndex = 0;

    //Class level variable to know whether the person used the cheat activity.
    private boolean mIsCheater;

    //Method that will be used to update the question text on the view.
    private void updateQuestion() {
        //Get the question from the array. Again, this will be an
        //integer because we are fetching the memory address stored
        //in the Question that points to the string we want to show.
        int question = mQuestions[mCurrentIndex].getTextResId();
        //Get the text on the question text view to the string resource
        //located at the memory address stored in Question.
        mQuestionTextView.setText(question);
    }

    //
    private void checkAnswer (boolean userPressedTrue){
        //Pull the answer from the current question
        boolean answerTrue = mQuestions[mCurrentIndex].isAnswerTrue();
        //Declare an int to hold the string resource id of the answer
        int messageResId = 0;

        //First check to see if the user cheated
        if(mIsCheater){
            //Get the answer to the 'do not cheat' string
            messageResId = R.string.judgement_toast;
        } else {
            //If the question's answer and userPressingTrue are equal
            //they got it right. Correct answers will be when both vars
            //are the same. If they are different it is wrong.
            //Set the messageResID once we determine what to set it to.
            if (userPressedTrue == answerTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        //Get a reference to the TextView that displays the question
        mQuestionTextView = (TextView) findViewById(question_text_view);

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Start CheatActivity
                //Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                Intent i = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                //startActivity(i);
                startActivityForResult(i, REQUEST_CODE_CHEAT);
            }
        });

        //Check the savedInstanceState bundle and see if there is
        //an index that we need to fetch out so we display the correct
        //question.
        //When the app first launches, there is no bundle. That only
        //happens when switching activities or on screen rotation.
        //Therefore we need to see if it is null before we try
        //to pull the info out from it.
        if (savedInstanceState != null) {
            //Get the value that is stored in it with the key of KEY_INDEX
            //if there is no entry with that key, use 0 as a default.
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        //Update the question now that we have the index.
        updateQuestion();


        //Use R to reach into the view layout and pull out a
        //reference to the button we want to use in code.
        //We will get access to the button in the view by using
        //the id property that we declared on the layout.
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);

            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //This will allow us to cycle the index back to zero
                //if it is about to become 5.
                mCurrentIndex = (mCurrentIndex +1) % mQuestions.length;
                //int question = mQuestions[mCurrentIndex].getTextResId();
                mIsCheater = false;
                //Update the question
                updateQuestion();
            }
        });
    }

    //Handle returning from another activity

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If something went wrong in the other activity, and the result
        //code is not OK, we can just return. No need to do any work.
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        //Check the Request code and see which one it is.
        //Since we only have one other activity, it is safe to say it will be that one.
        //But  in the event we had more activities we would
        //want to know which one we are returning from.
        if (requestCode == REQUEST_CODE_CHEAT) {
            //Check to see if the return data (intent) is null for some reason.
            if (data == null){
                return;
            }
            //Everything checks out. We can safely pull out the data we need.
            //We weill use the static method on the Cheat class to 'decode' the
            //returned
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState() called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }


}

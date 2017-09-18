package edu.kvcc.cis298.cis298inclass1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    //Array of questions. We send over the resource id from
    //R.java as the firest parameter of the constructor.
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
        //If the question's answer and userPressingTrue are equal
        //they got it right. Correct answers will be when both vars
        //are the same. If they are different it is wrong.
        //Set the messsageResID once we determine what to set it to.
        if (userPressedTrue == answerTrue){
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //Get a reference to the TextView that displays the question
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
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
                int question = mQuestions[mCurrentIndex].getTextResId();
                updateQuestion();
            }
        });
    }
}

package edu.kvcc.cis298.cis298inclass1;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private RadioGroup mQuestionGroup;
    private RadioButton mChoice1;
    private RadioButton mChoice2;
    private RadioButton mChoice3;
    private RadioButton mChoice4;

    private Button mSubmitButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    //Array of questions. We send over the resource id from
    //R.java as the first parameter of the constructor.
    //We will use this stored ResourceId
    //(Which references a string in the strings.xml) later.
    //If we were to have a string variable on the Question model
    //and try to pass the string value, it would work, but it
    //goes against the convention of android development.
    private Question[] mQuestions = new Question[] {
            //First parameter is the string that is the question text
            //Second parameter is the ID that is the correct answer for the question
            //This id is the id field assigned to the radio button widget that will
            //represent the correct answer.
            //If RadioButton2 is the correct answer, I need to assign the id for
            //RadioButton2. It will not have anything to do with the question itself.

            //Third parameter is an int array holding the possible answers to the question
            new Question(R.string.question_1_multiple, R.id.multiple_choice3,
                    new int[] {R.string.question_1_choice_1, R.string.question_1_choice_2,
                    R.string.question_1_choice_3, R.string.question_1_choice_4}),
            new Question(R.string.question_2_multiple, R.id.multiple_choice2,
                    new int[] {R.string.question_2_choice_1, R.string.question_2_choice_2,
                            R.string.question_2_choice_3, R.string.question_2_choice_4})
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

        //Fetch the question choice strings from the question object
        int[] choices = mQuestions[mCurrentIndex].getChoiceResId();

        //Assign each question choice text to the text property of the radio button
        mChoice1.setText(choices[0]);
        mChoice2.setText(choices[1]);
        mChoice3.setText(choices[2]);
        mChoice4.setText(choices[3]);
    }

    //
    private void checkAnswer (int selectedRadioButtonId){
        //Pull the answer from the current question
        int correctAnswer = mQuestions[mCurrentIndex].getCorrectAnswerResId();
        //Declare an int to hold the string resource id of the answer
        int messageResId = 0;
        //If the question's resId and correctAnswer resId are equal
        //they got it right. Correct answers will be when both vars
        //are the same. If they are different it is wrong.
        //Set the messsageResID once we determine what to set it to.
        if (selectedRadioButtonId == correctAnswer){
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

        //Get out the radio button
        mQuestionGroup = (RadioGroup) findViewById(R.id.multiple_group);

        mChoice1 = (RadioButton) findViewById(R.id.multiple_choice1);
        mChoice2 = (RadioButton) findViewById(R.id.multiple_choice2);
        mChoice3 = (RadioButton) findViewById(R.id.multiple_choice3);
        mChoice4 = (RadioButton) findViewById(R.id.multiple_choice4);

        updateQuestion();


        //Use R to reach into the view layout and pull out a
        //reference to the button we want to use in code.
        //We will get access to the button in the view by using
        //the id property that we declared on the layout.
        mSubmitButton = (Button) findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Query the radio button group to find out which radio
                //button was selected. Store the id of the selected one
                //in the variable selectedAnswerId
                //This will get the id of the RadioButton that we selected,
                //It will not return any string resource ids about the question
                //It is operating on the RadioButton widget, and thus return s the
                //id of the widget control.
                int selectedAnswerId = mQuestionGroup.getCheckedRadioButtonId();

                //Check if the user has selected a radio button
                if (selectedAnswerId == -1){
                    //Toast not selected
                    Toast.makeText(QuizActivity.this, R.string.not_selected_error, Toast.LENGTH_SHORT).show();
                } else {
                    //Call checkAnswer sending in the selectedAnswerId
                    checkAnswer(selectedAnswerId);
                }
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
                //Reset the radio group
                mQuestionGroup.clearCheck();
                updateQuestion();
            }
        });
    }
}

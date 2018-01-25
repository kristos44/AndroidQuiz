package com.example.krzysztof.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    int scores = 0;
    boolean questionNotAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkAnswers(View v) {
        checkRadioQuestion(R.id.question_1);
        checkEditTextQuestion(R.id.question_2_answer, getResources().getString(R.string.question_2_correct_answer));
        checkCheckboxQuestion(new ArrayList<>(Arrays.asList(R.id.question_3_answer_1, R.id.question_3_answer_2, R.id.question_3_answer_3)));
        checkRadioQuestion(R.id.question_4);

        String message;
        if(questionNotAnswered) {
            message = getResources().getString(R.string.message_answer_all);
        } else {
            message = getResources().getString(R.string.message_scores, scores);
            Button buttonSend = findViewById(R.id.button_check_answers);
            buttonSend.setEnabled(false);
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void resetAnswers(View v) {
        setContentView(R.layout.activity_main);
    }

    private void checkRadioQuestion(int radioGroupId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (checkedRadioButtonId != -1) {
            RadioButton radioButton = findViewById(checkedRadioButtonId);

            if (radioButton.getTag().toString().equals("true")) {
                scores++;
            }
        } else {
            questionNotAnswered = true;
        }
    }

    private void checkCheckboxQuestion(ArrayList<Integer> checkboxIds) {
        ArrayList<Boolean> checkboxAnswers = new ArrayList();
        ArrayList<Boolean> checkboxChecked = new ArrayList();

        for(Integer checkboxId: checkboxIds) {
            CheckBox checkBox = findViewById(checkboxId);

            if((checkBox.getTag().equals("true") && checkBox.isChecked())
                    || (checkBox.getTag().equals("false") && !checkBox.isChecked())) {
                checkboxAnswers.add(true);
            } else {
                checkboxAnswers.add(false);
            }

            if(checkBox.isChecked()) {
                checkboxChecked.add(true);
            }
        }

        if(!checkboxAnswers.contains(false)) {
            scores++;
        }

        if(!checkboxChecked.contains(true)) {
            questionNotAnswered = true;
        }
    }

    private void checkEditTextQuestion(Integer editTextId, String correctAnswer) {
        EditText editText = findViewById(editTextId);
        String answer = editText.getText().toString();

        if(answer.equals(correctAnswer)) {
            scores++;
        } else if (answer.isEmpty()) {
            questionNotAnswered = true;
        }
    }
}
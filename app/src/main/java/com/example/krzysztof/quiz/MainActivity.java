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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int scores = 0;
    boolean questionNotAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("question1State", getRadioGroupState(R.id.question_1));
//        EditText editText = findViewById(R.id.question_2_answer);
//        outState.putString("question2State", editText.getText().toString());
        outState.putString("question2State", getEditTextState(R.id.question_2_answer));
        outState.putSerializable("question3State", (Serializable) getCheckBoxState(new ArrayList<>(Arrays.asList(R.id.question_3_answer_1, R.id.question_3_answer_2, R.id.question_3_answer_3))));
        outState.putInt("question4State", getRadioGroupState(R.id.question_4));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        restoreRadioGroupState(R.id.question_1, savedInstanceState.getInt("question1State"));
//        EditText editText = findViewById(R.id.question_2_answer);
//        editText.setText(savedInstanceState.getInt("question2State"));
        restoreEditTextState(R.id.question_2_answer, savedInstanceState.getString("question2State"));
        restoreCheckBoxState((Map)savedInstanceState.getSerializable("question3State"));
        restoreRadioGroupState(R.id.question_4, savedInstanceState.getInt("question4State"));
    }

    public void checkAnswers(View v) {
        questionNotAnswered = false;

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

    private int getRadioGroupState(int radioGroupId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        return radioGroup.getCheckedRadioButtonId();
    }

    private String getEditTextState(int editTextId) {
        EditText editText = findViewById(editTextId);
        return editText.getText().toString();
    }

    private Map<Integer, Boolean> getCheckBoxState(ArrayList<Integer> checkboxIds) {
        Map<Integer, Boolean> mapCheckboxesState = new HashMap<>();

        for(int checkboxId: checkboxIds) {
            CheckBox checkBox = findViewById(checkboxId);
            mapCheckboxesState.put(checkboxId, checkBox.isChecked());
        }

        return mapCheckboxesState;
    }

    private void restoreRadioGroupState(int radioGroupId, int radioButtonId) {
        if(radioButtonId != -1) {
            RadioGroup radioGroup = findViewById(radioGroupId);
            radioGroup.check(radioButtonId);
        }
    }

    private void restoreEditTextState(int editTextId, String editTextValue) {
        EditText editText = findViewById(editTextId);
        editText.setText(editTextValue);
    }

    private void restoreCheckBoxState(Map<Integer, Boolean> mapCheckboxesState) {
        Iterator iterator = mapCheckboxesState.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry checkBoxData = (Map.Entry) iterator.next();
            if((boolean)checkBoxData.getValue()) {
                CheckBox checkBox = findViewById((int)checkBoxData.getKey());
                checkBox.setChecked(true);
            }
        }
    }
}
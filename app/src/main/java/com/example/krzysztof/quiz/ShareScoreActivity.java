package com.example.krzysztof.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by krzysztof on 26.01.18.
 */

public class ShareScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_score);

        Bundle bundle = getIntent().getExtras();
        String messageScore = bundle.getString("message_score");

        TextView textViewMessageScore = findViewById(R.id.message_score);
        textViewMessageScore.setText(messageScore);

        Toast.makeText(this, messageScore, Toast.LENGTH_SHORT).show();
    }

    public void shareScore(View v) {
        Bundle bundle = getIntent().getExtras();
        String messageShareScore = bundle.getString("message_share_score");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, messageShareScore);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}

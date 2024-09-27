package com.example.guesser;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.guesser.Interface.OnPredictionCompleteListener;
import com.example.guesser.Model.Passenger;
import com.example.guesser.API.PredictSurvivalTask;

public class MainActivity extends AppCompatActivity implements OnPredictionCompleteListener {

    private EditText etPclass, etSex, etAge, etSibSp, etParch, etFare;
    private TextView tvResult;
    private Button btnPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPclass = findViewById(R.id.etPclass);
        etSex = findViewById(R.id.etSex);
        etAge = findViewById(R.id.etAge);
        etSibSp = findViewById(R.id.etSibSp);
        etParch = findViewById(R.id.etParch);
        etFare = findViewById(R.id.etFare);
        tvResult = findViewById(R.id.tvResult);
        btnPredict = findViewById(R.id.btnPredict);

        btnPredict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pclass = Integer.parseInt(etPclass.getText().toString());
                String sex = etSex.getText().toString(); // Assume input as "male" or "female"
                int age = Integer.parseInt(etAge.getText().toString());
                int sibSp = Integer.parseInt(etSibSp.getText().toString());
                int parch = Integer.parseInt(etParch.getText().toString());
                double fare = Double.parseDouble(etFare.getText().toString());

                Passenger passenger = new Passenger(pclass, sex, age, sibSp, parch, fare);

                // Execute the AsyncTask to make the API request
                new PredictSurvivalTask(MainActivity.this).execute(passenger);
            }
        });
    }

    @Override
    public void onPredictionComplete(Integer result) {
        if (result != null) {
            String message = (result == 1) ? "Survived" : "Did not survive";
            tvResult.setText("Prediction: " + message);
        } else {
            tvResult.setText("Error: Could not get prediction");
        }
    }
}

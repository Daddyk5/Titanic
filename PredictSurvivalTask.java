package com.example.guesser.API;

import android.os.AsyncTask;
import android.util.Log;
import com.example.guesser.Interface.OnPredictionCompleteListener;
import com.example.guesser.Model.Passenger;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PredictSurvivalTask extends AsyncTask<Passenger, Void, Integer> {
    private OnPredictionCompleteListener listener;

    public PredictSurvivalTask(OnPredictionCompleteListener listener) {
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(Passenger... passengers) {
        HttpURLConnection connection = null;
        try {
            // Use the correct API URL for your emulator or real device
            URL url = new URL("http://10.0.2.2:5000/predict"); // For emulator; use your server IP for a real device
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Create JSON object for passenger data
            Passenger passenger = passengers[0];
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("Pclass", passenger.getPclass());
            jsonInput.put("Sex", passenger.getSex());
            jsonInput.put("Age", passenger.getAge());
            jsonInput.put("SibSp", passenger.getSibSp());
            jsonInput.put("Parch", passenger.getParch());
            jsonInput.put("Fare", passenger.getFare());

            // Ensure Embarked is valid (0, 1, or 2)
            int embarked = passenger.getEmbarked();
            if (embarked < 0 || embarked > 2) {
                Log.e("PredictSurvivalTask", "Invalid Embarked value: " + embarked);
                return null; // Invalid value for Embarked
            }
            jsonInput.put("Embarked", embarked);

            // Send the request
            OutputStream os = connection.getOutputStream();
            os.write(jsonInput.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Get the response
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                br.close();

                // Log response
                Log.d("API Response", response.toString());

                // Parse JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                return jsonResponse.getInt("Survived"); // Corrected key to match Flask response
            } else {
                // Handle error response codes
                Log.e("Prediction Error", "Response code: " + responseCode);
                return null; // or a specific error value based on the response code
            }

        } catch (Exception e) {
            Log.e("Prediction Error", "Error in prediction: " + e.getMessage());
            e.printStackTrace();
            return null; // You might want to handle this case better in a production app
        } finally {
            if (connection != null) {
                connection.disconnect(); // Ensure connection is closed
            }
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        listener.onPredictionComplete(result);
    }
}

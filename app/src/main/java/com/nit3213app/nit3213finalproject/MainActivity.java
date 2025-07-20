package com.nit3213app.nit3213finalproject;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import okhttp3.*;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyApp";
    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private ProgressBar progressBar;
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both fields", Toast.LENGTH_SHORT).show();
            } else {
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        RequestBody body = RequestBody.create(
                "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}",
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://nit3213api.onrender.com/sydney/auth")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    loginButton.setEnabled(true);
                    Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response code: " + response.code());
                Log.d(TAG, "Response message: " + response.message());
                Log.d(TAG, "Response URL: " + response.request().url());
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    LoginResponse loginResponse = gson.fromJson(responseData, LoginResponse.class);

                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        loginButton.setEnabled(true);
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        intent.putExtra("keypass", loginResponse.keypass);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        loginButton.setEnabled(true);
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    class LoginResponse {
        String keypass;
    }
}
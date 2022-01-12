package cg.example.greenlife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import cg.example.greenlife.R;
import cg.example.greenlife.api.RetrofitClient;
import cg.example.greenlife.model.Globals;
import cg.example.greenlife.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        findViewById(R.id.tvRegisterLink).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        findViewById(R.id.btnOverwriteGoToHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    private void loginUser() {
        final String userName = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        Boolean errorFlag = false;

        if (userName.isEmpty()) {
            etUsername.setError("Username is required");
            etUsername.requestFocus();
            errorFlag = true;
        } else if (password.isEmpty()) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            errorFlag = true;
        }

        if (!errorFlag) {
            User user = new User();
            if (userName.contains("@"))
                user.setEmail(userName);
            else
                user.setUsername(userName);
            user.setPassword(password);
            this.makeLoginCall(user);

        }
    }

    private void makeLoginCall(User user) {
        Call<User> call = RetrofitClient
                .getInstance()
                .getAPI()
                .checkUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Boolean success;
                success = response.isSuccessful();

                int requestCode = response.code();


                if (success) {
                    User crtUser = response.body();
                    Globals.currentUser = crtUser;
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    if (requestCode == 500)
                        Toast.makeText(LoginActivity.this, "Server error", Toast.LENGTH_LONG).show();
                    else {
                        if (requestCode == 400)
                            Toast.makeText(LoginActivity.this, "Bad request" + response.body(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(LoginActivity.this, "Email or username does not exist or wrong password" + requestCode, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
package cg.example.greenlife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import cg.example.greenlife.R;
import cg.example.greenlife.api.RetrofitClient;
import cg.example.greenlife.controller.InputValidator;
import cg.example.greenlife.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText username, password, rePassword, email, firstName, lastName;
    private InputValidator inputValidator = new InputValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.registerUsernameInput);
        password = findViewById(R.id.registerPasswordInput);
        rePassword = findViewById(R.id.registerPasswordRetype);
        email = findViewById(R.id.registerEmailInput);
        firstName = findViewById(R.id.registerFirstNameInput);
        lastName = findViewById(R.id.registerLastNameInput);

        findViewById(R.id.registerBtn).setOnClickListener(view -> registerUser());
        findViewById(R.id.registerGoToLoginLink).setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void registerUser() {
        HashMap<EditText, String> inputs = new HashMap<>();
        String usernameString = username.getText().toString().trim();
        String emailString = email.getText().toString().trim();
        String passwordString = password.getText().toString().trim();
        String rePasswordString = rePassword.getText().toString().trim();
        String firstNameString = firstName.getText().toString().trim();
        String lastNameString = lastName.getText().toString().trim();

        inputs.put(username, usernameString);
        inputs.put(email, emailString);
        inputs.put(password, passwordString);
        inputs.put(rePassword, rePasswordString);
        inputs.put(firstName, firstNameString);
        inputs.put(lastName, lastNameString);

        for (EditText field : inputs.keySet()) {  // check if any fields are empty (all are required)
            if (inputs.get(field).isEmpty()) {
                inputValidator.setFieldError(field, "This field is required!"); // if so, send error
                return; // is this needed?
            } // not sure how this behaves in this for
        }

        if(!inputValidator.doStringsMatch(passwordString, rePasswordString)){ // check if the retyped password matches
            inputValidator.setFieldError(rePassword, "Passwords do not match!");
            return;
        }


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .createUser(new User(usernameString, passwordString, emailString, firstNameString, lastNameString));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s = "";
                try {
                    s = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (s.equals("SUCCESS")) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered. Please login", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "User already exists!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
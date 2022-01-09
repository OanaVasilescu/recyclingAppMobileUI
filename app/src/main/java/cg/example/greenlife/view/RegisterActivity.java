package cg.example.greenlife.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
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

        Boolean errorFlag = false;

        for (EditText field : inputs.keySet()) {  // check if any fields are empty (all are required)
            if (inputs.get(field).isEmpty() && inputs.get(field) != rePasswordString) {
                inputValidator.setFieldError(field, "This field is required!"); // if so, send error
                errorFlag = true;
            } // not sure how this behaves in this for
        } // TODO: validate here if username or email is taken
        if (!inputs.get(rePassword).isEmpty()) {
            if (!inputValidator.doStringsMatch(passwordString, rePasswordString)) { // check if the retyped password matches
                inputValidator.setFieldError(rePassword, "Passwords do not match!");
                errorFlag = true;
            } else {
                errorFlag = false;
            }
        } else {
            errorFlag = true;
        }

        if (!errorFlag)
            this.makeCall(new User(usernameString, passwordString, emailString, firstNameString, lastNameString));
    }

    private void makeCall(User newUser) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .createUser(newUser);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Boolean success;
                success = response.isSuccessful();

                int requestCode = response.code();


                if (success) {
                    Toast.makeText(RegisterActivity.this, "Successfully registered. Please login", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    if (requestCode == 400)
                        Toast.makeText(RegisterActivity.this, "Username or email is taken!", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(RegisterActivity.this, "Server Error", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
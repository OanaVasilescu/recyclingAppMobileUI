package cg.example.greenlife.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import cg.example.greenlife.R;
import cg.example.greenlife.api.RetrofitClient;
import cg.example.greenlife.model.Globals;
import cg.example.greenlife.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {
    private EditText etUsername;
    private EditText etPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_tab_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        etUsername = view.findViewById(R.id.emailOrUsernameInput);
        etPassword = view.findViewById(R.id.passwordInput);
        Button loginButton = view.findViewById(R.id.loginButton);
        view.findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        float v = 0;

        etUsername.setTranslationX(300);
        etPassword.setTranslationX(300);
        loginButton.setTranslationX(300);

        etUsername.setAlpha(v);
        etPassword.setAlpha(v);
        loginButton.setAlpha(v);

        etUsername.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(4400).start();
        etPassword.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(4700).start();
        loginButton.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(5000).start();
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
                    Globals.setCurrentUser(crtUser);
                    startActivity(new Intent(getView().getContext(), MainActivity.class));
                } else {
                    if (requestCode == 500)
                        Toast.makeText(getView().getContext(), "Server error", Toast.LENGTH_LONG).show();
                    else {
                        if (requestCode == 400)
                            Toast.makeText(getView().getContext(), "Bad request" + response.body(), Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getView().getContext(), "Email or username does not exist or wrong password" + requestCode, Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getView().getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

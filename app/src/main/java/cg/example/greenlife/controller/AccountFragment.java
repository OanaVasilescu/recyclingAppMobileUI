package cg.example.greenlife.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import cg.example.greenlife.R;
import cg.example.greenlife.model.Globals;
import cg.example.greenlife.model.User;
import cg.example.greenlife.view.LoginActivity;
import cg.example.greenlife.view.RegisterActivity;

public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_account, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView accountName = view.findViewById(R.id.accountName);
        TextView accountEmail = view.findViewById(R.id.accountEmail);
        TextView accountUsername = view.findViewById(R.id.accountUsername);

        String name = Globals.currentUser.getFirstName() + Globals.currentUser.getLastName();
        accountName.setText(name);
        accountEmail.setText(Globals.currentUser.getEmail());
        accountUsername.setText(Globals.currentUser.getUsername());

        view.findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.setCurrentUser(new User());
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });

    }
}

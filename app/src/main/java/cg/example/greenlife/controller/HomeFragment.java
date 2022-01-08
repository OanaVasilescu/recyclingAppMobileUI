package cg.example.greenlife.controller;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import cg.example.greenlife.R;

public class HomeFragment extends Fragment {
    Dialog myDialog; //TODO: animate dialog maybe


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, parent, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        myDialog = new Dialog(view.getContext());

        view.findViewById(R.id.tellMeMoreBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                this.showPopup(view);
            }

            private void showPopup(View view) {
                TextView txtclose;
                myDialog.setContentView(R.layout.tip_popup);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

    }
}

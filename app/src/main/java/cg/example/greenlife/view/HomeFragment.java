package cg.example.greenlife.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import cg.example.greenlife.R;
import cg.example.greenlife.api.RetrofitClient;
import cg.example.greenlife.model.Tip;
import cg.example.greenlife.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Dialog myDialog; //TODO: animate dialog maybe

    String moreInfo="";
    String moreInfoTitle="";

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

                TextView moreInfoTitleTv = myDialog.findViewById(R.id.moreInfoTitle);
                TextView moreInfoTv =  myDialog.findViewById(R.id.moreInfoText);

                moreInfoTitleTv.setText(moreInfoTitle);
                moreInfoTv.setText(moreInfo);

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
        this.getRandomTip( view);
    }

    private void getRandomTip(View view) {
        Call<Tip> call = RetrofitClient
                .getInstance()
                .getAPI()
                .getRandomTip();

        call.enqueue(new Callback<Tip>() {
            @Override
            public void onResponse(Call<Tip> call, Response<Tip> response) {
                Boolean success;
                success = response.isSuccessful();

                int requestCode = response.code();


                if (success) {
                    this.setTipData(response, view);
                } else {
                    if (requestCode == 500)
                        Toast.makeText(view.getContext(), "Server error", Toast.LENGTH_LONG).show();
                    else {
                        Toast.makeText(view.getContext(), "no tips", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Tip> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }

            private void setTipData(Response<Tip> response, View view) {
                TextView text = view.findViewById(R.id.recyclingTipText);

                assert response.body() != null;
                Tip tip = response.body(); //TODO: see how to get data
                text.setText(tip.getTipText());
                moreInfoTitle = tip.getTipText();
                moreInfo = tip.getMoreInfo();
            }
        });
    }
}

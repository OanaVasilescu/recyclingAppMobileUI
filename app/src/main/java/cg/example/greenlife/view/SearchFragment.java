package cg.example.greenlife.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import cg.example.greenlife.R;


public class SearchFragment extends Fragment {
    private CodeScanner mCodeScanner;
    private static final int CAMERA_REQUEST_CODE = 101;

    String lastResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        final Activity activity = this.getActivity();
        View root = inflater.inflate(R.layout.fragment_search, parent, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        assert activity != null;
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setFlashEnabled(false);
        mCodeScanner.setCamera(0);
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
        mCodeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        mCodeScanner.setScanMode(ScanMode.CONTINUOUS);

        TextView textView = root.findViewById(R.id.barcode_text);
        TextView goToRecyclingInstructionsBtn = root.findViewById(R.id.go_to_instructions_btn);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                if (lastResult != result.getText()) { // so as to not do it continuously
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(result.getText());
                            lastResult = result.getText();
                            goToRecyclingInstructionsBtn.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });

        mCodeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("SearchFragment", "Code Scanner Error:" + error.getMessage());
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
       view.findViewById(R.id.go_to_instructions_btn).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View oView) {
               startActivity(new Intent(view.getContext(), InstructionsActivity.class));
           }
       });
    }

    private void setUpPermissions() {
        int permission = ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            this.makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
    }

    //TODO: show somehow that camera permission is necessary
}

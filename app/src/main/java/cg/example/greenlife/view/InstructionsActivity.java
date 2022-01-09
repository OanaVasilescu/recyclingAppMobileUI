package cg.example.greenlife.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cg.example.greenlife.R;

public class InstructionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        findViewById(R.id.goHomeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, MainActivity.class);
                intent.putExtra("Destination", "home");
                startActivity(intent);
            }
        });

        findViewById(R.id.reScanButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, MainActivity.class);
                intent.putExtra("Destination", "search");
                startActivity(intent);
            }
        });
    }
}

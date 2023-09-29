package com.example.laba_03_04;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private Spinner spinnerExperience;
    private RadioGroup radioGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvSurname = findViewById(R.id.tv_surname);
        TextView tvCompany = findViewById(R.id.tv_company);
        spinnerExperience = findViewById(R.id.spinner_experience);
        LinearLayout linearLayout = findViewById(R.id.male_layout);
        radioGender = linearLayout.findViewById(R.id.radio_gender);

        String nameWithLabel = "Name: " + getIntent().getStringExtra("name");
        tvName.setText(nameWithLabel);
        String surnameWithLabel = "Surname: " + getIntent().getStringExtra("surname");
        tvSurname.setText(surnameWithLabel);
        String companyWithLabel = "Company: " +getIntent().getStringExtra("company");
        tvCompany.setText(companyWithLabel);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                intent.putExtra("name", tvName.getText());
                intent.putExtra("surname", tvSurname.getText());
                intent.putExtra("company", tvCompany.getText());
                intent.putExtra("experience", spinnerExperience.getSelectedItem().toString());
                String gender = ((RadioButton)findViewById(radioGender.getCheckedRadioButtonId())).getText().toString();
                intent.putExtra("gender", gender);
                startActivity(intent);
            }
        });
    }
}


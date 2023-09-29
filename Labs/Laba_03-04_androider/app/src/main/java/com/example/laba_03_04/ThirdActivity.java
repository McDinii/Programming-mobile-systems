package com.example.laba_03_04;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ThirdActivity extends AppCompatActivity {

    private CheckBox chkInterest;
    private DatePicker dpBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvSurname = findViewById(R.id.tv_surname);
        TextView tvCompany = findViewById(R.id.tv_company);
        TextView tvExperience = findViewById(R.id.tv_experience);
        TextView tvGender = findViewById(R.id.tv_gender);
        chkInterest = findViewById(R.id.chk_interest);
        dpBirthday = findViewById(R.id.dp_birthday);

        String nameWithLabel = "Name: " + getIntent().getStringExtra("name");
        tvName.setText(nameWithLabel);
        String surnameWithLabel = "Surname: " + getIntent().getStringExtra("surname");
        tvSurname.setText(surnameWithLabel);
        String companyWithLabel = "Company: " +getIntent().getStringExtra("company");
        tvCompany.setText(companyWithLabel);
        String expWithLabel = "Exp.: " + getIntent().getStringExtra("experience");
        tvExperience.setText(expWithLabel);
        String expGender = "Gender: "+getIntent().getStringExtra("gender");
        tvGender.setText(expGender);

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
                String birthday = dpBirthday.getDayOfMonth() + "/" +
                        (dpBirthday.getMonth() + 1) + "/" +  // add 1 because month starts from 0
                        dpBirthday.getYear();

                Intent intent = new Intent(ThirdActivity.this, FourthActivity.class);
                intent.putExtra("name", tvName.getText());
                intent.putExtra("surname", tvSurname.getText());
                intent.putExtra("company", tvCompany.getText());
                intent.putExtra("experience", tvExperience.getText());
                intent.putExtra("gender", tvGender.getText());
                intent.putExtra("interest", chkInterest.isChecked());
                intent.putExtra("birthday", birthday);
                startActivity(intent);
            }
        });
    }
}

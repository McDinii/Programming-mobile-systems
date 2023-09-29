package com.example.laba_03_04;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.io.OutputStreamWriter;
import com.google.gson.Gson;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        TextView tvName = findViewById(R.id.tv_name);
        TextView tvSurname = findViewById(R.id.tv_surname);
        TextView tvCompany = findViewById(R.id.tv_company);
        TextView tvExperience = findViewById(R.id.tv_experience);
        TextView tvGender = findViewById(R.id.tv_gender);
        TextView tvInterest = findViewById(R.id.tv_interest);
        TextView tvBirthday = findViewById(R.id.tv_birthday);

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
        tvInterest.setText(getIntent().getBooleanExtra("interest", false) ? "Interested in conference" : "Not interested in conference");
        String birthday = "Birthday: " +getIntent().getStringExtra("birthday");
        tvBirthday.setText(birthday);

        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FourthActivity.this, "Data Submitted", Toast.LENGTH_SHORT).show();
                UserData userData = new UserData();
                userData.setName(tvName.getText().toString());
                userData.setSurname(tvSurname.getText().toString());
                userData.setCompany(tvCompany.getText().toString());
                userData.setExperience(tvExperience.getText().toString());
                userData.setGender(tvGender.getText().toString());
                userData.setInterest(getIntent().getBooleanExtra("interest", false));
                userData.setBirthday(tvBirthday.getText().toString());

                Gson gson = new Gson();
                String json = gson.toJson(userData);

                writeToFile(json, FourthActivity.this);


                // Вставьте ваш код для сохранения данных здесь
            }
        });
    }
    private  void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}

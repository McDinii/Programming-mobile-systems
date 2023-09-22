package com.example.myapplication;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private EditText currentDateEditText;
    private EditText birthDateEditText;
    private Button calculateButton;
    private Button resetButton;
    private TextView resultTextView;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentDateEditText = findViewById(R.id.currentDateEditText);
        birthDateEditText = findViewById(R.id.birthDateEditText);
        calculateButton = findViewById(R.id.calculateButton);
        resetButton = findViewById(R.id.resetButton);
        resultTextView = findViewById(R.id.resultTextView);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        currentDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(currentDateEditText);
            }
        });

        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(birthDateEditText);
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAge();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetFields();
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar selectedDateCalendar = Calendar.getInstance();
                selectedDateCalendar.set(year, month, dayOfMonth);

                String selectedDate = dateFormat.format(selectedDateCalendar.getTime());
                editText.setText(selectedDate);
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void calculateAge() {
        String currentDateStr = currentDateEditText.getText().toString();
        String birthDateStr = birthDateEditText.getText().toString();

        try {
            Date currentDate = dateFormat.parse(currentDateStr);
            Date birthDate = dateFormat.parse(birthDateStr);

            long diffInMillis = currentDate.getTime() - birthDate.getTime();
            long hours = TimeUnit.MILLISECONDS.toHours(diffInMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60;
            long months = (long) (diffInMillis / (30.44 * 24 * 60 * 60 * 1000));

            String result
                    = "Возраст: " +
                    months + " месяцев, " +
                    hours + " часов, " +
                    minutes + " минут.";

            resultTextView.setText(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void resetFields() {
        currentDateEditText.setText("");
        birthDateEditText.setText("");
        resultTextView.setText("");
    }
}

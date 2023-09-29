package com.example.laba_03_04;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {
    // Переменные для работы с разрешениями
    private final int REQUEST_PERMISSION_PHONE_CALL = 1;
    private final int REQUEST_PERMISSION_EMAIL = 2;
    private final int REQUEST_PERMISSION_GALLERY = 3;
    private final int REQUEST_PERMISSION_CAMERA = 4;
    // Для идентификации запросов
    private final int PICK_IMAGE_GALLERY = 5;
    private final int PICK_IMAGE_CAMERA = 6;

    // Информация о пользователе
    private String name, surname, company, email, phone, socialMediaLink;
    private Uri imageUri;

    private EditText edtName, edtSurname, edtCompany, edtEmail, edtPhone, edtSocialMediaLink;
    private ImageView ivPhoto;

    // Константы для сохранения данных
    private final String FILE_NAME = "user_info.txt";

    // Флаг для проверки доступа к читать/писать в память устройства
    private boolean hasStoragePermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = findViewById(R.id.edt_name);
        edtSurname = findViewById(R.id.edt_surname);
        edtCompany = findViewById(R.id.edt_company);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtSocialMediaLink = findViewById(R.id.edt_social_media);
        ivPhoto = findViewById(R.id.iv_photo);

        Button btnNext = findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("surname", surname);
                intent.putExtra("company", company);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("socialMediaLink", socialMediaLink);
                startActivity(intent);
            }
        });

        Button btnCall = findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });

        Button btnEmail = findViewById(R.id.btn_email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        Button btnSelectImage = findViewById(R.id.btn_select_image);
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        Button btnOpenSocialMedia = findViewById(R.id.btn_open_social_media);
        btnOpenSocialMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSocialMedia();
            }
        });

        checkStoragePermission();
    }
    // Метод для сохранения внесенных данных
    private void saveData() {
        name = edtName.getText().toString();
        surname = edtSurname.getText().toString();
        company = edtCompany.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();
        socialMediaLink = edtSocialMediaLink.getText().toString();

        // Сохраняем данные в файл
        try {
            String data = name + "," + surname + "," + company + "," + email + "," + phone + "," + socialMediaLink;
            OutputStream outputStream = Files.newOutputStream(Paths.get(getFilesDir() + "/" + FILE_NAME));
            outputStream.write(data.getBytes());
            outputStream.close();
            Toast.makeText(MainActivity.this, "Data saved.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Failed to save data.", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для проверки разрешения на доступ к памяти устройства
    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            hasStoragePermission = true;
        } else {
            // Запрос разрешения
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
    // Метод для исходящего вызова
    private void makePhoneCall() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_PHONE_CALL);
        } else {
            String phoneNumber = edtPhone.getText().toString();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(callIntent);
        }
    }

    // Метод для отправки email
    private void sendEmail() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, REQUEST_PERMISSION_EMAIL);
        } else {
            String emailAddress = edtEmail.getText().toString();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setType("text/plain");
            emailIntent.setData(Uri.parse("mailto:" + emailAddress));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Message");
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        }
    }

    // Метод для выбора изображения из галереи или снятия снимка с помощью камеры
    private void selectImage() {
        final CharSequence[] options = {"Choose from Gallery", "Take Photo", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Choose from Gallery")) {
                    openGallery();
                } else if (options[item].equals("Take Photo")) {
                    openCamera();
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    // Метод для открытия галереи и выбора изображения
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_GALLERY);
    }

    // Метод для открытия камеры и снятия снимка
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = createImageFile();
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);
        }
    }

    // Метод для создания файла для хранения снимка
    private File createImageFile() {
        String imageName = "photo.jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageName);

        try {
            imageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        imageUri = Uri.fromFile(imageFile);
        return imageFile;
    }

    // Метод для открытия социальной сети
    private void openSocialMedia() {
        String socialMediaLink = edtSocialMediaLink.getText().toString();
        Intent socialMediaIntent = new Intent(Intent.ACTION_VIEW);
        socialMediaIntent.setData(Uri.parse(socialMediaLink));
        startActivity(socialMediaIntent);
    }


}



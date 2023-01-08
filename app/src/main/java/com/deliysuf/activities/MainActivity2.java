package com.deliysuf.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
private ImageView imagesss;
private Button buttonSave;
private Button buttonDelete;
private Button buttonUpdate;
private EditText editText;
private Bitmap selectedImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imagesss = findViewById(R.id.imageView);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonSave = findViewById(R.id.buttonSave);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        editText = findViewById(R.id.editTextTextPersonName);

    }
    public void saveRecord(View view){
         String artName = editText.getText().toString();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        selectedImages.compress(Bitmap.CompressFormat.PNG ,50 ,outputStream);
        byte[] image = outputStream.toByteArray();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(ArtContentProvider.NAME ,artName );
        contentValues.put(ArtContentProvider.IMAGE ,image);
        Uri uri = getContentResolver().insert(ArtContentProvider.CONTENT_URI ,contentValues);
        Intent intent = new Intent(getApplicationContext() , MainActivity.class);
        startActivity(intent);

    }
    public void deleteRecord(View view){

    }
    public void updateRecord(View view){

    }
    public void selectImage(View view){
        if(ContextCompat.checkSelfPermission(MainActivity2.this , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this , new String[] {Manifest.permission.READ_EXTERNAL_STORAGE} , 1);
        }
        else{
            Intent intent = new Intent( Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent , 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent( Intent.ACTION_PICK , MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent , 2);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 2 && resultCode == RESULT_OK && data != null ){
            Uri image  = data.getData();
            try {
                selectedImages = MediaStore.Images.Media.getBitmap(this.getContentResolver() , image);
                imagesss.setImageBitmap(selectedImages);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
package com.deliysuf.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.security.Permission;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView ;
    static ArrayList<Bitmap> arrayImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    listView = findViewById(R.id.liste);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayImage = new ArrayList<>();
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 , arrayList);
        listView.setAdapter(arrayAdapter);
        String url = "content://com.deliysuf.activities";
        Uri ArtUri = Uri.parse(url);
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ArtUri ,null ,null ,null ,"name");
        if(cursor != null){
            while (cursor.moveToNext()){
                int i = cursor.getColumnIndex(ArtContentProvider.NAME);
                int j = cursor.getColumnIndex(ArtContentProvider.IMAGE);
                arrayList.add(cursor.getString(i));
                byte[] bytes = cursor.getBlob(j);
                Bitmap image = BitmapFactory.decodeByteArray(bytes ,0 ,bytes.length);
                arrayImage.add(image);
            }
        }
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getApplicationContext() , MainActivity2.class);
               intent.putExtra("info" , "old");
               intent.putExtra("name" ,arrayList.get(i));
               intent.putExtra("position" ,i);
               startActivity(intent);
           }
       });


        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_art , menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId() == R.id.add_art){
           Intent intent = new Intent(getApplicationContext() , MainActivity2.class);
           intent.putExtra("info" ,"new");
           startActivity(intent);
       }

        return super.onOptionsItemSelected(item);
    }

}
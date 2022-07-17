package com.example.artbookjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.artbookjava.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    ArrayList<Art> artArrayList;
    ArtAdapter artAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




        artArrayList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artAdapter = new ArtAdapter(artArrayList);
        binding.recyclerView.setAdapter(artAdapter);
        getData();





    }



    private void getData(){


        try {

            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM arts", null);
            int nameIx = cursor.getColumnIndex("artname");
            int idIx = cursor.getColumnIndex("id");

            while(cursor.moveToNext()){
                String name = cursor.getString(nameIx);
                int id = cursor.getInt(idIx);
                Art art = new Art(name,id);
                artArrayList.add(art);
            }

            artAdapter.notifyDataSetChanged();//verileri görmemizi sağlıyor

            cursor.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }



    //oluşturduğumuz menüyü ayarlayalım
    //oluşturmak için res klasörüne menu adında bir dosya ekliyoruz ve
    //bunun xml dosyasını oluşturup içerisinde itemleri ve özelliklerini veriyoruz (idler vs...)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.art_menu, menu); //neyi, nereye bağlıyoruz



        return super.onCreateOptionsMenu(menu);
    }


    //verdiğimiz itemlerden biri seçilirse ne olacağı
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //hangi seçeneğin seçildiğinde ne yapılacağını if kontrolüyle yapıyoruz
        if(item.getItemId() == R.id.add_art){
            Intent intent = new Intent(this,ArtActivity.class);
            intent.putExtra("info","new");
            startActivity(intent);

        }




        return super.onOptionsItemSelected(item);
    }
}
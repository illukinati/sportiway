package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus.sportiway.R;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ImageView img1, img2, img3;
    ImageView imgCheck1, imgCheck2, imgCheck3;
    Button btnNext;

    String character = "satu";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == img1){
            imgCheck1.setVisibility(View.VISIBLE);
            imgCheck2.setVisibility(View.GONE);
            imgCheck3.setVisibility(View.GONE);
            character = "satu";
        } else if (view == img2){
            imgCheck1.setVisibility(View.GONE);
            imgCheck2.setVisibility(View.VISIBLE);
            imgCheck3.setVisibility(View.GONE);
            character = "dua";
        } else if(view == img3){
            imgCheck1.setVisibility(View.GONE);
            imgCheck2.setVisibility(View.GONE);
            imgCheck3.setVisibility(View.VISIBLE);
            character = "tiga";
        } else if (view == btnNext){
            Intent intent = new Intent(this, EditProfileActivity.class);
            intent.putExtra("key", "baru");
            intent.putExtra("character", character);
            startActivity(intent);
        }
    }

    private void binding(){
        img1 = (ImageView)findViewById(R.id.img_1);
        img2 = (ImageView)findViewById(R.id.img_2);
        img3 = (ImageView)findViewById(R.id.img_3);
        imgCheck1 = (ImageView) findViewById(R.id.img_check_1);
        imgCheck2 = (ImageView) findViewById(R.id.img_check_2);
        imgCheck3 = (ImageView) findViewById(R.id.img_check_3);
        btnNext = (Button) findViewById(R.id.btn_next);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);
        img3.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }
}

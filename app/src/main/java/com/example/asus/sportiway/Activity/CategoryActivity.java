package com.example.asus.sportiway.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.sportiway.R;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ImageView imgIcon [] = new ImageView[4];
    ImageView imgCheck[] = new ImageView[4];
    String category [] = {"Sepak Bola", "Jogging", "Bulu Tangkis", "Basket"};
    Button btnNext;

    String cat = "Sepak Bola";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding();
    }

    private void binding(){
        imgIcon[0] = (ImageView) findViewById(R.id.img_1);
        imgIcon[1] = (ImageView) findViewById(R.id.img_2);
        imgIcon[2] = (ImageView) findViewById(R.id.img_3);
        imgIcon[3] = (ImageView) findViewById(R.id.img_4);

        imgCheck[0] = (ImageView) findViewById(R.id.img_check_1);
        imgCheck[1] = (ImageView) findViewById(R.id.img_check_2);
        imgCheck[2] = (ImageView) findViewById(R.id.img_check_3);
        imgCheck[3] = (ImageView) findViewById(R.id.img_check_4);

        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);
        for(int i = 0; i < 4; i ++){
            imgIcon[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        if(view == imgIcon[0]){
            imgCheck[0].setVisibility(View.VISIBLE);
            imgCheck[1].setVisibility(View.GONE);
            imgCheck[2].setVisibility(View.GONE);
            imgCheck[3].setVisibility(View.GONE);
            cat = category[0];
        } else if (view == imgIcon[1]){
            imgCheck[0].setVisibility(View.GONE);
            imgCheck[1].setVisibility(View.VISIBLE);
            imgCheck[2].setVisibility(View.GONE);
            imgCheck[3].setVisibility(View.GONE);
            cat = category[1];
        } else if (view == imgIcon[2]){
            imgCheck[0].setVisibility(View.GONE);
            imgCheck[1].setVisibility(View.GONE);
            imgCheck[2].setVisibility(View.VISIBLE);
            imgCheck[3].setVisibility(View.GONE);
            cat = category[2];
        } else if (view == imgIcon[3]) {
            imgCheck[0].setVisibility(View.GONE);
            imgCheck[1].setVisibility(View.GONE);
            imgCheck[2].setVisibility(View.GONE);
            imgCheck[3].setVisibility(View.VISIBLE);
            cat = category[3];
        } else if (view == btnNext){
            Intent intent = new Intent(this, CreateEventActivity.class);
            intent.putExtra("category", cat);
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

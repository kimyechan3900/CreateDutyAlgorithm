package org.techtown.dutyfornurse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main_portrait);
        }else{
            setContentView(R.layout.activity_main_landscape);
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        Button button=(Button)findViewById(R.id.RequireButton);

        button.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view){
               Intent intent = new Intent(getApplicationContext(),RequireMent.class);// RequireButton 버튼 클릭시 RequireMent창으로 이동
               startActivity(intent);
           }
        });

        Button button2=(Button)findViewById(R.id.TutorialButton);

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(getApplicationContext(),TutorialLayout.class);//TutorialButton 버튼 클릭시 TutorialLayout창으로 이동
                startActivity(intent);
            }
        });

        Button button3=(Button)findViewById(R.id.SaveButton);

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent= new Intent(getApplicationContext(),DutyList.class);//TutorialButton 버튼 클릭시 TutorialLayout창으로 이동
                startActivity(intent);
            }
        });
    }
}
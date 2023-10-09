package org.techtown.dutyfornurse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.w3c.dom.Text;

import java.time.LocalDate;

public class DutyList extends AppCompatActivity {
    SQLiteDatabase db;
    int db_size;

    String date[];
    int nurse_count[];
    int day_work[];
    int veteran[];
    int begginer[];
    int year[];
    int month[];
    int day[];
    String nurse_name[];
    String duty[];


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_list);
        Cursor cursor;
        db=openOrCreateDatabase("nurse",MODE_PRIVATE,null);
        try {
            cursor = db.rawQuery("SELECT * FROM nurse", null);
            db_size=cursor.getCount();
            date=new String[db_size];
            nurse_count=new int [db_size];
            day_work=new int [db_size];
            veteran=new int[db_size];
            begginer=new int[db_size];
            year=new int[db_size];
            month=new int[db_size];
            day=new int[db_size];
            nurse_name=new String[db_size];
            duty=new String[db_size];
            final TableLayout tableLayout =(TableLayout) findViewById(R.id.table);
            //blank.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            cursor.moveToNext();

            for(int i=0;i<db_size;i++){
                date[i]= (cursor.getString(cursor.getColumnIndex("person_id")));
                nurse_count[i]= (cursor.getInt(cursor.getColumnIndex("nurse_count")));
                day_work[i]= (cursor.getInt(cursor.getColumnIndex("day_work")));
                veteran[i]= (cursor.getInt(cursor.getColumnIndex("veteran")));
                begginer[i]= (cursor.getInt(cursor.getColumnIndex("begginer")));
                year[i]= (cursor.getInt(cursor.getColumnIndex("year")));
                month[i]= (cursor.getInt(cursor.getColumnIndex("month")));
                day[i]= (cursor.getInt(cursor.getColumnIndex("day")));
                nurse_name[i]= (cursor.getString(cursor.getColumnIndex("nurse_name")));
                duty[i]=(cursor.getString(cursor.getColumnIndex("duty")));
                final TableRow tableRow=new TableRow(this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                tableRow.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_list));
                tableRow.setId(i);
                final TextView text=new TextView(this);
                System.out.println(month[i]);
                text.setText("  "+date[i]+"  ("+year[i]+"년"+month[i]+"월)");
                text.setTextSize(15);
                text.setGravity(Gravity.CENTER);
                tableRow.addView(text);

                final Button button1=new Button(this);
                button1.setId((i*2)+0);
                button1.setText("보기");
                button1.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_button));
                TableRow.LayoutParams param_1=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                param_1.rightMargin=30;
                param_1.width=70;
                param_1.height=100;
                button1.setLayoutParams(param_1);
                tableRow.addView(button1);
                button1.setOnClickListener(new View.OnClickListener(){//듀티 보기 버튼 클릭시
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View view) {
                        int data=button1.getId()/2;//sql 배열 키값;
                        Duty duty_p=new Duty(nurse_count[data],day_work[data],veteran[data],begginer[data],day[data]);
                        String nurse_name_p[]=new String[nurse_count[data]];

                        int count=0;
                        StringBuilder a=new StringBuilder(); //간호사 정보
                        for(int j=0;j<nurse_name[data].length();j++){
                             if(nurse_name[data].charAt(j)=='@'){
                                 nurse_name_p[count++]=a.toString();
                                 a.delete(0,a.length());
                             }
                             else
                                 a.append(nurse_name[data].charAt(j));
                        }

                        int height=0;
                        int width=0;
                        for(int j=0;j<duty[data].length();j++){  //듀티 정보
                            if(duty[data].charAt(j)=='@'){
                                duty_p.setTimetable(height,width++,a.charAt(0));
                                if(width>=nurse_count[data]){
                                    width=0;
                                    height++;
                                }
                                a.deleteCharAt(0);
                            }
                            else{
                                a.append(duty[data].charAt(j));
                            }
                        }
                        Intent intent = new Intent(getApplicationContext(), DutyLayout_List.class);//DutyWaiting 인텐트시작
                        intent.putExtra("nurse_name",nurse_name_p);
                        intent.putExtra("Duty", duty_p);
                        intent.putExtra("Year",year[data]);
                        intent.putExtra("Month",month[data]);
                        intent.putExtra("Day",day[data]);
                        startActivity(intent);

                    }
                });

                final Button button2=new Button(this);
                button2.setId((i*2)+1);
                button2.setText("삭제");
                button2.setBackgroundDrawable(getResources().getDrawable(R.drawable.list_button));
                TableRow.LayoutParams param_2=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
                param_2.rightMargin=30;
                param_2.width=70;
                param_2.height=100;
                button2.setLayoutParams(param_2);
                tableRow.addView(button2);
                button2.setOnClickListener(new View.OnClickListener(){//듀티 삭제 버튼 클릭시
                    @SuppressLint("ResourceType")
                    @Override
                    public void onClick(View view) {
                        int data=button1.getId()/2;
                        AlertDialog.Builder d=new AlertDialog.Builder(DutyList.this);

                        d.setTitle("정말 삭제하시겠습니까?");
                        d.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.execSQL("DELETE FROM nurse WHERE person_id= '"+date[data]+"';");
                                Intent intent = getIntent();
                                finish();
                                finish();
                                startActivity(intent);
                            }
                        });
                        d.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        d.show();
                    }
                });

                tableLayout.addView(tableRow);
                cursor.moveToNext();
            }

        } catch(Exception e){
            AlertDialog.Builder d=new AlertDialog.Builder(DutyList.this);

            d.setTitle("저장되어 있는 듀티가 없습니다.");
            d.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            d.show();
            e.printStackTrace();

        }

    }
}
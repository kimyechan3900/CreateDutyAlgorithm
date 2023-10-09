package org.techtown.dutyfornurse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.Calendar;

public class JobApplication extends AppCompatActivity {

    /*SQLiteDatabase db;
    StringBuilder d_dutyBuilder=new StringBuilder();
    StringBuilder v_dutyBuilder=new StringBuilder();
    StringBuilder b_dutyBuilder=new StringBuilder();
    StringBuilder nurseBuilder=new StringBuilder();
    LocalDate now=LocalDate.now();*/


    Duty duty;
    Duty save_duty;
    int num;
    int veteran;
    int beginner;
    int daywork;

    int v_day;
    int v_evening;
    int v_night;

    int b_day;
    int b_evening;
    int b_night;

    int min_off;
    int year;
    int month;
    int day;

    String nurse_name[];

    boolean option1;
    boolean option2;
    boolean option3;
    boolean option4;
    boolean option5;
    boolean option6;


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {//옵션 메뉴
        getMenuInflater().inflate(R.menu.menu_option2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        if (curId == R.id.action_menu_list) {//듀티 리스트 버튼
            AlertDialog.Builder dlg = new AlertDialog.Builder(JobApplication.this);
            dlg.setTitle("최근 근무신청");

            Cursor c;
            c = db.rawQuery("SELECT * FROM nurse ORDER BY person_id LIMIT 5",null);
            while(c.moveToNext()){

            }
            final String[] datetime=new String[5];

            dlg.setSingleChoiceItems(datetime, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
                }
            });
//                버튼 클릭시 동작
            dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    //토스트 메시지
                    Toast.makeText(MainActivity.this,"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_application);
        ScrollView scrollView=(ScrollView) findViewById(R.id.scrollView2);
        /*db=openOrCreateDatabase("nurse",MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS nurse (person_id DATETIME,nurse_name TEXT," +
                "d_duty TEXT,v_duty TEXT,b_duty TEXT)");*/




        Intent intent = getIntent();

        veteran = intent.getIntExtra("Veteran", 1);
        beginner = intent.getIntExtra("Beginner", 1);
        daywork=intent.getIntExtra("Daywork",1);

        v_day = intent.getIntExtra("v_Day", 1);
        v_evening = intent.getIntExtra("v_Evening", 1);
        v_night = intent.getIntExtra("v_Night", 1);

        b_day = intent.getIntExtra("b_Day", 1);
        b_evening = intent.getIntExtra("b_Evening", 1);
        b_night = intent.getIntExtra("b_Night", 1);

        min_off = intent.getIntExtra("Min_off", 1);
        year=intent.getIntExtra("Year",1);
        month = intent.getIntExtra("Month", 1);
        int save_year=year;
        int save_month=month;

        num =veteran + beginner;

        option1 = intent.getBooleanExtra("option1", true);
        option2 = intent.getBooleanExtra("option2", true);
        option3 = intent.getBooleanExtra("option3", true);
        option4 = intent.getBooleanExtra("option4", true);
        option5 = intent.getBooleanExtra("option5", true);//전 인텐트에서 전달된 값 가져오기
        option6 = intent.getBooleanExtra("option6", true);

        Calendar cal= Calendar.getInstance();//해당 달계산
        Calendar next_cal= Calendar.getInstance();
        cal.set(year,month-1,1);
        next_cal.set(year,month,1);
        long diffsec=(next_cal.getTimeInMillis()-cal.getTimeInMillis())/1000;
        long diffdays=diffsec/(24*60*60);
        day=(int)diffdays;//한달 일수

        System.out.println("두 날짜간의 일 수차 ="+(int)diffdays+"일");
        duty = new Duty(num+daywork,daywork, veteran, beginner, (int)diffdays);
        save_duty=new Duty(num+daywork,daywork, veteran, beginner, (int)diffdays);

        nurse_name=new String[duty.timetable[0].length];










        int[] one_day_count = new int[4];//전체인원 하루 근무 저장배열

        final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

        final TableRow tableRow_nurse = new TableRow(this);
        final TextView blank = new TextView(this);
        tableRow_nurse.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        blank.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        blank.setText("      ");
        tableRow_nurse.setId(0);
        tableRow_nurse.addView(blank);


        for (int i = 0; i < duty.timetable[0].length; i++) {//첫째줄,간호사인원줄
            int n=i+1;
            EditText text = new EditText (this);
            text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text.setText("간호사"+n);
            text.setId(i+1);
            if(i<duty.timetable[0].length)
                text.setTextColor(Color.parseColor("#147814"));
            if(i<duty.v_table[0].length+daywork)
                text.setTextColor(Color.parseColor("#0064ff"));
            if(i<daywork)
                text.setTextColor(Color.parseColor("#ff0000"));
            text.setWidth(200);
            text.setHeight(110);
            text.setGravity(Gravity.CENTER);
            text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
            tableRow_nurse.addView(text);
        }
        tableLayout.addView(tableRow_nurse);



        for (int i = 0; i < day; i++) {// 한달 일수 반복
            for (int j = 0; j < 4; j++)
                one_day_count[j] = 0;

            final TableRow tableRow = new TableRow(getApplicationContext());
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setId((i+1));
            final TextView textday = new TextView(this);
            textday.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            textday.setText( (i + 1) + "일");//해당 줄의 일수 출력
            if(cal.get(cal.DAY_OF_WEEK)==7)//
                textday.setTextColor(Color.parseColor("#0064ff"));//토요일=파랑
            if(cal.get(cal.DAY_OF_WEEK)==1)
                textday.setTextColor(Color.parseColor("#ff0000"));//일요일=빨강
            textday.setWidth(100);
            textday.setHeight(105);
            textday.setGravity(Gravity.CENTER);
            textday.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
            tableRow.addView(textday);


           for (int j = 0; j < duty.timetable[0].length; j++) {//간호사 인원만큼 해당 일의 듀티 생성
                final EditText editText=new EditText(getApplicationContext());
                editText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                editText.setId(j+1);
                editText.setWidth(100);
                editText.setHeight(105);
                editText.setTextSize(16);
                editText.setGravity(Gravity.CENTER);
                editText.setBackground(getResources().getDrawable(R.drawable.textview_layout3));
                editText.setText(null);
                if(j<daywork) {
                    if(cal.get(cal.DAY_OF_WEEK)==7 || cal.get(cal.DAY_OF_WEEK)==1)
                        editText.setText("O");
                    else
                        editText.setText("D");
                }
                tableRow.addView(editText);
            }
            tableLayout.addView(tableRow);
            cal.add(cal.DATE,1);//하루 증가

        }
        final Button button=new Button(this);//듀티 생성 버튼
        TableLayout.LayoutParams lp=new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(20,20,20,20);
        button.setLayoutParams(lp);
        button.setText("듀티 생성");
        button.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_button));
        button.setWidth(300);
        button.setTextColor(getColor(R.color.white));
        button.setTextSize(25);
        tableLayout.addView(button);


        scrollView.invalidate();
        scrollView.requestLayout();



        button.setOnClickListener(new View.OnClickListener(){//듀티 생성 버튼 클릭시
            @Override
            public void onClick(View view){
               // db.execSQL("INSERT INTO nurse (person_id,nurse_name,d_duty,v_duty,b_duty) VALUES("+now+",'"+nurseBuilder.toString()+"','"+d_dutyBuilder.toString()+"','"+v_dutyBuilder.toString()+"','"+b_dutyBuilder.toString()+")");

                int day_count=0;
                int evening_count=0;
                int night_count=0;

                int work_error=0;
                int limit_error=0;
                int count_error=0;
                Intent intent = new Intent(getApplicationContext(),DutyWaiting.class);//DutyWaiting 인탠트
                intent.putExtra("Veteran", veteran);
                intent.putExtra("Beginner", beginner);
                intent.putExtra("Daywork",daywork);
                intent.putExtra("v_Day",v_day);
                intent.putExtra("v_Evening", v_evening);
                intent.putExtra("v_Night", v_night);
                intent.putExtra("b_Day",b_day);
                intent.putExtra("b_Evening", b_evening);
                intent.putExtra("b_Night", b_night);
                intent.putExtra("Min_off",min_off);
                intent.putExtra("Year",save_year);
                intent.putExtra("Month",save_month);
                intent.putExtra("Day",(int)diffdays);
                intent.putExtra("option1",option1);
                intent.putExtra("option2",option2);
                intent.putExtra("option3",option3);
                intent.putExtra("option4",option4);
                intent.putExtra("option5",option5);
                intent.putExtra("option6",option6);


                TableRow nurseTableRow=(TableRow) tableLayout.getChildAt(0);
                for (int i = 0; i < duty.timetable[0].length; i++) {
                    EditText editText=(EditText) nurseTableRow.getChildAt(i+1);
                    nurse_name[i]=editText.getText().toString();
                    //nurseBuilder.append(nurse_name[i]+"`");
                }
                intent.putExtra("nurse_name",nurse_name);

                for(int i=0;i<day;i++){// 주간근무 테이블에 신청근무 저장
                    TableRow tableRow=(TableRow) tableLayout.getChildAt(i+1);
                    for(int j=0;j<duty.d_table[0].length;j++){
                        EditText editText=(EditText) tableRow.getChildAt(j+1);
                        if(editText.length()!=0) {
                            char work = (editText.getText().toString()).charAt(0);
                            //d_dutyBuilder.append(work+"`");
                            if(editText.length()!=1)
                                work_error++;//
                            else if( work=='D' || work=='E' || work=='N' || work=='O') {
                                duty.d_table[i][j] = work;//D,E,N,O로 입력된 신청근무만 저장
                                save_duty.d_table[i][j]=work;
                            }
                            else
                                work_error++;
                        }
                        else{
                           // d_dutyBuilder.append(" "+"`");
                        }
                    }
                }

                for(int i=0;i<day;i++){//숙련자 테이블에 신청근무 저장
                    day_count=0;
                    evening_count=0;
                    night_count=0;
                    TableRow tableRow=(TableRow) tableLayout.getChildAt(i+1);
                    for(int j=0;j<duty.v_table[0].length;j++){
                        EditText editText=(EditText) tableRow.getChildAt(j+1+duty.d_table[0].length);
                        if(editText.length()!=0) {
                            char work = (editText.getText().toString()).charAt(0);
                           // v_dutyBuilder.append(work+"`");
                            if(editText.length()!=1)
                                work_error++;
                            else if(work=='D' || work=='E' || work=='N' || work=='O') {
                                duty.v_table[i][j] = work;//D,E,N,O로 입력된 신청근무만 저장
                                save_duty.v_table[i][j]=work;
                                if(work=='D')
                                    day_count++;
                                else if(work=='E')
                                    evening_count++;
                                else if(work=='N')
                                    night_count++;
                            }
                            else
                                work_error++;
                        }
                        else{
                           // v_dutyBuilder.append(" "+"`");
                        }
                    }
                    if(day_count>v_day) {
                        Toast.makeText(JobApplication.this, (i + 1) + "일 숙련자 day가 너무 많습니다.", Toast.LENGTH_SHORT).show();
                        count_error++;
                    }
                    if(evening_count>v_evening) {
                        Toast.makeText(JobApplication.this, (i + 1) + "일 숙련자 evening이 너무 많습니다.", Toast.LENGTH_SHORT).show();
                        count_error++;
                    }
                    if(night_count>v_night) {
                        Toast.makeText(JobApplication.this, (i + 1) + "일 숙련자 night가 너무 많습니다.", Toast.LENGTH_SHORT).show();
                        count_error++;
                    }
                }

                for(int i=0;i<day;i++){//비숙련자 테이블에 신청근무 저장
                    day_count=0;
                    evening_count=0;
                    night_count=0;
                    TableRow tableRow=(TableRow) tableLayout.getChildAt(i+1);
                    for(int j=0;j<duty.b_table[0].length;j++){
                        EditText editText=(EditText) tableRow.getChildAt(j+1+duty.d_table[0].length+duty.v_table[0].length);
                        if(editText.length()!=0) {
                            char work = (editText.getText().toString()).charAt(0);
                            //b_dutyBuilder.append(work+"`");
                            if(editText.length()!=1)
                                work_error++;
                            else if(work=='D' || work=='E' || work=='N' || work=='O') {
                                duty.b_table[i][j] = work;//D,E,N,O로 입력된 신청근무만 저장
                                save_duty.b_table[i][j]=work;
                                if(work=='D')
                                    day_count++;
                                else if(work=='E')
                                    evening_count++;
                                else if(work=='N')
                                    night_count++;
                            }
                            else
                                work_error++;
                        }
                        else{
                          //  b_dutyBuilder.append(" "+"`");
                        }
                    }
                    if(day_count>b_day) {
                        Toast.makeText(JobApplication.this, (i + 1) + "일 비숙련자 day가 너무 많습니다.", Toast.LENGTH_SHORT).show();
                        count_error++;
                    }
                    if(evening_count>b_evening) {
                        Toast.makeText(JobApplication.this, (i + 1) + "일 비숙련자 evening이 너무 많습니다.", Toast.LENGTH_SHORT).show();
                        count_error++;
                    }
                    if(night_count>b_night) {
                        Toast.makeText(JobApplication.this, (i + 1) + "일 비숙련자 night가 너무 많습니다.", Toast.LENGTH_SHORT).show();
                        count_error++;
                    }
                }

                if(work_error==0 && limit_error==0 && count_error==0) {//오류가 없을시  DUTY인텐트 전달
                    intent.putExtra("Duty", duty);
                    intent.putExtra("Save_Duty",save_duty);
                    startActivity(intent);//DutyWaiting 액티비티 시작
                }
                else if(work_error!=0){
                    Toast.makeText(JobApplication.this, "D,E,N,O 근무만 넣어주세요(대문자)", Toast.LENGTH_SHORT).show();
                }
                else if(limit_error != 0){
                    Toast.makeText(JobApplication.this, "하루 D,E,N,O 제한 칸은 다 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}

package org.techtown.dutyfornurse;

import static java.lang.Thread.sleep;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class RequireMent extends AppCompatActivity {
    ProgressDialog dialog;

    EditText veteran;
    EditText beginner;
    EditText daywork;

    EditText v_day;
    EditText v_evening;
    EditText v_night;

    EditText b_day;
    EditText b_evening;
    EditText b_night;
    Spinner min_off;

    TextView all_person_text;
    TextView all_day_text;
    TextView all_evening_text;
    TextView all_night_text;


    CheckBox Box1;
    CheckBox Box2;
    CheckBox Box3;
    CheckBox Box4;
    CheckBox Box5;
    CheckBox Box6;

    String[] items={"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
    String[] year_items={"2018","2019","2020","2021","2022","2023","2024","2025","2026","2027","2028","2029","2030","2031","2032","2033","2034","2035","2036","2037","2038","2039","2040","2041","2042","2043","2044","2045","2046","2047","2048","2049","2050"};
    String[] month_items={"1","2","3","4","5","6","7","8","9","10","11","12"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.requirement_portrait);
        }else{
            setContentView(R.layout.requirement_landscape);
        }

        veteran=findViewById(R.id.veteran_edit);
        beginner=findViewById(R.id.beginner_edit);
        daywork=findViewById(R.id.daywork_edit);
        v_day=findViewById(R.id.v_day_edit);
        v_evening=findViewById(R.id.v_evening_edit);
        v_night=findViewById(R.id.v_night_edit);
        b_day=findViewById(R.id.b_day_edit);
        b_evening=findViewById(R.id.b_evening_edit);
        b_night=findViewById(R.id.b_night_edit);
        min_off=findViewById(R.id.spinner);
        Box1=(CheckBox)findViewById(R.id.option1);
        Box2=(CheckBox)findViewById(R.id.option2);
        Box3=(CheckBox)findViewById(R.id.option3);
        Box4=(CheckBox)findViewById(R.id.option4);
        Box5=(CheckBox)findViewById(R.id.option5);
        Box6=(CheckBox)findViewById(R.id.option6);
        all_person_text=findViewById(R.id.all_person_text);
        all_day_text=findViewById(R.id.all_day_text);
        all_evening_text=findViewById(R.id.all_evening_text);
        all_night_text=findViewById(R.id.all_night_text);

        veteran.addTextChangedListener(new TextWatcher() {//숙련자인원 입력창에 입력시 총인원창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int veteran_p;
                int beginner_p;
                if(veteran.getText().toString().equals(""))
                    veteran_p=0;
                else
                    veteran_p=Integer.parseInt(veteran.getText().toString());

                if(beginner.getText().toString().equals(""))
                    beginner_p=0;
                else
                    beginner_p=Integer.parseInt(beginner.getText().toString());

                all_person_text.setText(String.valueOf(veteran_p+beginner_p));//숙련자인원+비숙련자인원=총인원
                 }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        beginner.addTextChangedListener(new TextWatcher() {//비숙련자인원 입력창에 변화가 있을시 총인원창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int veteran_p;
                int beginner_p;
                if(veteran.getText().toString().equals(""))
                    veteran_p=0;
                else
                    veteran_p=Integer.parseInt(veteran.getText().toString());

                if(beginner.getText().toString().equals(""))
                    beginner_p=0;
                else
                    beginner_p=Integer.parseInt(beginner.getText().toString());

                all_person_text.setText(String.valueOf(veteran_p+beginner_p));//숙련자인원+비숙련자인원=총인원
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });



        v_day.addTextChangedListener(new TextWatcher() {//숙련자 DAY 입력창에 변화가 있을시 총 DAY창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int v_day_p;
                int b_day_p;
                if(v_day.getText().toString().equals(""))
                    v_day_p=0;
                else
                    v_day_p=Integer.parseInt(v_day.getText().toString());

                if(b_day.getText().toString().equals(""))
                    b_day_p=0;
                else
                    b_day_p=Integer.parseInt(b_day.getText().toString());


                all_day_text.setText(String.valueOf(v_day_p+b_day_p));//숙련자DAY+비숙련자DAY=총DAY
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        b_day.addTextChangedListener(new TextWatcher() { //비숙련자 DAY 입력창에 변화가 있을시 총 DAY창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int v_day_p;
                int b_day_p;
                if(v_day.getText().toString().equals(""))
                    v_day_p=0;
                else
                    v_day_p=Integer.parseInt(v_day.getText().toString());

                if(b_day.getText().toString().equals(""))
                    b_day_p=0;
                else
                    b_day_p=Integer.parseInt(b_day.getText().toString());


                all_day_text.setText(String.valueOf(v_day_p+b_day_p));//숙련자DAY+비숙련자DAY=총DAY
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });


        v_evening.addTextChangedListener(new TextWatcher() {//숙련자 EVENING 입력창에 변화가 있을시 총 EVENING창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int v_evening_p;
                int b_evening_p;
                if (v_evening.getText().toString().equals(""))
                    v_evening_p = 0;
                else
                    v_evening_p = Integer.parseInt(v_evening.getText().toString());

                if (b_evening.getText().toString().equals(""))
                    b_evening_p = 0;
                else
                    b_evening_p = Integer.parseInt(b_evening.getText().toString());
                all_evening_text.setText(String.valueOf(v_evening_p + b_evening_p));//숙련자EVENING+비숙련자EVENING=총EVENING
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        b_evening.addTextChangedListener(new TextWatcher() {//비숙련자 EVENING 입력창에 변화가 있을시 총 EVENING창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int v_evening_p;
                int b_evening_p;
                if(v_evening.getText().toString().equals(""))
                    v_evening_p=0;
                else
                    v_evening_p=Integer.parseInt(v_evening.getText().toString());

                if(b_evening.getText().toString().equals(""))
                    b_evening_p=0;
                else
                    b_evening_p=Integer.parseInt(b_evening.getText().toString());
                all_evening_text.setText(String.valueOf(v_evening_p+b_evening_p));//숙련자EVENING+비숙련자EVENING=총EVENING
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        v_night.addTextChangedListener(new TextWatcher() {//숙련자 NIGHT 입력창에 변화가 있을시 총 NIGHT창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int v_night_p;
                int b_night_p;
                if(v_night.getText().toString().equals(""))
                    v_night_p=0;
                else
                    v_night_p=Integer.parseInt(v_night.getText().toString());

                if(b_night.getText().toString().equals(""))
                    b_night_p=0;
                else
                    b_night_p=Integer.parseInt(b_night.getText().toString());
                all_night_text.setText(String.valueOf(v_night_p+b_night_p));//숙련자NIGHT+비숙련자NIGHT=총NIGHT
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        b_night.addTextChangedListener(new TextWatcher() {//숙련자 NIGHT 입력창에 변화가 있을시 총 NIGHT창에 바로 업데이트한다.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable arg0) {
                int v_night_p;
                int b_night_p;
                if(v_night.getText().toString().equals(""))
                    v_night_p=0;
                else
                    v_night_p=Integer.parseInt(v_night.getText().toString());

                if(b_night.getText().toString().equals(""))
                    b_night_p=0;
                else
                    b_night_p=Integer.parseInt(b_night.getText().toString());
                all_night_text.setText(String.valueOf(v_night_p+b_night_p));//숙련자NIGHT+비숙련자NIGHT=총NIGHT
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });

        Spinner min_off_spinner=findViewById(R.id.spinner);//최소 OFF 스피너
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        min_off_spinner.setAdapter(adapter);


        Spinner year_spinner=findViewById(R.id.year_spinner);//듀티년도  스피너
        ArrayAdapter<String>year_adapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,year_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_spinner.setAdapter(year_adapter);


        Spinner month_spinner=findViewById(R.id.month_spinner);//듀티 달 스피너
        ArrayAdapter<String>month_adapter=new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_item,month_items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month_spinner.setAdapter(month_adapter);


        Button button=(Button)findViewById(R.id.create_Button);

        button.setOnClickListener(new View.OnClickListener(){//근무 신청버튼
            @Override
            public void onClick(View view) {
                boolean null_error = false;
                if (veteran.getText().toString().length()==0 || beginner.getText().toString().length()==0 || daywork.getText().toString().length()==0 || v_day.getText().toString().length()==0 || v_evening.getText().toString().length()==0 || v_night.getText().toString().length()==0 || b_day.getText().toString().length()==0 || b_evening.getText().toString().length()==0 || b_night.getText().toString().length()==0) {
                    null_error = true;
                    Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                }
                if (null_error == false) {
                    int Veteran = Integer.parseInt(veteran.getText().toString());
                    int Beginner = Integer.parseInt(beginner.getText().toString());
                    int Daywork = Integer.parseInt(daywork.getText().toString());
                    int v_Day = Integer.parseInt(v_day.getText().toString());
                    int v_Evening = Integer.parseInt(v_evening.getText().toString());
                    int v_Night = Integer.parseInt(v_night.getText().toString());
                    int b_Day = Integer.parseInt(b_day.getText().toString());
                    int b_Evening = Integer.parseInt(b_evening.getText().toString());
                    int b_Night = Integer.parseInt(b_night.getText().toString());
                    int Min_off = Integer.parseInt(min_off_spinner.getSelectedItem().toString());
                    int Year = Integer.parseInt(year_spinner.getSelectedItem().toString());
                    int Month = Integer.parseInt(month_spinner.getSelectedItem().toString());
                    boolean Veteran_error = false;
                    boolean Beginner_error = false;
                    boolean Off_error = false;

                    int one_day_off = Veteran + Beginner - v_Day - v_Evening - v_Night - b_Day - b_Evening - b_Night;


                    Calendar cal = Calendar.getInstance();//해당 달 계산
                    Calendar next_cal = Calendar.getInstance();
                    cal.set(Year, Month - 1, 1);
                    next_cal.set(Year, Month, 1);
                    long diffsec = (next_cal.getTimeInMillis() - cal.getTimeInMillis()) / 1000;
                    long diffdays = diffsec / (24 * 60 * 60);
                    int day = (int) diffdays;//한달 일수


                    if ((double) Veteran * (0.7) < (double) (v_Day + v_Evening + v_Night)) {//숙련자의 하루 오프가 30퍼 이하->오류
                        Veteran_error = true;
                        Toast.makeText(RequireMent.this, "숙련자 일일 근무량이 너무 많습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if ((double) Beginner * (0.7) < (double) (b_Day + b_Evening + b_Night)) {//비숙련자 하루 오프가 30퍼 이하->오류
                        Beginner_error = true;
                        Toast.makeText(RequireMent.this, "비숙련자 일일 근무량이 너무 많습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (one_day_off * day < Min_off * (Veteran + Beginner)) {//요구 OFF > 전체 OFF ->오류
                        Off_error = true;
                        Toast.makeText(RequireMent.this, "최소 off수가 너무 높습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (veteran.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (beginner.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (daywork.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (v_day.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (v_evening.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (v_night.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (b_day.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (b_evening.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }
                    if (b_night.getText().toString() == null) {
                        null_error = true;
                        Toast.makeText(RequireMent.this, "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show();//토스트메세지 생성
                    }

                    if (Veteran_error == false && Beginner_error == false && Off_error == false) {// 오류가 없을시 JobApplication 인텐트
                        Intent intent = new Intent(getApplicationContext(), JobApplication.class);
                        if (Box1.isChecked()) {
                            intent.putExtra("option1", true);
                        } else
                            intent.putExtra("option1", false);

                        if (Box2.isChecked()) {
                            intent.putExtra("option2", true);
                        } else
                            intent.putExtra("option2", false);

                        if (Box3.isChecked()) {
                            intent.putExtra("option3", true);
                        } else
                            intent.putExtra("option3", false);

                        if (Box4.isChecked()) {
                            intent.putExtra("option4", true);
                        } else
                            intent.putExtra("option4", false);

                        if (Box5.isChecked()) {
                            intent.putExtra("option5", true);
                        } else
                            intent.putExtra("option5", false);

                        if (Box6.isChecked()) {
                            intent.putExtra("option6", true);
                        } else
                            intent.putExtra("option6", false);


                        intent.putExtra("Veteran", Veteran);
                        intent.putExtra("Beginner", Beginner);
                        intent.putExtra("Daywork", Daywork);
                        intent.putExtra("v_Day", v_Day);
                        intent.putExtra("v_Evening", v_Evening);
                        intent.putExtra("v_Night", v_Night);
                        intent.putExtra("b_Day", b_Day);
                        intent.putExtra("b_Evening", b_Evening);
                        intent.putExtra("b_Night", b_Night);
                        intent.putExtra("Min_off", Min_off);
                        intent.putExtra("Year", Year);
                        intent.putExtra("Month", Month);
                        startActivity(intent);//JobApplication 액티비티 시작
                    }
                }
            }
        });
    }
}

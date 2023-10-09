package org.techtown.dutyfornurse;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class DutyWaiting extends AppCompatActivity {
    ProgressDialog dialog;

    Duty duty;
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

    boolean option1;
    boolean option2;
    boolean option3;
    boolean option4;
    boolean option5;
    boolean option6;

    boolean duty_cancle=false;
    String nurse_name[];

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";
    Duty save_duty;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_layout);

        Intent intent = getIntent();

        daywork=intent.getIntExtra("Daywork",1);
        veteran = intent.getIntExtra("Veteran", 1);
        beginner = intent.getIntExtra("Beginner", 1);

        v_day = intent.getIntExtra("v_Day", 1);
        v_evening = intent.getIntExtra("v_Evening", 1);
        v_night = intent.getIntExtra("v_Night", 1);

        b_day = intent.getIntExtra("b_Day", 1);
        b_evening = intent.getIntExtra("b_Evening", 1);
        b_night = intent.getIntExtra("b_Night", 1);


        min_off = intent.getIntExtra("Min_off", 1);
        year=intent.getIntExtra("Year",1);
        month = intent.getIntExtra("Month", 1);
        day=intent.getIntExtra("Day",1);
        int save_year=year;
        int save_month=month;
        num = veteran + beginner;

        option1 = intent.getBooleanExtra("option1", true);
        option2 = intent.getBooleanExtra("option2", true);
        option3 = intent.getBooleanExtra("option3", true);
        option4 = intent.getBooleanExtra("option4", true);
        option5 = intent.getBooleanExtra("option5", true);
        option6 = intent.getBooleanExtra("option6", true);

        duty =(Duty)intent.getSerializableExtra("Duty");
        save_duty=(Duty)intent.getSerializableExtra("Save_Duty");

        nurse_name=intent.getStringArrayExtra("nurse_name");

        int[][] count=new int[duty.timetable[0].length][4];// D->E->N->E 순으로 저장




        dialog = new ProgressDialog(DutyWaiting.this);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("듀티를 제작 중입니다......");
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "취소",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        duty_cancle=true;
                        duty.error_count=50000000;
                    }
                });
        dialog.show();//프래그먼트 다이얼로그 표시 ,듀티짜는동안 다이얼로그 계촉노출



        Thread thread = new Thread(new Runnable() {//보조 스레드 생성
            public void run() {
                duty_cancle=false;
                duty.DutyMake(v_day, v_evening, v_night, b_day, b_evening, b_night, min_off, option1, option2, option3, option4,option5,option6,save_duty);//듀티 생성함수
                if (duty.error_count >= 50000000 ) {//5000000번 이상 에러체크됐을시 중지;(대략 최대 1시간 계산)
                    DutyWaiting.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if(duty_cancle==true)//중간에 취소버튼 누르면 취소
                                Toast.makeText(DutyWaiting.this, "듀티 제작을 취소했습니다.", Toast.LENGTH_LONG).show();//토스트메세지 생성
                            else//에러체크 취소
                                Toast.makeText(DutyWaiting.this, "조건에 맞는 듀티가 없습니다.", Toast.LENGTH_LONG).show();//토스트메세지 생성
                            showError();
                        }
                    });
                    dialog.dismiss();
                    finish();
                    return;
                }
                else {// 듀티가 완성이 됐을시
                    dialog.dismiss();
                    finish();
                    showSucess();
                    Intent intent = new Intent(getApplicationContext(), DutyLayout.class);//DutyLayout 인텐트 생성
                    intent.putExtra("Duty", duty);
                    intent.putExtra("Save_Duty",save_duty);
                    intent.putExtra("Daywork",daywork);
                    intent.putExtra("Veteran", veteran);
                    intent.putExtra("Beginner", beginner);
                    intent.putExtra("v_Day",v_day);
                    intent.putExtra("v_Evening", v_evening);
                    intent.putExtra("v_Night", v_night);
                    intent.putExtra("b_Day",b_day);
                    intent.putExtra("b_Evening", b_evening);
                    intent.putExtra("b_Night", b_night);
                    intent.putExtra("Min_off",min_off);
                    intent.putExtra("Year",year);
                    intent.putExtra("Month",month);
                    intent.putExtra("Day",day);
                    intent.putExtra("option1",option1);
                    intent.putExtra("option2",option2);
                    intent.putExtra("option3",option3);
                    intent.putExtra("option4",option4);
                    intent.putExtra("option5",option5);
                    intent.putExtra("option6",option6);
                    intent.putExtra("nurse_name",nurse_name);
                    startActivity(intent);//DutyLayout 액티비티 시작
                }
            }
        });

        thread.start();
        showWating();



    }

    public void showWating(){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        }else{
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent=new Intent(this,DutyWaiting.class);
        PendingIntent mPendingIntent=PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_IMMUTABLE);

        builder.setContentTitle("듀티를 제작 중입니다......");
        builder.setContentText("최대 50분 소요됩니다.");
        builder.setSmallIcon(R.drawable.duty_helper);
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        builder.setContentIntent(mPendingIntent);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);



        Notification notification = builder.build();
        manager.notify(1,notification);
    }

    public void showError(){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        }else{
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent=new Intent(this,JobApplication.class);
        PendingIntent mPendingIntent=PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_IMMUTABLE);

        builder.setContentTitle("듀티 제작실패");
        builder.setContentText("조건에 맞는 듀티가 없습니다.");
        builder.setSmallIcon(R.drawable.duty_helper);
        builder.setAutoCancel(true);
        builder.setContentIntent(mPendingIntent);



        Notification notification = builder.build();
        manager.notify(1,notification);
    }

    public void showSucess(){
        try {
            builder = null;
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                manager.createNotificationChannel(
                        new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
                );
                builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            } else {
                builder = new NotificationCompat.Builder(this);
            }

            Intent intent = new Intent(this, DutyLayout.class);
            intent.putExtra("Duty", duty);
            intent.putExtra("Save_Duty",save_duty);
            intent.putExtra("Daywork",daywork);
            intent.putExtra("Veteran", veteran);
            intent.putExtra("Beginner", beginner);
            intent.putExtra("v_Day",v_day);
            intent.putExtra("v_Evening", v_evening);
            intent.putExtra("v_Night", v_night);
            intent.putExtra("b_Day",b_day);
            intent.putExtra("b_Evening", b_evening);
            intent.putExtra("b_Night", b_night);
            intent.putExtra("Min_off",min_off);
            intent.putExtra("Year",year);
            intent.putExtra("Month",month);
            intent.putExtra("Day",day);
            intent.putExtra("option1",option1);
            intent.putExtra("option2",option2);
            intent.putExtra("option3",option3);
            intent.putExtra("option4",option4);
            intent.putExtra("option5",option5);
            intent.putExtra("option6",option6);
            intent.putExtra("nurse_name",nurse_name);
            PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_IMMUTABLE);

            builder.setContentTitle("듀티 제작완료");
            builder.setSmallIcon(R.drawable.duty_helper);
            builder.setAutoCancel(true);
            builder.setContentIntent(mPendingIntent);


            Notification notification = builder.build();
            manager.notify(1, notification);
        }catch(Exception e){
        }finally{
            Intent intent = new Intent(this, DutyLayout.class);
        }
    }

}

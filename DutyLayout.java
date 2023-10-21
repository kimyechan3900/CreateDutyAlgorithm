package org.techtown.dutyfornurse;

import android.app.NotificationManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class DutyLayout extends AppCompatActivity {

    SQLiteDatabase db;
    StringBuilder dutyBuilder=new StringBuilder();
    StringBuilder nurseBuilder=new StringBuilder();

    Calendar cal= Calendar.getInstance();
    Duty duty;
    Duty save_duty;

    NotificationManager manager;
    NotificationCompat.Builder builder;

    private static String CHANNEL_ID = "channel2";
    private static String CHANEL_NAME = "Channel2";


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
    int save_year;
    int save_month;
    int save_day;

    boolean option1;
    boolean option2;
    boolean option3;
    boolean option4;
    boolean option5;
    boolean option6;

    String nurse_name[];



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//옵션 메뉴
        getMenuInflater().inflate(R.menu.menu_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int curId = item.getItemId();
        if (curId == R.id.action_menu_refreshr) {//새로고침 버튼 선택
            Intent intent = new Intent(getApplicationContext(), DutyWaiting.class);//DutyWaiting 인텐트시작
            intent.putExtra("Duty", save_duty);
            intent.putExtra("Save_Duty",save_duty);
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
            intent.putExtra("Day",save_day);
            intent.putExtra("option1",option1);
            intent.putExtra("option2",option2);
            intent.putExtra("option3",option3);
            intent.putExtra("option4",option4);
            intent.putExtra("option5",option5);
            intent.putExtra("option6",option6);
            intent.putExtra("nurse_name",nurse_name);
            finish();
            startActivity(intent);// 현재 페이지 정리하고 다시 DutyWaiting시작
        }
        if (curId == R.id.action_menu_excel) {//엑셀 버튼 선택
            saveExcel(duty);//지금 듀티를 엑셀에 저장후 공유
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duty_layout); //화면 이름

        db=openOrCreateDatabase("nurse",MODE_PRIVATE,null);

        db.execSQL("CREATE TABLE IF NOT EXISTS nurse (person_id Text,nurse_count INT,day_work INT,veteran INT,begginer INT,year INT,month INT,day INT,nurse_name Text,duty Text)");


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
        day=intent.getIntExtra("Day",1);
        save_year=year;
        save_month=month;
        save_day=day;
        System.out.println(day);
        System.out.println(month);

        num = veteran + beginner;

        option1 = intent.getBooleanExtra("option1", true);
        option2 = intent.getBooleanExtra("option2", true);
        option3 = intent.getBooleanExtra("option3", true);
        option4 = intent.getBooleanExtra("option4", true);
        option5 = intent.getBooleanExtra("option5", true);
        option6 = intent.getBooleanExtra("option6",true);

        nurse_name=intent.getStringArrayExtra("nurse_name");

        duty =(Duty)intent.getSerializableExtra("Duty");
        save_duty=(Duty)intent.getSerializableExtra("Save_Duty");
        cal.set(year,month-1,1);
        System.out.println(cal.getTime());


            int[] one_day_count = new int[4];//하루 근무 저장 배열
            int[][] nurse_count = new int[duty.timetable[0].length][4];//간호사 한명 근무 저장배열
            int[] all_count=new int[4];

            final TableLayout tableLayout = (TableLayout) findViewById(R.id.table);

            final TableRow tableRow_nurse = new TableRow(this);
            final TextView blank = new TextView(this);
            tableRow_nurse.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            blank.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            blank.setText("      ");
            tableRow_nurse.addView(blank);


            for (int i = 0; i < duty.timetable[0].length; i++) {//첫째줄,간호사인원줄
                final TextView text = new TextView(this);
                text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                text.setText(nurse_name[i]);

                nurseBuilder.append(nurse_name[i]+"@");


                text.setGravity(Gravity.CENTER);
                text.setWidth(160);
                if(i<duty.timetable[0].length)
                    text.setTextColor(Color.parseColor("#147814"));
                if(i<duty.v_table[0].length+daywork)
                    text.setTextColor(Color.parseColor("#0064ff"));
                if(i<daywork)
                    text.setTextColor(Color.parseColor("#ff0000"));
                text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
                tableRow_nurse.addView(text);
            }
            final TextView text1 = new TextView(this);//Day 칸 생성
            text1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text1.setText("   D   ");
            text1.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
            text1.setGravity(Gravity.CENTER);
            tableRow_nurse.addView(text1);

            final TextView text2 = new TextView(this);//Evening 칸 생성
            text2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text2.setText("   E   ");
            text2.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
            text2.setGravity(Gravity.CENTER);
            tableRow_nurse.addView(text2);

            final TextView text3 = new TextView(this);//Night 칸 생성
            text3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text3.setText("   N   ");
            text3.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
            text3.setGravity(Gravity.CENTER);
            tableRow_nurse.addView(text3);

            final TextView text4 = new TextView(this);//Off 칸 생성
            text4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            text4.setText("   O   ");
            text4.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
            text4.setGravity(Gravity.CENTER);
            tableRow_nurse.addView(text4);
            tableLayout.addView(tableRow_nurse);




            for (int i = 0; i < day; i++) {// 한달 일수 반복

                for (int j = 0; j < 4; j++)
                    one_day_count[j] = 0;

                final TableRow tableRow = new TableRow(this);
                final TextView textday = new TextView(this);
                tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                textday.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                textday.setText(" " + (i + 1) + "일 ");//해당 줄의 일수 출력
                textday.setGravity(Gravity.CENTER);
                if(cal.get(cal.DAY_OF_WEEK)==7)
                    textday.setTextColor(Color.parseColor("#0064ff"));//토요일=파랑
                if(cal.get(cal.DAY_OF_WEEK)==1)
                    textday.setTextColor(Color.parseColor("#ff0000"));//일요일=빨강
                textday.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
                tableRow.addView(textday);
                cal.add(cal.DATE,1);

                for (int j = 0; j < duty.timetable[0].length; j++) {//간호사 인원만큼 그 날의 듀티 생성
                    final TextView text = new TextView(this);
                    text.setText(duty.timetable[i][j] + "");
                    text.setGravity(Gravity.CENTER);

                    dutyBuilder.append(duty.timetable[i][j]+"@");

                    if (duty.timetable[i][j] == 'D') {//day
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.customize_day));
                        text.setPadding(50, 0, 50, 0);
                        one_day_count[0]++;
                        nurse_count[j][0]++;
                    } else if (duty.timetable[i][j] == 'E') {//evening
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.customize_evening));
                        one_day_count[1]++;
                        nurse_count[j][1]++;
                    } else if (duty.timetable[i][j] == 'N') {//night
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.customize_night));
                        one_day_count[2]++;
                        nurse_count[j][2]++;
                    } else {//off
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.customize_off));
                        one_day_count[3]++;
                        nurse_count[j][3]++;
                    }
                    text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tableRow.addView(text);
                }
                for (int j = 0; j < 4; j++) {
                    final TextView text = new TextView(this);
                    text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    if (j == 0) {//하루 Day 수 생성
                        text.setText(" " + one_day_count[0] + " ");
                        text.setGravity(Gravity.CENTER);
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout2));
                        tableRow.addView(text);
                    } else if (j == 1) {//하루 Evening 수 생성
                        text.setText(" " + one_day_count[1] + " ");
                        text.setGravity(Gravity.CENTER);
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout2));
                        tableRow.addView(text);
                    } else if (j == 2) {//하루 Night 수 생성
                        text.setText(" " + one_day_count[2] + " ");
                        text.setGravity(Gravity.CENTER);
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout2));
                        tableRow.addView(text);
                    } else {//하루 Off 수 생성
                        text.setText(" " + one_day_count[3] + " ");
                        text.setGravity(Gravity.CENTER);
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout2));
                        tableRow.addView(text);
                    }
                }
                tableLayout.addView(tableRow);
            }

            for (int i = 0; i < 4; i++) {//듀티아래 4줄 생성
                final TableRow tableRow = new TableRow(this);
                final TextView text_shift = new TextView(this);
                if (i == 0)
                    text_shift.setText(" D ");//간호사별 D 숫자 줄 생성
                else if (i == 1)
                    text_shift.setText(" E ");//간호사별 E 숫자 줄 생성
                else if (i == 2)
                    text_shift.setText(" N ");//간호사별 N 숫자 줄 생성
                else
                    text_shift.setText(" O ");//간호사별 O 숫자 줄 생성
                text_shift.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout));
                text_shift.setGravity(Gravity.CENTER);
                tableRow.addView(text_shift);
                for (int j = 0; j < duty.timetable[0].length+4; j++) {//간호사별 듀티 개수 생성
                    final TextView text = new TextView(this);
                    if (j < duty.timetable[0].length) {
                        text.setText(nurse_count[j][i] + " ");
                        all_count[i] += nurse_count[j][i];
                    }
                    text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout2));
                    text.setGravity(Gravity.CENTER);
                    tableRow.addView(text);

                    if((i==0) && (j==duty.timetable[0].length+0)){
                        text.setText(all_count[i] + " ");
                    }
                    if((i==1) && (j==duty.timetable[0].length+1)){
                        text.setText(all_count[i] + " ");
                    }
                    if((i==2) && (j==duty.timetable[0].length+2)){
                        text.setText(all_count[i] + " ");
                    }
                    if((i==3) && (j==duty.timetable[0].length+3)){
                        text.setText(all_count[i] + " ");
                    }
                    if(j>=duty.timetable[0].length)
                        text.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_layout5));
                }
                tableLayout.addView(tableRow);
            }



    }

    private void saveExcel(Duty duty) {//엑셀 저장 함수
        int day_count = 0;
        int evening_count = 0;
        int night_count = 0;
        int off_count = 0;
        int[] day_h = new int[day];
        int[] evening_h = new int[day];
        int[] night_h = new int[day];
        int[] off_h = new int[day];
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        format1.setTimeZone(TimeZone.getTimeZone("GMT+09:00"));
        String time1 = format1.format(System.currentTimeMillis());
        System.out.println(time1);



        db.execSQL("INSERT INTO nurse (person_id ,nurse_count,day_work,veteran,begginer,year,month,day,nurse_name,duty) VALUES('"+time1+"',"+ duty.timetable[0].length+","+duty.d_table[0].length+","+duty.v_table[0].length+","+duty.b_table[0].length+","+year+","+month+","+day+",'"+nurseBuilder+"','"+dutyBuilder+"');");




        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("듀티");// 새로운 시트 생성

        Row row;
        row = sheet.createRow(0); // 새로운 행 생성


        Cell cell;

        for (int j = 0; j < day; j++) {
            cell = row.createCell(j + 1);
            cell.setCellValue((j + 1) + "일");
            cell.getCellStyle();
        }
        cell = row.createCell(day + 1);
        cell.setCellValue("D");
        cell = row.createCell(day + 2);
        cell.setCellValue("E");
        cell = row.createCell(day + 3);
        cell.setCellValue("N");
        cell = row.createCell(day + 4);
        cell.setCellValue("O");


        for (int i = 0; i < duty.timetable[0].length; i++) {
            row = sheet.createRow(i + 1);
            cell = row.createCell(0);
            cell.setCellValue(nurse_name[i]);
            day_count = 0;
            evening_count = 0;
            night_count = 0;
            off_count = 0;

            for (int j = 0; j < day; j++) {
                cell = row.createCell(j + 1);
                cell.setCellValue(Character.toString(duty.timetable[j][i]));
                if (duty.timetable[j][i] == 'D') {
                    day_count++;
                    day_h[j]++;
                } else if (duty.timetable[j][i] == 'E') {
                    evening_count++;
                    evening_h[j]++;
                } else if (duty.timetable[j][i] == 'N') {
                    night_count++;
                    night_h[j]++;
                } else {
                    off_count++;
                    off_h[j]++;
                }
            }
            cell = row.createCell(day + 1);
            cell.setCellValue(day_count);
            cell = row.createCell(day + 2);
            cell.setCellValue(evening_count);
            cell = row.createCell(day + 3);
            cell.setCellValue(night_count);
            cell = row.createCell(day + 4);
            cell.setCellValue(off_count);
        }

        row = sheet.createRow(duty.timetable[0].length + 1);
        cell = row.createCell(0);
        cell.setCellValue("D");
        for (int j = 0; j < day; j++) {
            cell = row.createCell(j + 1);
            cell.setCellValue(day_h[j]);
        }
        row = sheet.createRow(duty.timetable[0].length + 2);
        cell = row.createCell(0);
        cell.setCellValue("E");
        for (int j = 0; j < day; j++) {
            cell = row.createCell(j + 1);
            cell.setCellValue(evening_h[j]);
        }
        row = sheet.createRow(duty.timetable[0].length + 3);
        cell = row.createCell(0);
        cell.setCellValue("N");
        for (int j = 0; j < day; j++) {
            cell = row.createCell(j + 1);
            cell.setCellValue(night_h[j]);
        }
        row = sheet.createRow(duty.timetable[0].length + 4);
        cell = row.createCell(0);
        cell.setCellValue("O");
        for (int j = 0; j < day; j++) {
            cell = row.createCell(j + 1);
            cell.setCellValue(off_h[j]);
        }


        File xlsFile = new File(getExternalFilesDir(null), "test.xls");
        try {
            FileOutputStream os = new FileOutputStream(xlsFile);
            workbook.write(os); // 외부 저장소에 엑셀 파일 생성


            File extRoot = getExternalFilesDir(null);
            String someFile = "/듀티파일/some.xls";

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("application/excel");//엑셀파일 공유 시
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", xlsFile);
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent,"엑셀 공유"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), xlsFile.getAbsolutePath() + "에 저장되었습니다", Toast.LENGTH_SHORT).show();//저장 메세지

    }


   /* public void showSucess(){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID);
        }else{
            builder = new NotificationCompat.Builder(this);
        }

        Intent intent=new Intent(this,DutyLayout.class);
        PendingIntent mPendingIntent=PendingIntent.getActivity(this,0,intent,
                PendingIntent.FLAG_NO_CREATE);

        builder.setContentTitle("듀티를 제작완료");
        builder.setSmallIcon(R.drawable.duty_helper);
        builder.setAutoCancel(false);
        builder.setContentIntent(mPendingIntent);


        Notification notification = builder.build();
        manager.notify(1,notification);
    }*/
}

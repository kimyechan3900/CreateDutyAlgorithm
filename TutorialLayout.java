package org.techtown.dutyfornurse;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

public class TutorialLayout extends AppCompatActivity {

    private static final int NUM_PAGES=7;

    ViewPager pager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.tutorial_layout);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        pager=findViewById(R.id.pager);
        MyPagerAdapter adapter=new MyPagerAdapter(getSupportFragmentManager());

        Tutorial1 fragment1=new Tutorial1();//튜토리얼1
        adapter.addItem(fragment1);

        Tutorial2 fragment2=new Tutorial2();//튜토리얼2
        adapter.addItem(fragment2);

        Tutorial3 fragment3=new Tutorial3();//튜토리얼3
        adapter.addItem(fragment3);

        Tutorial4 fragment4=new Tutorial4();//튜토리얼4
        adapter.addItem(fragment4);

        Tutorial5 fragment5=new Tutorial5();//튜토리얼5
        adapter.addItem(fragment5);

        Tutorial6 fragment6=new Tutorial6();//튜토리얼6
        adapter.addItem(fragment6);

        Tutorial7 fragment7=new Tutorial7();//튜토리얼7
        adapter.addItem(fragment7);
        

        pager.setAdapter(adapter);
    }



    class MyPagerAdapter extends FragmentStatePagerAdapter{
        ArrayList<Fragment> items=new ArrayList<Fragment>();
        public MyPagerAdapter(FragmentManager fm){

            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }


}

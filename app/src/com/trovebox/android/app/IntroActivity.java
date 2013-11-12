
package com.trovebox.android.app;

import java.sql.Timestamp;
import java.util.Date;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;

public class IntroActivity extends FragmentActivity
{

    ImageFragmentAdapter mAdapter;
    ViewPager mPager;
    PageIndicator mIndicator;
    int mPosition;
    float mPositionOffset;
    int mPositionOffsetPixels;
    int mState;

    float x1, x2;
    float y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        // Set the pager with an adapter
        mPager = (ViewPager) findViewById(R.id.pager);

        mAdapter = new ImageFragmentAdapter(getSupportFragmentManager());
        mPager.setAdapter(mAdapter);
        mPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent touchEvent) {
                int action = touchEvent.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    {
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    }
                    case MotionEvent.ACTION_UP:
                    {
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();

                        // if left to right sweep event on screen
                        if (x1 > x2 && mPosition + 1 == mAdapter.getCount())
                            skipIntro(null);
                        break;
                    }
                }
                return false;
            }
        });
        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);

        mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPosition = position;
                mPositionOffset = positionOffset;
                mPositionOffsetPixels = positionOffsetPixels;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mState = state;
            }

            @Override
            public void onPageSelected(int arg0) {
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("DD", "onResume()");
//        
        SharedPreferences  sharedPrefs = this.getSharedPreferences(
                "skip_intro", this.MODE_PRIVATE);
        Log.i("DD", "" + (sharedPrefs.getInt("clickedSkip", 0)));

        if(sharedPrefs.getInt("clickedSkip", 0) == 1)
        {
            startActivity(new Intent(this, AccountActivity.class));
            finish();
        }
            
      //  startActivity(new Intent(this, MainActivity.class));
        
        
    }
  
    public void skipIntro(View v)
    {
        
          
        SharedPreferences  sharedPrefs = this.getSharedPreferences(
                  "skip_intro", this.MODE_PRIVATE);
        Log.i("DD", "" + (sharedPrefs.getInt("clickedSkip", 0)));
        
        if(sharedPrefs.getInt("clickedSkip", 0) == 0)
        {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putInt("clickedSkip", 1);
            editor.commit();
           
        }
        Log.i("DD", "" + (sharedPrefs.getInt("clickedSkip", 0)));
        startActivity(new Intent(this, AccountActivity.class));
        finish();
        
    }
}

package com.wangjw.listviewpagerdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

    private ViewPager mViewPager;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mViewPager = new ViewPager(this);
        mViewPager.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.header_height)));

        mListView = (ListView) findViewById(R.id.ListView);
        mListView.addHeaderView(mViewPager);
        mListView.setAdapter(new ItemAdapter(this));

        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(new HeaderAdapter(this));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "scroll state = " + state);
        boolean isScrolling = state != ViewPager.SCROLL_STATE_IDLE;
        mListView.requestDisallowInterceptTouchEvent(isScrolling);
    }

    private static class ItemAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        public ItemAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 25;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_intercept_row, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.TextView_Item);
            textView.setText(String.format("Item Row %d", position + 1));

            return convertView;
        }
    }

    private static class HeaderAdapter extends PagerAdapter {
        private static final int[] COLORS = {0xff555500, 0xff770077, 0xff007777, 0xff777777};

        private Context mContext;

        public HeaderAdapter(Context context) {
            mContext = context;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(mContext);
            tv.setBackgroundColor(COLORS[position]);
            tv.setText(String.format("Header Card %d", position + 1));
            tv.setGravity(Gravity.CENTER);

            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return COLORS.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }
    }
}

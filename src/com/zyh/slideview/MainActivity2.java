package com.zyh.slideview;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class MainActivity2 extends Activity {

    private ListView mListview;
    private Context context;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context = this;
        bindView();
    }

    private void bindView() {
        mListview = (ListView) findViewById(R.id.listview);
        adapter = new MyAdapter();
        mListview.setAdapter(adapter);
    }

    // ////////////////////////////////////////////////////////
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 100;
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
                convertView = new MoveLayout(context);
            }
            return convertView;
        }
    }

}

package com.sliding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sliding.utils.ScreenUtils;
import com.sliding.widget.SlidingDeleteView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    RecyclerView recyclerView;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setAdapter(myAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.sample_slidingview, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            //TODO 这里需要重新计算 item - containerView的宽度，否则containerView会显示错误(重要)
            LinearLayout containerView = (LinearLayout) holder.slidingDeleteView.findViewById(R.id.lay_container);
            containerView.getLayoutParams().width = ScreenUtils.getScreenWidth(getBaseContext());
            holder.slidingDeleteView.setEnable(true);
            holder.slidingDeleteView.setOnDeleteViewStateChangedListener(new SlidingDeleteView.OnDeleteViewStateChangedListener() {
                @Override
                public void onVisibile() {
                    Log.i(TAG, "显示抽屉视图");
                }

                @Override
                public void onGone() {
                    Log.i(TAG, "隐藏抽屉视图");
                }

                @Override
                public void onDownOrMove() {

                }
            });

            holder.tvKey.setText("key");
            holder.tvValue.setText("" + position);
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getBaseContext(), "position == " + position + " onClick", Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return 20;
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SlidingDeleteView slidingDeleteView;
        TextView tvKey;
        TextView tvValue;
        TextView tvDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            slidingDeleteView = (SlidingDeleteView) itemView.findViewById(R.id.slidingview);
            tvKey = (TextView) itemView.findViewById(R.id.tv_key);
            tvValue = (TextView) itemView.findViewById(R.id.tv_value);
            tvDelete = (TextView) itemView.findViewById(R.id.tv_delete);
        }

    }


}

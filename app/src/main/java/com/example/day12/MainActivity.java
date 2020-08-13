package com.example.day12;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {

    private RecyclerView mRel;

    private ArrayList<InfoBean.RecentBean> list;
    private MainPresenter presenter;
    private MainAdapter adapter;
    /**
     * 取消
     */
    private Button mBt2;
    /**
     * 删除
     */
    private static Button mBt3;
    /**
     * 编辑
     */
    private  Button mBt;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        initView();
        initData();
    }

    private void initData() {
        presenter.getData();
    }

    private void initView() {
        mRel = (RecyclerView) findViewById(R.id.rel);
        mRel.setLayoutManager(new LinearLayoutManager(this));
        mRel.addItemDecoration(new DividerItemDecoration(this,1));
        list = new ArrayList<>();
        adapter = new MainAdapter(this, list);
        mRel.setAdapter(adapter);
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mBt2 = (Button) findViewById(R.id.bt2);
        mBt2.setOnClickListener(this);
        mBt3 = (Button) findViewById(R.id.bt3);
        mBt3.setOnClickListener(this);
        adapter.setContext(new MainAdapter.setOnClick() {
            @Override
            public void onClick(int position) {
                page = position;
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                intent.putExtra("name",list.get(position).getUrl());
                startActivityForResult(intent,100);
            }
        });
    }


    @Override
    public void setData(List<InfoBean.RecentBean> recent) {
        adapter.addData(recent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                adapter.setV1(true);
                adapter.notifyDataSetChanged();
                mBt.setVisibility(View.GONE);
                mBt2.setVisibility(View.VISIBLE);
                break;
            case R.id.bt2:
                adapter.setV1(false);
                mBt2.setVisibility(View.GONE);
                mBt3.setVisibility(View.GONE);
                mBt.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                break;
            case R.id.bt3:
                list.remove(page);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private static class MainAdapter extends RecyclerView.Adapter {
        private final Context context;
        private final ArrayList<InfoBean.RecentBean> list;
        private boolean vi;

        public MainAdapter(Context context, ArrayList<InfoBean.RecentBean> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            final ViewHolder holder1 = (ViewHolder) holder;
            InfoBean.RecentBean bean = list.get(position);
            holder1.tv.setText(list.get(position).getTitle());
            holder1.tv2.setText("累计阅读时长"+(bean.getTime()/1000+"s"));
            if (vi){
                holder1.cb.setVisibility(View.VISIBLE);
            }else {
                holder1.cb.setVisibility(View.GONE);
            }

            holder1.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isPressed()){
                        mBt3.setVisibility(View.VISIBLE);
                    }
                }
            });
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void addData(List<InfoBean.RecentBean> recent) {
            list.addAll(recent);
            notifyDataSetChanged();
        }

        public void setV1(boolean vi) {
            this.vi=vi;
        }
        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv;
            private TextView tv2;
            private CheckBox cb;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
                tv2 = itemView.findViewById(R.id.tv2);
                cb = itemView.findViewById(R.id.cb);
            }
        }
        public setOnClick onClick;
        public void setContext (setOnClick onClick){

            this.onClick = onClick;
        }
        public interface setOnClick{
            void onClick(int position);
        };

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            long times = data.getLongExtra("time",0);
            times+=list.get(page).getTime();
            list.get(page).setTime(times);
            adapter.notifyDataSetChanged();
        }
    }

}

package com.example.day12json;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRel;
    private ArrayList<InfoBean> list2;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        OkHttpClient client = new OkHttpClient();
        Request build = new Request.Builder()
                .get()
                .url("https://wanandroid.com/navi/json")
                .build();
        client.newCall(build)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(string);
                            JSONArray data = jsonObject.optJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                //遍历data数组中一个一个的json
                                JSONObject o = (JSONObject) data.get(i);
                                o.optString("name");
                                o.optLong("cid");
                                JSONArray articles = o.optJSONArray("articles");
                                for (int j = 0; j < articles.length(); j++) {
                                    String json = articles.optString(j, "");
                                    if (!TextUtils.isEmpty(json)){
                                        JSONObject object = new JSONObject(json);
                                        final String author = object.optString("author");
                                        final String chapterName = object.optString("chapterName");
                                        final String niceShareDate = object.optString("niceShareDate");
                                        final String title = object.optString("title");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                list2.add(new InfoBean(author,chapterName,niceShareDate,title));
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void initView() {
        mRel = (RecyclerView) findViewById(R.id.rel);
        mRel.setLayoutManager(new LinearLayoutManager(this));
        list2 = new ArrayList<>();
        adapter = new MainAdapter(this,list2);
        mRel.setAdapter(adapter);
    }
}

package com.example.day12;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    String url="https://news-at.zhihu.com/";
    @GET("api/4/news/hot")
    Observable<InfoBean> getData();
}

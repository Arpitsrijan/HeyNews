package com.example.newsproject;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements categoryRVAdapter.categoryClickInterface {
    //4c0a1b688fa84c0c9ad62f146778b7a6
private RecyclerView newRV,categoryRV;
private ProgressBar loadingBar;
private ArrayList<Articles> articlesArrayList;
private ArrayList<CategoryRVModel> categoryRVModelArrayList;
private categoryRVAdapter categoryRVAdapter;
private newsRVAdpter newsRVAdpter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newRV=findViewById(R.id.idRNews);
        categoryRV=findViewById(R.id.idRVCategories);
        loadingBar=findViewById(R.id.idPBLoading);
        articlesArrayList= new ArrayList<>();
        categoryRVModelArrayList =new ArrayList<>();
        newsRVAdpter = new newsRVAdpter(articlesArrayList, MainActivity.this);
        categoryRVAdapter = new categoryRVAdapter(categoryRVModelArrayList ,this,this::onCategoryClick);
        newRV.setLayoutManager(new LinearLayoutManager(this));
        newRV.setAdapter(newsRVAdpter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("ALL");
        newsRVAdpter.notifyDataSetChanged();
    }

private  void getCategories(){
        categoryRVModelArrayList.add(new CategoryRVModel("ALL","https://images.unsplash.com/photo-1518318334752-0a61581ac83a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8YWxsfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Technology","https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fFRlY2hub2xvZ3l8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Science","https://plus.unsplash.com/premium_photo-1663014611296-1af1ab71aee2?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fHNjaWVuY2V8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Sports","https://images.unsplash.com/photo-1612872087720-bb876e2e67d1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fHNwb3J0c3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("General","https://images.unsplash.com/photo-1494059980473-813e73ee784b?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Business","https://images.unsplash.com/photo-1444653614773-995cb1ef9efa?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njl8fGJ1c2luZXNzfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Entertainment","https://images.unsplash.com/photo-1549342902-be005322599a?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fGVudGVydGFpbm1lbnR8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        categoryRVModelArrayList.add(new CategoryRVModel("Health","https://images.unsplash.com/photo-1552196563-55cd4e45efb3?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODN8fGhlYWx0aHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60"));
        categoryRVAdapter.notifyDataSetChanged();
}

private void getNews(String category){
        loadingBar.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=4c0a1b688fa84c0c9ad62f146778b7a6";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomain=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=4c0a1b688fa84c0c9ad62f146778b7a6";
        String BASE_URL="https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
    Call<NewsModel> call;
    if(category.equals("ALL")){
        call=retrofitAPI.getAllNews(url);
    }else {
        call=retrofitAPI.getNewsByCategory(categoryUrl);
    }

    call.enqueue(new Callback<NewsModel>() {
        @Override
        public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
            NewsModel newsModel= response.body();
            loadingBar.setVisibility(View.GONE);
            ArrayList<Articles> articles=newsModel.getArticles();
            for(int i=0;i<articles.size();i++){
              articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),articles.get(i).getUrl(),articles.get(i).getContent()));
            }
            newsRVAdpter.notifyDataSetChanged();
        }

        @Override
        public void onFailure(Call<NewsModel> call, Throwable t) {
            Toast.makeText(MainActivity.this,"Fail to fetch news",Toast.LENGTH_LONG).show();
        }
    });


}


    @Override
    public void onCategoryClick(int position) {
        String category= categoryRVModelArrayList.get(position).getCategory();
        getNews(category);




    }
}
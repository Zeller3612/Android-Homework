package com.bytedance.network;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bytedance.network.api.TestService;
import com.bytedance.network.model.GetInfo;
import com.bytedance.network.model.PostInfo;
import com.bytedance.network.model.Video;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api-sjtu-camp.bytedance.com/invoke/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn1).setOnClickListener(v -> {
            try {
                uploadInfo();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(view -> downloadInfo());
    }

    private void downloadInfo() {
        // 通过 retrofit 对象的 create() 方法来实例化 java 接口的对象
        // 拿到该接口对象后直接调用相应 api 所对应的方法，并传入对应的参数，获取到 Call<T> 的实例化对象
        // 通过该对象即可向服务器发起网络请求
        RecyclerView recyclerView = findViewById(R.id.tv);
        SearchAdapter searchAdapter = new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TestService testService = retrofit.create(TestService.class);

        testService.get().enqueue(new Callback<GetInfo>() {
            @Override public void onResponse(final Call<GetInfo> call, final Response<GetInfo> response) {
                // 合法性校验
                if (!response.isSuccessful()) {
                    return;
                }

                final List<Video> videoList = response.body().getFeeds();

                if (videoList == null || videoList.isEmpty()) {
                    return;
                }

                List<String> list = new ArrayList<>();
                for (int i = 0; i < videoList.size(); i++) {
                    final Video repo = videoList.get(i);
                    list.add("学生编号：" + repo.getStudentId());
                    list.add("用户姓名：" + repo.getUserName());
                    list.add("图片URL：" + repo.getImageUrl());
                    list.add("视频URL：" + repo.getVideoUrl());
                    list.add(" ");
                }
                searchAdapter.notifyItems(list);
            }

            @Override public void onFailure(final Call<GetInfo> call, final Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void uploadInfo() throws IOException {

        TestService testService = retrofit.create((TestService.class));

        testService.post("000123000", "zeller", getMultipartFromAsset(), getMultipartFromAsset1())
                .enqueue(new Callback<PostInfo>() {
            @Override
            public void onResponse(Call<PostInfo> call, Response<PostInfo> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (response.isSuccessful()) {
                    Log.d("TAG", "111");
                } else {
                    Log.d("TAG", "!isSuccessful");
                }
            }

            @Override
            public void onFailure(Call<PostInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private MultipartBody.Part getMultipartFromAsset() throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream = assetManager.open("pic.png");
        final String partKey = "cover_image";
        final String localFileName = "pic.png";
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileNameToByte(inputStream));
        return MultipartBody.Part.createFormData(partKey, localFileName, requestFile);
    }

    private MultipartBody.Part getMultipartFromAsset1() throws IOException {
        AssetManager assetManager = getAssets();
        InputStream inputStream1 = assetManager.open("video.mp4");
        final String partKey1 = "video";
        final String localFileName1 = "video.mp4";
        RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), fileNameToByte(inputStream1));
        return MultipartBody.Part.createFormData(partKey1, localFileName1, requestFile1);
    }

    private byte[] fileNameToByte(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return  output.toByteArray();
    }

}
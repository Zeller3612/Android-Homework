package com.bytedance.network.api;

import com.bytedance.network.model.GetInfo;
import com.bytedance.network.model.PostInfo;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface TestService {

    @GET("video")
    Call<GetInfo> get();

    @Multipart
    @POST("video")
    Call<PostInfo> post(@Query("student_id") String studentId,
                        @Query("user_name") String userName,
                        @Part MultipartBody.Part image,
                        @Part MultipartBody.Part video);
}

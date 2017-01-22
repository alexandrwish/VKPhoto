package com.vk.vkphoto.http;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface VkClient {

    @GET
    Observable<Response<ResponseBody>> loadPhoto(@Url String url);
}
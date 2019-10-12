package net.qiujuer.italker.factory.net;

import net.qiujuer.italker.common.Common;
import net.qiujuer.italker.factory.Factory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {

    /**
     * 构建一个retrofit
     * @return
     */
    public static Retrofit getRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder().build();

        Retrofit.Builder builder = new Retrofit.Builder();

        //设置电脑连接
        return builder.baseUrl(Common.Constance.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(Factory.getGson()))
                .build();
    }
}

package com.linsh.protocol.impl.http;

import com.linsh.protocol.http.GetBuilder;
import com.linsh.protocol.http.HttpManager;
import com.linsh.protocol.http.PostBuilder;
import com.linsh.protocol.http.RequestBuilderFactory;
import com.linsh.protocol.http.ServiceFactory;
import com.linsh.protocol.impl.http.converter.StringConverterFactory;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/10/09
 *    desc   : 使用 Retrofit 进行网络请求的管理器
 * </pre>
 */
public class RetrofitManager implements HttpManager {

    final Retrofit retrofit;
    final Call.Factory callFactory;
    private final ServiceFactory serviceFactory;
    private final RequestBuilderFactory requestBuilderFactory;
    private final List<Converter.Factory> converterFactories;


    public RetrofitManager(String baseUrl) {
        this(new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build());
    }

    public RetrofitManager(Retrofit retrofit) {
        this.retrofit = retrofit;
        this.callFactory = retrofit.callFactory();
        this.serviceFactory = new RetrofitServiceFactory(retrofit);
        this.requestBuilderFactory = new RetrofitRequestBuilderFactory(this);
        this.converterFactories = retrofit.converterFactories();
    }

    @Override
    public <T> T service(Class<T> clazz) {
        return serviceFactory.create(clazz);
    }

    @Override
    public GetBuilder get(String url) {
        return requestBuilderFactory.get(url);
    }

    @Override
    public PostBuilder post(String url) {
        return requestBuilderFactory.post(url);
    }

    <T> Converter<ResponseBody, T> nextResponseBodyConverter(Type type) {
        if (type == null) throw new NullPointerException("type == null");

        if (converterFactories == null)
            throw new NullPointerException("converterFactories == null");
        if (converterFactories.size() == 0)
            throw new NullPointerException("converterFactories can not be empty");

        for (Converter.Factory converterFactory : converterFactories) {
            Converter<ResponseBody, ?> converter =
                    converterFactory.responseBodyConverter(type, null, null);
            if (converter != null)
                return (Converter<ResponseBody, T>) converter;
        }
        throw new IllegalArgumentException("Could not locate ResponseBody converter for " + type);
    }
}

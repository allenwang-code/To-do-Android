package allenwang.todolist;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by allenwang on 2017/1/15.
 */

public class ApiServiceGenerator {
    //url: https://sheetsu.com/apis/v1.0/c07aa6aa9bed
    private static final String version = "v1.0";
    private static final String BASE_URL = " https://sheetsu.com/apis/" + version + "/";

    private static OkHttpClient.Builder okHttpClientBuilder =
            new OkHttpClient.Builder();

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create());


    public static <T> T createService(Class<T> serviceClass) {
        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(serviceClass);
    }
}

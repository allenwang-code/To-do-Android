package allenwang.todolist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by allenwang on 2017/1/15.
 */

public interface ApiInterface {

    @GET("c07aa6aa9bed")
    Call<ArrayList<TodoItem>> getToDoList();


}

package allenwang.todolist;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by allenwang on 2017/1/15.
 */

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "appDatabase";
    public static final int VERSION = 1;
}

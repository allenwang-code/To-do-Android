package allenwang.todolist;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by allenwang on 2017/1/15.
 */

@Table(database = AppDatabase.class)
public class ToDoTable extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String title;

    @Column
    String date;

    @Column
    boolean isFinished; // private with getters and setters
}

package allenwang.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Update;
import com.raizlabs.android.dbflow.sql.language.Where;

import java.util.List;

import static allenwang.todolist.R.id.datePicker;
import static allenwang.todolist.ToDoTable_Table.id;

/**
 * Created by allenwang.
 */
public class ContentFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private EditText mEditText;
    private Button mSendButton;
    private DatePicker mDatePicker;

    private long mTodoItemId;
    private ToDoTable mItem;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTodoItemId = getArguments().getLong("id");
            List<ToDoTable> organizationList = SQLite.select().from(ToDoTable.class)
                    .where(id.eq(mTodoItemId)).queryList();
            mItem = organizationList.get(0);
        } else {
            mTodoItemId = -1;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        MainActivity activity = (MainActivity) getActivity();
        activity.getSupportActionBar().hide();

        mEditText = (EditText) view.findViewById(R.id.editText_title);
        mSendButton = (Button) view.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(this);
        mDatePicker = (DatePicker) view.findViewById(datePicker);

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        if (mItem != null) {
            mEditText.setText(mItem.title);
            String date = mItem.date;
            int firstDash = date.indexOf("-");
            int secondDash = date.lastIndexOf("-");
            String year = date.substring(0, firstDash);
            String month = date.substring(firstDash+1, secondDash);
            String day = date.substring(secondDash+1, date.length());
            mDatePicker.updateDate(Integer.parseInt(year),
                    Integer.parseInt(month),
                    Integer.parseInt(day));
        }
    }

    @Override
    public void onClick(View v) {
        String title = mEditText.getText().toString();
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth();
        int day = mDatePicker.getDayOfMonth();
        String date = year + "-" + month + "-" + day;

        ToDoTable todo = new ToDoTable();
        todo.date = date;
        todo.title = title;
        if (mTodoItemId == -1) { todo.save(); }
        else { update(todo); }

        ((MainActivity)getActivity()).navigateToFragement(new MainFragment());
    }

    private void update(ToDoTable todo) {
        Where update = new Update<>(ToDoTable.class)
                .set(ToDoTable_Table.title.eq(todo.title),
                        ToDoTable_Table.date.eq(todo.date)
                        )
                .where(ToDoTable_Table.id.is(mTodoItemId));
        update.execute();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}

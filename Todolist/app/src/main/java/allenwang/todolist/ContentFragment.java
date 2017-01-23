package allenwang.todolist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import static allenwang.todolist.R.id.datePicker;

/**
 * Created by allenwang.
 */
public class ContentFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private EditText mEditText;
    private Button mSendButton;
    private DatePicker mDatePicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onClick(View v) {
        String title = mEditText.getText().toString();
        int year = mDatePicker.getYear();
        int month = mDatePicker.getMonth() + 1;
        int day = mDatePicker.getDayOfMonth();
        String date = year + "-" + month + "-" + day;

        Toast.makeText(getContext(), title + "  " + date, Toast.LENGTH_SHORT).show();
    }
}

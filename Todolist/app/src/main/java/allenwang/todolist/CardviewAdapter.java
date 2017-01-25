package allenwang.todolist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by allenwang.
 */
public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.ViewHolder> {
    private Context mContext;
    private AdapterCallback mAdapterCallback;
    private List<ToDoTable> mDataset;

    /**
     * package
     */
    CardviewAdapter(Context context, AdapterCallback callback, List myDataset) {
        mDataset = myDataset;
        mContext = context;
        mAdapterCallback = callback;
    }

    @Override
    public CardviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_main, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ToDoTable todoItem = mDataset.get(position);

        holder.mTitleTextView.setText(todoItem.title);
        holder.mDateTextView.setText(todoItem.date);
        holder.mCheckBox.setChecked(todoItem.isFinished);
        holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(mContext, String.valueOf(b), Toast.LENGTH_SHORT).show();
            }
        });
        holder.mContainerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext instanceof MainActivity) {
                    ContentFragment f = new ContentFragment();
                    Bundle b = new Bundle();
                    b.putLong("id", todoItem.id);
                    f.setArguments(b);
                    ((MainActivity)mContext).navigateToFragement(f);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * package
     */
    void updateData(List<ToDoTable> myDataset) {
        mDataset = myDataset;
    }

    /**
     * package
     */
    interface AdapterCallback {
        void onArticleClick(String url);
    }

    /**
     * package
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView mContainerCardView;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mCheckBox;

        private ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mContainerCardView = (CardView) itemLayoutView.findViewById(R.id.card_view);
            mTitleTextView = (TextView) itemLayoutView.findViewById(R.id.textView_title);
            mDateTextView = (TextView) itemLayoutView.findViewById(R.id.textView_date);
            mCheckBox = (CheckBox) itemLayoutView.findViewById(R.id.checkBox);
        }
    }

}


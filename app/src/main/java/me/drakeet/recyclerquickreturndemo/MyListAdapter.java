package me.drakeet.recyclerquickreturndemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by drakeet on 2/14/15.
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder> {

    private List<String> mList;
    private View mView;
    private final int TYPE_HEADER = 0;
    private final int TYPE_CHILD = 1;

    public MyListAdapter(List<String> list) {
        mList = list;
    }

    @Override
    public MyListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mView);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
            ViewHolder holder = new ViewHolder(v);
            holder.content = (TextView) v.findViewById(R.id.tv_content);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(MyListAdapter.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_HEADER) return;
        if (mView != null) {
            position = position - 1;
        }
        final String string = mList.get(position);
        holder.content.setText(string);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_CHILD;
    }

    @Override
    public int getItemCount() {
        return mView != null ? mList.size() + 1 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        TextView content;
    }

    public void addHeaderView(View view) {
        this.mView = view;
    }

}

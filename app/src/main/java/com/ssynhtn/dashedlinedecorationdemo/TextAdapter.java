package com.ssynhtn.dashedlinedecorationdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangtongnao on 2019/4/12.
 * Email: huangtongnao@gmail.com
 */
public class TextAdapter extends RecyclerView.Adapter {

    private int layoutId;
    private List<String> data = new ArrayList<>();

    public interface OnClickTextListener {
        void onClick(String text);
    }

    private OnClickTextListener listener;

    public TextAdapter setListener(OnClickTextListener listener) {
        this.listener = listener;
        return this;

    }

    public TextAdapter(@NonNull String text) {
        for (int i = 0; i < text.length(); i++) {
            data.add(text.charAt(i) + "");
        }
    }

    public TextAdapter(@NonNull String text, @LayoutRes int layoutId) {
        this(text);
        this.layoutId = layoutId;
    }

    public TextAdapter(List<String> data) {
        this.data = data;
    }

    public void setData(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId != 0 ? layoutId : android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bind(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            if (itemView instanceof TextView) {
                textView = (TextView) itemView;
            } else {
                textView = itemView.findViewById(android.R.id.text1);
            }

            if (textView != null) {
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getAdapterPosition() == -1) return;
                        if (listener != null) {
                            listener.onClick(data.get(getAdapterPosition()));
                        }

                    }
                });
            }
        }

        public void bind(String text, int position) {
            if (textView != null) {
                textView.setText(text + " " + position);
            }
        }
    }
}

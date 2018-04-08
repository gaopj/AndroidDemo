package com.gpj.materialdesigndemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private String title;

    public RecyclerViewAdapter(Context mContext,String title) {
        this.mContext = mContext;
        this.title = title;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewAdapter.ViewHolder holder, final int position) {
        final View view = holder.mView;
        holder.textView.setText(title+holder.textView.getText());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"click-->"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
         final View mView;
         public TextView textView;

         ViewHolder(View view) {
            super(view);
              mView = view;
             textView = view.findViewById(R.id.text);
        }
    }
}

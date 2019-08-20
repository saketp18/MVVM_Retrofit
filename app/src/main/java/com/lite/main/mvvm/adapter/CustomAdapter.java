package com.lite.main.mvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lite.main.mvvm.R;
import com.lite.main.mvvm.models.ImageSource;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomHolder> implements Observer<List<ImageSource>> {

    private ArrayList<ImageSource> sources = new ArrayList<>();
    private Context mContext;

    public CustomAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        CustomHolder customHolder = new CustomHolder(view);
        return customHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder holder, int position) {
        if(sources.size() > 0) {
            Glide.with(mContext).load(sources.get(position).getUrls().getRegular()).override(1024, 720)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return sources.size();
    }


    protected class CustomHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    @Override
    public void onChanged(List<ImageSource> newsSource) {
        sources.addAll(newsSource);
        notifyItemRangeInserted(sources.size(), newsSource.size());
    }
}

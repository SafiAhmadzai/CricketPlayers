package com.example.topcricketplayer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.example.topcricketplayer.R;
import com.example.topcricketplayer.models.Player;

import static com.android.volley.VolleyLog.TAG;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder> {

    private OnItemClickListener listener;
    List<Player> list;
    Context context;

    public interface OnItemClickListener {
        void onItemClick(Player item);
    }


    public LanguageAdapter(List<Player> list, Context context, OnItemClickListener listener) {
        this.list = list;

        Log.d(TAG, "LanguageAdapter: +" + list.size());
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.celebrity_row_item, parent, false);

        return new LanguageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LanguageViewHolder holder, int position) {


        Player player = list.get(position);


        holder.tvPhone.setText("Rating: " + player.getRating());
        holder.tvName.setText(player.getName().toUpperCase());


        Glide
                .with(context)
                .load(player.getImage())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.ivPhoto);

        holder.bind(player, listener);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LanguageViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvPhone;

        ImageView ivPhoto;

        public LanguageViewHolder(@NonNull final View itemView) {
            super(itemView);


            tvName = itemView.findViewById(R.id.tvNameCelebrity);
            tvPhone = itemView.findViewById(R.id.tvPhoneCelebrity);
            ivPhoto = itemView.findViewById(R.id.ivphotoCelbrity);

        }
        
        public void bind(final Player item, final OnItemClickListener listener) {
            Log.d(TAG, "bind: ");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }


    }
}

package com.sh.imoocmusicdemo.adapts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.activitys.AlbumListActivity;
import com.sh.imoocmusicdemo.models.AlbumModel;

import java.util.List;

public class MusicGridAdapter extends RecyclerView.Adapter<MusicGridAdapter.ViewHolder> {

    private Context mContext;
    private List<AlbumModel> mDataSource;



    public MusicGridAdapter(Context context, List<AlbumModel> dataSource){
        this.mContext=context;
        this.mDataSource=dataSource;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_music,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final AlbumModel albumModel=mDataSource.get(position);

        Glide.with(mContext)
                .load(albumModel.getPoster())
                .into(holder.ivIcon);
        holder.mTvPlayNum.setText(albumModel.getPlayNum());
        holder.mTvName.setText(albumModel.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, AlbumListActivity.class);
                intent.putExtra(AlbumListActivity.AlbumId,albumModel.getAlbumId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivIcon;
        private View itemView;
        private TextView mTvPlayNum,mTvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            this.ivIcon=itemView.findViewById(R.id.iv_icon);
            this.mTvPlayNum=itemView.findViewById(R.id.tv_play_num);
            this.mTvName=itemView.findViewById(R.id.tv_name);
        }
    }


}

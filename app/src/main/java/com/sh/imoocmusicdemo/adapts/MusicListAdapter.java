package com.sh.imoocmusicdemo.adapts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.activitys.PlayMusicActivity;
import com.sh.imoocmusicdemo.models.MusicModel;

import java.util.List;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {


    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulationRvHeight;
    private List<MusicModel> mDataSource;

    public MusicListAdapter(Context context,RecyclerView recyclerView,List<MusicModel> list){
        this.mRv=recyclerView;
        this.mContext=context;
        this.mDataSource=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        this.mItemView=LayoutInflater.from(mContext).inflate(R.layout.item_list_music,parent,false);
        return new ViewHolder(mItemView) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setRecyclerViewHeight();

        final MusicModel musicModel=mDataSource.get(position);

        Glide.with(mContext)
                .load(musicModel.getPoster())
                .into(holder.ivIcon);
        holder.mTvName.setText(musicModel.getName());
        holder.mTvAuthor.setText(musicModel.getAuthor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PlayMusicActivity.class);
                intent.putExtra(PlayMusicActivity.MUSIC_ID,musicModel.getMusicId());
                intent.putExtra(PlayMusicActivity.isPlaying,true);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSource.size();
    }

    /**
     * 1.获取itemview的高度
     * 2.itemview的数量
     * 3.itemviewHeight * itemviewnumber=recycview的高度
     */
    private void setRecyclerViewHeight(){
        if(isCalcaulationRvHeight||mRv==null)return;

        isCalcaulationRvHeight=true;
        //获取itemview的高度
        RecyclerView.LayoutParams itemViewLp = (RecyclerView.LayoutParams )mItemView.getLayoutParams();
        //itemview的数量
        int itemCount = getItemCount();
        //itemviewHeight * itemviewnumber=recycview的高度
        int recyclerHeight = itemViewLp.height * itemCount;
        //设置recyclerview的高度
        LinearLayout.LayoutParams rvLp=(LinearLayout.LayoutParams)mRv.getLayoutParams();
        rvLp.height=recyclerHeight;
        mRv.setLayoutParams(rvLp);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        ImageView ivIcon;
        TextView mTvName,mTvAuthor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            mTvName=itemView.findViewById(R .id.tv_name);
            mTvAuthor=itemView.findViewById(R.id.tv_author);
            ivIcon=itemView.findViewById(R.id.iv_icon);
        }
    }

}

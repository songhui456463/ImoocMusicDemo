package com.sh.imoocmusicdemo.adapts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sh.imoocmusicdemo.R;
import com.sh.imoocmusicdemo.activitys.PlayMusicActivity;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {


    private Context mContext;
    private View mItemView;
    private RecyclerView mRv;
    private boolean isCalcaulationRvHeight;

    public MusicListAdapter(Context context,RecyclerView recyclerView){
        this.mRv=recyclerView;
        this.mContext=context;
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

        Glide.with(mContext)
                .load("http://res.lgdsunday.club/poster-1.png")
                .into(holder.ivIcon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, PlayMusicActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 8;
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
        private ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView=itemView;
            ivIcon=itemView.findViewById(R.id.iv_icon);
        }
    }

}

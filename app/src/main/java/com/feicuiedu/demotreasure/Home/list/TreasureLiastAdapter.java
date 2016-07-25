package com.feicuiedu.demotreasure.Home.list;

import android.support.v7.widget.RecyclerView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.demotreasure.Home.Detail.TreasureDetailActivity;
import com.feicuiedu.demotreasure.Home.Treasure;
import com.feicuiedu.demotreasure.commons.TreasureView;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Administrator on 2016/7/25.
 */
public class TreasureLiastAdapter extends RecyclerView.Adapter<TreasureLiastAdapter.HolderView> {

private ArrayList<Treasure> lists=new ArrayList<Treasure>();

    public  final void setItem(Collection<Treasure> items){
        if (items !=null){
            lists.addAll(items);
            notifyItemMoved(0,lists.size());
        }
    }
    // 创建ViewHolder对象
    @Override
    public HolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        TreasureView treasureView=new TreasureView(parent.getContext());
        return new HolderView(treasureView);
    }

    @Override
    public void onBindViewHolder(HolderView holder, int position) {
        final Treasure treasure=lists.get(position);
        holder.treasureView.bindTreasure(treasure);
        holder.treasureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreasureDetailActivity.open(view.getContext(), treasure);
            }
        });


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    static final class HolderView extends RecyclerView.ViewHolder{
    private TreasureView treasureView;

        public HolderView(TreasureView itemView) {

            super(itemView);
            treasureView=itemView;
        }
    }
}

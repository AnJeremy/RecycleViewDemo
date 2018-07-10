package cn.syxg.recycleviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class ElePriceStaggeredAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<ElePriceTableModel> mData;
    private int mColumn;
    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;
    public ElePriceStaggeredAdapter(Context context, List<ElePriceTableModel> data) {
        mContext = context;
        mData = data;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == ITEM_VIEW_TYPE_HEADER){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_heard, parent, false);
            return new HeaderViewHolder(view);
        }else {

            view = LayoutInflater.from(mContext).inflate(R.layout.item_content, parent, false);
            return new BodyViewHolder(view);

        }

       /* View view = LayoutInflater.from(mContext).inflate(R.layout.item_elec_price, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;*/
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ElePriceTableModel item = mData.get(position);

        if (holder.getItemViewType() == ITEM_VIEW_TYPE_HEADER){

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ((HeaderViewHolder)holder).mHeaderContent.getLayoutParams();
            lp.height = item.getHeightMultiple() * mContext.getResources().getDimensionPixelOffset(R.dimen.height_35dp) +
                    (item.getHeightMultiple() - 1) * mContext.getResources().getDimensionPixelOffset(R.dimen.height_1dp);
            lp.rightMargin = item.isHighlight() ? 0 : mContext.getResources().getDimensionPixelOffset(R.dimen.height_1dp);
            ((HeaderViewHolder) holder).mHeaderContent.setLayoutParams(lp);

        }else {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ((BodyViewHolder)holder).mContent.getLayoutParams();
            lp.height = mContext.getResources().getDimensionPixelOffset(R.dimen.height_35dp);

            lp.rightMargin = item.isHighlight() ? 0 : mContext.getResources().getDimensionPixelOffset(R.dimen.height_1dp);
            ((BodyViewHolder) holder).mContent.setLayoutParams(lp);
        }

    }

   /* //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ElePriceTableModel item = mData.get(position);
        holder.mContent.setText(item.getContent());
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.mContent.getLayoutParams();

        if (item.getHeightMultiple() == -1) { //表头
            lp.height = mContext.getResources().getDimensionPixelOffset(R.dimen.height_50dp);
            lp.rightMargin = (position + 1 == getColumn()) ? 0 : mContext.getResources().getDimensionPixelOffset(R.dimen.height_1dp);
            holder.mContent.setLayoutParams(lp);
            holder.mContent.setTextColor(mContext.getResources().getColor(R.color.color_999));
        } else {
            if (item.getHeightMultiple() > 1) {
                lp.height = item.getHeightMultiple() * mContext.getResources().getDimensionPixelOffset(R.dimen.height_35dp) +
                        (item.getHeightMultiple() - 1) * mContext.getResources().getDimensionPixelOffset(R.dimen.height_1dp);
            } else {
                lp.height = mContext.getResources().getDimensionPixelOffset(R.dimen.height_35dp);
            }
            lp.rightMargin = item.isHighlight() ? 0 : mContext.getResources().getDimensionPixelOffset(R.dimen.height_1dp);
            holder.mContent.setLayoutParams(lp);
            holder.mContent.setTextColor(mContext.getResources().getColor(item.isHighlight() ? R.color.common_blue : R.color.text_black_standard));
        }
    }*/

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getHeightMultiple()>=4 ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

   /* //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mContent;

        public ViewHolder(View view) {
            super(view);
            mContent = (TextView) view.findViewById(R.id.text_content);
        }
    }*/

    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public RelativeLayout mHeaderContent;
        private TextView textView;
        private ImageView imageView;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            imageView = (ImageView) itemView.findViewById(R.id.iv);
            mHeaderContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        }
        public TextView getTextView() {
            return textView;
        }
        public ImageView getImageView() {
            return imageView;
        }
        public RelativeLayout getHeaderContent() {
            return mHeaderContent;
        }
    }

    /**
     * 给GridView中的条目用的ViewHolder，里面只有一个TextView
     */
    public static class BodyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public RelativeLayout mContent;

        public BodyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            mContent = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        }
        public TextView getTextView() {
            return textView;
        }
        public RelativeLayout getBodyContent() {
            return mContent;
        }
    }





    public List<ElePriceTableModel> getData() {
        return mData;
    }

    public Context getContext() {
        return mContext;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }
}
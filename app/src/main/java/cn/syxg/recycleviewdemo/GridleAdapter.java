package cn.syxg.recycleviewdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */

public class GridleAdapter extends RecyclerView.Adapter{

    //先定义两个ItemViewType，0代表头，1代表表格中间的部分
    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private Context mContext;

    private List<GridleBean> gridleBeans;

    public GridleAdapter(Context mContext, List<GridleBean> gridleBeans) {
        this.mContext = mContext;
        this.gridleBeans = gridleBeans;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_VIEW_TYPE_HEADER){
            //对于Header，我们应该返回填充有Header对应布局文件的ViewHolder（再次我们返回的都是一个布局文件，请根据不同的需求做相应的改动）
            return new HeaderViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_heard, parent,false));
            //return null;

        }else {
            return new BodyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_content, parent,false));
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {



       if(holder.getItemViewType()==ITEM_VIEW_TYPE_HEADER){
           LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((HeaderViewHolder)holder).mHeaderContent.getLayoutParams();
           //lp.height = mContext.getResources().getDimensionPixelOffset();

           lp.rightMargin = (position + 1 == getColumn()) ? 0 : mContext.getResources().getDimensionPixelOffset(R.dimen.dp_4);
           ((HeaderViewHolder) holder).mHeaderContent.setLayoutParams(lp);
           //((HeaderViewHolder) holder).mHeaderContent.setTextColor(mContext.getResources().getColor(R.color.color_999));

       }else {
           LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) ((BodyViewHolder)holder).mContent.getLayoutParams();
           lp.rightMargin =  mContext.getResources().getDimensionPixelOffset(R.dimen.dp_4);
           ((BodyViewHolder) holder).mContent.setLayoutParams(lp);
       }

    }

    @Override
    public int getItemCount() {
        return gridleBeans.size();
    }


    @Override
    public int getItemViewType(int position) {
        return gridleBeans.get(position).getImageId() <= 0 ? ITEM_VIEW_TYPE_ITEM:ITEM_VIEW_TYPE_HEADER;
    }


    /**
     * 给头部专用的ViewHolder，大家根据需求自行修改
     */
    public  class HeaderViewHolder extends RecyclerView.ViewHolder {
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
    public class BodyViewHolder extends RecyclerView.ViewHolder {
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

    private int mColumn;

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }
}

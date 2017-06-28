package com.zmm.listviewpaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/6/28
 * Time:下午4:05
 */

public class MyListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDeviceNames;
    private int mItemCount = 3;
    private int mCurrentPage = 0;
    private int mSize = 0;
    private OnListItemClickListener mOnListItemClickListener;
    private int checkedLayout = -1;

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        mOnListItemClickListener = onListItemClickListener;
    }

    public MyListAdapter(Context context, List<String> deviceNames) {
        mContext = context;
        mDeviceNames = deviceNames;
        if(deviceNames != null && deviceNames.size() > 0){
            mSize = mDeviceNames.size();
        }
    }

    @Override
    public int getCount() {
        int i = mDeviceNames.size() % mItemCount;
        if(i == 0){
            return mItemCount;
        }else {
            return (isLastOne()) ? i : mItemCount;
        }
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_device_register, parent, false);
            holder = new ViewHolder();
            holder.mDeviceId = convertView.findViewById(R.id.tv_device_id);
            holder.mDeviceItem = convertView.findViewById(R.id.ll_device_item);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        final int currentPosition = position + mCurrentPage * mItemCount;
        if(currentPosition < mDeviceNames.size()){
            holder.mDeviceId.setText(mDeviceNames.get(currentPosition));
            holder.mDeviceItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("当前选中的position = "+currentPosition);
                    checkedLayout = currentPosition;
                    notifyDataSetChanged();
                    if(mOnListItemClickListener != null){
                        mOnListItemClickListener.OnItemClickListener(currentPosition);
                    }
                }
            });
        }

        if(checkedLayout == currentPosition){
            holder.mDeviceItem.setBackgroundResource(R.drawable.shape_rectangle_blue);
        }else {
            holder.mDeviceItem.setBackgroundResource(R.drawable.shape_rectangle);
        }

        return convertView;
    }

    class ViewHolder {
        LinearLayout mDeviceItem;
        TextView mDeviceId;
    }

    private boolean isFirstOne() {
        return this.mCurrentPage == 0;
    }

    private boolean isLastOne() {
        int i = mDeviceNames.size() % mItemCount;
        if(i == 0){
            return this.mCurrentPage == mSize/mItemCount - 1;
        }else {
            return this.mCurrentPage == mSize/mItemCount;
        }
    }

    public void turnToPre() {

        if (!isFirstOne()) {
            mCurrentPage--;
            notifyDataSetChanged();
        } else {
            System.out.println("已经是第一页");
            Toast.makeText(mContext,"已经是第一页",Toast.LENGTH_SHORT).show();
        }
    }

    public void turnToNext() {

        if (!isLastOne()) {
            mCurrentPage++;
            notifyDataSetChanged();
        } else {
            System.out.println("已经是最后一页");
            Toast.makeText(mContext,"已经是最后一页",Toast.LENGTH_SHORT).show();

        }
    }

    public interface OnListItemClickListener{
        void OnItemClickListener(int index);
    }
}

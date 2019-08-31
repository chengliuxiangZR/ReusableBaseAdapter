package com.example.asus.reusablebaseadapter;

import android.content.Context;
import android.media.Image;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class MyAdapter<T> extends BaseAdapter {
    private Context mContext;
    //将Entity实体类设置成泛型
    private ArrayList<T> mData;
    private int mLayoutRes;  //布局id
    public MyAdapter(){
    }

    public MyAdapter(ArrayList<T> mData, int mLayoutRes) {
        this.mLayoutRes=mLayoutRes;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData!=null?mData.size():0;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=ViewHolder.bind(parent.getContext(),convertView,parent,mLayoutRes,position);
        bindView(holder,getItem(position));
        return holder.getItemView();



//        ViewHolder holder=null;
        //复用convertView
//        if(convertView==null){
//            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_list,parent,false);
//            holder=new ViewHolder();
//            holder.imageView=(ImageView)convertView.findViewById(R.id.img_icon);
//            holder.textView=(TextView)convertView.findViewById(R.id.txt_content);
//            //Viewholder组件重用
//            convertView.setTag(holder);
//        }else {
//            holder=(ViewHolder)convertView.getTag();
//        }
//        holder.imageView.setImageResource(mData.get(position).getContent());
//        return convertView;
    }
    //添加一个元素
    public void add(T data){
        if(mData==null){
            mData=new ArrayList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }
    //往特定位置添加元素
    public void add(int position,T data){
        if(mData==null){
            mData=new ArrayList<>();
        }
        mData.add(position,data);
        notifyDataSetChanged();
    }
    //删除指定数据
    public void remove(T data){
        if(mData!=null){
            mData.remove(data);
        }
        notifyDataSetChanged();
    }
    //删除指定位置的数据
    public void remove(int position){
        if(mData!=null){
            mData.remove(position);
        }
        notifyDataSetChanged();
    }
    //清空数据
    public void clear(){
        if(mData!=null){
            mData.clear();
        }
        notifyDataSetChanged();
    }
    //把getView()中的一些逻辑搬到ViewHolder中
    public static class ViewHolder{
        private SparseArray<View> mViews; //存储ListView的item中的view
        private View item;    //存放convertView
        private int position; //游标
        private Context context; //Context上下文

        private ViewHolder(Context context,ViewGroup parent,int layoutRes){
            mViews=new SparseArray<>();
            this.context=context;
            View convertView=LayoutInflater.from(context).inflate(layoutRes,parent,false);
            convertView.setTag(this);
            item=convertView;
        }
        //绑定ViewHolder与Item
        public static ViewHolder bind(Context context,View convertView,ViewGroup parent,int layoutRes,int position){
            ViewHolder holder;
            if(convertView==null){
                holder=new ViewHolder(context,parent,layoutRes);
            }else {
                holder=(ViewHolder)convertView.getTag();
                holder.item=convertView;
            }
            holder.position=position;
            return holder;
        }
        //根据id获取集合中保存的控件
        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int id){
            T t=(T)mViews.get(id);
            if(t==null){
                t=(T)item.findViewById(id);
                mViews.put(id,t);
            }
            return t;
        }
        //根据需要添加扩展方法
        /**
         * 获取当前条目
         */
        public View getItemView() {
            return item;
        }

        /**
         * 获取条目位置
         */
        public int getItemPosition() {
            return position;
        }

        /**
         * 设置文字
         */
        public ViewHolder setText(int id, CharSequence text) {
            View view = getView(id);
            if(view instanceof TextView) {
                ((TextView) view).setText(text);
            }
            return this;
        }

        /**
         * 设置图片
         */
        public ViewHolder setImageResource(int id, int drawableRes) {
            View view = getView(id);
            if(view instanceof ImageView) {
                ((ImageView) view).setImageResource(drawableRes);
            } else {
                view.setBackgroundResource(drawableRes);
            }
            return this;
        }


        /**
         * 设置点击监听
         */
        public ViewHolder setOnClickListener(int id, View.OnClickListener listener) {
            getView(id).setOnClickListener(listener);
            return this;
        }

        /**
         * 设置可见
         */
        public ViewHolder setVisibility(int id, int visible) {
            getView(id).setVisibility(visible);
            return this;
        }

        /**
         * 设置标签
         */
        public ViewHolder setTag(int id, Object obj) {
            getView(id).setTag(obj);
            return this;
        }
        //其他方法可自行扩展
    }
    public abstract void bindView(ViewHolder holder,T obj);

}

package com.jhs.taolibao.base.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dds on 16/4/9.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListener1 mOnItemClickListener1;
    private OnItemLongClickListener mOnItemLongClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemClickListener1(OnItemClickListener1 onItemClickListener1) {
        this.mOnItemClickListener1 = onItemClickListener1;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    /**
     * add data
     */
    public void addDataToAdapater(T e) {
        addDataToAdapater(e, false);
    }

    public void deleteDataToAdapater(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }


    public void addDataToAdapater(T e, boolean isReset) {
        if (isReset) {
            mDatas.clear();
        }
        if (mDatas.size() <= 0) {
            mDatas.add(e);
            notifyDataSetChanged();
        } else {
            mDatas.add(mDatas.size(), e);
            notifyItemInserted(mDatas.size());
        }

    }

    /**
     * add datas
     */
    public void addDataToAdapater(List<T> es) {
        addDataToAdapater(es, false);
    }

    public void addDataToAdapater(List<T> es, boolean isReset) {
        if (isReset) {
            mDatas.clear();
        }
        if (es != null) {
            mDatas.addAll(es);
        }
        notifyDataSetChanged();
    }

    public CommonAdapter(Context context, int layoutId) {
        this.mContext = context;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
        this.mDatas = new ArrayList<T>();
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }

                if (mOnItemClickListener1 != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener1.onItemClick1(parent, v, mDatas.get(position - 1), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemLongClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
                }
                return false;
            }
        });


    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(ViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


}

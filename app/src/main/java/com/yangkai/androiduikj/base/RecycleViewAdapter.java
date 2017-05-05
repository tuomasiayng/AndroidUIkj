package com.yangkai.androiduikj.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @param <T>
 */
public abstract class RecycleViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private View mContentView;

    private List<T> mData;

    private int mLayoutResId;

    private LayoutInflater mLayoutInflater;

    private OnRecyclerViewItemChildClickListener onRecyclerViewItemClickListener;

    public RecycleViewAdapter(int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public RecycleViewAdapter(View contentView, List<T> data) {
        this(0, data);
        mContentView = contentView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder;
        int headerCount = 0;
        this.mLayoutInflater = LayoutInflater.from(parent.getContext());
        baseViewHolder = onCreateDefViewHolder(parent, viewType);
       /* if (parent instanceof XRecyclerView) {
            headerCount = ((XRecyclerView) parent).getHeaderViewCount();
        }*/
        initItemClickListener(baseViewHolder, headerCount);
        return baseViewHolder;
    }


    private BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, mLayoutResId);
    }


    private void initItemClickListener(final BaseViewHolder baseViewHolder, final int headerCount) {
        if (onRecyclerViewItemClickListener != null) {
            baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemChildClick(RecycleViewAdapter.this, v,
                            baseViewHolder.getLayoutPosition() - headerCount);
                }
            });
        }
    }

    protected BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutResId) {
        if (mContentView == null) {
            return new BaseViewHolder(getItemView(layoutResId, parent));
        }
        return new BaseViewHolder(mContentView);
    }

    protected View getItemView(int layoutResId, ViewGroup parent) {
        View view = mLayoutInflater.inflate(layoutResId, parent, false);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return view;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        convert((BaseViewHolder) holder,
                mData.get(position));
    }

    protected abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<T> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addDataAndNotify(List<T> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addItemAndNotify(T t) {
        this.mData.add(t);
        notifyItemInserted(this.mData.size());
    }

    public void removeItemAndNotify(int position) {
        this.mData.remove(position);
        position++;
        notifyItemRemoved(position);
    }

    public void removeAllAndNotify() {
        int len = this.mData.size();
        for (int i = len - 1; i >= 0; i--) {
            this.mData.remove(i);
            notifyItemRemoved(i);
        }
    }

    public List getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void setOnRecyclerViewItemChildClickListener(OnRecyclerViewItemChildClickListener childClickListener) {
        this.onRecyclerViewItemClickListener = childClickListener;
    }

    public interface OnRecyclerViewItemChildClickListener {

        void onItemChildClick(RecycleViewAdapter adapter, View view, int position);
    }

}

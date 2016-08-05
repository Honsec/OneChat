package genius.rv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import genius.rv.ViewHolder;
import genius.rv.support.OnItemClickListener;

/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;

    private  View emptyView;
    public void notifyWithEmptyDatasetChanged(){
        notifyDataSetChanged();
        checkEmpty();
    }

    private  void checkEmpty() {
        if (emptyView != null) {
            if (getmDatas().size() < 1) {
                if (emptyView.getVisibility() != View.VISIBLE) {
                    emptyView.setVisibility(View.VISIBLE);
                }
            } else {
                if (emptyView.getVisibility() != View.INVISIBLE) {
                    emptyView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.mOnItemClickListener = onItemClickListener;
    }

    public CommonAdapter(Context context, int layoutId, List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);

        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder)
    {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType)
    {
        return true;
    }


    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType)
    {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                if (mOnItemClickListener != null)
                {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
                }
                return false;
            }
        });
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.updatePosition(position);
        convert(holder,getPosition(holder),mDatas.get(position));
    }

    public abstract void convert(ViewHolder holder,int position ,T t);

    @Override
    public int getItemCount()
    {
        return mDatas.size();
    }


    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}

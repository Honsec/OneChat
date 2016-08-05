package genius.rv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import genius.rv.adapter.CommonAdapter;

/**
 * https://gist.github.com/adelnizamutdinov/31c8f054d1af4588dc5c
 */
public class EmptyRecyclerView extends RecyclerView
{
    View emptyView;
    private CommonAdapter tCommonAdapter;

    public EmptyRecyclerView(Context context)
    {
        super(context);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    void checkIfEmpty()
    {
        if (emptyView != null)
        {
            emptyView.setVisibility(tCommonAdapter.getmDatas().size() > 0 ? GONE : VISIBLE);
        }
    }

    final AdapterDataObserver observer = new AdapterDataObserver()
    {
        @Override
        public void onChanged()
        {
            super.onChanged();
            checkIfEmpty();
        }
    };

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews)
    {
        final Adapter oldAdapter =tCommonAdapter;
        if (oldAdapter != null)
        {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }

        if (adapter != null)
        {
            adapter.registerAdapterDataObserver(observer);
        }
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
        checkIfEmpty();
    }

    @Override
    public void setAdapter(Adapter adapter)
    {
        final Adapter oldAdapter = tCommonAdapter;
        if (oldAdapter != null)
        {
            oldAdapter.unregisterAdapterDataObserver(observer);
        }
        super.setAdapter(adapter);
        if (adapter != null)
        {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void setEmptyView(View emptyView, CommonAdapter tCommonAdapter)
    {
        this.emptyView = emptyView;
        this.tCommonAdapter = tCommonAdapter;
        checkIfEmpty();
    }
}
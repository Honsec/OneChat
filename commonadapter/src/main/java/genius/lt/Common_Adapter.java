package genius.lt;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 
 * @author HongSec
 *
 * @param <T>
 */
public abstract class Common_Adapter<T>  extends BaseAdapter{
	public  List<T> mDatas;
	private Context context;
	private int layoutid;
	
	public void setmDatas(List<T> mDatas) {
		this.mDatas = mDatas;
	}


	public List<T> getmDatas() {
		return mDatas;
	}

	public Common_Adapter(Context mContext, int layoutid, List<T> mDatas){
		this.context=mContext;
		this.layoutid=layoutid;
		this.mDatas=mDatas;
	}
	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position,View convertView, ViewGroup parent) {
		if(getViewTypeCount()==1||getItemViewType(position)==0){
			return getDefault_view(position, convertView, null);
		}else{
			return getOtherView(convertView,position,null);
		}
	}


	protected View getDefault_view(int position, View convertView,
								   ViewGroup parent) {
			ViewHolder holder= ViewHolder.getInstance(context, convertView, parent,layoutid, position);
			getDefaultView(holder,position,this.mDatas.get(position));
			return holder.getConvertView();
	}

	/**
	 *  혹은 ViewTypeCount가 1일때, ItemType 가 0 일떄
	 * @param holder
	 * @param position
	 * @param data
	 */
	public abstract void getDefaultView(ViewHolder holder, int position, T data) ;
	
	/**
	 * ItemType 가 0이 아닐떄, ViewTypeCount가 1이상일때 사용
	 * @param convertView
	 * @param position
	 * @param parent
	 * @return
	 */
	public abstract View getOtherView(View convertView, int position, ViewGroup parent);
}

package genius.lt;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author HongSec
 *
 */
public class ViewHolder {
	/**
	 * save Views
	 */
	private final SparseArray<View> mViews;
	
	/**
	 * Save ConvertViews
	 */
	private View mConvertView;
	/**
	 * Init convertView and setTag
	 * @param context
	 * @param viewGroup
	 * @param LayoutId
	 * @param position
	 */
	public ViewHolder(Context context, ViewGroup viewGroup, int LayoutId, int position) {
		this.mViews =new SparseArray<View>();
		this.mConvertView=LayoutInflater.from(context).inflate(LayoutId,viewGroup,false);
		mConvertView.setTag(this);
	}
	
	/**
	 * new ConvertView or getTag from holder
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder getInstance(Context context, View convertView,
										 ViewGroup parent, int layoutId, int position){
			if(convertView==null){
				return new ViewHolder(context, parent, layoutId, position);
			}else{
				return (ViewHolder) convertView.getTag();
			}
	}
	
	
	/**
	 * get View ，if not exist find by id
	 * @param viewId
	 * @return
	 */
	public <T extends View>T getView(int viewId){
		View view=mViews.get(viewId);
		if(view==null){
			view=mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}	
	
	/**
	 * ConvertView
	 * @return
	 */
	public View getConvertView(){
		return mConvertView;
	}
 
	/**
	 * SetText
	 * @param viewid
	 * @param txt
	 * @return
	 */
	public ViewHolder setText(int viewid, String txt){
		View view=getView(viewid);

		if (view instanceof TextView)
			((TextView) view).setText(txt);
		return this;
	}

	public ViewHolder setText(int viewid, Spanned txt) {
		View view = getView(viewid);

		if(view instanceof TextView)
		((TextView)view).setText(txt);
		return this;
	}

	public ViewHolder setTextColor(int viewid, int color){
		View view=getView(viewid);

		if(view instanceof TextView)
		((TextView)view).setTextColor(color);
		return this;
	}

	public ViewHolder setImageBitmap(int viewid, Bitmap bitmap){
		View view=getView(viewid);
		if(view instanceof ImageView){
			((ImageView)view).setImageBitmap(bitmap);
		}
		return this;
	}
	public ViewHolder setImageResource(int viewid, int source){
		View view=getView(viewid);
		if(view instanceof ImageView){
			((ImageView)view).setImageResource(source);
		}
		return this;
	}
	
	public ViewHolder setCheck(int viewid, boolean bool){
		View view=getView(viewid);
		if(view instanceof CompoundButton){
			((CompoundButton)view).setChecked(bool);
		}
		return this;
	}
	
	public ViewHolder setVisibility(int viewid, int visiable){
		View view=getView(viewid);
		view.setVisibility(visiable);
		return this;
	}
		
}

package lifeassistant.zk.com.zkframework.framework.base;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/***
 * 基础的adapter
 * 
 * @author 099
 * 
 * @param <T>
 */
public abstract class IBaseAdapter<T> extends BaseAdapter {
	/** 上下文 **/
	public Context mContext;
	/** 适配数据 */
	public List<T> list;

	private ViewGroup parent;
	
	private int layoutResId = -1;
	
	private LayoutInflater layoutInflater;
	
	public IBaseAdapter(Context context){
		this.mContext = context;
		layoutInflater = LayoutInflater.from(context);
	}

	public IBaseAdapter(Context context, List<T> list) {
		this(context);
		addListData(list);

	}
	public IBaseAdapter(Context context,int layoutResId){
		this(context);
		this.layoutResId = layoutResId;
	}
	public IBaseAdapter(Context context,List<T> list,int layoutResId){
		this(context);
		this.layoutResId = layoutResId;
		addListData(list);
	}
	
	public List<T> getList(){
		return list;
	}
	

	@Override
	public int getCount() {
		return list == null ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return (list != null && list.size() > 0) ? list.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.parent = parent;
		BaseViewHolder holder = null;
		if (convertView != null) {
			holder = (BaseViewHolder) convertView.getTag();
			if(holder!=null){
				holder.position=position;
			}
		} else {
			convertView = initAdaperView(layoutInflater, position,parent);
			holder = initViewHolder(convertView);
			if(holder!=null&&convertView!=null){
				holder.position=position;
				
				convertView.setTag(holder);
			}
			

		}
		setView(holder, position, convertView, parent);
		return convertView;
	}

	/** 初始化AdapterViwe */
	public   View initAdaperView(LayoutInflater layoutInflater,int position, ViewGroup parent){
		if(layoutResId!=-1)
		{
			return layoutInflater.inflate(layoutResId, null);
		}
		return null;
	}
	public abstract BaseViewHolder initViewHolder(View view);
	/**
	 * 绑定数据
	 * @param holder
	 * @param position
	 * @param convertView
	 * @param parent
	 */
	public abstract void setView(BaseViewHolder holder, final int position,
			View convertView, ViewGroup parent);

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null) {
			super.unregisterDataSetObserver(observer);
		}
	}

	/***
	 * 通知数据更新
	 * 
	 * @param list
	 */
	public void notifyDataChanged(List<T> list) {
		addListData(list);
		notifyDataSetChanged();
	}

	/***
	 * 添加list的数据
	 * 
	 * @param list
	 */
	private void addListData(List<T> list) {
		if (this.list == null) {
			this.list = new ArrayList<T>();
		}
		this.list.clear();
		this.list.addAll(list);
	}
	
	
	/**
	 * 添加元素到头
	 * @param item
	 */
	public void addItemFirst(T item){
		this.list.add(0, item);
		this.notifyDataSetChanged();
	}
	/**
	 * 添加元素
	 * @param position
	 * @param item
	 */
	public void addItem(int position,T item){
		this.list.add(position, item);
		this.notifyDataSetChanged();
	}
	/**
	 * 移除元素
	 * @param item
	 */
	public void removeItem(T item){
		this.list.remove(item);
		this.notifyDataSetChanged();
	}
	/**
	 * 移除元素
	 * @param position
	 */
	public void removeItem(int position){
		this.list.remove(position);
		this.notifyDataSetChanged();
	}
	/**
	 * 获取位置对应的子View,一般用于直接更新数据，避免使用adapter.notifyDataSetChanged，可以节省效率。
	 * 如果当前position对应的View不再界面中，返回null.
	 * 
	 * @param position
	 * @return
	 */
	public View getViewByPosition(int position) {
		if (this.parent != null) {
			for (int index = 0; index < this.parent.getChildCount(); index++) {
				View view = this.parent.getChildAt(index);
				BaseViewHolder viewHolder = (BaseViewHolder) view.getTag();
				if (viewHolder == null) {
					continue;
				} else {
					if (viewHolder.position == position) {
						return view;
					}
				}
			}
		}
		return null;
	}

	/**
	 * 更新第position个条目，运行于主线程
	 * 
	 * @param position
	 */
	public void invalidateItem(int position) {
		View view = getViewByPosition(position);
		if (view != null)
			view.invalidate();
	}
	/**
	 * 更新某个条目by T
	 * @param item
	 */
	public void invalidateItemByObj(T item){
		int position=list.indexOf(item);
		if(position>=0){
			invalidateItem(position);
		}
	}
	
	

	/** 基础的ViewHolder */
	public class BaseViewHolder {
		public int position;
	}

}

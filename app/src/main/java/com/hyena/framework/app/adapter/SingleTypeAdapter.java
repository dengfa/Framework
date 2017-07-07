/**
 * Copyright (C) 2014 The AndroidSupport Project
 */
package com.hyena.framework.app.adapter;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 单类型Adapter
 * @author yangzc
 * @param <T> 泛型
 */
public abstract class SingleTypeAdapter<T> extends BaseAdapter {

	private   List<T> mItems;
	protected Context mContext;
	
	public SingleTypeAdapter(Context context) {
		super();
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		if(mItems == null)
			return 0;
		return mItems.size();
	}

	@Override
	public T getItem(int position) {
		if(mItems == null)
			return null;
		if(position < mItems.size())
			return mItems.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public void setItems(List<T> items){
		this.mItems = items;
		notifyDataSetChanged();
	}
	
	public void addItems(List<T> items) {
		if(mItems != null) {
			this.mItems.addAll(items);
			notifyDataSetChanged();
		}
	}
	
	public List<T> getItems(){
		return mItems;
	}
	
	public void removeItem(T t){
		if(mItems.contains(t)) {
			mItems.remove(t);
			notifyDataSetChanged();
		}
	}
}

package com.ksy.djd.util;



import com.ksy.djd.model.ModelBase;

import android.view.View;
import android.view.ViewGroup;

/**
 * 视图绑定接口
 */
public interface DataViewBinder<T extends ModelBase> {
	View getView(final T data, View convertView, ViewGroup parent);
}

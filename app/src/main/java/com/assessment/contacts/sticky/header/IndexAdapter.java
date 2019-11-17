package com.assessment.contacts.sticky.header;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.contacts.R;
import com.assessment.contacts.base.BaseRecyclerViewListAdapter;

import java.util.List;

public class IndexAdapter<M extends IIndexItem>
		extends BaseRecyclerViewListAdapter<IndexAdapter.IndexViewHolder, M, List<M>> {

	/**
	 * The context object is mainly used to initialize the Layout inflater, since we need layout inflater in every
	 * single onCreateViewHolder method call, its effective to instantiate the inflater at the time of constructing
	 * this adapter.
	 *
	 * @param context Context from which the adapter class is invoked.
	 */
	IndexAdapter(Context context) {
		super(context);
	}

	@Override
	protected IndexViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater mInflater) {
		return new IndexViewHolder(mInflater.inflate(R.layout.partial_sticky_header_item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull IndexViewHolder holder, int position) {
		holder.tvIndexView.setText(getIndexStringAt(position));
		holder.tvIndexView.setVisibility(isStickyHeader(position) ? View.VISIBLE : View.INVISIBLE);
	}

	private boolean isStickyHeader(int position) {
		return position == 0 || !getIndexStringAt(position - 1).equals(getIndexStringAt(position));
	}

	private String getIndexStringAt(int position) {
		IIndexItem item = getItem(position);
		return null != item ? item.getInitial() : "";
	}

	static class IndexViewHolder extends RecyclerView.ViewHolder {

		private final TextView tvIndexView;

		IndexViewHolder(@NonNull View itemView) {
			super(itemView);
			tvIndexView = (TextView) itemView;
		}
	}
}

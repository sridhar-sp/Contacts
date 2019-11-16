package com.assessment.contacts.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.contacts.R;
import com.assessment.contacts.base.BaseRecyclerViewListAdapter;
import com.assessment.contacts.list.model.IContactItem;

import java.util.List;

public class ContactListAdapter<M extends IContactItem> extends
		BaseRecyclerViewListAdapter<ContactListAdapter.ContactViewHolder, M, List<M>> {

	/**
	 * The context object is mainly used to initialize the Layout inflater, since we need layout inflater in every
	 * single onCreateViewHolder method call, its effective to instantiate the inflater at the time of constructing
	 * this adapter.
	 *
	 * @param context Context from which the adapter class is invoked.
	 */
	public ContactListAdapter(Context context) {
		super(context);
	}

	@Override
	protected ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater mInflater) {
		ContactViewHolder viewHolder = new ContactViewHolder(mInflater.inflate(R.layout.partial_contact_list_item,
				parent, false));
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

	}

	static class ContactViewHolder extends RecyclerView.ViewHolder {

		private ContactViewHolder(@NonNull View itemView) {
			super(itemView);
		}
	}
}

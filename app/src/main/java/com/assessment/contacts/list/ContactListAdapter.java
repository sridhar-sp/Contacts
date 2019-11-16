package com.assessment.contacts.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.contacts.R;
import com.assessment.contacts.base.BaseRecyclerViewListAdapter;
import com.assessment.contacts.database.table.model.IContactItem;

import java.util.List;
import java.util.Random;

public class ContactListAdapter<M extends IContactItem> extends
		BaseRecyclerViewListAdapter<ContactListAdapter.ContactViewHolder, M, List<M>> {

	private int[] mInitialColors;

	private Random mRandom = new Random();

	/**
	 * The context object is mainly used to initialize the Layout inflater, since we need layout inflater in every
	 * single onCreateViewHolder method call, its effective to instantiate the inflater at the time of constructing
	 * this adapter.
	 *
	 * @param context Context from which the adapter class is invoked.
	 */
	ContactListAdapter(Context context) {
		super(context);
		mInitialColors = context.getResources().getIntArray(R.array.contactInitialBackgroundColors);
	}

	@Override
	protected ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater mInflater) {
		return new ContactViewHolder(mInflater.inflate(R.layout.partial_contact_list_item, parent, false));
	}

	@Override
	public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

		IContactItem iContactItem = getItem(position);

		if (null == iContactItem)
			return;

		holder.tvContactName.setText(iContactItem.getText());
		holder.tvContactInitial.setText(iContactItem.getInitial());

		if (iContactItem.getInitialBackgroundColor() == -1)
			iContactItem.setInitialBackgroundColor(mInitialColors[mRandom.nextInt(mInitialColors.length)]);

		holder.tvContactInitial.getBackground().setTint(iContactItem.getInitialBackgroundColor());
	}

	static class ContactViewHolder extends RecyclerView.ViewHolder {

		private final TextView tvContactInitial;

		private final TextView tvContactName;

		private ContactViewHolder(@NonNull View itemView) {
			super(itemView);
			tvContactInitial = itemView.findViewById(R.id.tv_partial_contact_item_initial);
			tvContactName = itemView.findViewById(R.id.tv_partial_contact_item_name);
		}
	}
}

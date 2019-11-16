package com.assessment.contacts.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Base RecyclerView adapter, takes care of all the common routine needed to when constructing RecyclerAdapter.
 * <p>
 * The common routine includes.
 * - Maintaining the data-set,
 * - Caching of {@link LayoutInflater},
 * - Provides Efficient click delegation,
 * - etc.
 * <p>
 *
 * @param <V> Type of View Holder.
 * @param <M> Type of Model object used in the data-set.
 * @param <D> Type of data-set must be an child of {@link List}.
 **/
public abstract class BaseRecyclerViewListAdapter<V extends RecyclerView.ViewHolder, M, D extends List<M>>
		extends RecyclerView.Adapter<V> {

	/**
	 * Data-set to populate on the recycler view.
	 */
	private D mDataSet;

	/**
	 * This instance will get a valid reference to inflater object at the time of constructing this class.
	 * This variable is made global just because it is used every the time when a new view has to
	 * be brought in to the screen.
	 * <p>
	 * This instance is passed as one of the arguments in onCreateViewHolder method, to inflate new views.
	 */
	private LayoutInflater mInflater;

	/**
	 * ItemViewClickDelegate instance to delegate the various click events to the caller of
	 * {@link #setItemViewClickDelegate(ItemViewClickDelegate)}.
	 */
	private ItemViewClickDelegate<M> mItemViewClickDelegate;

	/**
	 * The context object is mainly used to initialize the Layout inflater, since we need layout inflater in every
	 * single onCreateViewHolder method call, its effective to instantiate the inflater at the time of constructing
	 * this adapter.
	 *
	 * @param context Context from which the adapter class is invoked.
	 */
	public BaseRecyclerViewListAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
		this.mDataSet = null;
		mItemViewClickDelegate = null;
	}

	@NonNull
	@Override
	public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return onCreateViewHolder(parent, viewType, mInflater);
	}

	@Override
	public int getItemCount() {
		if (null != mDataSet)
			return mDataSet.size();
		return 0;
	}

	/**
	 * This version of onCreateViewHolder method provide the inflater instance as the additional parameter, which
	 * will be handy to inflate the view.
	 *
	 * @param parent    RootView of the layout.
	 * @param viewType  View type.
	 * @param mInflater Inflater instance.
	 * @return ViewHolder instance.
	 */
	protected abstract V onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater mInflater);

	/**
	 * Accessor method to get the ItemViewClickDelegate instance.
	 *
	 * @return instance of ItemViewClickDelegate.
	 */
	protected ItemViewClickDelegate<M> getItemViewClickDelegate() {
		return mItemViewClickDelegate;
	}

	/**
	 * Callback instance to notify when the view is clicked.
	 *
	 * @param mItemViewClickDelegate callback instance.
	 */
	public void setItemViewClickDelegate(ItemViewClickDelegate<M> mItemViewClickDelegate) {
		this.mItemViewClickDelegate = mItemViewClickDelegate;
	}

	/**
	 * Return the data-set, can be null if no data-set reference is assgined via {@link #setDataSet(List)} so far.
	 *
	 * @return The data-set reference.
	 */
	@Nullable
	public D getDataSet() {
		return mDataSet;
	}

	/**
	 * Method to set the reference to mDataSet object.
	 *
	 * @param mDataSet data-set reference.
	 */
	@UiThread
	public final void setDataSet(@NonNull D mDataSet) {
		this.mDataSet = mDataSet;
		notifyDataSetChanged();
	}

	/**
	 * Clear the entire data-set.
	 */
	public void clearDataSet() {
		D dataSet = getDataSet();
		if (null != dataSet) {
			dataSet.clear();
			notifyDataSetChanged();
		}
	}

	/**
	 * Try to retrieve the item at {@code pos}.
	 *
	 * @param pos The position to retrieve the item.
	 * @return The object at pos upon successful, null on error scenarios.
	 */
	@Nullable
	public final M getItem(int pos) {
		D dataSet = getDataSet();
		if (null == dataSet)
			return null;
		if (pos >= 0 && pos < dataSet.size())
			return dataSet.get(pos);
		return null;
	}


	/**
	 * Callback Interface, the click events are delivered to this instance.
	 */
	public interface ItemViewClickDelegate<M> {

		/**
		 * This method called when the recycler item view is clicked.
		 *
		 * @param viewClicked     View object that is clicked.
		 * @param adapterPosition Corresponding adapter position of the clicked view.
		 * @param item            Corresponding model associated with the {@code adapterPosition}.
		 */
		void onItemViewClick(View viewClicked, int adapterPosition, @Nullable M item);

	}

	/**
	 * Helper class to map and delegate the click events with its corresponding adapter position
	 * to the registered callback instance {@link #setItemViewClickDelegate(ItemViewClickDelegate)}.
	 */
	public class SimpleViewClickListener implements View.OnClickListener {

		/**
		 * Base reference to the view holder object.
		 */
		RecyclerView.ViewHolder viewHolder;

		/**
		 * Keeping the view holder reference, to query the corresponding view position when the view is clicked.
		 *
		 * @param viewHolder Base reference to the view holder object.
		 */
		public SimpleViewClickListener(RecyclerView.ViewHolder viewHolder) {
			this.viewHolder = viewHolder;
		}

		@Override
		public void onClick(View v) {
			ItemViewClickDelegate<M> itemViewClickDelegate = getItemViewClickDelegate();
			if (null != itemViewClickDelegate) {
				int adapterPosition = viewHolder.getAdapterPosition();
				itemViewClickDelegate.onItemViewClick(v, adapterPosition, getItem(adapterPosition));
			}
		}

	}
}

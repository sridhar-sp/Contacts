package com.assessment.contacts.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.assessment.contacts.AppExecutorService;
import com.assessment.contacts.R;
import com.assessment.contacts.database.table.model.ContactMinimal;
import com.assessment.contacts.list.model.ContactListViewModel;
import com.assessment.contacts.sticky.header.StickyHeaderLayout;

import java.util.List;

public class ContactListFragment extends Fragment {

	private ContactListViewModel mContactViewModel;

	private ContactListAdapter<ContactMinimal> mContactListAdapter;

	private StickyHeaderLayout<ContactMinimal> mStickyHeaderLayout;

	private SwipeRefreshLayout swipeRefreshLayout;

	private TextView tvEmptyContactsPlaceholder;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.contact_list_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		findGlobalViews(view);
		setupContactList(view);
		setupSwipeRefreshListener();

		mContactViewModel = ViewModelProviders.of(this).get(ContactListViewModel.class);
		mContactViewModel.getContactMinimalList().observe(this, this::onContactsListChanged);

		mContactViewModel.loadAllContacts();
		swipeRefreshLayout.setRefreshing(true);
	}

	private void findGlobalViews(@NonNull View view) {
		swipeRefreshLayout = view.findViewById(R.id.srl_cl_frag_list_refresh);
		tvEmptyContactsPlaceholder = view.findViewById(R.id.tv_cl_frag_contact_list_empty);
	}

	private void setupContactList(View view) {
		RecyclerView rvContactList = view.findViewById(R.id.rv_cl_frag_contact_list);

		mContactListAdapter = new ContactListAdapter<>(getContext());
		rvContactList.setAdapter(mContactListAdapter);

		mStickyHeaderLayout = view.findViewById(R.id.shl_cl_frag_sticky_header);
		mStickyHeaderLayout.attachWithContentRecyclerView(rvContactList);

	}

	private void setupSwipeRefreshListener() {
		swipeRefreshLayout.setOnRefreshListener(() -> mContactViewModel.loadAllContacts());
	}

	private void onContactsListChanged(List<ContactMinimal> contactMinimals) {

		mContactListAdapter.setDataSet(contactMinimals);
		mStickyHeaderLayout.setIndexDataSet(contactMinimals);

		tvEmptyContactsPlaceholder.setVisibility(contactMinimals.isEmpty() ? View.VISIBLE : View.GONE);
		swipeRefreshLayout.setRefreshing(false);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_contact_list, menu);

		MenuItem searchMenuItem = menu.findItem(R.id.menu_item_search);

		SearchView searchView = (SearchView) searchMenuItem.getActionView();

		searchView.setOnQueryTextListener(new SearchViewQueryTextListener());

		searchMenuItem.setOnActionExpandListener(new SearchViewActionExpandListener(menu.findItem(R.id.menu_item_sort)));
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.menu_item_sort) {
			mContactViewModel.toggleSort();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class SearchViewActionExpandListener implements MenuItem.OnActionExpandListener {

		private MenuItem mSortMenuItem;

		private SearchViewActionExpandListener(MenuItem sortMenuItem) {
			this.mSortMenuItem = sortMenuItem;
		}

		@Override
		public boolean onMenuItemActionExpand(MenuItem item) {
			mSortMenuItem.setVisible(false);
			mContactViewModel.enterSearchMode();
			tvEmptyContactsPlaceholder.setText(R.string.text_no_search_results);
			return true;
		}

		@Override
		public boolean onMenuItemActionCollapse(MenuItem item) {
			mSortMenuItem.setVisible(true);
			//Menu collapse will clear the search view text, that wiil call
			// {@link SearchViewQueryTextListener#onQueryTextChange} one last time after this call executes. hence
			// exit from search mode in the next UI cycle.
			AppExecutorService.getInstance().getMainThreadExecutor().execute(() -> mContactViewModel.exitSearchMode());
			tvEmptyContactsPlaceholder.setText(R.string.text_empty_contacts);
			return true;
		}
	}

	private class SearchViewQueryTextListener implements SearchView.OnQueryTextListener {
		@Override
		public boolean onQueryTextSubmit(String query) {
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			mContactViewModel.searchContactAsync(newText);
			return false;
		}
	}
}

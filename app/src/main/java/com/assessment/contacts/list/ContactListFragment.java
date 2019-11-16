package com.assessment.contacts.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.contacts.Logger;
import com.assessment.contacts.R;
import com.assessment.contacts.database.table.model.ContactMinimal;
import com.assessment.contacts.list.model.ContactListViewModel;

public class ContactListFragment extends Fragment {

	private static final String TAG = ContactListFragment.class.getSimpleName();

	private ContactListViewModel mViewModel;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                         @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.contact_list_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		mViewModel = ViewModelProviders.of(this).get(ContactListViewModel.class);

		setupContactList(view);
	}

	private void setupContactList(View view) {
		RecyclerView recyclerView = view.findViewById(R.id.rv_cl_frag_contact_list);

		ContactListAdapter<ContactMinimal> adapter = new ContactListAdapter<>(getActivity());
		recyclerView.setAdapter(adapter);

		mViewModel.getContactMinimalList().observe(this, adapter::setDataSet);

		mViewModel.loadAllContacts();
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
		switch (item.getItemId()) {
			case R.id.menu_item_search:
				return true;
			case R.id.menu_item_sort:
				mViewModel.toggleSort();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private class SearchViewActionExpandListener implements MenuItem.OnActionExpandListener {

		private MenuItem mSortMenuItem;

		private SearchViewActionExpandListener(MenuItem sortMenuItem) {
			this.mSortMenuItem = sortMenuItem;
		}

		@Override
		public boolean onMenuItemActionExpand(MenuItem item) {
			mSortMenuItem.setVisible(false);
			return true;
		}

		@Override
		public boolean onMenuItemActionCollapse(MenuItem item) {
			mSortMenuItem.setVisible(true);
			return true;
		}
	}

	private class SearchViewQueryTextListener implements SearchView.OnQueryTextListener {
		@Override
		public boolean onQueryTextSubmit(String query) {
			Logger.log(TAG, "onQueryTextSubmit " + query);
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			Logger.log(TAG, "onQueryTextChange " + newText);
			return false;
		}
	}
}

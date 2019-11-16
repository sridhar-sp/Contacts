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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.contacts.R;
import com.assessment.contacts.list.model.ContactListViewModel;
import com.assessment.contacts.list.model.ContactMinimal;

public class ContactListFragment extends Fragment {

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

		mViewModel.getContactList().observe(this, adapter::setDataSet);
	}

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.menu_contact_list, menu);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_item_search:
				return true;
			case R.id.menu_item_sort:
				mViewModel.mockData();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

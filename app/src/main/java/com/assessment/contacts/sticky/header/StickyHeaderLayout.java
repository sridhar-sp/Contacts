package com.assessment.contacts.sticky.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.contacts.Logger;
import com.assessment.contacts.R;

import java.util.List;

public class StickyHeaderLayout<M extends IIndexItem> extends RelativeLayout {

	private static final String TAG = StickyHeaderLayout.class.getSimpleName();

	private RecyclerView rvGroupIndex;

	private TextView tvStickyIndex;

	private IndexAdapter<M> indexAdapter;

	public StickyHeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public StickyHeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public StickyHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	public StickyHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	private void init(Context context) {
		inflate(context, R.layout.layout_sticky_header, this);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		rvGroupIndex = findViewById(R.id.rv_layout_sh_group_index_list);
		tvStickyIndex = findViewById(R.id.tv_layout_sh_sticky_index);

		indexAdapter = new IndexAdapter<>(getContext());
		rvGroupIndex.setAdapter(indexAdapter);
	}

	public void setIndexDataSet(List<M> datset) {
		indexAdapter.setDataSet(datset);
	}

	public void attachWithContentRecyclerView(@NonNull RecyclerView contentRecyclerView) {
		contentRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(@NonNull RecyclerView contentRecyclerView, int dx, int dy) {
				super.onScrolled(contentRecyclerView, dx, dy);

				if (contentRecyclerView.getChildCount() < 2 || null == contentRecyclerView.getAdapter() || contentRecyclerView.getAdapter().getItemCount() < 2)
					return;

				scrollAlongWithContentRecyclerView(contentRecyclerView);


			}

			private void scrollAlongWithContentRecyclerView(RecyclerView contentRecyclerView) {

				View firstVisibleChild = contentRecyclerView.getChildAt(0);

				LinearLayoutManager groupIndexLayoutManager = (LinearLayoutManager) rvGroupIndex.getLayoutManager();
				assert null != groupIndexLayoutManager;

				int childAdapterPosition = contentRecyclerView.getChildAdapterPosition(firstVisibleChild);
				Logger.log(TAG, "childAdapterPosition " + childAdapterPosition);
				Logger.log(TAG, "firstVisibleChild.getTop() " + firstVisibleChild.getTop());
				groupIndexLayoutManager.scrollToPositionWithOffset(
						childAdapterPosition,
						firstVisibleChild.getTop()
				);
			}
		});
	}


}

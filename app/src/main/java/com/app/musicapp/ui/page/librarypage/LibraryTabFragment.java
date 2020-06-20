package com.app.musicapp.ui.page.librarypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.app.musicapp.R;
import com.app.musicapp.ui.page.librarypage.artist.ArtistChildTab;
import com.app.musicapp.ui.page.librarypage.genre.GenreChildTab;
import com.app.musicapp.ui.page.librarypage.playlist.PlaylistChildTab;
import com.app.musicapp.ui.page.librarypage.song.SongChildTab;
import com.app.musicapp.ui.widget.fragmentnavigationcontroller.SupportFragment;
import com.app.musicapp.util.Tool;
import com.google.android.material.tabs.TabLayout;

public class LibraryTabFragment extends SupportFragment {
    private static final String TAG = "LibraryTabFragment";

    @BindView(R.id.back_image)
    ImageView mBackImage;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    public LibraryPagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    private LibraryPagerAdapter mPagerAdapter;
    @BindView(R.id.status_bar)
    View mStatusView;

    public MotionLayout getMotionLayout() {
        return mMotionLayout;
    }

    @BindView(R.id.root)
    MotionLayout mMotionLayout;

    @Nullable
    @Override
    protected View onCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.library_tab, container, false);
    }

    @OnClick(R.id.search_view)
    void searchViewClicked() {
        mSearchView.onActionViewExpanded();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //  mSearchView.onActionViewExpanded();
        // mSearchView.clearFocus();
        mStatusView.getLayoutParams().height = Tool.getStatusHeight(getResources());
        mStatusView.requestLayout();
        //  mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();


        //if(true) return;
        mViewPager.setOnTouchListener((v, event) -> getMainActivity().backStackStreamOnTouchEvent(event));
        mPagerAdapter = new LibraryPagerAdapter(getActivity(), getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        // mTabLayout.setTabsFromPagerAdapter(mTabAdapter);//deprecated
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public void onResume() {
        super.onResume();
        mSearchView.onActionViewExpanded();
        mSearchView.clearFocus();
    }

    public Fragment navigateToTab(int item) {
        if (item < mPagerAdapter.getCount()) {
            mViewPager.setCurrentItem(item, false);
            return mPagerAdapter.getItem(item);
        }
        return null;
    }

    public Fragment navigateToTab(final String tag) {
        switch (tag) {
            case SongChildTab.TAG:
                return navigateToTab(0);
            case PlaylistChildTab.TAG:
                return navigateToTab(1);
            case ArtistChildTab.TAG:
                return navigateToTab(2);
            case GenreChildTab.TAG:
                return navigateToTab(3);
            case FolderChildTab.TAG:
                return navigateToTab(4);
            default:
                return null;
        }
    }
}

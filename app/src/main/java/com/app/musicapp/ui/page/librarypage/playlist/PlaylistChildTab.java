package com.app.musicapp.ui.page.librarypage.playlist;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.musicapp.R;
import com.app.musicapp.ui.BaseActivity;
import com.app.musicapp.ui.page.BaseMusicServiceFragment;
import com.app.musicapp.ui.page.subpages.PlaylistPagerFragment;
import com.app.musicapp.loader.medialoader.PlaylistLoader;
import com.app.musicapp.model.Playlist;
import com.app.musicapp.ui.page.featurepage.FeaturePlaylistAdapter;
import com.app.musicapp.ui.widget.fragmentnavigationcontroller.SupportFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaylistChildTab extends BaseMusicServiceFragment implements FeaturePlaylistAdapter.PlaylistClickListener {
    public static final String TAG = "PlaylistChildTab";

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    PlaylistChildAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.playlist_child_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mAdapter = new PlaylistChildAdapter(getActivity(), true);
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).addMusicServiceEventListener(this);
        }
        refreshData();
        ;
    }

    private void refreshData() {
        if (getActivity() != null)
            mAdapter.setData(PlaylistLoader.getAllPlaylistsWithAuto(getActivity()));
    }

    @Override
    public void onClickPlaylist(Playlist playlist, @org.jetbrains.annotations.Nullable Bitmap bitmap) {
        SupportFragment sf = PlaylistPagerFragment.newInstance(getContext(), playlist, bitmap);
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof SupportFragment)
            ((SupportFragment) parentFragment).getNavigationController().presentFragment(sf);
    }

    @Override
    public void onMediaStoreChanged() {
        refreshData();
    }

}

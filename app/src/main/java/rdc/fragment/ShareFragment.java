package rdc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import rdc.adapter.ShareRecyclerViewAdapter;
import rdc.avtivity.R;
import rdc.bean.ShareItem;
import rdc.util.ShareUtil;


public class ShareFragment extends BottomSheetDialogFragment {

    private List<ResolveInfo> mShareResolveInfoList;
    private List<ShareItem> mShareList;
    private Context mContext;
    private static String mTitle;
    private static Uri mImageUri;

    /**
     * 获取 ShareFragment 实例
     * @param title 分享的标题
     * @param uri 需要分享的图片的Uri
     * @return ShareFragment
     */
    public static ShareFragment getInstance(String title, Uri uri) {
        mTitle = title;
        mImageUri = uri;
        return new ShareFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getContext();
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        initData();
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.fragment_share_recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        ShareRecyclerViewAdapter adapter = new ShareRecyclerViewAdapter(mShareList, mContext);
        adapter.setOnClickShareItemListener(new ShareRecyclerViewAdapter.OnClickShareItemListener() {
            @Override
            public void OnClick(int position) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_STREAM, mImageUri);
                intent.setType("image/*");
                ActivityInfo activityInfo = mShareResolveInfoList.get(position).activityInfo;
                intent.setClassName(activityInfo.packageName, activityInfo.name);
                intent.putExtra(Intent.EXTRA_TITLE, mTitle);
                startActivity(intent);
                dismiss();
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void initData() {
        mShareList = new ArrayList<>();
        mShareResolveInfoList = ShareUtil.getShareList(mContext);
        for (int i = 0; i < mShareResolveInfoList.size(); i++) {
            ShareItem item = new ShareItem();
            item.setIcon(mShareResolveInfoList.get(i).loadIcon(mContext.getPackageManager()));
            item.setLabel(mShareResolveInfoList.get(i).loadLabel(mContext.getPackageManager()).toString());
            mShareList.add(item);
        }
    }
}

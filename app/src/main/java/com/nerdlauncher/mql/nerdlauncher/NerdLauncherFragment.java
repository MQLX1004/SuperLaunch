package com.nerdlauncher.mql.nerdlauncher;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MQL on 2017/3/30.
 */
public class NerdLauncherFragment extends Fragment {
    private static final String TAG = "NerdLauncherFragment";
    private RecyclerView mRecyclerView;

    public static NerdLauncherFragment newIntence(){
        return new NerdLauncherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_nerd_launcher,container,false);
        mRecyclerView=(RecyclerView) view.findViewById(R.id.fragment_nerd_launcher_recycle_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        steupAdapter();

        return view;
    }
    private void steupAdapter(){
        Intent startupIntent=new Intent(Intent.ACTION_MAIN);//以启动类型作为参数创建Intent
        startupIntent.addCategory(Intent.CATEGORY_LAUNCHER);//添加类型过滤器
        PackageManager pm=getActivity().getPackageManager();//获取包管理器
        List<ResolveInfo> activities=pm.queryIntentActivities(startupIntent,0);//包管理器根据Intent搜寻出匹配的Activity(ResolveInfo)列表
        Collections.sort(activities, new Comparator<ResolveInfo>() {//对Activity标签进行排序
            @Override
            public int compare(ResolveInfo resolveInfo, ResolveInfo t1) {
                PackageManager pm=getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(
                        resolveInfo.loadLabel(pm).toString(),
                        t1.loadLabel(pm).toString()
                );
            }
        });
        mRecyclerView.setAdapter(new ActivityAdapter(activities));
    }
    private class ActivityHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ResolveInfo mResolveInfo;
        private TextView mNameTextView;

        public ActivityHolder(View itemView) {
            super(itemView);
            mNameTextView=(TextView)itemView;
            mNameTextView.setOnClickListener(this);
        }

        public void bindActivity(ResolveInfo resolveInfo){
            mResolveInfo=resolveInfo;
            PackageManager pm=getActivity().getPackageManager();
            String appName=mResolveInfo.loadLabel(pm).toString();
            mNameTextView.setText(appName);
        }

        @Override
        public void onClick(View view) {
            ActivityInfo activityInfo=mResolveInfo.activityInfo;//从ResolveInfo中获取ActivityInfo
            Intent i=new Intent(Intent.ACTION_MAIN).setClassName(activityInfo.applicationInfo.packageName,activityInfo.name)//获取包名和类名
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//在新任务中打开应用
            startActivity(i);
        }
    }
    private class ActivityAdapter extends RecyclerView.Adapter<ActivityHolder>{
        private final List<ResolveInfo> mResolveInfo;
        public ActivityAdapter(List<ResolveInfo> activities) {
            mResolveInfo=activities;
        }
        @Override
        public void onBindViewHolder(ActivityHolder holder, int position) {
            ResolveInfo resolverinfo=mResolveInfo.get(position);
            holder.bindActivity(resolverinfo);
        }

        @Override
        public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
            return new ActivityHolder(view);
        }

        @Override
        public int getItemCount() {
            return mResolveInfo.size();
        }
    }
}

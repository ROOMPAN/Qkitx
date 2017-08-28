package com.yanhui.qktx.fragment;

import com.yanhui.qktx.R;
import com.yanhui.qktx.models.entity.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/8/14.
 */

public class FragmentVideo extends BaseFragment {
    private List<Channel> mChannelList = new ArrayList<>();
    private List<NewsListFragment> mFrgamentList = new ArrayList<>();

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

}

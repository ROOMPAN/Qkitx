package com.yanhui.qktx.constants;

/**
 * @author ChayChan
 * @description: 记录常量的类
 * @date 2017/6/16  20:22
 */

public class Constant {
    /**
     * 已选中频道的json
     */
    public static final String SELECTED_CHANNEL_JSON = "selectedChannelJson";
    /**
     * w未选频道的json
     */
    public static final String UNSELECTED_CHANNEL_JSON = "unselectChannelJson";

    /**
     * 频道对应的请求参数
     */
    public static final String CHANNEL_CODE = "channelCode";
    public static final String IS_VIDEO_LIST = "isVideoList";

    public static final String ARTICLE_GENRE_VIDEO = "video";
    public static final String ARTICLE_GENRE_AD = "ad";

    public static final String TAG_MOVIE = "video_movie";

    public static final String URL_VIDEO = "/video/urls/v/1/toutiao/mp4/%s?r=%s";

    /**
     * 获取评论列表每页的数目
     */
    public static final int COMMENT_PAGE_SIZE = 20;
    //传给 webview 的标识
    public static final String WEB_VIEW_LOAD_URL = "WEB_URL";
    //webview 底部导航栏是否显示

    public static final String SHOW_WEB_VIEW_BUTTOM = "SHOW";
    public static final String SHOW_WEB_VIEW_CLEAR = "CLEAR";
    public static final int SHOW_CLEAR = 1;
    public static final int SHOW_BUTOM = 1;
    public static final int GONE_BUTTOM = 0;
    public static final String TASKID = "taskId";
    public static final String COMMENTID = "commentid";
    public static final String ARTICLETYPE = "articleType";
    public static final String SHARE_URL = "share_url";
    public static final String SHARE_IMG_URL = "share_img_url";
    public static final String SHARE_CONTEXT = "share_context";
    public static final String SHARE_TITLE = "share_title";
    public static final String ISCONN = "isconn";
    public static final String COMMENTS_NUM = "commentnum";
    public static final String ISNEWBIETASK = "isNewbieTask";
    public static final String PROTOCOL = "protocol_url";//建议隐私
    public static final String ABOUT = "about_url"; //关于
    public static final String VIDEO_URL = "VIDEO_URL";
    public static final String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";


    //修改用户资料
    public static final int USER_CHANGE_INFOR_REQUESCODE = 100;
    //activity 回调
    public static final int USER_REQUST_CODE = 102;

    //登录返回
    public static final int USER_LOGIN_REQUEST_CODE = 10011;

    //搜索类型
    public static final String SEACH_TYPE = "SEACHTYPE";
    //视频搜索
    public static final int SEACH_VIDEO = 2;
    //文章搜索
    public static final int SEACH_AIRTS = 1;
    //所有列表数据加载条数;
    public static final int PAGER_SIZE = 8;
    //储存,搜索关键词 key;
    public static final String KEY_WORD_KEY = "key_word";

}

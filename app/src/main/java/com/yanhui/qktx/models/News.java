package com.yanhui.qktx.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * @author ChayChan
 * @description: TODO
 * @date 2017/7/6  15:11
 */

public class News {


    /**
     * log_pb : {"impr_id":"201707061547030100080440135802B6"}
     * read_count : 628838
     * media_name : 电竞手游君
     * ban_comment : 0
     * abstract : 王者荣耀赛季结束的段位奖励一直以来都还是很给力的，除了免费的赛季限定皮肤之外（注：本次的赛季奖励皮肤获取方式为获得10场胜利游戏后自动获取）还有就是大把大把的钻石奖励了。那么这个钻石到底怎么用合适呢？
     * image_list : [{"url":"http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp","width":700,"url_list":[{"url":"http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"},{"url":"http://pb9.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"},{"url":"http://pb1.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"}],"uri":"list/2c23000095ae9f56b15f","height":393},{"url":"http://p3.pstatp.com/list/300x196/2c2c0004688def37bb3c.webp","width":699,"url_list":[{"url":"http://p3.pstatp.com/list/300x196/2c2c0004688def37bb3c.webp"},{"url":"http://pb9.pstatp.com/list/300x196/2c2c0004688def37bb3c.webp"},{"url":"http://pb1.pstatp.com/list/300x196/2c2c0004688def37bb3c.webp"}],"uri":"list/2c2c0004688def37bb3c","height":393},{"url":"http://p1.pstatp.com/list/300x196/2c2a000049bb1a95374c.webp","width":699,"url_list":[{"url":"http://p1.pstatp.com/list/300x196/2c2a000049bb1a95374c.webp"},{"url":"http://pb3.pstatp.com/list/300x196/2c2a000049bb1a95374c.webp"},{"url":"http://pb9.pstatp.com/list/300x196/2c2a000049bb1a95374c.webp"}],"uri":"list/2c2a000049bb1a95374c","height":393}]
     * ugc_recommend : {"reason":"","activity":""}
     * article_type : 0
     * tag : news_game
     * forward_info : {"forward_count":0}
     * has_m3u8_video : 0
     * keywords : 韩信,王者荣耀,王者水晶,游戏,胜利游戏
     * rid : 201707061547030100080440135802B6
     * show_portrait_article : false
     * user_verified : 0
     * aggr_type : 1
     * cell_type : 0
     * article_sub_type : 0
     * bury_count : 3
     * title : 王者荣耀用段位奖励的钻石抽到王者水晶之后千万别急着换韩信
     * ignore_web_transform : 1
     * source_icon_style : 5
     * tip : 0
     * hot : 0
     * share_url : http://toutiao.com/a6436927152504258818/?iid=0&app=news_article
     * has_mp4_video : 0
     * source : 电竞手游君
     * comment_count : 902
     * article_url : http://toutiao.com/group/6436927152504258818/
     * filter_words : [{"id":"8:0","name":"看过了","is_selected":false},{"id":"9:1","name":"内容太水","is_selected":false},{"id":"5:1189115162","name":"拉黑来源:电竞手游君","is_selected":false},{"id":"3:306461185","name":"不想看:钻石","is_selected":false},{"id":"6:153758450","name":"不想看:王者荣耀","is_selected":false},{"id":"6:236362","name":"不想看:韩信","is_selected":false}]
     * share_count : 211
     * publish_time : 1498714396
     * action_list : [{"action":1,"extra":{},"desc":""},{"action":3,"extra":{},"desc":""},{"action":7,"extra":{},"desc":""},{"action":9,"extra":{},"desc":""}]
     * gallary_image_count : 4
     * cell_layout_style : 1
     * tag_id : 6436927152504259000
     * video_style : 0
     * verified_content :
     * display_url : http://toutiao.com/group/6436927152504258818/
     * large_image_list : []
     * media_info : {"user_id":59834611934,"verified_content":"","avatar_url":"http://p3.pstatp.com/large/216b000e0abb3ee9cb91","media_id":1567608882475010,"name":"电竞手游君","recommend_type":0,"follow":false,"recommend_reason":"","is_star_user":false,"user_verified":false}
     * item_id : 6436929317376099000
     * is_subject : false
     * show_portrait : false
     * repin_count : 1110
     * cell_flag : 11
     * user_info : {"verified_content":"","avatar_url":"http://p3.pstatp.com/thumb/216b000e0abb3ee9cb91","user_id":59834611934,"name":"电竞手游君","follower_count":0,"follow":false,"user_auth_info":"","user_verified":false,"description":"游戏 资讯 游戏攻略 你要的这里都有，来这里就对了。"}
     * source_open_url : sslocal://profile?uid=59834611934
     * level : 0
     * like_count : 19
     * digg_count : 19
     * behot_time : 1499325873
     * cursor : 1499325873999
     * url : http://toutiao.com/group/6436927152504258818/
     * preload_web : 0
     * user_repin : 0
     * has_image : true
     * item_version : 0
     * has_video : false
     * group_id : 6436927152504259000
     * video_duration: 68,
     * middle_image : {"url":"http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp","width":700,"url_list":[{"url":"http://p3.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"},{"url":"http://pb9.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"},{"url":"http://pb1.pstatp.com/list/300x196/2c23000095ae9f56b15f.webp"}],"uri":"list/2c23000095ae9f56b15f","height":393}
     */

    public int read_count;
    public String media_name;
    public int ban_comment;
    @SerializedName("abstract")
    public String abstractX;
    public int article_type;
    public String tag;
    public int has_m3u8_video;
    public String keywords;
    public String rid;
    public boolean show_portrait_article;
    public int user_verified;
    public int aggr_type;
    public int cell_type;
    public int article_sub_type;
    public int bury_count;
    public String title;
    public int ignore_web_transform;
    public int source_icon_style;
    public int tip;
    public int hot;
    public String share_url;
    public int has_mp4_video;
    public String source;
    public int comment_count;
    public String article_url;
    public int share_count;
    public int publish_time;
    public int gallary_image_count;
    public int cell_layout_style;
    public long tag_id;
    public int video_style;
    public String verified_content;
    public String display_url;
    public String item_id;
    public boolean is_subject;
    public boolean show_portrait;
    public int repin_count;
    public int cell_flag;
    public String source_open_url;
    public int level;
    public int like_count;
    public int digg_count;
    public long behot_time;
    public long cursor;
    public String url;
    public int preload_web;
    public int user_repin;
    public boolean has_image;
    public int item_version;
    public boolean has_video;
    public int video_duration;
    public String group_id;

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public String getMedia_name() {
        return media_name;
    }

    public void setMedia_name(String media_name) {
        this.media_name = media_name;
    }

    public int getBan_comment() {
        return ban_comment;
    }

    public void setBan_comment(int ban_comment) {
        this.ban_comment = ban_comment;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public int getArticle_type() {
        return article_type;
    }

    public void setArticle_type(int article_type) {
        this.article_type = article_type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getHas_m3u8_video() {
        return has_m3u8_video;
    }

    public void setHas_m3u8_video(int has_m3u8_video) {
        this.has_m3u8_video = has_m3u8_video;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public boolean isShow_portrait_article() {
        return show_portrait_article;
    }

    public void setShow_portrait_article(boolean show_portrait_article) {
        this.show_portrait_article = show_portrait_article;
    }

    public int getUser_verified() {
        return user_verified;
    }

    public void setUser_verified(int user_verified) {
        this.user_verified = user_verified;
    }

    public int getAggr_type() {
        return aggr_type;
    }

    public void setAggr_type(int aggr_type) {
        this.aggr_type = aggr_type;
    }

    public int getCell_type() {
        return cell_type;
    }

    public void setCell_type(int cell_type) {
        this.cell_type = cell_type;
    }

    public int getArticle_sub_type() {
        return article_sub_type;
    }

    public void setArticle_sub_type(int article_sub_type) {
        this.article_sub_type = article_sub_type;
    }

    public int getBury_count() {
        return bury_count;
    }

    public void setBury_count(int bury_count) {
        this.bury_count = bury_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIgnore_web_transform() {
        return ignore_web_transform;
    }

    public void setIgnore_web_transform(int ignore_web_transform) {
        this.ignore_web_transform = ignore_web_transform;
    }

    public int getSource_icon_style() {
        return source_icon_style;
    }

    public void setSource_icon_style(int source_icon_style) {
        this.source_icon_style = source_icon_style;
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getHas_mp4_video() {
        return has_mp4_video;
    }

    public void setHas_mp4_video(int has_mp4_video) {
        this.has_mp4_video = has_mp4_video;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(int publish_time) {
        this.publish_time = publish_time;
    }

    public int getGallary_image_count() {
        return gallary_image_count;
    }

    public void setGallary_image_count(int gallary_image_count) {
        this.gallary_image_count = gallary_image_count;
    }

    public int getCell_layout_style() {
        return cell_layout_style;
    }

    public void setCell_layout_style(int cell_layout_style) {
        this.cell_layout_style = cell_layout_style;
    }

    public long getTag_id() {
        return tag_id;
    }

    public void setTag_id(long tag_id) {
        this.tag_id = tag_id;
    }

    public int getVideo_style() {
        return video_style;
    }

    public void setVideo_style(int video_style) {
        this.video_style = video_style;
    }

    public String getVerified_content() {
        return verified_content;
    }

    public void setVerified_content(String verified_content) {
        this.verified_content = verified_content;
    }

    public String getDisplay_url() {
        return display_url;
    }

    public void setDisplay_url(String display_url) {
        this.display_url = display_url;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public boolean is_subject() {
        return is_subject;
    }

    public void setIs_subject(boolean is_subject) {
        this.is_subject = is_subject;
    }

    public boolean isShow_portrait() {
        return show_portrait;
    }

    public void setShow_portrait(boolean show_portrait) {
        this.show_portrait = show_portrait;
    }

    public int getRepin_count() {
        return repin_count;
    }

    public void setRepin_count(int repin_count) {
        this.repin_count = repin_count;
    }

    public int getCell_flag() {
        return cell_flag;
    }

    public void setCell_flag(int cell_flag) {
        this.cell_flag = cell_flag;
    }

    public String getSource_open_url() {
        return source_open_url;
    }

    public void setSource_open_url(String source_open_url) {
        this.source_open_url = source_open_url;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getDigg_count() {
        return digg_count;
    }

    public void setDigg_count(int digg_count) {
        this.digg_count = digg_count;
    }

    public long getBehot_time() {
        return behot_time;
    }

    public void setBehot_time(long behot_time) {
        this.behot_time = behot_time;
    }

    public long getCursor() {
        return cursor;
    }

    public void setCursor(long cursor) {
        this.cursor = cursor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPreload_web() {
        return preload_web;
    }

    public void setPreload_web(int preload_web) {
        this.preload_web = preload_web;
    }

    public int getUser_repin() {
        return user_repin;
    }

    public void setUser_repin(int user_repin) {
        this.user_repin = user_repin;
    }

    public boolean isHas_image() {
        return has_image;
    }

    public void setHas_image(boolean has_image) {
        this.has_image = has_image;
    }

    public int getItem_version() {
        return item_version;
    }

    public void setItem_version(int item_version) {
        this.item_version = item_version;
    }

    public boolean isHas_video() {
        return has_video;
    }

    public void setHas_video(boolean has_video) {
        this.has_video = has_video;
    }

    public int getVideo_duration() {
        return video_duration;
    }

    public void setVideo_duration(int video_duration) {
        this.video_duration = video_duration;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public News() {
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

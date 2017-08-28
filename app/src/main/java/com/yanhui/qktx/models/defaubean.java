package com.yanhui.qktx.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by liupanpan on 2017/7/19.
 */

public class defaubean extends BaseEntity {

    /**
     * 1 : {"url":"http://www.firefoxchina.cn/?ntab","defaultposition":"1","thumbnail":"/data/thumbnails/e98cec9e7b04c9a31bdd176c90111395b8888a4d.png","title":"火狐中文网"}
     * 2 : {"url":"https://www.baidu.com/index.php?tn=monline_3_dg","defaultposition":"2","thumbnail":"/data/thumbnails/d5f6527fc143629ac6508c9127148a72255260cc.png","title":"百度"}
     * 3 : {"url":"http://www.sina.com.cn/","defaultposition":"3","thumbnail":"/data/thumbnails/71ecd4fd03c40c088bba4b2a961254b1992cec29.png","title":"新浪"}
     * 4 : {"url":"http://www.youku.com/","defaultposition":"4","thumbnail":"/data/thumbnails/fe656b66ff0eb278cf65cc2cd03439d40bb90f29.png","title":"优酷"}
     * 5 : {"url":"https://s.click.taobao.com/t?e=m%3D2%26s%3D4uK7wvK07WQcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMfvTqVQdnGPZxq3IhSJN6GRoVxuUFnM6iMUjUj9sJ%2FOjBzhZfopMJkV9%2F6K2pXwHaaUuZxIcp9pfUIgVEmFmgnbDX0%2BHH2IEVa7A5ve%2FEYDnFveQ9Ld2jopwTqWNBsAwm%2BIKl4JSR4lzxgxdTc00KD8%3D","defaultposition":"5","thumbnail":"/data/thumbnails/aba55ec2bc9ea1847e17091c5b7899294cd28939.gif","title":"天猫精选"}
     * 6 : {"url":"http://union.click.jd.com/jdc?e=&p=AyIPZRprFDJWWA1FBCVbV0IUWVALHFRBEwQAQB1AWQkFawtJDF0rRgllYhQEHhhcD0lsBRISWWUOHjdWGFoRAhsFVh1rFgETA1YaWRwHIjc0aWtebBM3VxNSHQsTBFMbaxUHFgNSGlISAhsDVBhrEjJuWR5DBkhSEQZVHmslAw%3D%3D&t=W1dCFFlQCxxUQRMEAEAdQFkJBQ%3D%3D","defaultposition":"6","thumbnail":"/data/thumbnails/27b0cb0f6d4d81bcb9e413bdf4fb9a43a49cb5bc.png","title":"京东商城"}
     * 7 : {"url":"https://www.taobao.com/","defaultposition":"7","thumbnail":"/data/thumbnails/70199cbab66679281b636f491170e2302fb6f608.png","title":"淘宝网"}
     * 8 : {"url":"http://www.ctrip.com/?allianceid=1381&sid=1068414","defaultposition":"8","thumbnail":"/data/thumbnails/237d73b26d0918f01536540716c1309bb836ae96.png","title":"携程旅行"}
     */

    @SerializedName("1")
    private _$1Bean _$1;
    @SerializedName("2")
    private _$2Bean _$2;
    @SerializedName("3")
    private _$3Bean _$3;
    @SerializedName("4")
    private _$4Bean _$4;
    @SerializedName("5")
    private _$5Bean _$5;
    @SerializedName("6")
    private _$6Bean _$6;
    @SerializedName("7")
    private _$7Bean _$7;
    @SerializedName("8")
    private _$8Bean _$8;

    public _$1Bean get_$1() {
        return _$1;
    }

    public void set_$1(_$1Bean _$1) {
        this._$1 = _$1;
    }

    public _$2Bean get_$2() {
        return _$2;
    }

    public void set_$2(_$2Bean _$2) {
        this._$2 = _$2;
    }

    public _$3Bean get_$3() {
        return _$3;
    }

    public void set_$3(_$3Bean _$3) {
        this._$3 = _$3;
    }

    public _$4Bean get_$4() {
        return _$4;
    }

    public void set_$4(_$4Bean _$4) {
        this._$4 = _$4;
    }

    public _$5Bean get_$5() {
        return _$5;
    }

    public void set_$5(_$5Bean _$5) {
        this._$5 = _$5;
    }

    public _$6Bean get_$6() {
        return _$6;
    }

    public void set_$6(_$6Bean _$6) {
        this._$6 = _$6;
    }

    public _$7Bean get_$7() {
        return _$7;
    }

    public void set_$7(_$7Bean _$7) {
        this._$7 = _$7;
    }

    public _$8Bean get_$8() {
        return _$8;
    }

    public void set_$8(_$8Bean _$8) {
        this._$8 = _$8;
    }

    public static class _$1Bean {
        /**
         * url : http://www.firefoxchina.cn/?ntab
         * defaultposition : 1
         * thumbnail : /data/thumbnails/e98cec9e7b04c9a31bdd176c90111395b8888a4d.png
         * title : 火狐中文网
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$2Bean {
        /**
         * url : https://www.baidu.com/index.php?tn=monline_3_dg
         * defaultposition : 2
         * thumbnail : /data/thumbnails/d5f6527fc143629ac6508c9127148a72255260cc.png
         * title : 百度
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$3Bean {
        /**
         * url : http://www.sina.com.cn/
         * defaultposition : 3
         * thumbnail : /data/thumbnails/71ecd4fd03c40c088bba4b2a961254b1992cec29.png
         * title : 新浪
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$4Bean {
        /**
         * url : http://www.youku.com/
         * defaultposition : 4
         * thumbnail : /data/thumbnails/fe656b66ff0eb278cf65cc2cd03439d40bb90f29.png
         * title : 优酷
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$5Bean {
        /**
         * url : https://s.click.taobao.com/t?e=m%3D2%26s%3D4uK7wvK07WQcQipKwQzePCperVdZeJviK7Vc7tFgwiFRAdhuF14FMfvTqVQdnGPZxq3IhSJN6GRoVxuUFnM6iMUjUj9sJ%2FOjBzhZfopMJkV9%2F6K2pXwHaaUuZxIcp9pfUIgVEmFmgnbDX0%2BHH2IEVa7A5ve%2FEYDnFveQ9Ld2jopwTqWNBsAwm%2BIKl4JSR4lzxgxdTc00KD8%3D
         * defaultposition : 5
         * thumbnail : /data/thumbnails/aba55ec2bc9ea1847e17091c5b7899294cd28939.gif
         * title : 天猫精选
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$6Bean {
        /**
         * url : http://union.click.jd.com/jdc?e=&p=AyIPZRprFDJWWA1FBCVbV0IUWVALHFRBEwQAQB1AWQkFawtJDF0rRgllYhQEHhhcD0lsBRISWWUOHjdWGFoRAhsFVh1rFgETA1YaWRwHIjc0aWtebBM3VxNSHQsTBFMbaxUHFgNSGlISAhsDVBhrEjJuWR5DBkhSEQZVHmslAw%3D%3D&t=W1dCFFlQCxxUQRMEAEAdQFkJBQ%3D%3D
         * defaultposition : 6
         * thumbnail : /data/thumbnails/27b0cb0f6d4d81bcb9e413bdf4fb9a43a49cb5bc.png
         * title : 京东商城
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$7Bean {
        /**
         * url : https://www.taobao.com/
         * defaultposition : 7
         * thumbnail : /data/thumbnails/70199cbab66679281b636f491170e2302fb6f608.png
         * title : 淘宝网
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class _$8Bean {
        /**
         * url : http://www.ctrip.com/?allianceid=1381&sid=1068414
         * defaultposition : 8
         * thumbnail : /data/thumbnails/237d73b26d0918f01536540716c1309bb836ae96.png
         * title : 携程旅行
         */

        private String url;
        private String defaultposition;
        private String thumbnail;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDefaultposition() {
            return defaultposition;
        }

        public void setDefaultposition(String defaultposition) {
            this.defaultposition = defaultposition;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}

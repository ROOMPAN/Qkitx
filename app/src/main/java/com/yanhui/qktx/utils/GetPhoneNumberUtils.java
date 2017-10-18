package com.yanhui.qktx.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.yanhui.qktx.models.ContactInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liupanpan on 2017/10/17.
 */

public class GetPhoneNumberUtils {
    /**
     * 获取联系人数据
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> getAllContacts(Context context) {

        // 获取联系人数据
        ContentResolver cr = context.getContentResolver();
        //获取所有电话信息（而不是联系人信息），这样方便展示
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,// 姓名
                ContactsContract.CommonDataKinds.Phone.NUMBER,// 电话号码
        };
        Cursor cursor = cr.query(uri, projection, null, null, null);
        if (cursor == null) {
            return null;
        }
        //最终要返回的数据
        List<ContactInfo> result = new ArrayList<ContactInfo>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            //保存到对象里
            ContactInfo info = new ContactInfo();
            info.setName(name);
            info.setMobile(number);
            //保存到集合里
            result.add(info);
        }
        //用完记得关闭
        cursor.close();
        return result;


//        ==-------------------
//        List<ContactInfo> list = new ArrayList<ContactInfo>();
//        // 获取解析者
//        ContentResolver resolver = context.getContentResolver();
//        // 访问地址
//        Uri raw_contacts = Uri.parse("content://com.android.contacts/raw_contacts");
//        Uri data = Uri.parse("content://com.android.contacts/data");
//        // 查询语句
//        // select contact_id from raw_contacts;//1 2 3 4
//        // select mimetype,data1 from view_data where raw_contact_id=3;
//        // Cursor cursor=resolver.query(访问地址, 返回字段 null代表全部, where 语句, 参数, 排序)
//        Cursor cursor = resolver.query(raw_contacts, new String[]{"contact_id"}, null, null, null);
//
//        while (cursor.moveToNext()) {
//            // getColumnIndex根据名称查列号
//            String id = cursor.getString(cursor.getColumnIndex("contact_id"));
//            // 创建实例
//            ContactInfo info = new ContactInfo();
////            info.id = id;
//            Cursor item = resolver.query(data, new String[]{"mimetype", "data1"}, "raw_contact_id=?", new String[]{id}, null);
//
//            while (item.moveToNext()) {
//                String mimetype = item.getString(item.getColumnIndex("mimetype"));
//                String data1 = item.getString(item.getColumnIndex("data1"));
//                if ("vnd.android.cursor.item/name".equals(mimetype)) {
//                    info.name = data1;
//                } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
//                    info.mobile = data1;
//                }
//            }
//            item.close();
//            // 添加集合
//            list.add(info);
//        }
//        cursor.close();
//        return list;
    }
}

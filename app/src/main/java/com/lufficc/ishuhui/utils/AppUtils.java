package com.lufficc.ishuhui.utils;

import android.os.Environment;

import com.lufficc.ishuhui.activity.preview.ImageItem;
import com.lufficc.ishuhui.model.FileEntry;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lufficc on 2016/11/5.
 */

public class AppUtils {
    public static String getChapterUrl(String chapterId) {
        return "http://www.ishuhui.net/ComicBooks/ReadComicBooksToIsoV1/" + chapterId + ".html";
    }

    public static void downloadChapterImages(String chapterId) {
        PtrUtil.getInstance().start().put("chapter_" + chapterId + "_is_downloaded", true).apply();
    }

    public static boolean isChapterImagesDownloaded(String chapterId) {
        return PtrUtil.getInstance().getBoolean("chapter_" + chapterId + "_is_downloaded", false);
    }

    public static File getAppDir() {
        File sdCardRoot = Environment.getExternalStorageDirectory();
        return new File(sdCardRoot, File.separator + "鼠绘漫画" + File.separator);
    }

    public static List<ImageItem> fileEntry2ImageItem(List<FileEntry> files) {
        final List<ImageItem> list = new ArrayList<>();
        for (FileEntry fileEntry : files) {
            ImageItem imageItem = new ImageItem(fileEntry.getUrl(), fileEntry.getTitle());
            imageItem.setLocalPath(fileEntry.getLocalPath());
            list.add(imageItem);
        }
        return list;
    }
}

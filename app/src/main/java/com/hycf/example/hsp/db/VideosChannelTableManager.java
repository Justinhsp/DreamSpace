
package com.hycf.example.hsp.db;


import com.hycf.example.hsp.AppApplication;
import com.hycf.example.hsp.R;
import com.hycf.example.hsp.bean.VideoChannelTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideosChannelTableManager {

    /**
     * 加载视频类型
     * @return
     */
    public static List<VideoChannelTable> loadVideosChannelsMine() {
        List<String> channelName = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.video_channel_name));
        List<String> channelId = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.video_channel_id));
        ArrayList<VideoChannelTable>newsChannelTables=new ArrayList<>();
        for (int i = 0; i < channelName.size(); i++) {
            VideoChannelTable entity = new VideoChannelTable(channelId.get(i), channelName.get(i));
            newsChannelTables.add(entity);
        }
        return newsChannelTables;
    }

}

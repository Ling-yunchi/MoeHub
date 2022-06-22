package tv.moehub.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Video;
import tv.moehub.model.BaseResult;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoService {
    private final VideoDao videoDao;

    public void getVideoInfo(String videoId, BaseResult<Video> result) {
        Video video = videoDao.queryVideoById(videoId);
        if (video == null) {
            result.construct(false, "视频不存在");
            return;
        }
        result.construct(true, "查询成功", video);
    }

    public void searchVideo(String videoTitle, BaseResult<List<Video>> result) {
        String videoTitleLike = "%" + videoTitle + "%";
        List<Video> videoList = videoDao.findByTitleLike(videoTitleLike);
        if (videoList.size() == 0) {
            result.construct(false, "无相关视频");
            return;
        }
        result.construct(true, "相关视频如下", videoList);
    }
}

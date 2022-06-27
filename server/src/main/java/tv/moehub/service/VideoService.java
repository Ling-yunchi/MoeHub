package tv.moehub.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import tv.moehub.dao.SearchDao;
import tv.moehub.dao.UserDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Search;
import tv.moehub.entity.User;
import tv.moehub.entity.Video;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoResult;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoService {
    private final VideoDao videoDao;
    private final UserDao userDao;

    public void queryVideoById(String videoId, BaseResult<VideoResult> result) {
        VideoResult videoResult = videoDao.queryVideoById(videoId);
        if (videoResult == null) {
            result.construct(false, "视频不存在");
            return;
        }
        result.construct(true, "视频如下", videoResult);
    }

    public void searchVideoByTitle(String videoTitle, BaseResult<Page<VideoResult>> result, int pageNum, int pageSize) {
        String videoTitleLike = "%" + videoTitle + "%";
        List<VideoResult> videoResultList = videoDao.findByTitleLike(videoTitleLike);
        if (videoResultList.size() == 0) {
            result.construct(false, "无相关视频");
            return;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC);
        Page<VideoResult> videoResultPage = new PageImpl<VideoResult>(videoResultList, pageable, videoResultList.size());
        result.construct(true, "相关视频如下", videoResultPage);
    }

    public void searchVideoByAuthor(String nickname, BaseResult<Page<VideoResult>> result, int pageNum, int pageSize) {
        User user = userDao.findByNickname(nickname);
        if (user != null) {
            List<VideoResult> videoResultList = videoDao.findByAuthorId(user.getId());
            if (videoResultList.size() == 0) {
                result.construct(false, "该用户未上传视频");
                return;
            }
            Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC);
            Page<VideoResult> videoResultPage = new PageImpl<VideoResult>(videoResultList, pageable, videoResultList.size());
            result.construct(true, "相关视频如下", videoResultPage);
            return;
        }
        result.construct(false, "未查询到该用户");
    }
}

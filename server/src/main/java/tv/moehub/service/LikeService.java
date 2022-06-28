package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.bean.LikeVideoBean;
import tv.moehub.dao.LikeVideoDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.LikeVideo;
import tv.moehub.entity.Video;
import tv.moehub.model.BaseResult;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LikeService {
    private final LikeVideoDao likeVideoDao;
    private final VideoDao videoDao;

    public void setLikeVideo(String videoId, BaseResult<Void> result) {
        Optional<Video> videoOptional = videoDao.findById(videoId);
        if (!videoOptional.isPresent()) {
            result.construct(false, "视频不存在");
            return;
        }
//        var video = videoOptional.get();
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        LikeVideo like = likeVideoDao.queryLikeVideoByUserIdAndVideoId(userId, videoId);
        if (like != null) {
            result.construct(false, "勿重复点赞");
            return;
        }
        LikeVideo lv = LikeVideo.builder()
                .userId(userId)
                .videoId(videoId)
                .build();
        likeVideoDao.save(lv);
        result.construct(true, "点赞视频成功");
    }

    public void cancelLikeVideo(String videoId, BaseResult<Void> result) {
        Optional<Video> videoOptional = videoDao.findById(videoId);
        if (!videoOptional.isPresent()) {
            result.construct(false, "视频不存在");
            return;
        }
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        LikeVideo like = likeVideoDao.queryLikeVideoByUserIdAndVideoId(userId, videoId);
        if (like == null) {
            result.construct(false, "取消点赞失败");
            return;
        }
        likeVideoDao.delete(like);
        result.construct(true, "取消点赞成功");
    }

    public void countLikeVideo(String videoId, BaseResult<Integer> result) {
        Optional<Video> videoOptional = videoDao.findById(videoId);
        if(!videoOptional.isPresent()) {
            result.construct(true, "视频不存在");
            return;
        }
        int count = likeVideoDao.countLikeVideoByVideoId(videoId);
        result.construct(true, "视频点赞数查询", count);
    }
}

package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.bean.LikeVideoBean;
import tv.moehub.dao.LikeVideoDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.LikeVideo;
import tv.moehub.entity.Video;
import tv.moehub.model.BaseResult;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LikeService {
    private final LikeVideoDao likeVideoDao;
    private final VideoDao videoDao;

    public void setLikeVideo(LikeVideoBean likeVideoBean, BaseResult<LikeVideo> result){
        Video video = videoDao.findByVideoId(likeVideoBean.getVideoId());
        if(video == null){
            result.construct(true, "视频不存在");
            return;
        }
        LikeVideo like = likeVideoDao.queryLikeVideoByUserIdAndVideoId(
                likeVideoBean.getUserId(), likeVideoBean.getVideoId());
        if(like != null){
            result.construct(true, "勿重复点赞", like);
            return;
        }
        LikeVideo likeVideo = new LikeVideo();
        BeanUtils.copyProperties(likeVideoBean, likeVideo);
        likeVideoDao.save(likeVideo);
        result.construct(true, "点赞视频成功", likeVideo);
    }

    public void cancelLikeVideo(LikeVideoBean likeVideoBean, BaseResult<LikeVideo> result){
        Video video = videoDao.findByVideoId(likeVideoBean.getVideoId());
        if(video == null) {
            result.construct(true, "视频不存在");
            return;
        }
        LikeVideo like = likeVideoDao.queryLikeVideoByUserIdAndVideoId(
                likeVideoBean.getUserId(), likeVideoBean.getVideoId());
        if(like == null){
            result.construct(true, "取消点赞失败");
            return;
        }
        likeVideoDao.delete(like);
        result.construct(true, "取消点赞成功", like);
    }

    public void countLikeVideo(String videoId, BaseResult<Integer> result) {
        Video video = videoDao.findByVideoId(videoId);
        if(video == null) {
            result.construct(true, "视频不存在");
            return;
        }
        int count = likeVideoDao.countLikeVideoByVideoId(videoId);
        result.construct(true, "视频点赞数查询", count);
    }
}

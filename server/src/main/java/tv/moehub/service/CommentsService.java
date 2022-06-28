package tv.moehub.service;


import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tv.moehub.bean.CommentsBean;
import tv.moehub.dao.CommentsDao;
import tv.moehub.dao.UserDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Comments;
import tv.moehub.entity.Video;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.CommentsResult;

import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentsService {

    private final CommentsDao commentsDao;
    private final VideoDao videoDao;
    private final UserDao userDao;


    public void addComments(CommentsBean commentsBean, BaseResult<Void> result) {
        //给视频加一个评论
        Optional<Video> videoOptional = videoDao.findById(commentsBean.getVideoId());
        if (!videoOptional.isPresent()) {
            result.construct(false, "未找到视频,评论失败");
            return;
        }
        var video = videoOptional.get();
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        Comments comments = Comments.builder()
                .content(commentsBean.getContent())
                .videoId(commentsBean.getVideoId())
                .userId(userId)
                .createAt(new Date())
                .build();

        commentsDao.save(comments);
        result.construct(true, "评论成功");
    }

    public void deleteComments(String id, BaseResult<Void> result) {
        Comments comments = commentsDao.queryCommentsById(id);
        if (comments == null) {
            result.construct(false, "没有该评论,删除失败");//后边没加东西
            return;
        }
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        if (!comments.getUserId().equals(userId)) {
            result.construct(false, "没有权限删除该评论");
            return;
        }
        commentsDao.deleteById(id);
        result.construct(true, "删除成功");

    }

    public void searchCommentsByTime(String videoId, Integer pageNum, Integer pageSize, BasePageResult<CommentsResult> result) {
        //找出视频的所有评论(先看最新评论)
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<CommentsResult> comments = commentsDao.queryAllByVideoId(videoId, pageable);
        result.construct(true, "查询成功", comments);
    }

}

package tv.moehub.service;


import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tv.moehub.bean.CommentsBean;
import tv.moehub.dao.CommentsDao;
import tv.moehub.dao.UserDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Comments;
import tv.moehub.model.BaseResult;
import tv.moehub.model.CommentsResult;

import java.util.Date;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentsService {

    private final CommentsDao commentsDao;
    private final VideoDao videoDao;
    private final UserDao userDao;


    public void addComments(CommentsBean commentsBean, BaseResult<CommentsResult> result) {
        //给视频加一个评论

        if (videoDao.queryVideoById(commentsBean.getVideoId()) == null) {
            result.construct(false, "未找到视频,评论失败");
            return;
        }
        Comments comments = new Comments();

        Date date = new Date();//设置时间
        commentsBean.setTime(date);

        BeanUtils.copyProperties(commentsBean, comments);
        commentsDao.save(comments);
        result.construct(true, "评论成功", new CommentsResult( comments));
    }

    public void deleteComments(String id, BaseResult<CommentsResult> result) {
        //删评论
        if (commentsDao.queryCommentsById(id) == null) {
            result.construct(false, "没有该评论,删除失败");//后边没加东西
            return;
        }
        commentsDao.deleteById(id);
        result.construct(true, "删除成功");

    }

    public void searchCommentsByTime(String videoId, BaseResult<Page<CommentsResult>> result, int pageNum,int pageSize) {
        //找出视频的所有评论(先看最新评论)

        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC, "time");
        Page<Comments> comments = commentsDao.queryAllByVideoId(videoId, pageable);

        Page<CommentsResult> results = new Page<>();

        for (Comments example:comments)
        {
            CommentsResult a = new CommentsResult(example);
            a.setUserName(userDao.queryUserById(example.getUserId()).getUsername());
            a.setAvatar(userDao.queryUserById(example.getUserId()).getAvatar());
            results.add(a);
        }

        if (results.isEmpty()) {
            result.construct(true, "找不到", results);
            return;
        }

        result.construct(true, "查询成功", results);
    }

}

package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.bean.CommentsBean;
import tv.moehub.dao.CommentsDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Comments;
import tv.moehub.model.BaseResult;
import tv.moehub.model.CommentsResult;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class CommentsService {
    private final CommentsDao commentsDao;
    private final VideoDao videoDao;
    public void getVideoCommentsByTime(String videoId, BaseResult<List<Comments>> result){
        //找出视频的所有评论(先看最新评论)

        List<Comments> comments = commentsDao.queryAllByVideoId(videoId);
        if (comments.size()==0){
            result.construct(false,"找不到");
            return;
        }
        //排序(先看最新评论)
        comments = comments.stream().sorted(Comparator.comparing(Comments::getTime).reversed())
                .collect(Collectors.toList());
        result.construct(true, "查询成功", comments);

    }

    public void getVideoCommentsByLike(String videoId, BaseResult<List<Comments>> result){
        //找出视频的所有评论(先看最热评论)

        List<Comments> comments = commentsDao.queryAllByVideoId(videoId);
        if (comments.size()==0){
            result.construct(false,"找不到");
            return;
        }
        //排序(先看最热评论)
        comments = comments.stream().sorted(Comparator.comparing(Comments::getLikeNum).reversed())
                .collect(Collectors.toList());
        result.construct(true, "查询成功", comments);

    }

    public void makeComments(CommentsBean commentsBean, BaseResult<CommentsResult> result){
        //给视频加一个评论
//        if(videoDao.queryVideoById(commentsBean.getVideoId())==null){
//            result.construct(false,"未找到视频,评论失败");
//            return;
//        }
        Comments comments = new Comments();
        Date date = new Date();
        commentsBean.setTime(date);
        BeanUtils.copyProperties(commentsBean, comments);
        commentsDao.save(comments);
        result.construct(true,"评论成功",new CommentsResult(comments));
    }

    public void deleteComments(String id,BaseResult<CommentsResult> result){
        //删评论
        if (commentsDao.queryCommentsById(id)==null){
            result.construct(false,"没有该评论,删除失败");//后边没加东西
            return;
        }
        commentsDao.deleteById(id);
        result.construct(true,"删除成功");

    }

}

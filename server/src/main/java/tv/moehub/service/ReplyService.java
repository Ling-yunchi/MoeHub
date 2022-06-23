package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tv.moehub.bean.ReplyBean;
import tv.moehub.dao.CommentsDao;
import tv.moehub.dao.ReplyDao;
import tv.moehub.entity.Reply;
import tv.moehub.model.BaseResult;
import tv.moehub.model.ReplyResult;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyService {
    
    private final ReplyDao replyDao;
    private final CommentsDao commentsDao;

    public void makeReply(ReplyBean replyBean, BaseResult<ReplyResult> result){
        //回复评论
        if(commentsDao.queryCommentsById(replyBean.getCommentsId())==null){
            result.construct(false,"未找到评论,回复失败");
            return;
        }
        Reply reply = new Reply();
        Date date = new Date();
        replyBean.setTime(date);
        BeanUtils.copyProperties(replyBean, reply);
        replyDao.save(reply);
        result.construct(true,"回复成功",new ReplyResult(reply));
    }

    public void getCommentsReply(String commentsId, BaseResult<List<Reply>> result){
        //找出评论的回复

        List<Reply> reply = replyDao.queryAllByCommentsId(commentsId);
        if (reply.size()==0){
            result.construct(false,"找不到");
            return;
        }

        reply = reply.stream().sorted(Comparator.comparing(Reply::getTime).reversed())
                .collect(Collectors.toList());
        result.construct(true, "查询成功", reply);

    }

    public void deleteReply(String Id,BaseResult<ReplyResult> result){
    //删回复
        if (replyDao.queryReplyById(Id)== null){
            result.construct(false,"删除失败");//后边没加东西
            return;
        }
        replyDao.deleteById(Id);
        result.construct(true,"删除成功");

    }
    
}

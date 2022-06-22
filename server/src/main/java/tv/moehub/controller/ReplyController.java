package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.bean.ReplyBean;
import tv.moehub.entity.Reply;
import tv.moehub.model.BaseResult;
import tv.moehub.model.ReplyResult;
import tv.moehub.service.ReplyService;

import java.util.List;

@RestController
@RequestMapping("/reply")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReplyController {
    private final ReplyService replyService;

    @GetMapping("/getReply")//找评论的回复
    public BaseResult<List<Reply>> getCommentsReply(@RequestParam String commentsId){
        BaseResult<List<Reply>> result = new BaseResult<>();
        replyService.getCommentsReply(commentsId,result);
        return result;//是否return reply集合
    }

    @PostMapping("/makeReply")//写评论
    public BaseResult<ReplyResult> makeReply(@RequestBody ReplyBean replyBean){
        BaseResult<ReplyResult> result = new BaseResult<>();
        replyService.makeReply(replyBean,result);
        return result;
    }

    @DeleteMapping("/deleteReply")
    public BaseResult<ReplyResult> deleteReply(@RequestParam String id){
        BaseResult<ReplyResult> result = new BaseResult<>();
        replyService.deleteReply(id,result);
        return result;

    }
}

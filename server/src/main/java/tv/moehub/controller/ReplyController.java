package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.annotation.Login;
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

    @Login
    @PostMapping("/add")//写回复
    public BaseResult<ReplyResult> addReply(@RequestBody ReplyBean replyBean) {
        BaseResult<ReplyResult> result = new BaseResult<>();
        replyService.addReply(replyBean, result);
        return result;
    }

    @Login
    @GetMapping("/delete")//删回复
    public BaseResult<ReplyResult> deleteReply(@RequestParam String id) {
        BaseResult<ReplyResult> result = new BaseResult<>();
        replyService.deleteReply(id, result);
        return result;

    }

    @GetMapping("/search")//找评论的回复
    public BaseResult<List<Reply>> searchReplyByTime(@RequestParam String commentsId) {
        BaseResult<List<Reply>> result = new BaseResult<>();
        replyService.searchReplyByTime(commentsId, result);
        return result;
    }
}





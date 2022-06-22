package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.bean.CommentsBean;
import tv.moehub.entity.Comments;
import tv.moehub.model.BaseResult;
import tv.moehub.model.CommentsResult;
import tv.moehub.service.CommentsService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping("/getVideoComments")//找视频的评论
    public BaseResult<List<Comments>> getVideoComments(@RequestParam String videoId){
        BaseResult<List<Comments>> result = new BaseResult<>();
        commentsService.getVideoComments(videoId,result);
        return result;//是否return comments?
    }

    @PostMapping("/makeComments")//写评论
    public BaseResult<CommentsResult> makeComments(@RequestBody CommentsBean commentsBean){
        BaseResult<CommentsResult> result = new BaseResult<>();
        commentsService.makeComments(commentsBean,result);
        return result;
    }

    @DeleteMapping("/deleteComments")
    public BaseResult<CommentsResult> deleteComments(@RequestParam String id){
        BaseResult<CommentsResult> result = new BaseResult<>();
        commentsService.deleteComments(id,result);
        return result;

    }

}

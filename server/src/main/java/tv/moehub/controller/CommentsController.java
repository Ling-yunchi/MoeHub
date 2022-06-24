package tv.moehub.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tv.moehub.annotation.Login;
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

    @Login
    @PostMapping("/add")//写评论
    public BaseResult<CommentsResult> addComments(@RequestBody CommentsBean commentsBean){
        BaseResult<CommentsResult> result = new BaseResult<>();
        commentsService.addComments(commentsBean,result);
        return result;
    }

    @Login
    @GetMapping("/delete")//删评论
    public BaseResult<CommentsResult> deleteComments(@RequestParam String id){
        BaseResult<CommentsResult> result = new BaseResult<>();
        commentsService.deleteComments(id,result);
        return result;

    }

    @GetMapping("/search")//(先看最新评论)
    public BaseResult<List<Comments>> searchCommentsByTime(@RequestParam String videoId){
        BaseResult<List<Comments>> result = new BaseResult<>();
        commentsService.searchCommentsByTime(videoId,result);
        return result;
    }

//    @GetMapping//(先看最热评论)
//    public BaseResult<List<Comments>> getVideoCommentsByLike(@RequestParam String videoId){
//        BaseResult<List<Comments>> result = new BaseResult<>();
//        commentsService.getVideoCommentsByLike(videoId,result);
//        return result;
//    }


}

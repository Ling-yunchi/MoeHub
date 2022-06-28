package tv.moehub.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import tv.moehub.annotation.Login;
import tv.moehub.bean.CommentsBean;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.CommentsResult;
import tv.moehub.service.CommentsService;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor(onConstructor = @__(@Autowired))

public class CommentsController {
    private final CommentsService commentsService;

    @Login
    @PostMapping("/add")//写评论
    public BaseResult<Void> addComments(@RequestBody CommentsBean commentsBean) {
        BaseResult<Void> result = new BaseResult<>();
        commentsService.addComments(commentsBean, result);
        return result;
    }

    @Login
    @GetMapping("/delete")//删评论
    public BaseResult<Void> deleteComments(@RequestParam String id) {
        BaseResult<Void> result = new BaseResult<>();
        commentsService.deleteComments(id, result);
        return result;
    }

    @GetMapping("/search")//(先看最新评论)
    public BasePageResult<CommentsResult> searchCommentsByTime(@RequestParam String videoId, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        BasePageResult<CommentsResult> result = new BasePageResult<>();
        commentsService.searchCommentsByTime(videoId, pageNum,pageSize, result);
        return result;
    }

}

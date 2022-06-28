package tv.moehub.controller;


import com.github.pagehelper.Page;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public BaseResult<CommentsResult> addComments(@RequestBody CommentsBean commentsBean) {
        BaseResult<CommentsResult> result = new BaseResult<>();
        commentsService.addComments(commentsBean, result);
        return result;
    }

    @Login
    @GetMapping("/delete")//删评论
    public BaseResult<CommentsResult> deleteComments(@RequestParam String id) {
        BaseResult<CommentsResult> result = new BaseResult<>();
        commentsService.deleteComments(id, result);
        return result;

    }

    @GetMapping("/search")//(先看最新评论)
    public BasePageResult<CommentsResult> searchCommentsByTime(@RequestParam String videoId, @RequestParam int pageNum, @RequestParam int pageSize) {

        BaseResult<Page<CommentsResult>> comments = new BaseResult<>();
        commentsService.searchCommentsByTime(videoId, comments, pageNum,pageSize);
        BasePageResult<CommentsResult> result= new BasePageResult<CommentsResult>(comments.getSuccess(),comments.getMessage(),comments.getData());
        return result;
    }

}
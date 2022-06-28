package tv.moehub.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.bean.VideoBean;
import tv.moehub.dao.UserDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.User;
import tv.moehub.entity.Video;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.utils.FileUtil;
import tv.moehub.utils.Uuid;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VideoService {
    private final VideoDao videoDao;
    private final UserDao userDao;
    private final FileService fileService;

    public void queryVideoById(String videoId, BaseResult<Video> result) {
        Video video = videoDao.queryVideoById(videoId);
        if (video == null) {
            result.construct(false, "视频不存在");
            return;
        }
        result.construct(true, "视频如下", video);
    }

    public void searchVideoByTitle(String videoTitle, BaseResult<List<Video>> result) {
        String videoTitleLike = "%" + videoTitle + "%";
        List<Video> videoList = videoDao.findByTitleLike(videoTitleLike);
        if (videoList.size() == 0) {
            result.construct(false, "无相关视频");
            return;
        }
        result.construct(true, "相关视频如下", videoList);
    }

    public void searchVideoByAuthor(String nickname, BaseResult<List<Video>> result) {
        User user = userDao.findByNickname(nickname);
        if (user != null) {
            List<Video> videoList = videoDao.findByAuthorId(user.getId());
            if (videoList.size() == 0) {
                result.construct(false, "该用户未上传视频");
                return;
            }
            result.construct(true, "相关视频如下", videoList);
            return;
        }
        result.construct(false, "未查询到该用户");
    }

    public void uploadTemp(MultipartFile file, BaseResult<String> result) {
        var filePrefix = "temp/" + Uuid.getUuid() + "." + FileUtil.getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            String tempPrefix = null;
            tempPrefix = fileService.uploadFile(file, filePrefix);
            result.construct(true, "上传成功", tempPrefix);
        } catch (Exception e) {
            result.construct(false, "上传失败");
        }
    }

    public void add(VideoBean videoBean, BaseResult<Void> result) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        try {
            var videoPrefix = "video/" + videoBean.getVideoPrefix().substring(videoBean.getVideoPrefix().lastIndexOf("/") + 1);
            log.info("move video from {} to {}", videoBean.getVideoPrefix(), videoPrefix);
            fileService.moveFile(videoBean.getVideoPrefix(), videoPrefix);
            var coverPrefix = "cover/" + videoBean.getCoverPrefix().substring(videoBean.getCoverPrefix().lastIndexOf("/") + 1);
            log.info("move cover from {} to {}", videoBean.getCoverPrefix(), coverPrefix);
            fileService.moveFile(videoBean.getCoverPrefix(), coverPrefix);
        } catch (Exception e) {
            result.construct(false, "上传失败,可能是文件上传错误导致");
            return;
        }
        Video video = Video.builder()
                .title(videoBean.getTitle())
                .description(videoBean.getDescription())
                .authorId(userId)
                .length(videoBean.getLength())
                .createAt(new Date())
                .coverPrefix(videoBean.getCoverPrefix())
                .videoPrefix(videoBean.getVideoPrefix())
                .build();
        videoDao.save(video);

        result.construct(true, "上传成功");
    }

    public void getUserVideo(Integer pageNum, Integer pageSize, BasePageResult<Video> result) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<Video> videoList = videoDao.findByAuthorIdPageable(userId, pageable);
        result.construct(true, "查询成功", videoList);
    }
}

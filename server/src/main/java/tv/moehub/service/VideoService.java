package tv.moehub.service;


import io.minio.errors.*;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tv.moehub.bean.VideoBean;
import tv.moehub.dao.SearchDao;
import tv.moehub.dao.UserDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Search;
import tv.moehub.entity.User;
import tv.moehub.entity.Video;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoResult;
import tv.moehub.utils.FileUtil;
import tv.moehub.utils.Uuid;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VideoService {
    private final VideoDao videoDao;
    private final UserDao userDao;
    private final FileService fileService;

    public void queryVideoById(String videoId, BaseResult<VideoResult> result) {
        VideoResult videoResult = videoDao.queryVideoById(videoId);
        if (videoResult == null) {
            result.construct(false, "视频不存在");
            return;
        }
        result.construct(true, "视频如下", videoResult);
    }

    public void searchVideoByTitle(String videoTitle, BasePageResult<VideoResult> result, int pageNum, int pageSize) {
        String videoTitleLike = "%" + videoTitle + "%";
        List<VideoResult> videoResultList = videoDao.findByTitleLike(videoTitleLike);
        if (videoResultList.size() == 0) {
            result.construct(false, "无相关视频", null);
            return;
        }
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC);
        Page<VideoResult> videoResultPage = new PageImpl<VideoResult>(videoResultList, pageable, videoResultList.size());
        result.construct(true, "相关视频如下", videoResultPage);
    }

    public void searchVideoByAuthor(String nickname, BasePageResult<VideoResult> result, int pageNum, int pageSize) {
        User user = userDao.findByNickname(nickname);
        if (user != null) {
            List<VideoResult> videoResultList = videoDao.findByAuthorId(user.getId());
            if (videoResultList.size() == 0) {
                result.construct(false, "该用户未上传视频", null);
                return;
            }
            Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC);
            Page<VideoResult> videoResultPage = new PageImpl<VideoResult>(videoResultList, pageable, videoResultList.size());
            result.construct(true, "相关视频如下", videoResultPage);
            return;
        }
        result.construct(false, "未查询到该用户", null);
    }

    public void uploadVideo(MultipartFile video, BaseResult<String> result) {

    }

    public void upload(MultipartFile video, BaseResult<String> result) {
        var filePrefix = "video/" + Uuid.getUuid() + "." + FileUtil.getFileExtension(Objects.requireNonNull(video.getOriginalFilename()));
        try {
            String videoId = null;
            videoId = fileService.uploadFile(video, filePrefix);
            result.construct(true, "上传成功", videoId);
        } catch (Exception e) {
            result.construct(false, "上传失败");
            return;
        }

    }

    public void add(VideoBean videoBean, BaseResult<Void> result) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();

    }
}

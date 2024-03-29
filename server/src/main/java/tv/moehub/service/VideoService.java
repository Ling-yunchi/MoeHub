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
import tv.moehub.dao.*;
import tv.moehub.entity.*;
import tv.moehub.model.*;
import tv.moehub.utils.FileUtil;
import tv.moehub.utils.Uuid;

import java.util.*;
import java.util.stream.Collectors;

import static tv.moehub.utils.Const.category;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VideoService {
    private final VideoDao videoDao;
    private final UserDao userDao;
    private final FileService fileService;
    private final FavoriteDao favoriteDao;
    private final LikeVideoDao likeVideoDao;
    private final LastPlayLocationDao lastPlayLocationDao;

//    public void queryVideoById(String videoId, BaseResult<VideoListResult> result) {
//        VideoListResult videoResult = videoDao.queryVideoById(videoId);
//        if (videoResult == null) {
//            result.construct(false, "视频不存在");
//            return;
//        }
//        result.construct(true, "视频如下", videoResult);
//    }

    public void showHot(BasePageResult<VideoListResult> result, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<VideoListResult> videoList = videoDao.showHot(pageable).map(videoListResult -> {
            try {
                videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                return videoListResult;
            } catch (Exception e) {
                log.error("get video cover url error", e);
                videoListResult.setCoverUrl(null);
                return videoListResult;
            }
        });
        result.construct(true, "相关视频如下", videoList);
    }

    public void searchVideoByTitle(String videoTitle, BasePageResult<VideoListResult> result, int pageNum, int pageSize) {
        String videoTitleLike = "%" + videoTitle + "%";
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<VideoListResult> videoList = videoDao.findByTitleLike(videoTitleLike, pageable).map(videoListResult -> {
            try {
                videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                return videoListResult;
            } catch (Exception e) {
                log.error("get video cover url error", e);
                videoListResult.setCoverUrl(null);
                return videoListResult;
            }
        });
        result.construct(true, "相关视频如下", videoList);
    }

    public void searchVideoByAuthor(String nickname, BasePageResult<VideoListResult> result, int pageNum, int pageSize) {
        User user = userDao.findByNickname(nickname);
        if (user != null) {
            Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
            Page<VideoListResult> videoList = videoDao.findByAuthorIdPage(user.getId(), pageable).map(videoListResult -> {
                try {
                    videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                    return videoListResult;
                } catch (Exception e) {
                    log.error("get video cover url error", e);
                    videoListResult.setCoverUrl(null);
                    return videoListResult;
                }
            });
            result.construct(true, "查询成功", videoList);
        } else {
            result.construct(false, "未查询到该用户");
        }
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
        var videoPrefix = "video/" + videoBean.getVideoPrefix().substring(videoBean.getVideoPrefix().lastIndexOf("/") + 1);
        var coverPrefix = "cover/" + videoBean.getCoverPrefix().substring(videoBean.getCoverPrefix().lastIndexOf("/") + 1);
        try {
            log.info("move video from {} to {}", videoBean.getVideoPrefix(), videoPrefix);
            fileService.moveFile(videoBean.getVideoPrefix(), videoPrefix);
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
                .coverPrefix(coverPrefix)
                .videoPrefix(videoPrefix)
                .views(0)
                .category(videoBean.getCategory())
                .build();
        videoDao.save(video);

        result.construct(true, "上传成功");
    }

    public void getUserVideo(String userId, Integer pageNum, Integer pageSize, BasePageResult<VideoListResult> result) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<VideoListResult> videoList = videoDao.findByAuthorIdPage(userId, pageable).map(videoListResult -> {
            try {
                videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                return videoListResult;
            } catch (Exception e) {
                log.error("get video cover url error", e);
                videoListResult.setCoverUrl(null);
                return videoListResult;
            }
        });
        result.construct(true, "查询成功", videoList);
    }

    public void getVideoDetails(BaseResult<List<VideoDetailResult>> result) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        var list = videoDao.findVideoDetailByAuthorId(userId).stream().peek(video -> {
            try {
                video.setCoverUrl(fileService.getFileUrl(video.getCoverUrl()));
            } catch (Exception e) {
                log.error("get video cover url error", e);
                video.setCoverUrl(null);
            }
        }).collect(Collectors.toList());
        result.construct(true, "查询成功", list);
    }

    public void delete(String videoId, BaseResult<Void> result) {
        Optional<Video> videoOptional = videoDao.findById(videoId);
        if (!videoOptional.isPresent()) {
            result.construct(false, "视频不存在");
            return;
        }
        var video = videoOptional.get();
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        if (!userId.equals(video.getAuthorId())) {
            result.construct(false, "没有权限");
            return;
        }

        try {
            fileService.deleteFile(video.getCoverPrefix());
            fileService.deleteFile(video.getVideoPrefix());
        } catch (Exception e) {
            log.error("delete video error", e);
            result.construct(false, "删除失败");
            return;
        }

        videoDao.deleteById(videoId);
        result.construct(true, "删除成功");
    }

    public void getVideoInfo(String videoId, BaseResult<VideoResult> result) {
        Optional<Video> videoOptional = videoDao.findById(videoId);
        if (!videoOptional.isPresent()) {
            result.construct(false, "视频不存在");
            return;
        }
        var video = videoOptional.get();
        String coverUrl = null;
        String videoUrl = null;
        try {
            coverUrl = fileService.getFileUrl(video.getCoverPrefix());
            videoUrl = fileService.getFileUrl(video.getVideoPrefix());
        } catch (Exception e) {
            log.error("get video or cover url error", e);
            result.construct(false, "获取视频或封面失败");
            return;
        }
        User author = userDao.queryUserById(video.getAuthorId());
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        boolean isLiked = false;
        boolean isFavorite = false;
        if (userId != null) {
            Favorite favorite = favoriteDao.queryByUserIdAndVideoId(userId, videoId);
            if (favorite != null) {
                isFavorite = true;
            }
            LikeVideo likeVideo = likeVideoDao.queryLikeVideoByUserIdAndVideoId(userId, videoId);
            if (likeVideo != null) {
                isLiked = true;
            }
        }
        VideoResult res = VideoResult.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .length(video.getLength())
                .createAt(video.getCreateAt())
                .coverUrl(coverUrl)
                .videoUrl(videoUrl)
                .authorId(video.getAuthorId())
                .authorName(author == null ? "用户已注销" : author.getNickname())
                .authorAvatar(author == null ? "" : author.getAvatar())
                .views(video.getViews())
                .favorites(favoriteDao.countByVideoId(videoId))
                .likes(0)
                .likes(likeVideoDao.countLikeVideoByVideoId(videoId))
                .isFavorite(isFavorite)
                .isLiked(isLiked)
                .category(video.getCategory())
                .build();
        result.construct(true, "查询成功", res);
    }

    public void view(String videoId, BaseResult<Void> result) {
        Optional<Video> videoOptional = videoDao.findById(videoId);
        if (!videoOptional.isPresent()) {
            result.construct(false, "视频不存在");
            return;
        }
        var video = videoOptional.get();
        video.setViews(video.getViews() + 1);
        videoDao.save(video);
        result.construct(true, "成功");
    }

    public void getAllVideo(Integer pageNum, Integer pageSize, BasePageResult<VideoListResult> result) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<VideoListResult> videoList = videoDao.findAllPage(pageable).map(videoListResult -> {
            try {
                videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                return videoListResult;
            } catch (Exception e) {
                log.error("get video cover url error", e);
                videoListResult.setCoverUrl(null);
                return videoListResult;
            }
        });
        result.construct(true, "查询成功", videoList);
    }

    public void getAllVideoByCategory(BaseResult<List<VideoListResult>> result) {
        List<VideoListResult> res = new ArrayList<>();
        Arrays.stream(category).forEach(category -> {
            Pageable pageable = PageRequest.of(0, 5);
            videoDao.findByCategoryPage(category, pageable).map(videoListResult -> {
                try {
                    videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                } catch (Exception e) {
                    log.error("get video cover url error", e);
                    videoListResult.setCoverUrl(null);
                }
                return videoListResult;
            }).forEach(res::add);
        });
        result.construct(true, "查询成功", res);
    }


    public void getLastPosition(String videoId, BaseResult<Integer> result) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        LastPlayLocation lastPosition = lastPlayLocationDao.findLastPositionByVideoIdAndUserId(videoId, userId);
        if (lastPosition != null) {
            result.construct(true, "上次观看到", lastPosition.getPlayLocation());
        } else {
            result.construct(false, "没看过", -1);
        }
    }

    public void setPlayPosition(String videoId, Integer playPosition, BaseResult<Void> result) {
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        LastPlayLocation lastPosition = lastPlayLocationDao.findLastPositionByVideoIdAndUserId(videoId, userId);
        if (lastPosition == null) {
            lastPosition = new LastPlayLocation();
            lastPosition.setVideoId(videoId);
            lastPosition.setUserId(userId);
        }
        lastPosition.setPlayLocation(playPosition);
        lastPlayLocationDao.save(lastPosition);
        result.construct(true, "记录成功");
    }
}

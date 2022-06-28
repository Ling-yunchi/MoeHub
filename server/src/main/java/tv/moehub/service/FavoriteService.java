package tv.moehub.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import tv.moehub.dao.FavoriteDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Favorite;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoListResult;
import tv.moehub.model.VideoResult;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final VideoDao videoDao;
    private final FileService fileService;

    public void isFavorite(String userId, String videoId, BaseResult<Void> result) {
        Favorite favorite = favoriteDao.queryByUserIdAndVideoId(userId, videoId);
        if (favorite == null) {
            Favorite f = Favorite.builder()
                    .userId(userId)
                    .videoId(videoId)
                    .build();
            favoriteDao.save(f);
            result.construct(true, "收藏成功");
        } else {
            favoriteDao.deleteById(favorite.getId());
            result.construct(true, "取消收藏成功");
        }
    }

    public void showMyFavorite(String userId, BasePageResult<VideoListResult> result, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        Page<VideoListResult> videoList = favoriteDao.queryByUserId(userId, pageable);
        videoList.map(videoListResult -> {
            try {
                videoListResult.setCoverUrl(fileService.getFileUrl(videoListResult.getCoverUrl()));
                return videoListResult;
            } catch (Exception e) {
                log.error("get video cover url error", e);
                videoListResult.setCoverUrl(null);
                return videoListResult;
            }
        });
        result.construct(true, "收藏视频如下", videoList);
    }
}

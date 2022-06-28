package tv.moehub.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;
import tv.moehub.dao.FavoriteDao;
import tv.moehub.dao.VideoDao;
import tv.moehub.entity.Favorite;
import tv.moehub.model.BasePageResult;
import tv.moehub.model.BaseResult;
import tv.moehub.model.VideoResult;


import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FavoriteService {
    private final FavoriteDao favoriteDao;
    private final VideoDao videoDao;

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

    public void showMyFavorite(String userId, BasePageResult<VideoResult> result, int pageNum, int pageSize) {
        List<Favorite> favoriteList = favoriteDao.queryByUserId(userId);
        List<VideoResult> videoResultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.Direction.DESC);
        if (favoriteList.size() == 0) {
            result.construct(true, "暂未收藏视频", null);
            return;
        }
        for (Favorite f : favoriteList)
            videoResultList.add(videoDao.queryVideoById(f.getVideoId()));
        Page<VideoResult> videoResultPage = new PageImpl<VideoResult>(videoResultList, pageable, videoResultList.size());
        result.construct(true, "收藏视频如下", videoResultPage);
    }

}

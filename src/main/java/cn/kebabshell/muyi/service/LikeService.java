package cn.kebabshell.muyi.service;

import cn.kebabshell.muyi.common.entity.LikeBase;

public interface LikeService {
    /**
     * 点赞
     * @param likeBase
     * @param token
     * @return
     */
    Boolean addLike(LikeBase likeBase, String token);
    /**
     * 取消点赞
     * @param likeBase
     * @param token
     * @return
     */
    Boolean cancelLike(LikeBase likeBase, String token);

    /**
     *
     * @param picId
     * @return
     */
    int getLikeCount(Integer picId);
}

package cn.kebabshell.muyi.service;

import cn.kebabshell.muyi.common.dto.UserHitDTO;
import cn.kebabshell.muyi.common.entity.HitBase;

import java.util.List;

public interface HitService {
    /**
     * 拿到图片的点击量
     * @param picId
     * @return
     */
    int getPicHitCount(Integer picId);

    /**
     * 拿到图片的点击数据详细信息
     * @param picId
     * @param pageNum
     * @param count
     * @return
     */
    List<UserHitDTO> getPicHitList(Integer picId, int pageNum, int count);

    /**
     * 增加图片点击量
     * @param hitBase
     * @return
     */
    Boolean addPicHit(HitBase hitBase);
}

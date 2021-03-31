package cn.kebabshell.muyi.service.impl;

import cn.kebabshell.muyi.common.dto.UserHitDTO;
import cn.kebabshell.muyi.common.entity.HitBase;
import cn.kebabshell.muyi.common.entity.UserBase;
import cn.kebabshell.muyi.common.mapper.HitBaseMapper;
import cn.kebabshell.muyi.common.mapper.UserBaseMapper;
import cn.kebabshell.muyi.service.HitService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-31 17:49
 **/
@Service
public class HitServiceImpl implements HitService {
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private HitBaseMapper hitBaseMapper;
    @Override
    public int getPicHitCount(Integer picId) {
        QueryWrapper<HitBase> hitBaseQueryWrapper = new QueryWrapper<>();
        hitBaseQueryWrapper
                .eq("hit_pic_id", picId);
        return hitBaseMapper.selectCount(hitBaseQueryWrapper);
    }

    @Override
    public List<UserHitDTO> getPicHitList(Integer picId, int pageNum, int count) {
        LinkedList<UserHitDTO> res = new LinkedList<>();
        QueryWrapper<HitBase> hitBaseQueryWrapper = new QueryWrapper<>();
        hitBaseQueryWrapper
                .eq("hit_pic_id", picId);
        List<HitBase> list = hitBaseMapper.selectPage(new Page<>(pageNum, count), hitBaseQueryWrapper).getRecords();
        if (list != null){
            for (HitBase hitBase : list) {
                Integer userId = hitBase.getHitUserId();
                QueryWrapper<UserBase> userBaseQueryWrapper = new QueryWrapper<>();
                userBaseQueryWrapper
                        .eq("user_id", userId);
                UserBase userBase = userBaseMapper.selectOne(userBaseQueryWrapper);
                res.add(new UserHitDTO(hitBase, userBase));
            }
        }
        return res;
    }

    @Override
    public Boolean addPicHit(HitBase hitBase) {
        LocalDateTime now = LocalDateTime.now();
        hitBase.setGmtCreate(now);
        hitBase.setGmtModified(now);
        hitBaseMapper.insert(hitBase);
        return true;
    }
}

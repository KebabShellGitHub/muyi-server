package cn.kebabshell.muyi.common.mapper;

import cn.kebabshell.muyi.common.dto.HotHitDTO;
import cn.kebabshell.muyi.common.entity.HitBase;
import cn.kebabshell.muyi.common.entity.UserBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 点击表 Mapper 接口
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
public interface HitBaseMapper extends BaseMapper<HitBase> {

    Page<HotHitDTO> getHotHit(Page<HotHitDTO> page);

}

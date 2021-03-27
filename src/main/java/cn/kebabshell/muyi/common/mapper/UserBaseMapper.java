package cn.kebabshell.muyi.common.mapper;

import cn.kebabshell.muyi.common.entity.UserBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * 用户基本表 Mapper 接口
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
public interface UserBaseMapper extends BaseMapper<UserBase> {
    Page<UserBase> getHotAuthor(Page<UserBase> page);
}

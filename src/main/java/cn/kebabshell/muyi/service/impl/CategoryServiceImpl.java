package cn.kebabshell.muyi.service.impl;

import cn.kebabshell.muyi.common.entity.CategoryBase;
import cn.kebabshell.muyi.common.entity.PicCategory;
import cn.kebabshell.muyi.common.mapper.CategoryBaseMapper;
import cn.kebabshell.muyi.common.mapper.PicCategoryMapper;
import cn.kebabshell.muyi.server.controller.PicController;
import cn.kebabshell.muyi.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-04-24 10:49
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryBaseMapper categoryBaseMapper;
    @Autowired
    private PicCategoryMapper picCategoryMapper;

    @Override
    public List<CategoryBase> getAll() {
        return categoryBaseMapper.selectList(null);
    }

    @Override
    public List<PicCategory> getPicSorts(Integer picId) {
        QueryWrapper<PicCategory> picCategoryQueryWrapper = new QueryWrapper<>();
        picCategoryQueryWrapper.eq("pic_id", picId);
        return picCategoryMapper.selectList(picCategoryQueryWrapper);
    }
}

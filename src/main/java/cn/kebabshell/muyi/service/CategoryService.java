package cn.kebabshell.muyi.service;

import cn.kebabshell.muyi.common.entity.CategoryBase;
import cn.kebabshell.muyi.common.entity.PicCategory;

import java.util.List;

public interface CategoryService {

    List<CategoryBase> getAll();

    List<PicCategory> getPicSorts(Integer picId);
}

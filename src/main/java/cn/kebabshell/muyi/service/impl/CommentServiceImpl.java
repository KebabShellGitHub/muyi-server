package cn.kebabshell.muyi.service.impl;

import cn.kebabshell.muyi.common.entity.CommentBase;
import cn.kebabshell.muyi.common.mapper.CommentBaseMapper;
import cn.kebabshell.muyi.service.CommentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @program: muyi-server
 * @description:
 * @author: KebabShell
 * @create: 2021-03-31 16:34
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentBaseMapper commentBaseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addComment(CommentBase commentBase) {
        LocalDateTime now = LocalDateTime.now();
        commentBase.setGmtCreate(now);
        commentBase.setGmtModified(now);

        commentBaseMapper.insert(commentBase);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delComment(Integer commentId) {
        commentBaseMapper.deleteById(commentId);
        return true;
    }

    @Override
    public List<CommentBase> getComment(int picId, int pageNum, int count) {
        QueryWrapper<CommentBase> commentBaseQueryWrapper = new QueryWrapper<>();
        commentBaseQueryWrapper
                .eq("comment_pic_id", picId);
        return commentBaseMapper.selectPage(new Page<>(pageNum, count), commentBaseQueryWrapper).getRecords();
    }
}

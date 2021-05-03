package cn.kebabshell.muyi.service;

import cn.kebabshell.muyi.common.dto.CommentDTO;
import cn.kebabshell.muyi.common.entity.CommentBase;
import cn.kebabshell.muyi.handler.result.MyResult;
import cn.kebabshell.muyi.handler.result.ResultCode;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {

     CommentDTO addComment(CommentBase commentBase);

     Boolean delComment(Integer commentId);

     List<CommentDTO> getComment(int picId, int pageNum, int count);

}

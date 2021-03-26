package cn.kebabshell.muyi.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 点赞表
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="LikeBase对象", description="点赞表")
public class LikeBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "点赞id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "被点赞图片id")
    private Integer likePicId;

    @ApiModelProperty(value = "点赞人id")
    private Integer likeUserId;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime gmtModified;


}

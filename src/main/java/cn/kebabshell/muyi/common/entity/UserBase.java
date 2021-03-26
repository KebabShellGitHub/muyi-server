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
 * 用户基本表
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserBase对象", description="用户基本表")
public class UserBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "头像（缩略图）")
    private String userAvatarThumbUrl;

    @ApiModelProperty(value = "自我介绍")
    private String userIntroduction;

    @ApiModelProperty(value = "是否封禁中")
    private Boolean isBenning;

    @ApiModelProperty(value = "是否已经删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime gmtModified;


}

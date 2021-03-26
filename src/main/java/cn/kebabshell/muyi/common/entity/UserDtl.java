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
 * 用户详细表
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserDtl对象", description="用户详细表")
public class UserDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "头像原图")
    private String userAvatarUrl;

    @ApiModelProperty(value = "性别")
    private Boolean userGender;

    @ApiModelProperty(value = "email")
    private String userEmail;

    @ApiModelProperty(value = "用户所在地区")
    private String userArea;

    @ApiModelProperty(value = "职业")
    private String userJob;

    @ApiModelProperty(value = "学校")
    private String userSchool;

    @ApiModelProperty(value = "生日")
    private LocalDateTime userBirthday;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime gmtModified;


}

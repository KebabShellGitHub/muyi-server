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
 * 具体权限表
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AuthPermission对象", description="具体权限表")
public class AuthPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "具体权限id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "具体权限名")
    private String permissionName;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime gmtModified;


}

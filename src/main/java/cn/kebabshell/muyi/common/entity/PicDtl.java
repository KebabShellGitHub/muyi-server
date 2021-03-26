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
 * 图片详细表
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PicDtl对象", description="图片详细表")
public class PicDtl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "图片id")
    private Integer picId;

    @ApiModelProperty(value = "原图")
    private String picUrl;

    @ApiModelProperty(value = "图片描述")
    private String picDescription;

    @ApiModelProperty(value = "拍摄地点")
    private String picArea;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime picCreate;

    @ApiModelProperty(value = "拍摄设备")
    private String picDevice;

    @ApiModelProperty(value = "编辑软件")
    private String picEdit;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime gmtModified;


}

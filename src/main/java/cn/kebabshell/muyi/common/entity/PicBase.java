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
 * 图片基本表
 * </p>
 *
 * @author kebabshell
 * @since 2021-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="PicBase对象", description="图片基本表")
public class PicBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "作者/用户id")
    private Integer picAuthorId;

    @ApiModelProperty(value = "图片名")
    private String picName;

    @ApiModelProperty(value = "图片缩略图")
    private String picThumbUrl;

    @ApiModelProperty(value = "是否已经删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建日期")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "更新日期")
    private LocalDateTime gmtModified;


}

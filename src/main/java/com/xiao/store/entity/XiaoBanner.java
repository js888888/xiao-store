package com.xiao.store.entity;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author jing
 * @since 2021-05-12
 */
@Data
@ApiModel(value="XiaoBanner对象", description="")
public class XiaoBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty(value = "图片名称")
    private String photoName;

    @ApiModelProperty(value = "图片地址")
    private String photoUrl;

    @ApiModelProperty(value = "是否启用")
    private String stop;

    @ApiModelProperty(value = "逻辑删除(1为删除)")
    private String del;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATED_TIME")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("UPDATED_TIME")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "后缀")
    private String lastStr;


}

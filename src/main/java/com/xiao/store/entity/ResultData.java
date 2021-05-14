package com.xiao.store.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @auther jing
 * @date
 **/
@Getter
@ApiModel("统一返回封装类")
public class ResultData {

    @ApiModelProperty(name = "code", value = "编码", dataType = "String")
    private final String code;

    @ApiModelProperty(name = "message", value = "返回信息", dataType = "String")
    private final String message;

    @ApiModelProperty(name = "data", value = "返回内容", dataType = "Object")
    private final Object data;

    private ResultData(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResultData returnFail(String message) {
        return new ResultData("fail", message, "");
    }

    public static ResultData returnMessage(String message) {
        return new ResultData("success", message, "");
    }

    public static ResultData resultData(Object data) {
        return new ResultData("success", "", data);
    }

}


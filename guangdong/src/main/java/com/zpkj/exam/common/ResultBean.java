package com.zpkj.exam.common;

import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * ResultBean
 */
@Data
@ApiModel(value = "返回的数据")
public class ResultBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "代码", example = "0")
    private Integer code = 0; // code
    @ApiModelProperty(value = "信息", example = "成功")
    private String msg = ""; // msg
    @ApiModelProperty(value = "data数量", example = "30")
    private long count; // count
    @ApiModelProperty(value = "数据")
    private Object data; // data

    public ResultBean() {
    }

    public ResultBean(Object data) {
        if (data instanceof Page) {
            Page page = (Page) data;
            this.count = page.getTotal();
        } else if (data instanceof List) {
            List list = (List) data;
            this.count = list.size();
        }
        this.data = data;
    }

    public ResultBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultBean(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        if (data instanceof Page) {
            Page page = (Page) data;
            this.count = page.getTotal();
        } else if (data instanceof List) {
            List list = (List) data;
            this.count = list.size();
        }
        this.data = data;
    }

    public ResultBean(Integer code, String msg, long count, Object data) {
        this.code = code;
        this.msg = msg;
        if (data instanceof Page) {
            Page page = (Page) data;
            this.count = page.getTotal();
        } else if (data instanceof List) {
            List list = (List) data;
            this.count = list.size();
        }
        this.data = data;
    }
}
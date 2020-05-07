package com.liugh.config;

import com.baomidou.mybatisplus.plugins.Page;
import com.fasterxml.jackson.databind.util.BeanUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@ApiModel(value = "PageListIO", description = "列表及分页数据")
public class PageListIO<T> implements Serializable {

    @ApiModelProperty(value = "当前页码", example = "1")
    private int pageIndex;
    @ApiModelProperty(value = "单页记录数量", example = "20")
    private int pageSize;
    @ApiModelProperty(value = "排序字段", example = " ")
    private String sortName;
    @ApiModelProperty(value = "排序方向", example = " ")
    private String sortOrder;
    @ApiModelProperty(value = "表单对象")
    private T formData;

    public PageListIO() {
    }

    public T getFormData() {
        return formData;
    }

    public void setFormData(T formData) {
        this.formData = formData;
    }

    public Page<T> getPagePlus(){
        Page page = new Page<>(this.pageIndex, this.pageSize);
        if (!StringUtils.isBlank(this.sortName)) {
            if (StringUtils.equalsIgnoreCase("ascending", this.sortOrder)) {
                page.setOrderByField(this.sortName);
                page.setAsc(true);
            }else{
                page.setOrderByField(this.sortName);
                page.setAsc(false);
            }
        }
        return page;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}

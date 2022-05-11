package com.c3stones.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

/**
 * 表格实体
 */
@Data
public class ExcelMode extends BaseRowModel {

    /**
     * 第1列的数据
     */
    @ExcelProperty(index = 0)
    private String column0;
    /**
     * 第2列的数据
     */
    @ExcelProperty(index = 1)
    private String column1;
    /**
     * 第3列的数据
     */
    @ExcelProperty(index = 2)
    private String column2;
    /**
     * 第4列的数据
     */
    @ExcelProperty(index = 3)
    private String column3;
    /**
     * 第5列的数据
     */
    @ExcelProperty(index = 4)
    private String column4;
    /**
     * 第6列的数据
     */
    @ExcelProperty(index = 5)
    private String column5;
    /**
     * 第7列的数据
     */
    @ExcelProperty(index = 6)
    private String column6;



}

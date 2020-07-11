package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {

    @ExcelProperty(index = 0) //表示这是第一列
    private String oneSubjectName;

    @ExcelProperty(index = 1) //表示这是第二列
    private String twoSubjectName;
}

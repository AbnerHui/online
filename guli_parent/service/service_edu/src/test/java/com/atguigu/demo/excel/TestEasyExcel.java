package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    public static void main(String[] args) {

        //实现excel写操作
        //1.设置写入文件夹地址和excel文件名称
        //String fileName = "C:\\Users\\Abner\\Desktop\\write.xlsx";
        //2.调用excel里面的方法实现写操作
       //EasyExcel.write(fileName,DemoData.class).sheet("学生列表").doWrite(getData());

        //实现读操作
        String fileName = "C:\\Users\\Abner\\Desktop\\write.xlsx";
        EasyExcel.read(fileName,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("lucy"+i);
            list.add(data);
        }
        return list;
    }
}

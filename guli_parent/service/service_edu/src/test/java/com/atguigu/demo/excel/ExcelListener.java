package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.omg.CORBA.portable.InvokeHandler;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoData> {

    //一行一行读取excel内容
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        System.out.println(demoData);
    }

    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

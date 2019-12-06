package com.lml.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

public class DemoDataListener extends AnalysisEventListener<ImageData> {
    List list=new ArrayList();
    @Override
    public void invoke(ImageData imageData, AnalysisContext analysisContext) {
        System.out.println(imageData);
        list.add(imageData);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("over");
    }
}

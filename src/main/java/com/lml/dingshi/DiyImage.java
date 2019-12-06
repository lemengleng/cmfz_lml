package com.lml.dingshi;

import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

public class DiyImage extends StringImageConverter {

    @Override
    public CellData convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        String[] str=value.split("/");
        String s=str[str.length-1];
        String url="D:\\IdeaProjects\\spring-workspace\\cmfz_lml\\src\\main\\webapp\\img\\"+s;
        return new CellData(FileUtils.readFileToByteArray(new File(url)));
    }
}

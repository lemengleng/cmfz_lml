package com.lml.entity;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by HIAPAD on 2019/12/2.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEasyExcel {
    String fileName = "C:\\Users\\李梦琳\\Desktop\\"+new Date().getTime()+"DemoData.xlsx";


    public void test02(){
        ExcelReader excelReader=EasyExcel.read("C:\\Users\\李梦琳\\Desktop\\1575270406045DemoData.xlsx",ImageData.class,new DemoDataListener()).build();
    }





    @Test
    public void test06() throws IOException {
        ImageData imageData = new ImageData();
        //imageData.setFile(new File("C:\\Users\\李梦琳\\Desktop\\百知教育\\JavaWeb\\html\\img\\1928.jpg"));
       // imageData.setByteArray(FileUtils.readFileToByteArray(new File("C:\\Users\\李梦琳\\Desktop\\百知教育\\JavaWeb\\html\\img\\1928.jpg")));
      //  imageData.setInputStream(new FileInputStream(new File("C:\\Users\\李梦琳\\Desktop\\百知教育\\JavaWeb\\html\\img\\1928.jpg")));
        imageData.setString("C:\\Users\\李梦琳\\Desktop\\百知教育\\JavaWeb\\html\\img\\1928.jpg");
        imageData.setUrl(new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575375355397&di=e79f39e71e34555cc320e73da3178f70&imgtype=0&src=http%3A%2F%2Fhbimg.b0.upaiyun.com%2F47177ddde1522bb85adccb4e01694249f5ffec19224df-DEXnIK_fw658"));
        List<ImageData> imageData1 = Arrays.asList(imageData);
        EasyExcel.write(fileName,ImageData.class).sheet().doWrite(imageData1);
    }

}

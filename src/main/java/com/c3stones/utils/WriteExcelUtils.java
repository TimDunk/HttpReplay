package com.c3stones.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelUtils {
    private static final String EXCEL_XLS = "xls";
    private static final String EXCEL_XLSX = "xlsx";

    public static void main(String[] args) {

        Map<String, String> dataMap=new HashMap<String, String>();
        dataMap.put("BankName", "BankName");
        dataMap.put("Addr", "Addr");
        dataMap.put("Phone", "Phone");
        List<Map> list=new ArrayList<Map>();
        list.add(dataMap);
        writeExcel(list, 3, "E:\\writeExcel.xls");

    }

    public static void writeExcel(List<Map> dataList, int cloumnCount,String finalXlsxPath){
        OutputStream out = null;
        try {
            // 获取总列数
            int columnNumCount = cloumnCount;
            // 读取Excel文档
            File finalXlsxFile = new File(finalXlsxPath);
            if (!finalXlsxFile.exists())
                finalXlsxFile.createNewFile();
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            /**
             * 删除原有数据，除了属性列
             */
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            System.out.println("原始数据总行数，除属性列：" + rowNumber);
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
            /**
             * 往Excel中写新数据
             */
            int maxContentSize=32766;  //xls单元格内容最多只能存32767字符
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);

                String stringHostOld = dataMap.get("stringHostOld").toString();
                String stringHostNew = dataMap.get("stringHostNew").toString();

                String stringUrlOld = dataMap.get("stringUrlOld").toString();
                String stringUrlNew = dataMap.get("stringUrlNew").toString();

                String headersOld = dataMap.get("headersOld").toString().length()>maxContentSize?dataMap.get("headersOld").toString().substring(0,maxContentSize):dataMap.get("headersOld").toString();

                String initMapParams = dataMap.get("mapParams").toString();
                String mapParams = initMapParams.length()>maxContentSize?initMapParams.substring(0,maxContentSize):initMapParams;

                String resultOld = dataMap.get("resultOld").toString();
                if (resultOld.length()>maxContentSize) resultOld =resultOld.substring(0,maxContentSize);

                String resultNew = dataMap.get("resultNew").toString();
                if (resultNew.length()>maxContentSize) resultNew =resultNew.substring(0,maxContentSize);

                String compare = dataMap.get("compare").toString();
                String number = dataMap.get("number").toString();



                for (int k = 0; k <= columnNumCount; k++) {
                    // 在一行内循环
                    Cell first = row.createCell(0);
                    first.setCellValue(stringHostOld);

                    Cell second = row.createCell(1);
                    second.setCellValue(stringHostNew);


                    Cell third = row.createCell(2);
                    third.setCellValue(stringUrlOld);

                    Cell four = row.createCell(3);
                    four.setCellValue(stringUrlNew);

                    Cell five = row.createCell(4);
                    five.setCellValue(headersOld);

                    Cell six = row.createCell(5);
                    six.setCellValue(mapParams);

                    Cell seven = row.createCell(6);
                    seven.setCellValue(resultOld);

                    Cell eight = row.createCell(7);
                    eight.setCellValue(resultNew);

                    Cell nine = row.createCell(8);
                    nine.setCellValue(compare);

                    Cell ten = row.createCell(9);
                    ten.setCellValue(number);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                if(out != null){
                    out.flush();
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据导出成功");
    }

    /**
     * 判断Excel的版本,获取Workbook
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbok(File file) throws IOException{
        Workbook wb=null;
        if(file.getName().endsWith(EXCEL_XLS)){     //Excel 2003
            wb = new HSSFWorkbook();
        }else if(file.getName().endsWith(EXCEL_XLSX)){    // Excel 2007/2010
            wb = new XSSFWorkbook();
        }else{
            wb = new HSSFWorkbook();  //读取其他格式的文件，如csv文件，输出也用xls文件
        }
        try  (OutputStream fileOut = new FileOutputStream(file)) {
            wb.createSheet("result");
            wb.write(fileOut);
        }catch (IOException e){
            e.printStackTrace();
        }
        return wb;
    }
}
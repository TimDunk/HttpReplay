package com.c3stones.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c3stones.Constants;
import com.c3stones.entity.FileExport;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class ReadExcelUtils {


    public static void main(String[] args) throws Exception {


        ReadExcelUtils obj = new ReadExcelUtils();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        //String fileName = "F:\\httprequest\\security_getOrganLogo.xls";
//        String fileName = "F:\\httprequest\\favorite_getFavorieList.xls";
//        String fileName = "F:\\httprequest\\security_getUserInfo.xls";
//        String fileName = "F:\\httprequest\\systemsetting_getQuotationsOperation.xls";
//          String fileName = "F:\\httprequest\\IsHaveCRMAdministratorRole.xls";
        String dir="D:\\InterfacesTest\\账号体系\\流量回放\\练习\\";
        String dateData="2022-04-20";

        for (FileExport fileExport: Constants.fileList) {

            String fileName = dir+dateData+"\\"+fileExport.getFileName();

            File file = new File(fileName);
            List excelList = obj.readExcel(file);
            System.out.println("list中的数据打印出来");
            List<Map> listExcelWrite=new ArrayList<Map>();

            Map<String, String> dataMapTittle =new HashMap<String, String>();
            dataMapTittle.put("stringHostOld", "stringHostOld");
            dataMapTittle.put("stringHostNew", "stringHostNew");
            dataMapTittle.put("stringUrlOld", "stringUrlOld");
            dataMapTittle.put("stringUrlNew", "stringUrlNew");
            dataMapTittle.put("headersOld", "headersOld");
            dataMapTittle.put("mapParams", "mapParams");
            dataMapTittle.put("resultOld", "resultOld(超过5000字符不打印)");
            dataMapTittle.put("resultNew", "resultNew（超过5000字符不打印）");
            dataMapTittle.put("compare", "结果对比");
            dataMapTittle.put("number", "序号");

            listExcelWrite.add(dataMapTittle);

            int size=excelList.size();
            if (size>500) {
                size=500;
            }


            System.out.println("fileName:~~~~~~~~~~~~~~~~~~~"+fileName);

//            for (int j = 0; j < 5; j++) {
            for (int j = 0; j < size; j++) {

                //获取第一条记录
                List list = (List) excelList.get(j);

                //组装参数post请求 线上环境
                HashMap<String, String> mapParams = new HashMap<>();
                String urlStr = URLDecoder.decode((String) list.get(2), "UTF-8");
                String str1 = urlStr.substring(0, urlStr.indexOf("params="));
                String str2 = urlStr.substring(str1.length() + 7, urlStr.length());
                mapParams.put("params", str2);

                String commond = (String) list.get(6);
                String commondPrams = commond.substring(2, commond.length());

                mapParams.put("command", commondPrams);

                String stringHeader = (String) list.get(3);
                Map stringToMap = JSONObject.parseObject(stringHeader);
                Map<String, String> headersOld = new HashMap<>();
                for (Object key : stringToMap.keySet()) {
                    if ("cookie".equals((String) key) || "authcode".equals((String) key)) {
                        JSONArray jsonArray= (JSONArray) stringToMap.get(key);
                        String string= (String) jsonArray.get(0);
                        headersOld.put((String) key, string);
                    }
                }
                String stringUrl = ((String) list.get(14)).replaceAll("http", "https");
                String stringHost = ((String) list.get(0)).replaceAll("http", "https");
                HttpResponse responseOld = HttpUtils.doPost(stringHost, stringUrl, "post", headersOld, null, mapParams);
                String resultOld = EntityUtils.toString(responseOld.getEntity());
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println(resultOld);
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                String  stringUrlNew =stringUrl.replaceAll("Web3D","WEB3D");
                String  stringHostNew ="https://pre-open.3vjia.com";
                HttpResponse responseNew = HttpUtils.doPost(stringHostNew, stringUrlNew, "post", headersOld, null, mapParams);
                String resultNew = EntityUtils.toString(responseNew.getEntity());

                System.out.println("判断结果:"+ resultOld.equals(resultNew));

//            if (responseOld.equals(responseNew)){
                //写入excel文件
                Map<String, Object> dataMap=new HashMap<String, Object>();
                dataMap.put("stringHostOld", stringHost);
                dataMap.put("stringHostNew", stringHostNew);
                dataMap.put("stringUrlOld", stringUrl);
                dataMap.put("stringUrlNew", stringUrlNew);
                dataMap.put("headersOld", headersOld.toString());
                dataMap.put("mapParams", mapParams.toString());
                dataMap.put("resultOld", resultOld);
                dataMap.put("resultNew", resultNew);
                dataMap.put("compare",  resultOld.equals(resultNew));
                dataMap.put("number",j+1);
                listExcelWrite.add(dataMap);
//            }
            }
//        File file1=new File("F:\\httprequest\\IsHaveCRMAdministratorRole_result.xls");
//        if (!file1.exists())
//            file1.createNewFile();
//        WriteExcelUtils.writeExcel(listExcelWrite, 6, "F:\\httprequest\\IsHaveCRMAdministratorRole_result2003.xls");
//          WriteExcelUtils.writeExcel(listExcelWrite, 6, "F:\\httprequest\\security_getOrganLogo_result2003.xls");
            WriteExcelUtils.writeExcel(listExcelWrite, 6, dir+dateData+"\\"+fileExport.getFileExportName());
        }

    }
    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                int size=sheet.getRows()>3000?3000:sheet.getRows(); //dxq
                for (int i = 0; i < size; i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        if(cellinfo.isEmpty()){
                            continue;
                        }
                        innerList.add(cellinfo);
                        //System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);

                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
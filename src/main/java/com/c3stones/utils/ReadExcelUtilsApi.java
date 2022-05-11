package com.c3stones.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.c3stones.Constants;
import com.c3stones.entity.FileExport;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadExcelUtilsApi {
    private Logger log = LoggerFactory.getLogger(ReadExcelUtilsApi.class);

    public static void main(String[] args) throws Exception {
        ReadExcelUtilsApi obj = new ReadExcelUtilsApi();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        //String fileName = "F:\\httprequest\\security_getOrganLogo.xls";
//        String fileName = "F:\\httprequest\\favorite_getFavorieList.xls";
//        String fileName = "F:\\httprequest\\security_getUserInfo.xls";
//        String fileName = "F:\\httprequest\\systemsetting_getQuotationsOperation.xls";
//          String fileName = "F:\\httprequest\\IsHaveCRMAdministratorRole.xls";

//          String fileName = "F:\\httprequest\\uc_get.xls";
//        String fileName = "F:\\httprequest\\uc_getPayStatusInfo.xls";
//        String fileName = "F:\\httprequest\\uc_getRoleListByUserId.xls";
//        String fileName = "F:\\httprequest\\uc_getShopByDeptId.xls";

        String dir="D:\\InterfacesTest\\账号体系\\流量回放\\2022-02-26结果basicsdapi\\2022-02-26\\";
        String dateData="2022-04-14";

        for (FileExport fileExport: Constants.fileApiList) {

            String fileName = dir+dateData+"\\" + fileExport.getFileName();

            File file = new File(fileName);
            List excelList = obj.readExcel(file);
            List<Map> listExcelWrite = new ArrayList<Map>();

            Map<String, String> dataMapTittle = new HashMap<String, String>();
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

            for (int j = 0; j < size; j++) {
                //获取第一条记录
                List list = (List) excelList.get(j);
                String stringJson = (String) list.get(2);
                if (!isJSON(stringJson)) {
                    stringJson = "";
                }
                String stringHeader = (String) list.get(3);
                Map<String, String> headersOld = new HashMap<>();
                try{
                    Map stringToMap = JSONObject.parseObject(stringHeader);

                    for (Object key : stringToMap.keySet()) {
                        if ("cookie".equals((String) key) || "authcode".equals((String) key)||"Cookie".equals((String) key)) {
                            JSONArray jsonArray = (JSONArray) stringToMap.get(key);
                            String string = (String) jsonArray.get(0);
                            headersOld.put((String) key, string);
                        }
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                    continue;
                }

                String stringUrl = (String) list.get(1);
                String stringHost = ((String) list.get(0)).replaceAll("http", "https");

                HttpResponse responseOld = HttpUtils.doPost(stringHost, stringUrl, "post", headersOld, null, stringJson);
                String resultOld = EntityUtils.toString(responseOld.getEntity());

                String stringUrlNew = stringUrl.replaceAll("sdapi", "sdapi-uc");
                String stringHostNew = "https://pre-open.3vjia.com";
                HttpResponse responseNew = HttpUtils.doPost(stringHostNew, stringUrlNew, "post", headersOld, null, stringJson);
                String resultNew = EntityUtils.toString(responseNew.getEntity());

                obj.log.debug("序号："+(j + 1)+" 判断结果:" + resultOld.equals(resultNew));

//            if (responseOld.equals(responseNew)){
                //写入excel文件
                Map<String, Object> dataMap = new HashMap<String, Object>();
                dataMap.put("stringHostOld", stringHost);
                dataMap.put("stringHostNew", stringHostNew);
                dataMap.put("stringUrlOld", stringUrl);
                dataMap.put("stringUrlNew", stringUrlNew);
                dataMap.put("headersOld", headersOld.toString());
                dataMap.put("mapParams", stringJson);
                dataMap.put("resultOld", resultOld);
                dataMap.put("resultNew", resultNew);
                dataMap.put("compare", resultOld.equals(resultNew));
                dataMap.put("number", j + 1);
                listExcelWrite.add(dataMap);
//            }
            }
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
                for (int i = 0; i < sheet.getRows(); i++) {
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

    public static boolean isJSON(String str) {
        boolean result = false;
        try {
            Object obj= JSON.parse(str);
            result = true;
        } catch (Exception e) {
            result=false;
        }
        return result;
    }

}
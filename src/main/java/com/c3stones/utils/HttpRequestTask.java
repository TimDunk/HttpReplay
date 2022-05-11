package com.c3stones.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequestTask {
    private static Logger log = LoggerFactory.getLogger(HttpRequestTask.class);

    public static void  main(String[] args){
        /*long startTime = System.currentTimeMillis();
        try {
            String dir="D:\\InterfacesTest\\账号体系\\流量回放\\练习\\2022-04-20";
            String resultDir=IOUtils.createOuputDir(dir);
            ArrayList<File> fileList=IOUtils.listExcelFiles(new File(dir));
            ReadExcelUtils readExcelUtils=new ReadExcelUtils();
            fileList.forEach(file->{
                List excelList=readExcelUtils.readExcel(file);
                try {
                    ArrayList<Map> postResultList=getThenPost(excelList);
                    ArrayList<Map> resultList=new ArrayList<>();
                    resultList.add(HttpRequestTask.addExcelTitleRow());
                    resultList.addAll(postResultList);

                    String resultFileName=IOUtils.createResultFileName(file);
                    WriteExcelUtils.writeExcel(resultList, 6, resultDir+"\\"+resultFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        log.warn("总耗时："+(endTime-startTime));*/
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

    public static Map<String, String> addExcelTitleRow(){
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
        return dataMapTittle;
    }

    /**
     * 设置content-type为application/x-www-form-urlencoded的请求体
     * @param requestRow 代表excel表中的一行请求记录
     * @return HashMap
     */
    public static HashMap<String, String> setWeb3DReqBody(List requestRow){
        HashMap<String, String> mapParams = new HashMap<>();

        String urlStr = null;
        try {
            urlStr = URLDecoder.decode((String) requestRow.get(2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str1 = urlStr.substring(0, urlStr.indexOf("params="));
        String str2 = urlStr.substring(str1.length() + 7);
        mapParams.put("params", str2);

        String command = (String) requestRow.get(6);
        String commondPrams = command.substring(2);
        mapParams.put("command", commondPrams);

        return mapParams;
    }

    /**
     * 设置content-type为application/json的请求体
     * @param requestRow 代表excel表中的一行请求记录
     * @return String
     */
    public static String setApiReqBody(List requestRow){
        String stringJson = (String) requestRow.get(2);
        return isJSON(stringJson)?stringJson:"";
    }

    /**
     * xls文件中存的请求读取到List中，之后用这个方法。不建议使用，因读了xls每行所有内容，占用内存也较大。
     * @param requestRow
     * @param rowIndex
     * @return
     * @throws Exception
     */
    public static  Map<String, Object> postThenCompare(List requestRow, int rowIndex) throws Exception{
        boolean isWeb3D=((String) requestRow.get(1)).toLowerCase().startsWith("/web3d.axd");

        String stringHost = ((String) requestRow.get(0)).replaceAll("http", "https");
        Map<String, String> headers =setHeaders((String) requestRow.get(3)); //设置请求头

        String stringUrl;
        HttpResponse responseOld;
        String  stringUrlNew;
        String  stringHostNew ="https://pre-open.3vjia.com";
        HttpResponse responseNew;

        HashMap<String, String> web3DBody=new HashMap<>();
        String apiBody="";
        if(isWeb3D){
            web3DBody.putAll(setWeb3DReqBody(requestRow));  //组装参数post请求
            stringUrl = ((String) requestRow.get(14)).replaceAll("http", "https");

            responseOld = HttpUtils.doPost(stringHost, stringUrl, "post", headers, null, web3DBody);
            stringUrlNew =stringUrl.replaceAll("Web3D","WEB3D");
            responseNew = HttpUtils.doPost(stringHostNew, stringUrlNew, "post", headers, null, web3DBody);
        }else{
            apiBody=setApiReqBody(requestRow);
            stringUrl = (String) requestRow.get(1);

            responseOld = HttpUtils.doPost(stringHost, stringUrl, "post", headers, null, apiBody);
            stringUrlNew = stringUrl.replaceAll("sdapi", "sdapi-uc");
            responseNew = HttpUtils.doPost(stringHostNew, stringUrlNew, "post", headers, null, apiBody);
        }
        String resultOld = EntityUtils.toString(responseOld.getEntity());
        String resultNew = EntityUtils.toString(responseNew.getEntity());


        Map<String, Object> dataMap=new HashMap<String, Object>();
        dataMap.put("stringHostOld", stringHost);
        dataMap.put("stringHostNew", stringHostNew);
        dataMap.put("stringUrlOld", stringUrl);
        dataMap.put("stringUrlNew", stringUrlNew);
        dataMap.put("headersOld", headers.toString());
        String reqBody=isWeb3D?web3DBody.toString():apiBody;
        dataMap.put("mapParams", reqBody);
        dataMap.put("resultOld", resultOld);
        dataMap.put("resultNew", resultNew);
//        dataMap.put("compare",  resultOld.equals(resultNew));
        dataMap.put("compare",  ResponseCompareUtils.compare(resultOld,resultNew,isWeb3D));
        dataMap.put("number",Thread.currentThread().getName()+",rowNum:"+(rowIndex+1));

        return dataMap;
    }


    public static Map<String, String> setHeaders(String stringHeader){
        Map<String, String> headersOld = new HashMap<>();
        try{
            Map stringToMap = JSONObject.parseObject(stringHeader);
            for (Object key : stringToMap.keySet()) {
                String cookieName=((String) key).toLowerCase();
                if ("content-type".equals(cookieName)||"accept".equals(cookieName)||"authcode".equals(cookieName)) {
                    JSONArray jsonArray = (JSONArray) stringToMap.get(key);
                    String value = (String) jsonArray.get(0);
                    headersOld.put((String) key, value);
                }
                if ("cookie".equals(cookieName)) {
                    JSONArray jsonArray = (JSONArray) stringToMap.get(key);
                    String value = (String) jsonArray.get(0);
                    String[] cookieArr=value.split(";");
                    for(String c:cookieArr){
                        if(c.trim().startsWith("Swj.Share=")){
                            headersOld.put((String) key, c.trim());
                        }
                    }
                }
            }
            headersOld.put("Cache-Control", "no-cache");

        }
        catch (Exception e){
            System.err.println(stringHeader);
            e.printStackTrace();
        }finally {
            return headersOld;
        }
    }


    public static HashMap<String, String> setWeb3DReqBodyNew(List requestRow){
        HashMap<String, String> mapParams = new HashMap<>();

        String urlStr="";
        try {
            urlStr = URLDecoder.decode((String) requestRow.get(1), "UTF-8");
            String[] paramsStrs=urlStr.split("&");
            for (String str:paramsStrs) {
                int indexOfEqaToken=str.indexOf("=");
                if(indexOfEqaToken!=-1 && indexOfEqaToken<urlStr.length()-1){
                    mapParams.put(str.substring(0,indexOfEqaToken),str.substring(indexOfEqaToken+1));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        return mapParams;
    }

    public static String setApiReqBodyNew(List requestRow){
        String stringJson = (String) requestRow.get(1);
        return isJSON(stringJson)?stringJson:"";
    }
    /**
     * CSV文件中存的请求读取到List中，之后用这个方法
     * @param requestRow
     * @param rowIndex
     * @return
     * @throws Exception
     */
    public static  Map<String, Object> postThenCompareNew(List requestRow, int rowIndex) throws Exception{
        boolean isWeb3D=((String) requestRow.get(3)).toLowerCase().startsWith("/web3d.axd");

        String stringHost = ((String) requestRow.get(0)).replaceAll("http://", "https://");
        Map<String, String> headers =setHeaders((String) requestRow.get(2)); //设置请求头,如果是来自cvs的，传2；来自xls的，传3

        HttpResponse responseOld;
        String  stringUrlNew;
        String  stringHostNew ="https://pre-open.3vjia.com";
        HttpResponse responseNew;

        String stringUrl=(String) requestRow.get(3);
        HashMap<String, String> web3DBody=new HashMap<>();
        String apiBody="";

        stringHost=stringHostNew;
        if(isWeb3D){
            web3DBody.putAll(setWeb3DReqBodyNew(requestRow));  //组装参数post请求
            responseOld = HttpUtils.doPost(stringHost, stringUrl, "post", headers, null, web3DBody);
            stringUrlNew =stringUrl.replaceAll("Web3D","WEB3D");
            responseNew = HttpUtils.doPost(stringHostNew, stringUrlNew, "post", headers, null, web3DBody);
        }else{
            apiBody=setApiReqBodyNew(requestRow);
            responseOld = HttpUtils.doPost(stringHost, stringUrl, "post", headers, null, apiBody);
            stringUrlNew = stringUrl.replaceAll("sdapi", "sdapi-uc");
            responseNew = HttpUtils.doPost(stringHostNew, stringUrlNew, "post", headers, null, apiBody);
        }
        String resultOld = EntityUtils.toString(responseOld.getEntity());
        String resultNew = EntityUtils.toString(responseNew.getEntity());


        Map<String, Object> dataMap=new HashMap<String, Object>();
        dataMap.put("stringHostOld", stringHost);
        dataMap.put("stringHostNew", stringHostNew);
        dataMap.put("stringUrlOld", stringUrl);
        dataMap.put("stringUrlNew", stringUrlNew);
        dataMap.put("headersOld", headers.toString());
        String reqBody=isWeb3D?web3DBody.toString():apiBody;
        dataMap.put("mapParams", reqBody);
        dataMap.put("resultOld", resultOld);
        dataMap.put("resultNew", resultNew);
        dataMap.put("compare",  ResponseCompareUtils.compare(resultOld,resultNew,isWeb3D));
        dataMap.put("number",Thread.currentThread().getName()+",rowNum:"+(rowIndex+1));

        return dataMap;
    }

    public static ArrayList<Map> getThenPost(List requestList) {
            ArrayList<Map> listExcelWrite=new ArrayList<>();
            int requestAmount=requestList.size()>10000?10000:requestList.size();
            try {
                for (int j = 0; j < requestAmount; j++) {
                    List list = (List) requestList.get(j);    //获取请求记录
//                    Map<String, Object> dataMap = postThenCompare(list, j);
                    Map<String, Object> dataMap = postThenCompareNew(list, j);
                    listExcelWrite.add(dataMap);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                return listExcelWrite;
            }
    }
}

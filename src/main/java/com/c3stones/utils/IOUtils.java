package com.c3stones.utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.c3stones.utils.WriteExcelUtils.getWorkbok;

public class IOUtils {
    public static void main(String[] args){
        String FileName="D:\\InterfacesTest\\账号体系\\流量回放\\2022-04-22-Category\\"+"_api_sdapi_category_getCategoryTree.csv";
        String FileName2="D:\\InterfacesTest\\账号体系\\流量回放\\2022-04-22-Category\\"+"W3D#Category_getEnterpriseCategory.csv";

//        readReqFromCSV(new File(FileName2),5);
       /* try {
//            ArrayList<File> fs=listExcelFiles(new File("D:\\InterfacesTest\\账号体系\\流量回放\\抓取的流量\\20220420_10w\\result_0423202459"));
//            System.out.println(fs.size());
            ArrayList<File> fs2=listCSV(new File("D:\\InterfacesTest\\账号体系\\流量回放\\抓取的流量\\20220420_10w"));
            System.out.println(fs2.size());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        absentApis("D:\\InterfacesTest\\账号体系\\流量回放\\sdapi3月份接口流量.xls","D:\\InterfacesTest\\账号体系\\流量回放\\抓取的流量\\20220420_10w");

    }

    private static void absentApis(String apiListFile,String OfferedApiDir){
        List list=(new ReadExcelUtils()).readExcel(new File(apiListFile));
        ArrayList<List> source= new ArrayList<>(list);
        Set<String> set=new HashSet<>();
        source.forEach(list1 -> {
            String url=(String)list1.get(0);
            String[] urlParts=url.toLowerCase().trim().split("/");
            int size=urlParts.length;
            if(size>=2){
                String api=urlParts[size-2]+"/"+urlParts[size-1];
                set.add(api);
            }
        });

        ArrayList<File> csvs;
        Set<String> set2=new HashSet<>();
        try {
            csvs=listCSV(new File(OfferedApiDir));
            csvs.forEach(csv->{
                ArrayList<ArrayList> csvContent=readReqFromCSV(csv,1);
                if(csvContent.size()>0){
                    String url=(String)csvContent.get(0).get(3);
                    url=url.toLowerCase();
                    if(url.startsWith("/api")){
                        String[] urlParts=url.trim().split("/");
                        int size=urlParts.length;
                        if(size>=2){
                            String api=urlParts[size-2]+"/"+urlParts[size-1];
                            set2.add(api);
                        }
                    }else{
                        String[] urlParts=url.trim().split("/?m=");
                        int size=urlParts.length;
                        if(size>=2){
                            String api=urlParts[size-1];
                            set2.add(api);
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        set2.add("building/getscalepoint");
        set2.add("log/writelogfor3d");
        set2.add("imagerecongnition/getsimilarimages");
        set2.add("imagerecongnition/detectimgobj");

        System.err.println("缺少的流量的接口有：");
        int counter=0;
        Iterator<String> iterator=set.iterator();
        while (iterator.hasNext()){
            String s=iterator.next();
            if(!set2.contains(s)){
                counter++;
                System.out.println(s);
                System.out.println(counter);
            }
        }
        System.out.println("计划要抓流量的接口数量："+set.size());
        System.out.println("提供的接口数量："+ set2.size());
        System.out.println("缺少的接口数量："+counter);

//        System.out.println("提供的接口有：");
//        Iterator<String> iterator2=set2.iterator();
//        while (iterator2.hasNext()){
//            String s=iterator2.next();
//            System.out.println(s);
//        }
    }

    public static ArrayList<ArrayList> readReqFromCSV(File file, int maxReadLines){
        return readReqFromCSV(file,0,maxReadLines);
    }

    public static ArrayList<ArrayList> readReqFromCSV(File file, int startIndex,int maxReadLines){
        ArrayList<ArrayList> resultList=new ArrayList<>();
        try {
            CsvReader csvReader = new CsvReader(new FileInputStream(file), ',', Charset.forName("UTF-8"));
            csvReader.setEscapeMode(CsvReader.ESCAPE_MODE_DOUBLED);
            int counter = 0;
            // 读取每行的内容
            while (counter < maxReadLines+startIndex && csvReader.readRecord()) {
                // 1. 通过下标获取
                if(counter>=startIndex){
                    List<String> list;
                    list = Arrays.asList(csvReader.get(1), csvReader.get(3),csvReader.get(4),csvReader.get(15));
                    resultList.add(new ArrayList(list));
                }
                counter++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
            System.err.println(file.getAbsolutePath());
        }
        return resultList;
    }

    public static boolean writeResultInCSV(List<Map> dataList,String finalXlsxPath){
        File finalXlsxFile = new File(finalXlsxPath);
        try {
            if (!finalXlsxFile.exists())
                finalXlsxFile.createNewFile();

            FileOutputStream outputStream=new FileOutputStream(finalXlsxFile,false);
            CsvWriter csvWriter=new CsvWriter(outputStream,',',Charset.forName("utf-8"));
            csvWriter.setEscapeMode(CsvReader.ESCAPE_MODE_DOUBLED);

            for (int j = 0; j < dataList.size(); j++) {
                // 得到要插入的每一条记录
                Map dataMap = dataList.get(j);

                String stringHostOld = dataMap.get("stringHostOld").toString();
                String stringHostNew = dataMap.get("stringHostNew").toString();

                String stringUrlOld = dataMap.get("stringUrlOld").toString();
                String stringUrlNew = dataMap.get("stringUrlNew").toString();

                String headersOld = dataMap.get("headersOld").toString().length()>30000?dataMap.get("headersOld").toString().substring(0,30000):dataMap.get("headersOld").toString();

                String initMapParams = dataMap.get("mapParams").toString();
//                String mapParams = initMapParams.length()>5000?initMapParams.substring(0,5000)+"...长度超过5000不显示":initMapParams;

                String resultOld = dataMap.get("resultOld").toString();
                if (resultOld.length()>5000) resultOld =resultOld.substring(0,5000);

                String resultNew = dataMap.get("resultNew").toString();
                if (resultNew.length()>5000) resultNew =resultNew.substring(0,5000);

                String compare = dataMap.get("compare").toString();
                String number = dataMap.get("number").toString();

                String[] rowContent={stringHostOld,stringHostNew,stringUrlOld,stringUrlNew,headersOld,initMapParams,resultOld,resultNew,compare,number};
                csvWriter.writeRecord(rowContent,true);
            }
            csvWriter.flush();
            csvWriter.close();
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean writeSummaryResult( ArrayList<HashMap<String,Long>> dataList,String finalXlsxPath){
        OutputStream out = null;
        try {
            File finalXlsxFile = new File(finalXlsxPath);
            if (!finalXlsxFile.exists())
                finalXlsxFile.createNewFile();
            Workbook workBook = getWorkbok(finalXlsxFile);
            // sheet 对应一个工作页
            Sheet sheet = workBook.getSheetAt(0);
            //删除原有数据，除了属性列
            int rowNumber = sheet.getLastRowNum();    // 第一行从0开始算
            for (int i = 1; i <= rowNumber; i++) {
                Row row = sheet.getRow(i);
                sheet.removeRow(row);
            }
            // 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);

            Row titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("接口");
            titleRow.createCell(1).setCellValue("失败的用例数量");

            //往Excel中写新数据
            int maxContentSize=32766;  //xls单元格内容最多只能存32767字符
            for (int j = 0; j < dataList.size(); j++) {
                // 创建一行：从第二行开始，跳过属性列
                Row row = sheet.createRow(j + 1);
                // 得到要插入的每一条记录
                HashMap<String,Long> dataMap = dataList.get(j);

                for (Map.Entry<String, Long> entry : dataMap.entrySet()) {
                    String tmpUrl=entry.getKey();
                    String url=tmpUrl.length()>maxContentSize?tmpUrl.substring(0,maxContentSize):tmpUrl;
                    String failCount=Long.toString(entry.getValue().longValue());
                    Cell first = row.createCell(0);
                    first.setCellValue(url);
                    Cell second = row.createCell(1);
                    second.setCellValue(failCount);
                }
            }
            // 创建文件输出流，准备输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
            out =  new FileOutputStream(finalXlsxPath);
            workBook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
        System.out.println("测试结果概览已写入文件");
        return true;
    }

    public static ArrayList<File> listCSV(File dir) throws IOException {
        if(!dir.isDirectory()){
            throw new IOException("这不是一个有效的目录");
        }
        if(!dir.exists()){
            throw new FileNotFoundException("目录不存在");
        }
        File[] files=dir.listFiles(f->f.getAbsolutePath().endsWith(".csv") && f.isFile() &&  f.canRead());
        List<File> list=Arrays.asList(files);
        ArrayList<File> arrayList=new ArrayList<>(list);

        File[] directories=dir.listFiles(f->f.isDirectory() && f.canRead());
        for(File d:directories){
            arrayList.addAll(listCSV(d));
        }

        return arrayList;
    }

    public static String  createOuputDir(String dir){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddHHmmss");
        String outputDir=dir+File.separator+"result_"+dtf.format(LocalDateTime.now());
        File directory = new File(outputDir);

        if(directory.exists())
            return outputDir;

        boolean hasSucceeded = directory.mkdir();
        System.out.println("创建文件夹结果（不含父文件夹）：" + hasSucceeded);

        File passDir=new File(outputDir+File.separator+"测试通过");
        File checkedDir=new File(outputDir+File.separator+"需要人工检查");

        if(!passDir.exists()){
            boolean p=passDir.mkdir();
            if(!p)
                System.out.println("测试通过  目录创建失败");
        }

        if(!checkedDir.exists()){
            boolean c=checkedDir.mkdir();
            if(!c)
                System.out.println("需要人工检查  目录创建失败");
        }

        return outputDir;
    }

    public static String createResultFileName(File file){
        return createResultFileName(file,".xls");
    }

    public static String createResultFileName(File file,String extension){
        String fileName=file.getName();
        int dotIndex=fileName.lastIndexOf(".");
        String fileNameSuffix="_result";
        String fileExtention=extension;
        String resultFileName=dotIndex!=-1?fileName.substring(0,dotIndex)+fileNameSuffix+fileExtention:fileName+fileExtention;
        return resultFileName;
    }

    public static ArrayList<File> listXlsFiles(File dir){
        File[] files=dir.listFiles(f->f.getAbsolutePath().endsWith(".xls"));
        List<File> list=Arrays.asList(files);
        return new ArrayList<>(list);
    }

    public static ArrayList<File> listXlxsFiles(File dir){
        File[] files=dir.listFiles(f->f.getAbsolutePath().endsWith(".xlxs"));
        List<File> list=Arrays.asList(files);
        return new ArrayList<>(list);
    }

    public static ArrayList<File> listExcelFiles(File dir) throws IOException{
        if(!dir.isDirectory()){
            throw new IOException("这不是一个有效的目录");
        }
        if(!dir.exists()){
            throw new FileNotFoundException("目录不存在");
        }
        ArrayList<File> files=new ArrayList<>();
        files.addAll(listXlsFiles(dir));
        files.addAll(listXlxsFiles(dir));
        return files;
    }

}

package com.c3stones.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinForHttpTask {
    private static Logger log = LoggerFactory.getLogger(ForkJoinForHttpTask.class);

    public static void main(String[] args) throws Exception {
        String dir="D:\\InterfacesTest\\账号体系\\流量回放\\抓取的流量\\20220420_10w\\未分类\\Product_getProductPriceList";
//        String dir="D:\\InterfacesTest\\账号体系\\流量回放\\抓取的流量\\20220420_10w\\A-json比对不相等-需要重新回放";
        long startTime = System.currentTimeMillis();
        ForkJoinForHttpTask.runTaskInCommonPool(dir);
        long endTime = System.currentTimeMillis();
        log.info("总耗时："+(endTime-startTime)/1000+" 秒");
    }

    static private void runTaskInCommonPool(String dir){
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","8");

        try {
            String resultDir= IOUtils.createOuputDir(dir);
            /*ArrayList<File> fileList= IOUtils.listExcelFiles(new File(dir));
            ReadExcelUtils readExcelUtils=new ReadExcelUtils();*/

            ArrayList<File> fileList= IOUtils.listCSV(new File(dir));
            ArrayList<HashMap<String,Long>> summaryResultList=new ArrayList<>();

            fileList.forEach(file->{
                System.out.println("开始 "+file.getName()+" 中的接口请求");
//                List requestList=readExcelUtils.readExcel(file);
                List requestList=IOUtils.readReqFromCSV(file,0,8000);
                try {
                    ArrayList<Map> initList=new ArrayList<>(requestList);
                    ForkJoinTask<ArrayList<Map>> task = new PostThenCompareResultTask(initList, 0, initList.size());
                    ArrayList<Map> postResultList = ForkJoinPool.commonPool().invoke(task);

                    HashMap<String,Long> apiFailMap=ResponseCompareUtils.getFailedCountMap(postResultList);
                    long failCount=0;
                    for (Map.Entry<String, Long> entry : apiFailMap.entrySet()) {
                        failCount=entry.getValue();
                    }

                    String ouputDir=resultDir;
                    if(failCount>0)
                        ouputDir=resultDir+File.separator+"需要人工检查";
                    else
                        ouputDir=resultDir+File.separator+"测试通过";

                    summaryResultList.add(apiFailMap);

                    ArrayList<Map> resultList=new ArrayList<>();
                    resultList.add(HttpRequestTask.addExcelTitleRow());
                    resultList.addAll(postResultList);

                    String resultFileName= IOUtils.createResultFileName(file,".xls");
                    WriteExcelUtils.writeExcel(resultList, 1, ouputDir+File.separator+resultFileName);
//                    IOUtils.writeResultInCSV(resultList, resultDir+"\\"+resultFileName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            IOUtils.writeSummaryResult(summaryResultList,resultDir+File.separator+"TestingResultSummary.xls");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static private void runTaskInPools(String dir){
        ArrayList<ForkJoinTask<ArrayList<Map>>> resultTaskList = new ArrayList<>();

        try {
            String resultDir= IOUtils.createOuputDir(dir);
            ArrayList<File> fileList= IOUtils.listExcelFiles(new File(dir));
            ReadExcelUtils readExcelUtils=new ReadExcelUtils();

            Map<ForkJoinTask<ArrayList<Map>>,File> taskToFileNameMap=new HashMap<>();
            fileList.forEach(file->{
                System.out.println("开始 "+file.getName()+" 中的接口请求");
                ForkJoinPool pool=new ForkJoinPool(8);
                List excelList=readExcelUtils.readExcel(file);
                try {
                    ArrayList<Map> initList=new ArrayList<>(excelList);
                    ForkJoinTask<ArrayList<Map>> task = new PostThenCompareResultTask(initList, 0, initList.size());
                    ForkJoinTask<ArrayList<Map>> submit_task= pool.submit(task);
                    resultTaskList.add(submit_task);
                    taskToFileNameMap.put(submit_task,file);
                    pool.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            while (!resultTaskList.isEmpty()){
                for(int i=0;i<resultTaskList.size();i++){
                    ForkJoinTask<ArrayList<Map>> future=resultTaskList.get(i);
                    if (future.isDone()){
                        try {
                            ArrayList<Map> postResultList = future.get();
                            ArrayList<Map> resultList=new ArrayList<>();
                            resultList.add(HttpRequestTask.addExcelTitleRow());
                            resultList.addAll(postResultList);

                            String resultFileName= IOUtils.createResultFileName(taskToFileNameMap.get(future));
                            WriteExcelUtils.writeExcel(resultList, 6, resultDir+"\\"+resultFileName);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        resultTaskList.remove(i);
                        break;
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class PostThenCompareResultTask extends RecursiveTask<ArrayList<Map>> {

    static final int THRESHOLD = 30;

    ArrayList<Map> initList;
    int start;
    int end;

    PostThenCompareResultTask(ArrayList<Map> list, int start, int end) {
        this.initList = list;
        this.start = start;
        this.end = end;
    }


    private ArrayList<Map> doPostThenCompare(int start, int end){
        ArrayList<Map> arrayList=new ArrayList<>();
        for (int i = start; i < end; i++) {
            arrayList.add(initList.get(i));
        }
        try {
            ArrayList<Map>  resultList=HttpRequestTask.getThenPost(arrayList);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, Object> dataMap=new HashMap<String, Object>();
        dataMap.put("stringHostOld", "");
        dataMap.put("stringHostNew", "");
        dataMap.put("stringUrlOld", "");
        dataMap.put("stringUrlNew", "");
        dataMap.put("headersOld", "");
        dataMap.put("mapParams", "");
        dataMap.put("resultOld", "");
        dataMap.put("resultNew", "");
        dataMap.put("compare", "PostThenCompareResultTask.doPostThenCompare方法捕捉到异常");
        dataMap.put("number","");
        ArrayList<Map> result=new ArrayList<>();
        result.add(dataMap);
        return result;

    }

    @Override
    protected ArrayList<Map> compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务足够小,直接计算:
            return doPostThenCompare(start,end);
        }
        // 任务太大,一分为二:
        int middle = (end + start) / 2;
        PostThenCompareResultTask subtask1 = new PostThenCompareResultTask(this.initList, start, middle);
        PostThenCompareResultTask subtask2 = new PostThenCompareResultTask(this.initList, middle, end);
        PostThenCompareResultTask[] tasks={subtask1, subtask2};
        invokeAll(tasks);

        boolean statusSubtask1=subtask1.isDone();
        boolean statusSubtask2=subtask2.isDone();
        ArrayList<Map> arrayList1 = subtask1.join();
        ArrayList<Map> arrayList2 = subtask2.join();
        arrayList1.addAll(arrayList2);
        return arrayList1;
    }
}

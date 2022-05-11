package com.c3stones.controller;

import com.alibaba.excel.EasyExcel;
import com.c3stones.entity.ExcelMode;
import com.c3stones.entity.Student;
import com.c3stones.listener.FileHttpListener;
import com.c3stones.listener.StudentListener;
import com.c3stones.utils.POIUtils;
import com.c3stones.utils.ReadExcelUtils;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 读取接口文件的Controller
 *
 *
 */
@RestController
@RequestMapping(value = "fileHttp")
public class FileHttpController {

	/**
	 * 读取Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "readExcel")
	public List<ExcelMode> readExcel() {
		System.out.println("32dsd的");
		String fileName = "F:\\httprequest\\IsHaveCRMAdministratorRole.xls";
		FileHttpListener fileHttpListener = new FileHttpListener();
		EasyExcel.read(fileName, ExcelMode.class, fileHttpListener).sheet("IsHaveCRMAdministratorRole").doRead();
		return fileHttpListener.getExcelModeList();
	}

	/**
	 * 读取Excel
	 *
	 * @return
	 */
	@RequestMapping(value = "readExcel2")
	public List<ExcelMode> readExcel2() throws IOException {
		System.out.println("32dsd的");
		String fileName = "F:\\httprequest\\IsHaveCRMAdministratorRole2.xls";

		File pdfFile = new File(fileName);

		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
				ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);

		try {
			List<String> list = POIUtils.readExcel(multipartFile);
//      list.removeIf(Objects::isNull);去掉null值
			//去掉空字符串
			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()){
				if (iterator.next() == ""){
					iterator.remove();
				}
			}
			//遍历list,查看数据
			for (String s : list) {
				System.out.println(s);
			}
			//创建map对象或者pojo类存入所需的数据，
			Map<String,Object> map = new HashMap<>();
			map.put("plan",list.get(0));
			map.put("er",list.get(2));
			map.put("date",list.get(4));
			System.out.println(map);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取Excel
	 *
	 * @return
	 */
	@RequestMapping(value = "httpTest")
	public String httpTest() {

		ReadExcelUtils obj = new ReadExcelUtils();
		// 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
		String fileName = "F:\\httprequest\\IsHaveCRMAdministratorRole222.xls";
		File file = new File(fileName);
		List excelList = obj.readExcel(file);

		for (int i = 0; i < excelList.size(); i++) {
			List list = (List) excelList.get(i);
			for (int j = 0; j < list.size(); j++) {
				System.out.print(list.get(j));
			}
			System.out.println();
		}



		return null;

	}




}

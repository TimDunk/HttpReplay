package com.c3stones.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.c3stones.entity.ExcelMode;
import com.c3stones.entity.Student;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生读取类
 * 
 * @author CL
 *
 */
public class FileHttpListener extends AnalysisEventListener<ExcelMode> {

	@Getter
	private List<ExcelMode> excelModeList = new ArrayList<ExcelMode>();

	public FileHttpListener() {
		super();
		excelModeList.clear();
	}

	/**
	 * 每一条数据解析都会调用
	 */
	@Override
	public void invoke(ExcelMode excelMode, AnalysisContext context) {
		excelModeList.add(excelMode);
	}

	/**
	 * 所有数据解析完成都会调用
	 */
	@Override
	public void doAfterAllAnalysed(AnalysisContext context) {
		excelModeList.forEach(System.out::println);
	}

}

package com.c3stones.entity;

import lombok.Data;

@Data
public class FileExport {

    private  String fileName;
    private  String fileExportName;

    public FileExport(String fileName, String fileExportName){
        this.fileName=fileName;
        this.fileExportName=fileExportName;
    }



}

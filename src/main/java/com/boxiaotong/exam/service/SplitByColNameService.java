package com.boxiaotong.exam.service;

import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface SplitByColNameService {
    void splitByColName(NoModelDataListener noModelDataListener, String colName, HttpServletResponse response) throws IOException;
}

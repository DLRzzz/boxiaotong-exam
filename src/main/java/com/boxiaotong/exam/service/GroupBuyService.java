package com.boxiaotong.exam.service;


import com.boxiaotong.exam.utils.GroupBuyDataListener;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public interface GroupBuyService {
    void splitExcel(GroupBuyDataListener groupBuyDataListener, String colName, HttpServletResponse response) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException;
}

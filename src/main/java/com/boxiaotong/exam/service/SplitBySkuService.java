package com.boxiaotong.exam.service;

import com.boxiaotong.exam.utils.NoModelDataListener;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface SplitBySkuService {
    void splitBySku(NoModelDataListener noModelDataListener,  HttpServletResponse response) throws IOException;
}

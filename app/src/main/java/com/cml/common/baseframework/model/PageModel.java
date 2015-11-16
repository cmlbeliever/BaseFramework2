package com.cml.common.baseframework.model;

import com.cml.data.dumydata.SimpleDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cmlBeliever on 2015/11/16.
 * 分页model
 */
public class PageModel {
    public int currentPage;
    public final int pageSize;
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    public PageModel(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public void nextPage() {
        currentPage++;
        this.getDummyData();
    }

    public void reset(int currentPage) {
        this.currentPage = currentPage;
        data.clear();
    }

    public void reset() {
        data.clear();
        this.currentPage = 0;
    }

    /**
     * 根据当前页数获取模拟数据
     *
     * @return
     */
    public List<Map<String, Object>> getDummyData() {
        int from = (currentPage - 1) * pageSize;
        data.addAll(SimpleDataService.simpleData(from, pageSize));
        return data;
    }
}

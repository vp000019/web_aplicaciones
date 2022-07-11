package ua.com.alevel.persistence.datatable;

import java.util.HashMap;
import java.util.Map;

public class DataTableRequest {

    private int size;
    private String order;
    private String sort;
    private Map<String, String[]> requestParamMap;

    public DataTableRequest() {
        this.requestParamMap = new HashMap<>();
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Map<String, String[]> getRequestParamMap() {
        return requestParamMap;
    }
}

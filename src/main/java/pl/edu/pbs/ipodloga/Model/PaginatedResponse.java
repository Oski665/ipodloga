package pl.edu.pbs.ipodloga.Model;

import java.util.List;

public class PaginatedResponse<T> {

    private List<T> data;
    private int currentPage;
    private int totalPage;

    public PaginatedResponse(List<T> data, int currentPage, int totalPage) {
        this.data = data;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
    }
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}

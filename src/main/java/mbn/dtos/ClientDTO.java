package mbn.dtos;

import lombok.Data;
import lombok.Value;


public class ClientDTO {
//    private  Long codPatient;
//    private  Integer currentPageNumber = 0;
//    private  Integer pageSize = 20;
//    private  String sortColumnName = "lastName";
//    private  String sortDirection = "desc";
    private  String filterText;
//
//    public Long getCodPatient() {
//        return codPatient;
//    }
//
//    public void setCodPatient(Long codPatient) {
//        this.codPatient = codPatient;
//    }
//
//    public Integer getCurrentPageNumber() {
//        return currentPageNumber;
//    }
//
//    public void setCurrentPageNumber(Integer currentPageNumber) {
//        this.currentPageNumber = currentPageNumber;
//    }
//
//    public Integer getPageSize() {
//        return pageSize;
//    }
//
//    public void setPageSize(Integer pageSize) {
//        this.pageSize = pageSize;
//    }
//
//    public String getSortColumnName() {
//        return sortColumnName;
//    }
//
//    public void setSortColumnName(String sortColumnName) {
//        this.sortColumnName = sortColumnName;
//    }
//
//    public String getSortDirection() {
//        return sortDirection;
//    }
//
//    public void setSortDirection(String sortDirection) {
//        this.sortDirection = sortDirection;
//    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }
}

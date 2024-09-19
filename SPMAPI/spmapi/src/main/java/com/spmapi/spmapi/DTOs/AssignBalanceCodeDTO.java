package com.spmapi.spmapi.DTOs;


public class AssignBalanceCodeDTO {
    private Long userId;
    private Long balanceCodeId;

    //----------------------------------------------------------------
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    //----------------------------------------------------------------
    public Long getBalanceCodeId() {
        return balanceCodeId;
    }
    public void setBalanceCodeId(Long balanceCodeId) {
        this.balanceCodeId = balanceCodeId;
    }
    //----------------------------------------------------------------
}

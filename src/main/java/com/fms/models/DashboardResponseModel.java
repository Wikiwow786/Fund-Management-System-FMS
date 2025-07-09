package com.fms.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public class DashboardResponseModel {

    private List<BankDashBoardModel> bankModels;
    private List<CustomerDashboardModel> customerModels;

    public DashboardResponseModel(List<BankDashBoardModel> bankModels, List<CustomerDashboardModel> customerModels) {
        this.bankModels = bankModels;
        this.customerModels = customerModels;
    }
}

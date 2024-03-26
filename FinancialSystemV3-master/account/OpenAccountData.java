package account;

import customer.CustomerApplicationData;

public record OpenAccountData(Integer numeroDaConta, CustomerApplicationData customerData) {
    }
package br.com.kaikeventura.simpllink.dto;

import lombok.Getter;

public enum Days {
    unlimited("Unlimited", 0),
    oneDay("One day", 1),
    oneWeek("One week", 7),
    oneMonth("One month", 30),
    oneYear("One year", 365);

    @Getter
    private String amount;
    @Getter
    private Integer amountDays;

    Days(String amount, Integer amountDays) {
        this.amount = amount;
        this.amountDays = amountDays;
    }
}
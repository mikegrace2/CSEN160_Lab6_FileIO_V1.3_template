package com.csen160.datamanager.helper;

public class Sales {
    int quarter;
    int year;
    int salesInK;

    public Sales(int quarter, int year, int salesInK) {
        //  implement me
    }

    public int getAmount() {
        return salesInK;
    }

    public int getYear() {
        return year;
    }

    public int getQuarter() {
        return quarter;
    }

    public String toString() {
        return "Year: " + year + " Quarter: " + quarter + " Sales: " + salesInK;
    }
}

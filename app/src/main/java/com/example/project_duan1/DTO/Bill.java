package com.example.project_duan1.DTO;

import com.example.project_duan1.DTO.GioHang;

import java.util.Date;
import java.util.List;

public class Bill {
    private String id_bill;

    private String fullname;
    private String address;
    private double subtotal;
    private double delivery;
    private double totalTax;
    private double total;
    private List<GioHang> gioHangList;
    private boolean isCOD;
    private boolean isATM;// Checkbox state
    private String formatdate;
    private boolean isConfirmed; // Trạng thái đã xác nhận

    public Bill() {
        // Empty constructor needed for Firebase
    }

    public Bill(String id_bill, String fullname, String address, double subtotal, double delivery, double totalTax, double total, List<GioHang> gioHangList, boolean isCOD, boolean isATM, String formatdate, boolean isConfirmed) {
        this.id_bill = id_bill;
        this.fullname = fullname;
        this.address = address;
        this.subtotal = subtotal;
        this.delivery = delivery;
        this.totalTax = totalTax;
        this.total = total;
        this.gioHangList = gioHangList;
        this.isCOD = isCOD;
        this.isATM = isATM;
        this.formatdate = formatdate;
        this.isConfirmed = isConfirmed;
    }

    public String getId_bill() {
        return id_bill;
    }

    public void setId_bill(String id_bill) {
        this.id_bill = id_bill;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<GioHang> getGioHangList() {
        return gioHangList;
    }

    public void setGioHangList(List<GioHang> gioHangList) {
        this.gioHangList = gioHangList;
    }

    public boolean isCOD() {
        return isCOD;
    }

    public void setCOD(boolean COD) {
        isCOD = COD;
    }

    public boolean isATM() {
        return isATM;
    }

    public void setATM(boolean ATM) {
        isATM = ATM;
    }

    public String getFormatdate() {
        return formatdate;
    }

    public void setFormatdate(String formatdate) {
        this.formatdate = formatdate;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
// Getters and setters
    // ...
}
package com.zhangtq.pojo;

public class Product {
    private Integer id;
    private String productName;

    public Integer getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                '}';
    }
}

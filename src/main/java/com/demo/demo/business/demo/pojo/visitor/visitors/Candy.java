package com.demo.demo.business.demo.pojo.visitor.visitors;

import com.demo.demo.business.demo.pojo.visitor.BaseProduct;
import com.demo.demo.business.demo.visitor.product.ProductAcceptable;
import com.demo.demo.business.demo.visitor.product.ProductVisitor;

public class Candy extends BaseProduct implements ProductAcceptable {
    public Candy(String name, Integer price) {
        super(name, price);
    }

    @Override
    public void accept(ProductVisitor visitor) {
        visitor.visit(this);
    }
}

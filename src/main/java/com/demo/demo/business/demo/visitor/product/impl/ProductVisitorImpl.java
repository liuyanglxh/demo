package com.demo.demo.business.demo.visitor.product.impl;

import com.demo.demo.business.demo.pojo.visitor.visitors.Candy;
import com.demo.demo.business.demo.pojo.visitor.visitors.Fruit;
import com.demo.demo.business.demo.pojo.visitor.visitors.Milk;
import com.demo.demo.business.demo.visitor.product.ProductVisitor;

public class ProductVisitorImpl implements ProductVisitor {
    @Override
    public void visit(Candy candy) {
        System.out.println("dealing with candy...");
    }

    @Override
    public void visit(Fruit fruit) {
        System.out.println("dealing with fruit");
    }

    @Override
    public void visit(Milk milk) {
        System.out.println("dealing with milk");
    }
}

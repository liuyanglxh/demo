package com.demo.demo.business.demo.visitor.product;

import com.demo.demo.business.demo.pojo.visitor.visitors.Candy;
import com.demo.demo.business.demo.pojo.visitor.visitors.Fruit;
import com.demo.demo.business.demo.pojo.visitor.visitors.Milk;

/**
 * 产品类的访问者接口
 */
public interface ProductVisitor {

    void visit(Candy candy);
    void visit(Fruit fruit);
    void visit(Milk milk);
}

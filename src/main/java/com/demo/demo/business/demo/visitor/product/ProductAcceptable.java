package com.demo.demo.business.demo.visitor.product;

/**
 * 产品的Acceptable
 */
public interface ProductAcceptable {

    void accept(ProductVisitor visitor);

}

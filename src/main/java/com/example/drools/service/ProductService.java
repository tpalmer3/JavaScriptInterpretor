package com.example.drools.service;

import com.example.drools.config.DroolsBeanFactory;
import com.example.drools.model.Product;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ProductService {

    private KieSession kieSession=new DroolsBeanFactory().getKieSession();

    public Product applyLabelToProduct(Product product){
        kieSession.insert(product);
        kieSession.fireAllRules();
        System.out.println(product.getLabel());
        return  product;

    }

}

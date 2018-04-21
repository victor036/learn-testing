package proxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;

import proxy.normalproxy.BusinessInterface;
import proxy.normalproxy.RealBusinessImpl;

public class Main {
    public static void main(String[] args) {    
        CglibProxy cglibProxy = new CglibProxy();    
    
        Enhancer enhancer = new Enhancer();  //主要的增强类  
        enhancer.setSuperclass(RealBusinessImpl.class);  //设置父类，被增强的类  
        enhancer.setCallback(cglibProxy);  //回调对象  
    
        BusinessInterface o = (BusinessInterface)enhancer.create();//用cglibProxy来增强UserServiceImpl    
        o.dosomething("1111");
    }  
}

package proxy.dynamicjava;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



import proxy.normalproxy.BusinessInterface;

/***
 * * （代理）调用处理器。<br>
 * 什么意思呢：当“代理者”被调用时，这个实现类中的invoke方法将被触发。<br>
 * “代理者”对象，外部模块/外部系统所调用的方法名、方法中的传参信息都将以invoke方法实参的形式传递到方法中。
 * 

 * @author victor
 *
 */
public class BusinessInvocationHandler implements InvocationHandler {
	/* private final Invoker<?> invoker;

	    public InvokerInvocationHandler(Invoker<?> handler) {
	        this.invoker = handler;
	    }*/
	 /**
     * 真实的业务处理对象
     */
    private BusinessInterface realBusiness;

    public BusinessInvocationHandler(BusinessInterface realBusiness) {
        this.realBusiness = realBusiness;
    }

    //关联的这个实现类的方法被调用时将被执行  
    /*InvocationHandler接口的方法，proxy表示代理，method表示原对象被调用的方法，args表示方法的参数*/  
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("（代理）调用处理器被激活=====");
        System.out.println("“代理者对象”：" + proxy.getClass().getName());
        System.out.println("“外部模块/外部系统”调用的方法名：" + method.getName());

        System.out.println("---------正式业务执行前；");
        Object resultObject = method.invoke(this.realBusiness, args);
        System.out.println("---------正式业务执行后；");

        return resultObject;
    }



}

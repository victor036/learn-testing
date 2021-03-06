package proxy.dynamicjava;

import java.lang.reflect.Proxy;

import proxy.normalproxy.BusinessInterface;
import proxy.normalproxy.RealBusinessImpl;

public class Main {
//比如日志系统、事务、拦截器、权限控制等。这也就是AOP(面向切面编程)的基本原理。
	public static void main(String[] args) throws Exception {

		BusinessInterface realBusiness = new RealBusinessImpl();
		BusinessInvocationHandler invocationHandler = new BusinessInvocationHandler(realBusiness);
		/*
		 * 生成一个动态代理实例。里面的三个参数需要讲解一下：
			1-loader：这个newProxyInstance会有一个返回值，即代理对象。
		 * 那么问题就是类实例的创建必须要有classloader的支持，第一个参数就是指等“代理对象”的创建所依据的classloader
		 * 
		 * 2-interfaces：第二个参数是一个数组。
		 * 在设计原理中，有一个重要的原则是“依赖倒置”，它的实践经验是：“依赖接口，而不是以来实现”。
		 * 所以，JAVA中动态代理的支持假定程序员是遵循这一原则的：所有业务都定义的接口。这个参数就是为动态代理指定“代理对象所实现的接口”，
		 * 由于JAVA中一个类可以实现多个接口，所以这个参数是一个数组（我的实例代码中，只为真实的业务实现定义了一个接口BusinessInterface，
		 * 所以参数中指定的也就只有这个接口）.另外，这个参数的类型是Class，所以如果您不定义接口，而是指定某个具体类，也是可行的。但是这不符合设计原则。
		 * 
		 * 3-InvocationHandler：这个就是我们的“调用处理器”，这个参数没有太多解释的
		 */
		BusinessInterface proxyBusiness = (BusinessInterface) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(), new Class[] { BusinessInterface.class },
				invocationHandler);
		
		/* Proxy.getProxyClass(loader, interfaces).
         getConstructor(new Class[] { InvocationHandler.class }).
         newInstance(new Object[] { handler });*/

		// 正式调用
		proxyBusiness.dosomething("yinwenjie");
	}
}

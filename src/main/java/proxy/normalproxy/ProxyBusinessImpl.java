package proxy.normalproxy;

/**
 * 业务接口的代理实现类（ProxyBusinessImpl）
 * 用java实现的传统的“代理模式”，有很多弊端。最大的弊端就是：<br>
 * 调用者必须清楚，自己将调用的某个对象需要被代理。。。。
 * 
 */
public class ProxyBusinessImpl implements BusinessInterface {

	/**
	 * 真实的调用对象
	 */
	private RealBusinessImpl realBusiness;

	public ProxyBusinessImpl(RealBusinessImpl realBusiness) {
		this.realBusiness = realBusiness;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see testDesignPattern.proxy.BusinessInterface#dosomething(java.lang.String)
	 */
	@Override
	public void dosomething(String username) {
		System.out.println("---------正式业务执行前；");
		this.realBusiness.dosomething(username);
		System.out.println("---------正式业务执行后；");
	}
}
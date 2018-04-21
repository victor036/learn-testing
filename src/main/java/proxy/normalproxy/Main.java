package proxy.normalproxy;

public class Main {
	  public static void main(String[] args) throws RuntimeException {
	        /*
	         * 调用者必须知道，我要使用RealBusinessImpl具体的实现；
	         * 必须使用ProxyBusinessImpl进行代理。
	         * 
	         * 这个做法写设计模式的实现实例倒还可以，没有什么实际意义
	         * */
	        RealBusinessImpl realBusiness = new RealBusinessImpl();
	        BusinessInterface proxyBusinessInterface = new ProxyBusinessImpl(realBusiness);

	        proxyBusinessInterface.dosomething("yinwenjie");
	    }
}

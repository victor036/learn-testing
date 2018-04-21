package filter;

public class Filter1 implements Filter {

	@Override
	public int invoke(Invoker invoker) {
		System.out.println("Filter1");
		//invoker.invoke();
		return invoker.invoke();
	}

}

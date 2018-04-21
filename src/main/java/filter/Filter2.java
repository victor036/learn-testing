package filter;

public class Filter2 implements Filter {

	@Override
	public int invoke(Invoker invoker) {
		System.out.println("Filter2");		
		return invoker.invoke();
	}

}

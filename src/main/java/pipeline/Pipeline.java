package pipeline;
//管道，我们一般的操作是先通过setBasic设置基础阀门，
//接着按顺序添加其他阀门，执行时的顺序是：先添加进来的先执行，最后才执行基础阀门。
public interface Pipeline {
	public Valve getFirst();

	public Valve getBasic();

	public void setBasic(Valve valve);

	public void addValve(Valve valve);
}

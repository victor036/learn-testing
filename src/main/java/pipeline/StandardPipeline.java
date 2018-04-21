package pipeline;

//管道，我们一般的操作是先通过setBasic设置基础阀门，
//接着按顺序添加其他阀门，执行时的顺序是：先添加进来的先执行，最后才执行基础阀门。
public class StandardPipeline implements Pipeline {
	protected Valve first = null;
	protected Valve basic = null;

	public void addValve(Valve valve) {
		if (first == null) {
			first = valve;
			valve.setNext(basic);
		} else {
			Valve current = first;
			while (current != null) {
				if (current.getNext() == basic) {
					current.setNext(valve);
					valve.setNext(basic);
					break;
				}
				current = current.getNext();
			}
		}
	}

	public Valve getBasic() {
		return basic;
	}

	public Valve getFirst() {
		return first;
	}

	public void setBasic(Valve valve) {
		this.basic = valve;
	}

}

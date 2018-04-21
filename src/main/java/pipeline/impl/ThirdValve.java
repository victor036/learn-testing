package pipeline.impl;

import pipeline.Valve;

public class ThirdValve implements Valve {
	protected Valve next = null;

	public Valve getNext() {
		return next;
	}

	public void invoke(String handling) {
		handling = handling.replaceAll("zz", "yy");
		System.out.println("Third阀门处理完后：" + handling);
		getNext().invoke(handling);
	}

	public void setNext(Valve valve) {
		this.next = valve;
	}
}

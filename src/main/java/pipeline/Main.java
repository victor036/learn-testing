package pipeline;

import pipeline.impl.BasicValve;
import pipeline.impl.SecondValve;
import pipeline.impl.ThirdValve;

/**
 * 这就是管道模式，在管道中连接一个或多个阀门，每个阀门负责一部分逻辑处理，数据按规定的顺序往下流。
 * 此模式分解了逻辑处理任务，可方便对某任务单元进行安装拆卸，
 * 提高了流程的可扩展性、可重用性、机动性、灵活性。
 * 
 * 一般的操作是先通过setBasic设置基础阀门，接着按顺序添加其他阀门，
 * 执行时的顺序是：先添加进来的先执行，最后才执行基础阀门
 * 输出的结果如下：
Second阀门处理完后：aabb2222zzyy
Third阀门处理完后：aabb2222yyyy
基础阀门处理完后：bbbb2222yyyy
 */
public class Main {
	public static void main(String[] args) {
		String handling = "aabb1122zzyy";
		
		StandardPipeline pipeline = new StandardPipeline();
		
		BasicValve basicValve = new BasicValve();
		
		SecondValve secondValve = new SecondValve();
		ThirdValve thirdValve = new ThirdValve();
		pipeline.setBasic(basicValve);
		
		pipeline.addValve(secondValve);
		pipeline.addValve(thirdValve);
		
		pipeline.getFirst().invoke(handling);

	}

}

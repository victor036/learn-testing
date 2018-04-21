package javassist;

import java.lang.reflect.Method;

public class JavassistDemo {
	public static void main(String[] args) throws Exception {
		test02();
	}
	public static void test02()throws Exception {
		   ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.makeClass("com.bjsxt.bean.Emp");

	        
//	        CtMethod m = CtNewMethod.make("public int add(int a,int b){return a+b;}", cc);
	        
	        CtMethod m = new CtMethod(CtClass.intType,"add",
	                new CtClass[]{CtClass.intType,CtClass.intType},cc);
	        m.setModifiers(Modifier.PUBLIC);
	        m.setBody("{System.out.println(\"Hello!!!\");return $1+$2;}");
	        
	        cc.addMethod(m);

	        //通过反射调用新生成的方法
	        Class clazz = cc.toClass();
	        Object obj = clazz.newInstance();  //通过调用Emp无参构造器，创建新的Emp对象
	        Method method = clazz.getDeclaredMethod("add", int.class,int.class);
	        Object result = method.invoke(obj, 200,300);
	        System.out.println(result);
	
	}
	
	public void test01()throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool.makeClass("com.bjsxt.bean.Emp");

		// 创建属性
		CtField f1 = CtField.make("private int empno;", cc);
		CtField f2 = CtField.make("private String ename;", cc);
		cc.addField(f1);
		cc.addField(f2);

		// 创建方法
		CtMethod m1 = CtMethod.make("public int getEmpno(){return empno;}", cc);
		CtMethod m2 = CtMethod.make("public void setEmpno(int empno){this.empno=empno;}", cc);
		cc.addMethod(m1);
		cc.addMethod(m2);

		// 添加构造器
		CtConstructor constructor = new CtConstructor(new CtClass[] { CtClass.intType, pool.get("java.lang.String") },
				cc);
		constructor.setBody("{this.empno=empno; this.ename=ename;}");
		cc.addConstructor(constructor);
		
		cc.writeFile("c:/myjava"); // 将上面构造好的类写入到c:/myjava中
		System.out.println("生成类，成功！");
	}
}

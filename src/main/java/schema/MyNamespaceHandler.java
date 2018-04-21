package schema;

/**
 * 
 * 设计配置属性和JavaBean
编写XSD文件
编写NamespaceHandler和BeanDefinitionParser完成解析工作
编写spring.handlers和spring.schemas串联起所有部件
在Bean文件中应用
 * @author victor
 *
 *下面需要完成解析工作，会用到NamespaceHandler和BeanDefinitionParser这两个概念。
 *具体说来NamespaceHandler会根据schema和节点名找到某个BeanDefinitionParser，
 *然后由BeanDefinitionParser完成具体的解析工作。
 *因此需要分别完成NamespaceHandler和BeanDefinitionParser的实现类，
 *Spring提供了默认实现类NamespaceHandlerSupport和AbstractSingleBeanDefinitionParser，
 *简单的方式就是去继承这两个类
 */

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("people", new PeopleBeanDefinitionParser());
	}

}

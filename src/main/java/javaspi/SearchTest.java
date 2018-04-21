package javaspi;

import java.util.ServiceLoader;
/***
 * 测试test1....
SearchB........
测试test2....
SearchB........
SearchA......

 * @author victor
 *
 */
public class SearchTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SearchTest test = new SearchTest();
		test.test1();
		test.test2();
	}
	public void test1() {
		System.out.println("测试test1....");
		Search search = SearchFactory.newSearch();
		search.search("java spi test");
	}
	
	public void test2() {
		System.out.println("测试test2....");
		ServiceLoader<Search> helloServiceLoader=ServiceLoader.load(Search.class);
		for(Search item:helloServiceLoader){
		    item.search("xxxx");
		}
		
	}
}

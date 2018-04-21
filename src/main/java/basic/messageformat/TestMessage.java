package basic.messageformat;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class TestMessage {
	private static final String BUNDLE_NAME = "basic.messageformat.LocalStrings";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public static String get(String key, Object... args) {
		String template = null;  
        try {  
            template = RESOURCE_BUNDLE.getString(key);  
        } catch (MissingResourceException e) {  
            StringBuilder b = new StringBuilder();  
            try {  
                b.append(RESOURCE_BUNDLE.getString("message.unknown"));  
                b.append(": ");  
            } catch (MissingResourceException e2) {}  
            b.append(key);  
            if (args != null && args.length > 0) {  
                b.append("(");  
                b.append(args[0]);  
                for (int i = 1; i < args.length; i++) {  
                    b.append(", ");  
                    b.append(args[i]);  
                }  
                b.append(")");  
            }  
            return b.toString();  
        }  
        return MessageFormat.format(template, args); 
	}
	public static void main(String[] args) {
		System.out.println(get("error.scan", 123, "aaa", "bbb"));
	}
}


/**
ResourceBundle读取的文件是在classpath路径下，
也就是src或者src目录下，而我们在项目中需要打包，打包后的properties文件在jar中，修改很不方便，
我们需要把properties文件放在jar外随时可以修改。 
1、一般情况下ResourceBundel读取文件方式默认的读取路径是classpath，
	配置文件名为resourceBundle.properties。在src根目录下为：

ResourceBundle rb=ResourceBundle.getBundle("resourceBundle")  
1如果在某包下，则为：package.resourceBundle，比如在xcc包下：
	ResourceBundle rb=ResourceBundle.getBundle("xcc.resourceBundle")  
2、resourceBundle.properties放在一个文件夹下，比如新建config文件夹，

    private static ResourceBundle rb;  
    private static BufferedInputStream inputStream;  
    static {   
        String proFilePath = System.getProperty("user.dir") +"\\config\\resourceBundle.properties";  
        try {  
            inputStream = new BufferedInputStream(new FileInputStream(proFilePath));  
            rb = new PropertyResourceBundle(inputStream);  
            inputStream.close();  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    } 
这样打包后可以直接修改properties文件
**/
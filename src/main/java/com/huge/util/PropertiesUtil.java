package com.huge.util;
import java.io.InputStream;
import java.util.Properties;
  
/** 
 *  <p>
 * 本类用提供一个线程同步的方法,读取资源文件中的配置信息
 * </p>
 * 
 */  
public class PropertiesUtil {  
	private String     file;
    
    private Properties properties;
    
    /**
     * 构造 PropertysReader
     * @param {String} path 相对于classes的文件路径
     */
    public PropertiesUtil(String path)
    {
        this.file = path;
        this.properties = new Properties();
    }
    
    public synchronized String getProperty(String key){
    	return this.getProperty(key, null);
    }
    
    /**
     * <p>
     * 本方法根据资源名获取资源内容
     * <p>
     * 
     * @param {String} key 资源文件内key
     * @param {Stirng} defaultValue 默认值
     * 
     * @reaurn String key对应的资源内容
     */
    public synchronized String getProperty(String key, String defaultValue)
    {
        try
        {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(this.file);
            
            properties.load(in);
            
        }
        catch (Exception ex1)
        {
            System.out.println("没有找到资源文件:" + this.file);
        }
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * <p>
     * 本方法根据资源名获取资源内容
     * <p>
     * 
     * @param {String} key 资源文件内key
     * @param {Stirng} defaultValue 默认值
     * @param {boolean} isnull 如果配置文件value为空，是否使用默认值
     * 
     * @reaurn String key对应的资源内容
     */
    public synchronized String getProperty(String key, String defaultValue,boolean isnull)
    {
        String value = null;
        value = getProperty(key,defaultValue);
        if(isnull && (value == null || "".equals(value.trim()) )  )
            value = defaultValue;
        return value;
    }
    
    public static void main(String[] args)
    {
    	PropertiesUtil preader = new PropertiesUtil("sysVariTable.properties");
        String rootLogger = preader.getProperty("tablename", "defaul");
        System.out.println(rootLogger);
    }
}  
package com.yang.springframework;

import cn.hutool.core.io.IoUtil;
import com.yang.springframework.bean.UserService;
import com.yang.springframework.beans.core.io.DefaultResourceLoader;
import com.yang.springframework.beans.core.io.Resource;
import com.yang.springframework.beans.core.io.ResourceLoader;
import com.yang.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.yang.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ApiTest {

    /**
     * 测试Reouce的三个实现类
     * 1. ClassPathResource:从类路径加载资源
     * 2. FileSystemResource：从本地路径加载资源
     * 3. UrlResource：通过URL加载资源
     */
    @Test
    public void test_Resource() throws IOException {
        // 1. 测试ClassPathResource
        ResourceLoader loader = new DefaultResourceLoader();
        Resource classPathResource = loader.getResource("classpath:test.properties");
        String context = IoUtil.readUtf8(classPathResource.getInputStream());
        System.out.println(context);

        // 2. 测试FileSystemResource
        Resource fileSystemResource = loader.getResource("src/test/resources/test.properties");
        context = IoUtil.readUtf8(fileSystemResource.getInputStream());
        System.out.println(context);

        // 3. 测试UrlResource
        Resource urlResource = loader.getResource("file:///C:/Users/zhou_Y/workspace/workspace/my-life/java/mini_spring/mini-spring6/src/test/resources/test.properties");
        context = IoUtil.readUtf8(urlResource.getInputStream());
        System.out.println(context);
    }

    /**
     * 测试从xml中获取bean配置注册对象
     */
    @Test
    public void test_xmlRegisterBean() {
        // 1. 获取bean工厂
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        // 2. 读取配置文件Spring.xml
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        UserService service = factory.getBean("userService", UserService.class);
        service.queryUserInfo("001");
    }
}

package com.yang.springframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.yang.springframework.beans.BeansException;
import com.yang.springframework.beans.PropertyValue;
import com.yang.springframework.beans.core.io.Resource;
import com.yang.springframework.beans.core.io.ResourceLoader;
import com.yang.springframework.beans.factory.config.BeanDefinition;
import com.yang.springframework.beans.factory.config.BeanReference;
import com.yang.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.yang.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * 用于XML bean定义的bean定义读取器。它将实际的XML文档读取工作委托给BeanDefinitionDocumentReader接口的实现。
 * <p>通常应用于org.springframework.beans.factory.support.DefaultListableBeanFactory或
 * org.springframework.context.support.GenericApplicationContext。
 * <p>此类加载DOM文档，并将BeanDefinitionDocumentReader应用于该文档。文档读取器将向给定的bean工厂注册每个bean定义，并与后者实现的BeanDefinitionRegistry接口进行交互。
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    /**
     * 为给定的bean工厂创建新的XmlBeanDefinitionReader。
     * @param registry 以BeanDefinitionRegistry形式存在的BeanFactory，用于加载bean定义
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 从指定的XML文件中加载bean定义。
     * @param resource XML文件的资源描述符
     * @throws BeansException 如果加载或解析过程中出错
     */
    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream is = resource.getInputStream();
            doLoadBeanDefinitions(is);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 实际从指定的XML文件中加载bean定义。
     * @param is 要读取的SAX InputSource
     * @throws ClassNotFoundException 如果加载或解析过程中出错
     */
    private void doLoadBeanDefinitions(InputStream is) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(is);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            // 判断是不是元素
            if (!(node instanceof Element bean)) {
                continue;
            }
            // 判断是不是bean元素
            if (!("bean".equals(node.getNodeName()))) {
                continue;
            }

            // 解析bean标签中的属性
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethodName = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");

            // 获取 Class：方便获取类中的名称，保存到BeanDefinition
            Class<?> clazz = Class.forName(className);
            // 获取beanName：优先级 id > name，注册到Registry中需要
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义BeanDefinition
            BeanDefinition bd = new BeanDefinition(clazz);

            bd.setInitMethodName(initMethodName);
            bd.setDestroyMethodName(destroyMethodName);
            // 读取属性并填充
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                Node childNode = bean.getChildNodes().item(j);
                if (!(childNode instanceof Element property)) {
                    continue;
                }
                if (!("property".equals(childNode.getNodeName()))) {
                    continue;
                }
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                Object val = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue pv = new PropertyValue(attrName, val);
                bd.getPropertyValues().addPropertyValue(pv);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            getRegistry().registerBeanDefinition(beanName, bd);
        }
    }
}

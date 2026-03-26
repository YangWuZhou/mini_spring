package com.yang.springframework.beans.factory;

/**
 * 由希望在销毁时释放资源的bean实现的接口。BeanFactory将在作用域bean单独销毁时调用其destroy方法。
 * 而org.springframework.context.ApplicationContext应在应用程序生命周期的驱动下，于关闭时销毁其所有的单例bean。
 * <p>出于相同目的，Spring管理的bean也可以实现Java的AutoCloseable接口。除了实现接口之外，另一种替代方案是指定自定义的销毁方法，
 * 例如在XML bean定义中进行配置。有关所有bean生命周期方法的列表，请参见BeanFactory的Javadoc。
 */
public interface DisposableBean {

    /**
     * 由包含该bean的BeanFactory在bean销毁时调用。
     * @throws Exception 如果关闭过程中发生错误。异常将被记录但不会重新抛出，以允许其他bean也能释放其资源。
     */
    void destroy() throws Exception;
}

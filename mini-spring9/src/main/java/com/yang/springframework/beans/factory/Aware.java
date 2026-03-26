package com.yang.springframework.beans.factory;

/**
 * 一个标记性超级接口，表明某个 bean 有资格通过回调方式接收 Spring 容器提供的特定框架对象。具体的方法签名由各个子接口单独定义，
 * 但通常应包含一个仅接受单个参数且返回类型为 void 的方法。
 * <p>需要注意的是，仅实现 Aware 接口本身并不提供任何默认功能。相关处理必须显式进行，例如在
 * org.springframework.beans.factory.config.BeanPostProcessor 中完成。有关处理特定 *Aware 接口回调的示例，
 * 请参考 org.springframework.context.support.ApplicationContextAwareProcessor。
 */
public interface Aware {
}

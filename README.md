### JCute 是什么
jcue是一套功能类似Spring的开源框架，实现了Spring的大部分功能，但用法相对Spring要简洁，整体采用注解方式，代码清晰，功能灵活，用户也可扩展基于xml文件或json等配置文件的注入方式。
jcute除了包含基本的IOC/AOP等功能外，还提供了基于注解的插件扩展机制，用户可以像使用spring boot一样扩展jcute，为jcute创建更丰富的插件来满足业务需求。
jcute内置了ShutDownHook，Cache，Transation基本插件。
### JCute有哪些注解
| 注解      | 功能描述 |
| --------- | -----|
| @Configuration  | 配置类标注注解，标注此注解对象也会被实例化且可以注入 |
| @ImportConfig  | 配置文件导入注解 |
| @Autowired  | 动态注入注解 |
| @Component     |   注入标记注解，标记此注解的类将被实例化 |
| @ComponentScan      |    包扫描注解，可在配置类@Configuration的类上标记此注解 |
| @Destory     |   销毁方法标记注解每标记此注解容器销毁前调用 |
| @Initial      |    初始化方法标记注解，属性及对象注入结束后首先调用此注解 |
| @Interceptor  | 拦截器注解 |
| @Order     |   排序注解，可与@Destory，@Initial，@Interceptor配合使用 |
| @Property      |    属性注入注解，用来注入配置文件中的属性只支持默认值 |
| @Pluggable     |   自定义插件扩展注解 |
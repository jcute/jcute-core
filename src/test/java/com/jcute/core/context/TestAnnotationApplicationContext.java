package com.jcute.core.context;

import com.jcute.core.annotation.Component;
import com.jcute.core.annotation.ComponentScan;
import com.jcute.core.annotation.Configuration;
import com.jcute.core.annotation.Destory;
import com.jcute.core.annotation.Initial;
import com.jcute.core.annotation.Order;
import com.jcute.core.bean.BeanInfoEntity;
import com.jcute.core.context.imp.ApplicationContextByAnnotation;
import com.jcute.core.context.service.imp.StudentService;

@Configuration
@ComponentScan(value = "com.jcute.core.bean")
public class TestAnnotationApplicationContext{

	public static void main(String[] args) throws Exception{
		ApplicationContext applicationContext = new ApplicationContextByAnnotation(TestAnnotationApplicationContext.class);
		applicationContext.initial();

		StudentService service = applicationContext.getBean(StudentService.class);

		// 测试代理执行时间
		// 1. 100万次 -> 52776130纳秒 -> 52.77613毫秒
		// 2. 100万次 -> 48187894纳秒 -> 48.18789毫秒
		// 3. 100万次 -> 48596540纳秒 -> 48.59654毫秒
		// 4. 1一亿次 -> 14776552纳秒 -> 1477.655毫秒 -> 1.4秒
		// 以上测试只是每次运行main函数结果,经过一次预热后,执行时间将降低50%
		long a = System.nanoTime();
		for(int i = 0;i < 10000;i++){
			service.getName();
		}
		System.out.println(System.nanoTime() - a);

		// System.out.println(applicationContext.getBeans(IUserService.class));

		// System.out.println(applicationContext.getBean(IUserService.class,"studentService"));
		// System.out.println(applicationContext.getBean(IUserService.class,"teacherService"));

		applicationContext.destory();
	}

	@Initial
	public void initialA(){
		System.out.println("a");
	}

	@Initial
	public void initialB(){
		System.out.println("b");
	}

	@Destory
	@Order(2)
	public void destoryA(){
		System.out.println("a");
	}

	@Destory
	@Order(1)
	public void destoryB(){
		System.out.println("b");
	}

	@Component
	public BeanInfoEntity beanInfoEntityAAA(){
		return new BeanInfoEntity();
	}

}
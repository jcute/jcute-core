package com.jcute.core.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import com.jcute.core.annotation.Configuration;
import com.jcute.core.context.ApplicationContext;
import com.jcute.core.context.imp.ApplicationContextByAnnotation;
import com.jcute.core.util.AnnotationUtil;

/**
 * 无缝集成JUnit测试
 * 
 * @author tangbin
 *
 */
public class JCuteJUnitForClassRunner extends BlockJUnit4ClassRunner{

	private ApplicationContext applicationContext;

	public JCuteJUnitForClassRunner(Class<?> klass) throws InitializationError{
		super(klass);
		if(!AnnotationUtil.hasAnnotation(klass,Configuration.class)){
			throw new InitializationError(new Throwable("Missing @Configuration annotation"));
		}
		this.applicationContext = new ApplicationContextByAnnotation(klass);
	}

	@Override
	protected Object createTest() throws Exception{
		try{
			this.applicationContext.initial();
			return this.applicationContext.getBean(this.getTestClass().getJavaClass());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 测试结束后,释放applicationContext
	 */
	@Override
	protected Statement withAfterClasses(final Statement statement){
		return new Statement(){
			@Override
			public void evaluate() throws Throwable{
				if(null != statement){
					statement.evaluate();
				}
				if(null != applicationContext){
					applicationContext.destory();
				}
			}
		};
	}

}
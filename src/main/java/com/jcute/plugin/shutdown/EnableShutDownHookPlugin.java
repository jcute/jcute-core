package com.jcute.plugin.shutdown;

import java.lang.annotation.Annotation;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import com.jcute.core.context.ApplicationContext;
import com.jcute.core.error.ContextInitialException;
import com.jcute.core.logging.Logger;
import com.jcute.core.logging.LoggerFactory;
import com.jcute.core.plugin.Plugin;

public class EnableShutDownHookPlugin extends Plugin implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(EnableShutDownHookPlugin.class);

	private static final ReentrantLock LOCK = new ReentrantLock();
	private static final Condition STOP = LOCK.newCondition();

	public EnableShutDownHookPlugin(ApplicationContext applicationContext,Annotation annotation){
		super(applicationContext,annotation);
	}

	@Override
	public void onBeforeInitial() throws ContextInitialException{
		final ApplicationContext applicationContext = this.getApplicationContext();
		Runtime.getRuntime().addShutdownHook(new Thread("nix-destory") {
			@Override
			public void run(){
				try{
					LOCK.lock();
					if(null != applicationContext){
						applicationContext.destory();
					}
					STOP.signal();
				}catch(Throwable e){
					e.printStackTrace();
				}finally{
					LOCK.unlock();
				}
			}
		});
		new Thread(this,"jcute-watting").start();
	}

	@Override
	public void run(){
		try{
			LOCK.lock();
			STOP.await();
		}catch(InterruptedException e){
			e.printStackTrace();
			logger.warn("Application stopped interrupted by other thread",e);
		}finally{
			LOCK.unlock();
		}
	}

}
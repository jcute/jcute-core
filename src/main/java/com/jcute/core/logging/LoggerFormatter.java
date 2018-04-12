package com.jcute.core.logging;

import java.util.HashSet;
import java.util.Set;

/**
 * 日志参数格式化,可使用{}作为占位符进行填充
 */
public class LoggerFormatter{

	private static final String DELIM_STR = "{}";
	private static final char ESCAPE_CHAR = '\\';

	public static LoggerFormatterTuple format(String messagePattern,Object arg){
		return arrayFormat(messagePattern,new Object[]{arg});
	}

	public static LoggerFormatterTuple format(String messagePattern,Object argA,Object argB){
		return arrayFormat(messagePattern,new Object[]{argA,argB});
	}

	public static LoggerFormatterTuple arrayFormat(final String messagePattern,final Object[] argArray){
		if(null == argArray || argArray.length == 0){
			return new LoggerFormatterTuple(messagePattern,null);
		}
		int lastArrIdx = argArray.length - 1;
		Object lastEntry = argArray[lastArrIdx];
		Throwable throwable = lastEntry instanceof Throwable ? (Throwable)lastEntry : null;
		if(messagePattern == null){
			return new LoggerFormatterTuple(null,throwable);
		}
		int j = messagePattern.indexOf(DELIM_STR);
		if(j == -1){
			return new LoggerFormatterTuple(messagePattern,throwable);
		}
		StringBuilder sbuf = new StringBuilder(messagePattern.length() + 50);
		int i = 0;
		int l = 0;
		do{
			boolean notEscaped = j == 0 || messagePattern.charAt(j - 1) != ESCAPE_CHAR;
			if(notEscaped){
				sbuf.append(messagePattern,i,j);
			}else{
				sbuf.append(messagePattern,i,j - 1);
				notEscaped = j >= 2 && messagePattern.charAt(j - 2) == ESCAPE_CHAR;
			}
			i = j + 2;
			if(notEscaped){
				deeplyAppendParameter(sbuf,argArray[l],null);
				l++;
				if(l > lastArrIdx){
					break;
				}
			}else{
				sbuf.append(DELIM_STR);
			}
			j = messagePattern.indexOf(DELIM_STR,i);
		}while(j != -1);
		sbuf.append(messagePattern,i,messagePattern.length());
		return new LoggerFormatterTuple(sbuf.toString(),l <= lastArrIdx ? throwable : null);
	}

	private static void deeplyAppendParameter(StringBuilder sbuf,Object o,Set<Object[]> seenSet){
		if(o == null){
			sbuf.append("null");
			return;
		}
		Class<?> objClass = o.getClass();
		if(!objClass.isArray()){
			if(Number.class.isAssignableFrom(objClass)){
				if(objClass == Long.class){
					sbuf.append(((Long)o).longValue());
				}else if(objClass == Integer.class || objClass == Short.class || objClass == Byte.class){
					sbuf.append(((Number)o).intValue());
				}else if(objClass == Double.class){
					sbuf.append(((Double)o).doubleValue());
				}else if(objClass == Float.class){
					sbuf.append(((Float)o).floatValue());
				}else{
					safeObjectAppend(sbuf,o);
				}
			}else{
				safeObjectAppend(sbuf,o);
			}
		}else{
			sbuf.append('[');
			if(objClass == boolean[].class){
				arrayAppend(sbuf,(boolean[])o);
			}else if(objClass == byte[].class){
				arrayAppend(sbuf,(byte[])o);
			}else if(objClass == char[].class){
				arrayAppend(sbuf,(char[])o);
			}else if(objClass == short[].class){
				arrayAppend(sbuf,(short[])o);
			}else if(objClass == int[].class){
				arrayAppend(sbuf,(int[])o);
			}else if(objClass == long[].class){
				arrayAppend(sbuf,(long[])o);
			}else if(objClass == float[].class){
				arrayAppend(sbuf,(float[])o);
			}else if(objClass == double[].class){
				arrayAppend(sbuf,(double[])o);
			}else{
				objectArrayAppend(sbuf,(Object[])o,seenSet);
			}
			sbuf.append(']');
		}
	}

	private static void objectArrayAppend(StringBuilder sbuf,Object[] a,Set<Object[]> seenSet){
		if(a.length == 0){
			return;
		}
		if(seenSet == null){
			seenSet = new HashSet<Object[]>(a.length);
		}
		if(seenSet.add(a)){
			deeplyAppendParameter(sbuf,a[0],seenSet);
			for(int i = 1;i < a.length;i++){
				sbuf.append(", ");
				deeplyAppendParameter(sbuf,a[i],seenSet);
			}
			seenSet.remove(a);
		}else{
			sbuf.append("...");
		}
	}

	private static void arrayAppend(StringBuilder sbuf,boolean[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,byte[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,char[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,short[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,int[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,long[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,float[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void arrayAppend(StringBuilder sbuf,double[] a){
		if(a.length == 0){
			return;
		}
		sbuf.append(a[0]);
		for(int i = 1;i < a.length;i++){
			sbuf.append(", ");
			sbuf.append(a[i]);
		}
	}

	private static void safeObjectAppend(StringBuilder sbuf,Object o){
		try{
			String oAsString = o.toString();
			sbuf.append(oAsString);
		}catch(Throwable t){
			System.err.println("Failed toString() invocation on an object of type [" + o.getClass().getName() + ']');
			t.printStackTrace();
			sbuf.append("[FAILED toString()]");
		}
	}

}
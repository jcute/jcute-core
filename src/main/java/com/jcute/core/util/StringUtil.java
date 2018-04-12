package com.jcute.core.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtil{

	/**
	 * <p>
	 * 判断字符串是否为空
	 * </p>
	 * 
	 * <pre>
	 * StringUtil.isBlank(null) = true 
	 * StringUtil.isBlank("") = true
	 * StringUtil.isBlank(" ") = true 
	 * StringUtil.isBlank("bob") = false
	 * StringUtil.isBlank("  bob  ") = false
	 * </pre>
	 * 
	 * @param cs 需要验证的CharSequence类型数据
	 * @return true标识CharSequence为空,false不为空
	 */
	public static boolean isBlank(final CharSequence cs){
		int length;
		if(cs == null || (length = cs.length()) == 0){
			return true;
		}
		for(int i = 0;i < length;i++){
			if(!Character.isWhitespace(cs.charAt(i))){
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * 判断字符串是否不为空,规则同{@link isBlank}相反
	 * </p>
	 * 
	 * @param cs
	 * @return true标识CharSequence不为空,false为空
	 */
	public static boolean isNotBlank(final CharSequence cs){
		return !isBlank(cs);
	}

	/**
	 * 集合类转String数组,null或empty返回0长度数组
	 * 
	 * @param collection
	 * @return 返回拆分后的字符串数组,空为0长度数组
	 */
	public static String[] toArray(final Collection<String> collection){
		if(null == collection || collection.size() == 0){
			return new String[0];
		}
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * 指定分隔符打断字符串为数组
	 * 
	 * @param value
	 * @param delimiters
	 * @param trimToken
	 * @param ignoreEmptyToken
	 * @return 返回拆分后的字符串数组,空为0长度数组
	 */
	public static String[] tokenToArray(final String value,final String delimiters,final boolean trimToken,final boolean ignoreEmptyToken){
		if(null == value){
			return new String[0];
		}
		StringTokenizer stringTokenizer = new StringTokenizer(value,delimiters);
		List<String> tokens = new ArrayList<String>();
		while(stringTokenizer.hasMoreTokens()){
			String token = stringTokenizer.nextToken();
			if(trimToken){
				token = token.trim();
			}
			if(!ignoreEmptyToken || token.length() > 0){
				tokens.add(token);
			}
		}
		return toArray(tokens);
	}

	/**
	 * 将集合转换为字符串，可在字符串转换过程中追加 prefix，suffix，delimiter
	 * 
	 * @param collection
	 * @param delimiter
	 * @param prefix
	 * @param suffix
	 * @return 返回转换后的结果
	 */
	public static String collectionToDelimitedString(Collection<?> collection,String delimiter,String prefix,String suffix){
		if(null == collection || collection.isEmpty()){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Iterator<?> iter = collection.iterator();
		while(iter.hasNext()){
			sb.append(prefix).append(iter.next()).append(suffix);
			if(iter.hasNext()){
				sb.append(delimiter);
			}
		}
		return sb.toString();
	}

	public static String collectionToDelimitedString(Collection<?> collection,String delimiter){
		return collectionToDelimitedString(collection,delimiter,"","");
	}

	/**
	 * 删除指定字符串中存在的指定字符
	 * 
	 * @param value
	 * @param charsToDelete
	 * @return 返回删除后的字符串
	 */
	public static String deleteAny(String value,String charsToDelete){
		if(StringUtil.isBlank(value) || StringUtil.isBlank(charsToDelete)){
			return value;
		}
		StringBuilder sb = new StringBuilder(value.length());
		for(int i = 0,l = value.length();i < l;i++){
			char c = value.charAt(i);
			if(charsToDelete.indexOf(c) != -1){
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串替换
	 * 
	 * @param value
	 * @param oldPattern
	 * @param newPattern
	 * @return 返回替换后的字符串
	 */
	public static String replace(String value,String oldPattern,String newPattern){
		if(StringUtil.isBlank(value) || StringUtil.isBlank(oldPattern) || StringUtil.isBlank(newPattern)){
			return value;
		}
		int index = value.indexOf(oldPattern);
		if(index == -1){
			return value;
		}
		int capacity = value.length();
		if(newPattern.length() > oldPattern.length()){
			capacity += 16;
		}
		StringBuilder stringBuilder = new StringBuilder(capacity);
		int position = 0;
		int patternLength = oldPattern.length();
		while(index >= 0){
			stringBuilder.append(value.substring(position,index));
			stringBuilder.append(newPattern);
			position = index + patternLength;
			index = value.indexOf(oldPattern,position);
		}
		stringBuilder.append(value.substring(position));
		return stringBuilder.toString();
	}

	/**
	 * 将容器转换为数组
	 * 
	 * @param collection
	 * @return 返回转换结果
	 */
	public static String[] toStringArray(Collection<String> collection){
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * 将枚举容器转换为数组
	 * 
	 * @param enumeration
	 * @return 返回转换结果
	 */
	public static String[] toStringArray(Enumeration<String> enumeration){
		List<String> list = Collections.list(enumeration);
		return toStringArray(list);
	}

	/**
	 * 分割字符串为数组，可指定需要删除的字符，如换行符等
	 * 
	 * @param value
	 * @param delimiter
	 * @param charsToDelete
	 * @return 返回分割后的结果
	 */
	public static String[] delimitedListToStringArray(String value,String delimiter,String charsToDelete){
		if(null == value){
			return new String[0];
		}
		if(delimiter == null){
			return new String[]{value};
		}
		List<String> result = new ArrayList<String>();
		if("".equals(delimiter)){
			for(int i = 0,l = value.length();i < l;i++){
				result.add(deleteAny(value.substring(i,i + 1),charsToDelete));
			}
		}else{
			int position = 0;
			int delPosition;
			while((delPosition = value.indexOf(delimiter,position)) != -1){
				result.add(deleteAny(value.substring(position,delPosition),charsToDelete));
				position = delPosition + delimiter.length();
			}
			if(value.length() > 0 && position <= value.length()){
				result.add(deleteAny(value.substring(position),charsToDelete));
			}
		}
		return toStringArray(result);
	}

	/**
	 * 分割字符串为数组
	 * 
	 * @param value
	 * @param delimiter
	 * @return 返回分割后的数组
	 */
	public static String[] delimitedListToStringArray(String value,String delimiter){
		return delimitedListToStringArray(value,delimiter,null);
	}

}
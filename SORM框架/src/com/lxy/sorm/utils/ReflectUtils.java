package com.lxy.sorm.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * 	��װ���÷���Ͷ�̬�������
 * @author ������
 *
 */
@SuppressWarnings("all")
public class ReflectUtils {
	/**
	 * 	����obj�����Ӧ����fieldName��get����
	 * @param fieldName
	 * @param obj
	 */
	public static Object invokeGet(String fieldName,Object obj) {
		Class clazz = obj.getClass();
		try {
			Method m = clazz.getDeclaredMethod("get"+StringUtils.initialUpper(fieldName), null);
			return m.invoke(obj, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void invokeSet(String fieldName,Object obj,Object fieldValue) {
		Class clazz = obj.getClass();
		try {
			if(fieldValue!=null) {
				Method m = clazz.getDeclaredMethod("set"+StringUtils.initialUpper(fieldName),
						fieldValue.getClass());
				
//				ֱ�Ӵ�ֵ������  ��fieldValue.getClass()  �����Ͳ�ƥ��Ĵ��󱨴�
				m.invoke(obj, fieldValue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, byte[]> compile(String fileName, String source) {
	    Map<String, byte[]> results = new HashMap<>();
	    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	    StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
	    try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
	        JavaFileObject javaFileObject = manager.makeStringSource(fileName, source);
	        CompilationTask task = compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject));
	        if (task.call()) {
	            results = manager.getClassBytes();
	        }
	        return results;
	    } catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}	
}


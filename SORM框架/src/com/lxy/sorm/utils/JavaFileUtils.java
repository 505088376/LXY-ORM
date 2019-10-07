package com.lxy.sorm.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lxy.sorm.bean.ColumnInfo;
import com.lxy.sorm.bean.JavaFieldGetSet;
import com.lxy.sorm.bean.TableInfo;
import com.lxy.sorm.core.DBManager;
import com.lxy.sorm.core.MysqlTypeConvertor;

/**
 * 	封装java文件操作
 * @author 李晓勇
 *
 */
public class JavaFileUtils {
	/**
	 * 	根据字段信息生成java属性信息  如 varchar username --> private String username; 以及相应的set和get方法
	 * @param columns 字段信息
	 * @return	java属性和set/get方法
	 */
	public static JavaFieldGetSet createFileGetSet(ColumnInfo columns) {
		String name = columns.getName();
		String javaDataType = columns.getDateType();
		return createFileGetSet(name,javaDataType);
	}
	
	/**
	 * 	根据表信息生成java类的源代码
	 * @param tableInfo 表信息
	 * @return java类源代码
	 */
	public static String javaSrc(TableInfo tableInfo) {
		Map<String,ColumnInfo> columns = tableInfo.getColumns();
		List<JavaFieldGetSet> list = new ArrayList<>();
		for(ColumnInfo c:columns.values()) {
			JavaFieldGetSet jfgs = createFileGetSet(c);
			list.add(jfgs);
		}
		StringBuilder src = new StringBuilder();
		src.append("package "+ DBManager.getConf().getPoPackage()+";\n\n");
		src.append("import java.util.*;\n");
		src.append("import java.sql.*;\n");
//		生成类名
		src.append("public class "+StringUtils.initialUpper(tableInfo.getTname())+" {\n\n");
//		生成属性
		for(JavaFieldGetSet info : list) {
			src.append(info.getFiledInfo()+"\n");
		}
		
//		生成get方法
		for(JavaFieldGetSet info : list) {
			src.append(info.getGetInfo()+"\n");
		}
//		生成set方法
		for(JavaFieldGetSet info : list) {
			src.append(info.getSetInfo()+"\n");
		}
		src.append("}\n");
//		System.out.println(src);
		return src.toString();
	}
	
	public static Class createJavaPOFile(TableInfo tableInfo) {
		String srcPath = DBManager.getConf().getSrcPath();
		String poPath = DBManager.getConf().getPoPackage().replaceAll("\\.", "/");
		String path = srcPath+"/"+poPath+"/"+StringUtils.initialUpper(tableInfo.getTname())+".java";
		String code = javaSrc(tableInfo);
		String className = StringUtils.initialUpper(tableInfo.getTname());
//		System.out.println(path);
		File f =new File(srcPath+"/"+poPath);
//		System.out.println(f.getAbsolutePath());
		if(!f.exists()) {
			f.mkdirs();
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(code);
			bw.flush();
			System.out.println(className+".java类已建立");
			JavaStringCompiler compiler = new JavaStringCompiler();
			Map<String, byte[]> results = ReflectUtils.compile(className+".java",code);
			return compiler.loadClass(DBManager.getConf().getPoPackage()+"."+className, results);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bw.flush();
				if(null!=bw) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static JavaFieldGetSet createFileGetSet(String fieldName,String typeName) {
		JavaFieldGetSet jfgs = new JavaFieldGetSet();
		MysqlTypeConvertor mtc = new MysqlTypeConvertor();
		String javaDataType = mtc.databaseTypeToJavaType(typeName);
//		转换为属性源码：private String name;
		String fieldInfo = "\tprivate "+javaDataType+" "+fieldName+";\n";
		jfgs.setFiledInfo(fieldInfo);
		
//		转换为get方法源码：public String getName(){return name;}
		StringBuilder getInfo = new StringBuilder();
		getInfo.append("\tpublic "+javaDataType+" get"+StringUtils.initialUpper(fieldName)+"(){\n");
		getInfo.append("\t\treturn "+fieldName+";\n");
		getInfo.append("\t}\n");
		jfgs.setGetInfo(getInfo.toString());
		
//		转换为set方法源码：public void setName(String name){this.name = name}
		StringBuilder setInfo = new StringBuilder();
		setInfo.append("\tpublic void set"+StringUtils.initialUpper(fieldName)+"("+javaDataType
						+ " "+fieldName+"){\n");
		setInfo.append("\t\tthis."+fieldName+" = "+fieldName+";\n");
		setInfo.append("\t}\n");
		jfgs.setSetInfo(setInfo.toString());
		
		return jfgs;
	}
	
	public static Class createJavaPOFile(List<StringBuilder> infoClass,String className) {
		String srcPath = DBManager.getConf().getSrcPath();
		String voPath = DBManager.getConf().getVoPackage().replaceAll("\\.", "/");
		String path = srcPath+"/"+voPath+"/" +className + ".java";
		String code = javaSrc(infoClass,className);
//		System.out.println(path);
		File f =new File(srcPath+"/"+ voPath);
		if(!f.exists()) {
			f.mkdirs();
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(code);
			bw.flush();
			System.out.println(className + ".java类已建立");
			JavaStringCompiler compiler = new JavaStringCompiler();
			Map<String, byte[]> results = ReflectUtils.compile(className+".java", code);
			return compiler.loadClass(DBManager.getConf().getVoPackage()+"."+className, results);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(null!=bw) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static String javaSrc(List<StringBuilder> infoClass,String className) {
		StringBuilder src = new StringBuilder();
		src.append("package "+ DBManager.getConf().getVoPackage()+";\n\n");
		src.append("import java.util.*;\n");
		src.append("import java.sql.*;\n");
//		生成类名
		src.append("public class "+className+" {\n\n");
//		生成属性
		src.append(infoClass.get(0)+"\n");
//		生成get方法
		src.append(infoClass.get(1)+"\n");
//		生成set方法
		src.append(infoClass.get(2)+"\n");
		src.append("}\n");
//		System.out.println(src);
		return src.toString();
	}
}

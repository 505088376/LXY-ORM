package com.lxy.sorm.example;

import java.util.List;

import com.lxy.po.Employees;
import com.lxy.sorm.core.Query;
import com.lxy.sorm.core.QueryFactory;
import com.lxy.sorm.utils.ReflectUtils;
/**
 * 	测试用例
 * @author 李晓勇
 *
 */
public class Example01 {
	
	/**
	 * 删除测试
	 */
	public static void deleteTest() {
		/**
		 * 	通过对象删除
		 */
		Employees emp = new Employees();
		emp.setEmployee_id(127);
		Query query =  QueryFactory.createQuery();
		query.delete(emp);
		
		/**
		 * 	通过类和主键主键删除
		 */
		query.delete(emp.getClass(), 127);
	}
	
	/**
	 * 	插入测试
	 */
	public static void insertTest() {
		Employees emp = new Employees();
		emp.setEmployee_id(127);
		emp.setFirst_name("James");
		emp.setLast_name("Landry");
		emp.setEmail("JLANDRY");
		emp.setPhone_number("650.124.1334");
		emp.setJob_id("ST_CLERK");
		emp.setSalary(2400.00);
		emp.setManager_id(120);
		emp.setDepartment_id(50);
		Query query =  QueryFactory.createQuery();
		query.insert(emp);
	}
	
	/**
	 * 	更新测试  注意：一定要在java对应的类中设置主键值
	 */
	public static void updateTest() {
		Employees emp = new Employees();
		emp.setEmployee_id(127);
		emp.setFirst_name("Wade");
		emp.setPhone_number("5201314");
		Query query =  QueryFactory.createQuery();
		query.update(emp,new String[] {"first_name","phone_number"});
	}
	
	/**
	 * 	多行查询测试
	 */
	public static void queryRowsTest() {
		/**
		 * 	多行查询方式一：
		 * 		自己传入类
		 */
		String sql = "select * from employees where employee_id > ?";
		Employees emp = new Employees();
		List<Employees> list = QueryFactory.createQuery().queryRows(sql, emp.getClass(), new Object[] {120});
		for(Employees result: list) {
			System.out.println(result.getJob_id());
		}
		
		/**
		 * 	多行查询方式二：
		 * 		不用传入类  会自动生成类放在配置文件中voPackage目录下   类的名字命名为ResultSet
		 *	优点：
		 *		做联合查询的时候， 不用自己在手写一个你要查询的类传入  如果字段名很多 写起来会较为复杂
		 *	缺点：
		 * 		需要调用反射机制使用此类
		 * 	注意：
		 * 		遇到查询名称非字母、数字、下划线的时候必须使用别名 别名必须用字母、数字、下划线命名
		 */
		sql = "select * from employees e "
				+ "join departments d on e.department_id = d.department_id"
				+ " where employee_id  = ?";
		
		List list2 = QueryFactory.createQuery().queryRows(sql, new Object[] {150});
		System.out.println(list2.isEmpty());
		for(Object obj: list2) {
			System.out.println(ReflectUtils.invokeGet("Job_id", obj));
			
		}
	}
	
	/**
	 * 	单行查询测试
	 */
	public static void queryUniqueRowTest() {
		/**
		 * 	一行查询方式一：
		 * 		自己传入类
		 */
		String sql = "select * from employees where employee_id = ?";
		QueryFactory.createQuery().queryUniqueRow(sql, Employees.class, new Object[] {127});
		
		/**
		 * 	一行查询方式二：
		 * 		同多行查询方式二：源码就是调用的多行查询方式二
		 */
		QueryFactory.createQuery().queryUniqueRow(sql, new Object[] {127});
		
	}
	
	/**
	 * 	单值查询测试
	 */
	public static void queryValueTest() {
		String sql = "select commission_pct from employees where employee_id = ?";
		Object b = QueryFactory.createQuery().queryValue(sql, new Object[] {127});
		
		Number a = QueryFactory.createQuery().queryNumber(sql, new Object[] {127});
		
		Employees emp = new Employees();
		QueryFactory.createQuery().queryById(emp.getClass(), 127);
		
	}
	
//	测试
	public static void main(String[] args) {
//		deleteTest();
//		insertTest();
		updateTest();
		queryRowsTest();
//		queryUniqueRowTest();
//		queryValueTest();
	}
}

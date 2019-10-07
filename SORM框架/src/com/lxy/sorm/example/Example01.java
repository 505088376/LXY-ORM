package com.lxy.sorm.example;

import java.util.List;

import com.lxy.po.Employees;
import com.lxy.sorm.core.Query;
import com.lxy.sorm.core.QueryFactory;
import com.lxy.sorm.utils.ReflectUtils;
/**
 * 	��������
 * @author ������
 *
 */
public class Example01 {
	
	/**
	 * ɾ������
	 */
	public static void deleteTest() {
		/**
		 * 	ͨ������ɾ��
		 */
		Employees emp = new Employees();
		emp.setEmployee_id(127);
		Query query =  QueryFactory.createQuery();
		query.delete(emp);
		
		/**
		 * 	ͨ�������������ɾ��
		 */
		query.delete(emp.getClass(), 127);
	}
	
	/**
	 * 	�������
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
	 * 	���²���  ע�⣺һ��Ҫ��java��Ӧ��������������ֵ
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
	 * 	���в�ѯ����
	 */
	public static void queryRowsTest() {
		/**
		 * 	���в�ѯ��ʽһ��
		 * 		�Լ�������
		 */
		String sql = "select * from employees where employee_id > ?";
		Employees emp = new Employees();
		List<Employees> list = QueryFactory.createQuery().queryRows(sql, emp.getClass(), new Object[] {120});
		for(Employees result: list) {
			System.out.println(result.getJob_id());
		}
		
		/**
		 * 	���в�ѯ��ʽ����
		 * 		���ô�����  ���Զ���������������ļ���voPackageĿ¼��   �����������ΪResultSet
		 *	�ŵ㣺
		 *		�����ϲ�ѯ��ʱ�� �����Լ�����дһ����Ҫ��ѯ���ഫ��  ����ֶ����ܶ� д�������Ϊ����
		 *	ȱ�㣺
		 * 		��Ҫ���÷������ʹ�ô���
		 * 	ע�⣺
		 * 		������ѯ���Ʒ���ĸ�����֡��»��ߵ�ʱ�����ʹ�ñ��� ������������ĸ�����֡��»�������
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
	 * 	���в�ѯ����
	 */
	public static void queryUniqueRowTest() {
		/**
		 * 	һ�в�ѯ��ʽһ��
		 * 		�Լ�������
		 */
		String sql = "select * from employees where employee_id = ?";
		QueryFactory.createQuery().queryUniqueRow(sql, Employees.class, new Object[] {127});
		
		/**
		 * 	һ�в�ѯ��ʽ����
		 * 		ͬ���в�ѯ��ʽ����Դ����ǵ��õĶ��в�ѯ��ʽ��
		 */
		QueryFactory.createQuery().queryUniqueRow(sql, new Object[] {127});
		
	}
	
	/**
	 * 	��ֵ��ѯ����
	 */
	public static void queryValueTest() {
		String sql = "select commission_pct from employees where employee_id = ?";
		Object b = QueryFactory.createQuery().queryValue(sql, new Object[] {127});
		
		Number a = QueryFactory.createQuery().queryNumber(sql, new Object[] {127});
		
		Employees emp = new Employees();
		QueryFactory.createQuery().queryById(emp.getClass(), 127);
		
	}
	
//	����
	public static void main(String[] args) {
//		deleteTest();
//		insertTest();
		updateTest();
		queryRowsTest();
//		queryUniqueRowTest();
//		queryValueTest();
	}
}

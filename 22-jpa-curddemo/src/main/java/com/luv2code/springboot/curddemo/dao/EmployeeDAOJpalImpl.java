package com.luv2code.springboot.curddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springboot.curddemo.entity.Employee;

public class EmployeeDAOJpalImpl implements EmployeeDAO {

	@Repository
	public class EmployeeDAOJpaImpl implements EmployeeDAO {

		private EntityManager entityManager;
		
		@Autowired
		public EmployeeDAOJpaImpl(EntityManager theEntityManager) {
			entityManager = theEntityManager;
		}
		
		@Override
		public List<Employee> findAll() {

			// create a query
			Query theQuery = 
					entityManager.createQuery("from Employee");
			
			// execute query and get result list
			List<Employee> employees = theQuery.getResultList();
			
			// return the results		
			return employees;
		}

		@Override
		public Employee findById(int theId) {

			// get employee
			Employee theEmployee = 
					entityManager.find(Employee.class, theId);
			
			// return employee
			return theEmployee;
		}

		@Override
		public void save(Employee theEmployee) {

			// save or update the employee
			Employee dbEmployee = entityManager.merge(theEmployee);
			
			// update with id from db ... so we can get generated id for save/insert
			theEmployee.setId(dbEmployee.getId());
			
		}

		@Override
		public void deleteById(int theId) {

			// delete object with primary key
			Query theQuery = entityManager.createQuery(
								"delete from Employee where id=:employeeId");
			
			theQuery.setParameter("employeeId", theId);
			
			theQuery.executeUpdate();
		}

	}


	private EntityManager entityManager;

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

	@Override
	public Employee findById(int theId) {
		       // get the current hibernate session
				Session currentSession = entityManager.unwrap(Session.class);
				
				// get the employee
				Employee theEmployee =
						currentSession.get(Employee.class, theId);
				
				// return the employee
				return theEmployee;
	}

	@Override
	public void save(Employee theEmployee) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save employee
		currentSession.saveOrUpdate(theEmployee);
	}


	@Override
	public void deleteById(int theId) {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
				
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery(
						"delete from Employee where id=:employeeId");
		theQuery.setParameter("employeeId", theId);
		
		theQuery.executeUpdate();
		
	}
}

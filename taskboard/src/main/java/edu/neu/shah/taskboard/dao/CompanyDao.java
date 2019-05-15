package edu.neu.shah.taskboard.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.shah.taskboard.pojo.Company;
import edu.neu.shah.taskboard.pojo.Project;
import edu.neu.shah.taskboard.pojo.User;

@Repository
@Transactional
public class CompanyDao {

	@Autowired
	private EntityManager entityManager;

	public void insertCompany(Company company) {
		entityManager.persist(company);
	}

	@SuppressWarnings("unchecked")
	public List<Project> getProjects(final int companyId) {
		return entityManager.createQuery("SELECT p from Project p WHERE p.company.id = :companyId")
				.setParameter("companyId", companyId).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers(final int companyId) {
		return entityManager.createQuery("SELECT e from User e WHERE e.company.id = :companyId")
				.setParameter("companyId", companyId).getResultList();
	}

}

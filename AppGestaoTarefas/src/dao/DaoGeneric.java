package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import util.JPAUtil;

public class DaoGeneric<O> {
	
	EntityManager em;
	
	public void salvar(O obj) {
		try {
			em = JPAUtil.getEntityManager();
			em.getTransaction().begin(); //Inicia transação no banco
			em.persist(obj); //PERSISTE objeto no banco
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro na Gravação : " + e);
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public List<O> listar(Class<O> objEntidade){
		List<O> retornoLista = new ArrayList<O>();
		try {
			em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Query sql = em.createQuery(" FROM " + objEntidade.getName().toString()); //Utilizando PQL não precisa do SELECT
			retornoLista = sql.getResultList();
		} catch (Exception e) {
			System.out.println("Erro ao consultar!");
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return retornoLista;
	}
	
	public List<O> consultaTarefa(Class<O> objEntidade, String sqlFrom){
		List<O> retornoLista = new ArrayList<O>();
		try {
			em = JPAUtil.getEntityManager();
			em.getTransaction().begin();
			Query sql = em.createQuery(" FROM " + objEntidade.getName().toString() + sqlFrom); //Utilizando PQL não precisa do SELECT
			retornoLista = sql.getResultList();
		} catch (Exception e) {
			System.out.println("Erro ao consultar!");
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
		return retornoLista;
	}
	
	public void remover(int id) {
		try {
			em = JPAUtil.getEntityManager();
			em.getTransaction().begin(); //Inicia transação no banco
			Query query = em.createNativeQuery("DELETE FROM TAREFA WHERE id = " + id);//remove objeto no banco
			query.executeUpdate();
//			em.remove(obj);
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao tentar remover ");
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public void atualizar(O obj) {
		try {
			em = JPAUtil.getEntityManager();
			em.getTransaction().begin(); //Inicia transação no banco
			em.merge(obj); //PERSISTE objeto no banco
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao tentar atualizar : " + e);
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	
	
}

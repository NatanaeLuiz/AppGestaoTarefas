package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Tarefa;

public class TarefaController {

	EntityManagerFactory emf;
	EntityManager em;
	
	public TarefaController() {
		emf = Persistence.createEntityManagerFactory("tarefa"); //Mesmo parametro informado na persistence.xml (name="tarefa")
		em = emf.createEntityManager();
	}
	
	public void salva(Tarefa _tarefa) {
		try {
			em.getTransaction().begin(); //Inicia transação no banco
			if (_tarefa.getId() == 0) {
				em.persist(_tarefa); //PERSISTE objeto no banco
			} else {
				em.merge(_tarefa); //MERGE   atualiza objeto no banco
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro na Gravação");
			em.getTransaction().rollback();
		} finally {
			emf.close();
		}
	}
	
	public void remove(Tarefa _tarefa) {
		try {
			em.getTransaction().begin(); //Inicia transação no banco
			Query query = em.createNativeQuery("DELETE tarefa FROM TAREFA WHERE id = " + _tarefa.getId());//remove objeto no banco
			query.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao tentar remover " + _tarefa.getTitulo());
			em.getTransaction().rollback();
		} finally {
			emf.close();
		}
	}
	
	public List<Tarefa> lista(){
		List<Tarefa> tarefas = new ArrayList<Tarefa>();
		try {
			em.getTransaction().begin();
			Query sql = em.createQuery("FROM Tarefa tarefa"); //Utilizando PQL não precisa do SELECT
			tarefas = sql.getResultList();
		} catch (Exception e) {
			System.out.println("Erro ao consultar!");
			em.getTransaction().rollback();
		} finally {
			emf.close();
		}
		return tarefas;
	}
	
	public Tarefa getTarefa(Integer id) {
		Tarefa tarefa = null;
		try {
			tarefa = em.find(Tarefa.class, id);
		} catch (Exception e) {
			System.out.println("Erro ao buscar tarefa por id " + id);
		} finally {
			emf.close();
		}
		return tarefa;
	}

}

package util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private static EntityManagerFactory emf;
	private static EntityManager em;
	
//	public JPAUtil() {
//		emf = Persistence.createEntityManagerFactory("tarefa"); //Mesmo parametro informado na persistence.xml (name="tarefa")
//		em = emf.createEntityManager();
//	}
	
	static
	{
		try
		{
			emf = Persistence.createEntityManagerFactory("tarefa");
		}
		catch (Exception e)
		{
			throw new ExceptionInInitializerError(e);
		}
	}
	
	public static EntityManager getEntityManager() {
		em = emf.createEntityManager();
		return em;
	}
	
}

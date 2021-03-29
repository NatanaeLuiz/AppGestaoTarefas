package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;

import dao.DaoGeneric;
import model.Tarefa;

@ViewScoped
@ManagedBean(name = "tarefaBean")
public class TarefaController{

	EntityManager em;
	private Tarefa tarefa = new Tarefa();
	private DaoGeneric<Tarefa> daoGeneric = new DaoGeneric<Tarefa>();
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	private List<String> prioridades;
	
	
	


	public void salva() {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date data = formato.parse("26/03/2021");
			tarefa.setData(data);
			if (tarefa.getId() == 0) {
				daoGeneric.salvar(tarefa);
				
			} else {
				daoGeneric.atualizar(tarefa);
			}
			lista();
			tarefa = new Tarefa();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	public void remove(Tarefa tarefa) {
		try {
			daoGeneric.remover(tarefa.getId());
			lista();
		} catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
		
	}
	
//	public void atualiza(Tarefa tarefa) {
//		daoGeneric.atualizar(tarefa);
//	}
	
	public void selecionaTarefa(Tarefa tarefaSelecionada) {
		//tarefa = new Tarefa();
//		tarefa.setId(tarefaSelecionada.getId());
//		tarefa.setTitulo(tarefaSelecionada.getTitulo());
//		tarefa.setDescricao(tarefaSelecionada.getDescricao());
//		tarefa.setPrioridade(tarefaSelecionada.getPrioridade());
//		tarefa.setResponsavel(tarefaSelecionada.getResponsavel());
//		tarefa.setData(tarefaSelecionada.getData());
//		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
//		try {
//			context.redirect("index.xhtml");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		tarefa = tarefaSelecionada;
	}
	
	@PostConstruct
	public void lista(){
		tarefas = daoGeneric.listar(Tarefa.class);
	}
	
	public Tarefa getTarefa() { return tarefa; }
	
	public void setTarefa(Tarefa tarefa) { this.tarefa = tarefa; }
	
	public DaoGeneric<Tarefa> getDaoGeneric() { return daoGeneric;	}
	
	public void setDaoGeneric(DaoGeneric<Tarefa> daoGeneric) { this.daoGeneric = daoGeneric; }
	
	public List<Tarefa> getTarefas() { return tarefas; }

	public void setTarefas(List<Tarefa> tarefas) { this.tarefas = tarefas; }

	public List<String> getPrioridades() {
		this.prioridades = new ArrayList<String>();
		prioridades.add("ALTA");
		prioridades.add("MEDIA");
		prioridades.add("BAIXA");
		return prioridades;
	}

	public void setPrioridades(List<String> prioridades) {
		this.prioridades = prioridades;
	}
	
	
	
//	
//	public Tarefa getTarefa(Integer id) {
//		Tarefa tarefa = null;
//		try {
//			tarefa = em.find(Tarefa.class, id);
//		} catch (Exception e) {
//			System.out.println("Erro ao buscar tarefa por id " + id);
//		} finally {
//			emf.close();
//		}
//		return tarefa;
//	}

}

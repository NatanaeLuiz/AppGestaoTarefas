package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import util.Util;
import dao.DaoGeneric;
import model.Tarefa;

@ViewScoped
@ManagedBean(name = "tarefaBean")
public class TarefaController{

	EntityManager em;
	private Tarefa tarefa = new Tarefa();
	private DaoGeneric<Tarefa> daoGeneric = new DaoGeneric<Tarefa>();
	private List<Tarefa> tarefas = new ArrayList<Tarefa>();
	private List<Tarefa> tarefasConsulta = new ArrayList<Tarefa>();
	private List<String> prioridades;
	private List<String> status;
	
	public TarefaController() {
		this.tarefa.setId(0);
		this.tarefa.setTitulo("");
		this.tarefa.setDescricao("");
		this.tarefa.setResponsavel("");
		this.tarefa.setPrioridade("");
		this.tarefa.setStatus("");
	}

	public void salva() {
		try {
			if (tarefa.getId() == 0) {
				tarefa.setStatus("ANDAMENTO");
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
	
	public void selecionaTarefa(Tarefa tarefaSelecionada) {
		tarefa = tarefaSelecionada;
	}
	
	public void concluiTarefa(Tarefa tarefaSelecionada) {
		tarefaSelecionada.setStatus("CONCLUIDA");
		daoGeneric.atualizar(tarefaSelecionada);
	}
	
	@PostConstruct
	public void consultaTarefa() {
		StringBuilder sqlFrom = new StringBuilder();
		sqlFrom.append(" WHERE 1 = 1");
		if (tarefa.getId() != 0)
			sqlFrom.append(" AND ID = " + tarefa.getId());
		if (!"".equals(tarefa.getTitulo()))
			sqlFrom.append(" AND TITULO LIKE '%" + tarefa.getTitulo() + "%'");
		if (!"".equals(tarefa.getResponsavel()))
			sqlFrom.append(" AND RESPONSAVEL = '" + tarefa.getResponsavel() + "'");
		if (!"null".equals(tarefa.getPrioridade()))
			sqlFrom.append(" AND PRIORIDADE = '" + tarefa.getPrioridade() + "'");
		if (!"null".equals(tarefa.getStatus()))
			sqlFrom.append(" AND STATUS = '" + tarefa.getStatus() + "'");
		

		tarefasConsulta = daoGeneric.consultaTarefa(Tarefa.class, sqlFrom.toString());
	}
	
	public void redirectConsultaTarefa(String enderecoUrl) {
		Util.redirecionarPagina(enderecoUrl);
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

	public List<Tarefa> getTarefasConsulta() { return tarefasConsulta; }

	public void setTarefasConsulta(List<Tarefa> tarefasConsulta) { this.tarefasConsulta = tarefasConsulta; }

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

	public List<String> getStatus() {
		this.status = new ArrayList<String>();
		status.add("ANDAMENTO");
		status.add("CONCLUIDA");
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	
}

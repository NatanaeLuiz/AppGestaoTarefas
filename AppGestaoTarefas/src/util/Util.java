package util;

import javax.faces.context.FacesContext;

public class Util {
	
	public static void redirecionarPagina(String pagina) {
        FacesContext context = FacesContext.getCurrentInstance();
        String url = context.getExternalContext().getRequestContextPath();
        try {
            context.getExternalContext().redirect(url + "/faces/" + pagina + ".xhtml");
        } catch (Exception ex) {
            System.out.println("Erro ao tentar redirecionar!");
        }
    }
	
}

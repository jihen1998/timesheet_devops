package tn.esprit.spring.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.services.IEmployeService;


@Controller
public class IControllerEmployeImpl  {
	@Autowired
	IEmployeService iemployeservice;
	
	public int ajouterContrat(Contrat contrat) {
		iemployeservice.ajouterContrat(contrat);
		return contrat.getReference();
	}
	
	
	public void deleteContratById(int contratId) {
		iemployeservice.deleteContratById(contratId);
	}

	

}

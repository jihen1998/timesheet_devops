package tn.esprit.spring.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.ContratDTO;
import tn.esprit.spring.services.IEmployeService;

@RestController
public class RestControlEmploye {
	@Autowired
	IEmployeService iemployeservice;
	// http://localhost:8081/SpringMVC/servlet/ajouterContrat
	@PostMapping("/ajouterContrat")
	@ResponseBody
	public int ajouterContrat(@RequestBody ContratDTO contrat) {
		Contrat persitentcontrat=new Contrat(contrat.getDateDebut(), contrat.getTypeContrat(), contrat.getSalaire(),contrat.getEmploye());
		iemployeservice.ajouterContrat(persitentcontrat);
		return persitentcontrat.getReference();
	}
	
	

    
 // URL : http://localhost:8081/SpringMVC/servlet/deleteContratById/2
    @DeleteMapping("/deleteContratById/{idcontrat}") 
	@ResponseBody
	public void deleteContratById(@PathVariable("idcontrat")int contratId) {
		iemployeservice.deleteContratById(contratId);
	}
	
}

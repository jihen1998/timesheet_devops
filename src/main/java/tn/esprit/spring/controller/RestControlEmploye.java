package tn.esprit.spring.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.ContratDTO;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.services.IEmployeService;

@RestController
public class RestControlEmploye {
	@Autowired
	IEmployeService iemployeservice;
	//SIWAR
	
	// http://localhost:8081/SpringMVC/servlet/ajouterEmployer
		//{"id":1,"nom":"kallel", "prenom":"khaled", "email":"Khaled.kallel@ssiiconsulting.tn", "isActif":true, "role":"INGENIEUR"}
		
		@PostMapping("/ajouterEmployer")
		@ResponseBody
		public Employe ajouterEmploye(@RequestBody Employe employe)
		{
			iemployeservice.ajouterEmploye(employe);
			return employe;
		}
		
		// Modifier email : http://localhost:8081/SpringMVC/servlet/modifyEmail/1/newemail
		@PutMapping(value = "/modifyEmail/{id}/{newemail}") 
		@ResponseBody
		public void mettreAjourEmailByEmployeId(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {
			iemployeservice.mettreAjourEmailByEmployeId(email, employeId);
			
		}

		   // URL : http://localhost:8081/SpringMVC/servlet/getEmployePrenomById/2
		   @GetMapping(value = "getEmployePrenomById/{idemp}")
		   @ResponseBody
		   public String getEmployePrenomById(@PathVariable("idemp")int employeId) {
				return iemployeservice.getEmployePrenomById(employeId);
			}

		    // URL : http://localhost:8081/SpringMVC/servlet/deleteEmployeById/1
		    @DeleteMapping("/deleteEmployeById/{idemp}") 
			@ResponseBody 
			public void deleteEmployeById(@PathVariable("idemp")int employeId) {
				iemployeservice.deleteEmployeById(employeId);
				
			}
		
		    // Modifier email : http://localhost:8081/SpringMVC/servlet/mettreAjourEmailByEmployeIdJPQL/2/newemail
		 	@PutMapping(value = "/mettreAjourEmailByEmployeIdJPQL/{id}/{newemail}") 
		 	@ResponseBody
			public void mettreAjourEmailByEmployeIdJPQL(@PathVariable("newemail") String email, @PathVariable("id") int employeId) {	
			iemployeservice.mettreAjourEmailByEmployeIdJPQL(email, employeId);
				
			}
	
		 // URL : http://localhost:8081/SpringMVC/servlet/getNombreEmployeJPQL
		    @GetMapping(value = "getNombreEmployeJPQL")
		    @ResponseBody
			public int getNombreEmployeJPQL() {
				
				return iemployeservice.getNombreEmployeJPQL();
			}

		    // URL : http://localhost:8081/SpringMVC/servlet/getAllEmployeNamesJPQL
		    @GetMapping(value = "getAllEmployeNamesJPQL")
		    @ResponseBody
			public List<String> getAllEmployeNamesJPQL() {
				
				return iemployeservice.getAllEmployeNamesJPQL();
			}
		    // URL : http://localhost:8081/SpringMVC/servlet/getSalaireByEmployeIdJPQL/2
		    @GetMapping(value = "getSalaireByEmployeIdJPQL/{idemp}")
		    @ResponseBody
			public float getSalaireByEmployeIdJPQL(@PathVariable("idemp")int employeId) {
				return iemployeservice.getSalaireByEmployeIdJPQL(employeId);
			}
		    // URL : http://localhost:8081/SpringMVC/servlet/getAllEmployes
			@GetMapping(value = "/getAllEmployes")
		    @ResponseBody
			public List<Employe> getAllEmployes() {
				
				return iemployeservice.getAllEmployes();
			}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//JIHEN
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
 // http://localhost:8081/SpringMVC/servlet/affecterContratAEmploye/6/1
    @PutMapping(value = "/affecterContratAEmploye/{idcontrat}/{idemp}") 
 	public void affecterContratAEmploye(@PathVariable("idcontrat")int contratId, @PathVariable("idemp")int employeId)
 	{
 		iemployeservice.affecterContratAEmploye(contratId, employeId);
 	}
    // URL : http://localhost:8081/SpringMVC/servlet/deleteAllContratJPQL
    @DeleteMapping("/deleteAllContratJPQL") 
	@ResponseBody
	public void deleteAllContratJPQL() {
		iemployeservice.deleteAllContratJPQL();
		
	}

 
}

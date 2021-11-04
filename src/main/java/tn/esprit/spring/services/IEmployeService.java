package tn.esprit.spring.services;


import java.util.List;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;



public interface IEmployeService {
    //SIWAR
	public int ajouterEmploye(Employe employe);
	public void mettreAjourEmailByEmployeId(String email, int employeId);
	public String getEmployePrenomById(int employeId);
	public void deleteEmployeById(int employeId);
	public int getNombreEmployeJPQL();
	public List<String> getAllEmployeNamesJPQL();
	public float getSalaireByEmployeIdJPQL(int employeId);
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId);
	public List<Employe> getAllEmployes();
	public Integer addOrUpdateEmploye(Employe employe);
	public Employe getEmployeById(int id);
	public void deleteEmploye(int id);
	

	

	
	//Jihen
	public void deleteContratById(int contratId);
	public int ajouterContrat(Contrat contrat);
	public void deleteAllContratJPQL();
	public void affecterContratAEmploye(int contratId, int employeId);
}

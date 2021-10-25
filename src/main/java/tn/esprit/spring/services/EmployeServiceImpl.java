package tn.esprit.spring.services;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;


import tn.esprit.spring.repository.ContratRepository;

import tn.esprit.spring.repository.EmployeRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	
	

	@Autowired
	EmployeRepository employeRepository;
	
	@Autowired
	ContratRepository contratRepoistory;
	
	
	
	//SIWAR
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		employe.setEmail(email);
		employeRepository.save(employe);

	}
    public String getEmployePrenomById(int employeId) {
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}
	public void deleteEmployeById(int employeId)
	{
		Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	}


	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}
	

	
	
	
	
	//JIHEN 
		public void deleteAllContratJPQL() {
	         employeRepository.deleteAllContratJPQL();
		}
		public void deleteContratById(int contratId) {
			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			contratRepoistory.delete(contratManagedEntity);

		}
		public void affecterContratAEmploye(int contratId, int employeId) {
			Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
			Employe employeManagedEntity = employeRepository.findById(employeId).get();

			contratManagedEntity.setEmploye(employeManagedEntity);
			contratRepoistory.save(contratManagedEntity);
			
		}


		public int ajouterContrat(Contrat contrat) {
			contratRepoistory.save(contrat);
			return contrat.getReference();
		}

}

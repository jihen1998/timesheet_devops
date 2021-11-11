package tn.esprit.spring.departementtest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import java.util.Optional;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;



import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import tn.esprit.spring.employetest.ContratTest;
import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartementTest {
	@MockBean
	DepartementRepository departementRepository;
	@MockBean
    EmployeRepository employeRepository;
	private static final Logger logger = LogManager.getLogger(ContratTest.class);
	@Autowired
	IEntrepriseService iEntrepriseService;
	 @Autowired
	IEmployeService iemployeService;
	

	@Test
	public void testAjouterDepartement()throws ParseException {
		logger.info("debut test ajout");
		Departement d1 = new Departement();
		d1.setId(1);
		d1.setName("dep");
		   
		logger.trace("affichage id DEPARTEMENT à ajouter: " + d1.toString());
		when(departementRepository.save(d1)).thenReturn(d1);	
		logger.trace("affichage id DEPARTEMENT existe dans repo mock: "+ iEntrepriseService.ajouterDepartement(d1));
		assertEquals(d1.getId(), iEntrepriseService.ajouterDepartement(d1));
		logger.info(" Termine ajout DEPARTEMENT");	
	}

	
	@Test
	public void testDeleteDepartementById() {
		logger.info("debut test suppression par id ");	
		   
		Departement d1 = new Departement();
		d1.setId(2);
		d1.setName("dep");
		    
		Optional<Departement> optionalEntityType = Optional.of(d1);
		when(departementRepository.findById(1)).thenReturn(optionalEntityType);
		iEntrepriseService.deleteDepartementById(d1.getId());
		verify(departementRepository,times(1)).delete(d1);
		logger.info("fin test suppression entreprise avec succées");	
	}
	
	@Test
	public void testAffecterEmployeADepartement() {
		logger.info("debut l'affectation");
		Departement d1 = new Departement();
		d1.setId(3);
		d1.setName("dep");
		logger.trace("affichage id DEPARTEMENT à ajouter: " + d1.toString());
		   when(departementRepository.findById(1)).thenReturn(Optional.of(d1));
		   Employe e = new Employe();
		   e.setActif(true);
		   e.setId(2);
		   e.setEmail("firas@gmail.com");
		   e.setNom("firas");
		   e.setPrenom("mansour");
		   when(employeRepository.findById(2)).thenReturn(Optional.of(e));
		   when(departementRepository.save(d1)).thenReturn(d1);	
		  
	}

}

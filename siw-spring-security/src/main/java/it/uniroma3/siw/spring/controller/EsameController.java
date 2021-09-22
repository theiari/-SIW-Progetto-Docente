package it.uniroma3.siw.spring.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.controller.validator.EsameValidator;
import it.uniroma3.siw.spring.model.Credentials;
import it.uniroma3.siw.spring.model.Esame;
import it.uniroma3.siw.spring.model.Indicatore;
import it.uniroma3.siw.spring.model.Medico;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.model.User;
import it.uniroma3.siw.spring.service.EsameService;
import it.uniroma3.siw.spring.service.IndicatoreService;
import it.uniroma3.siw.spring.service.MedicoService;
import it.uniroma3.siw.spring.service.TipologiaEsameService;
import it.uniroma3.siw.spring.service.UserService;

@Controller
public class EsameController {
	@Autowired
	EsameValidator esameValidator;
	@Autowired
	EsameService esameService;
	@Autowired
	IndicatoreService indicatoreService;
	@Autowired
	MedicoService medicoService;
	@Autowired
	TipologiaEsameService tipologiaEsameService;
	@Autowired
	UserService userService;
	
	
	//@RequestMapping(value = {"/esami"}, method = RequestMethod.GET) la chiamata esami, e le altre chiamate delle voci di menu, vengono gestite dal AuthController
	public String esame(Model model) {
		return "esame/esame";
	}
	
	
	@RequestMapping(value = {"/admin/esameForm"}, method = RequestMethod.GET)
	public String Addesame(Model model ) {
		UserDetails nome= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<User> pazienti = new ArrayList<User>();
		pazienti.addAll(this.userService.getAllUsers());
		for (User u : pazienti) {
			if (u.getCognome() == nome.toString())
				pazienti.remove(u);
		}
		model.addAttribute("esame", new Esame());
		model.addAttribute("pazienti", pazienti);
		model.addAttribute("medici", this.medicoService.tutti());
		model.addAttribute("tipologiaEsami", this.tipologiaEsameService.tutti());
		   
	           
	        
	        return "esame/esameForm";
	    }
	
	 @RequestMapping(value={"/admin/selezionaIndicatore"}, method = RequestMethod.GET) //1 seleziona l'indicatore da aggiungere alla chiave della mappa risultato
		public String index(Model model) {
		 model.addAttribute("indicatori", this.indicatoreService.tutti());
				return "esame/SelezioneIndicatore";
		}
	 
	 @RequestMapping(value = "/admin/indicatoreScelto/{id}", method = RequestMethod.GET) //2 inserisce l'indicatore selezionato alla mappa e porta al form per inserire la chiave stringa della mappa e le date
	    public String CaricaMappa(@PathVariable("id") Long id, Model model, Esame esame) {
		 	
	    	model.addAttribute("indicatore", this.indicatoreService.indicatorePerId(id)); //aggiunge al model l'indicatore con l'id specificato
	    	//model.addAttribute("prerequisiti", this.tipologiaesamiService.tipologiaesamiPerId(id).getPrerequisito());
	    	return "esame/AddValore";
	    }
	 
	 
	 @RequestMapping(value={"/admin/selezionaMedico"}, method = RequestMethod.GET)
		public String SelMedico(Model model) {
		 model.addAttribute("medici", this.medicoService.tutti());
				return "esame/SelezionaMedico";
		}
	 
	 @RequestMapping(value = "/esameForm", method = RequestMethod.POST)
	    public String newEsame(Long taskTipologiaEsame, Long taskPaziente, Long taskMedico,  @ModelAttribute("esame") Esame esame, 
	    									Model model, BindingResult bindingResult) {
	    	 this.esameValidator.validate(esame, bindingResult);
	    	    if (!bindingResult.hasErrors()) {
	    	  
	    	    	Medico medico = this.medicoService.medicoPerId(taskMedico);
	    	    	User user = this.userService.getUser(taskPaziente);		
	    	    	TipologiaEsame tipologiaesame = this.tipologiaEsameService.tipologiaesamiPerId(taskTipologiaEsame);
	    	    	
	    	    	esame.setMedico(medico);
	    	    	esame.setUser(user);
	    	    	esame.setTipologiaesami(tipologiaesame);
	    	    	
	    	    	medico.getEsami().add(esame);
	    	    	user.getEsami().add(esame);
    	    	    tipologiaesame.getEsami().add(esame);
    	    	    this.esameService.inserisci(esame);
	    	    	
	    	    	this.tipologiaEsameService.inserisci(tipologiaesame);
	    	    	this.userService.saveUser(user);
	    	    	this.medicoService.inserisci(medico);
	    	    	
	    	        
	    	        	
	    	            model.addAttribute("esame", this.esameService.tutti());
	    	            return "admin/home";
	    	        }
	    	        return "Esame/esameForm";
	    }
	
	 @RequestMapping(value = "/Esami", method = RequestMethod.GET)
	    public String getPrerequisiti(Model model) {
	    		model.addAttribute("esami", this.esameService.tutti());
	    		return "esame/esami";
	    }
	 
	 @RequestMapping(value={"/admin/risultatiForm"}, method = RequestMethod.GET)
		public String scegliEsame(Model model) {
		 model.addAttribute("esami", this.esameService.tutti());
				return "esame/risultatiForm";
		}
	 
	 
	 
	 @RequestMapping(value={"/admin/esameScelto/{id}"}, method = RequestMethod.GET)
		public String risultatiForm(@PathVariable("id") Long id, Model model, Esame esame) {
		
		 List<Indicatore> indicatori = this.esameService.esamePerId(id).getTipologiaesami().getIndicatori();
		 Map<Indicatore,String> risultati = new HashMap<Indicatore,String>();
		 for (Indicatore indicatore: indicatori) {
			 risultati.put(indicatore, "");
		 }
		 this.esameService.esamePerId(id).setRisultati(risultati);
		 model.addAttribute("esame", this.esameService.esamePerId(id));
		 model.addAttribute("indicatori", indicatori);
		 model.addAttribute("risultati",risultati);
		 
				return "esame/aggiungiValori";
		}
	 
	 
	 @RequestMapping(value = "/esame/{id}", method = RequestMethod.GET)
	    public String getmedico(@PathVariable("id") Long id, Model model) {
	    	model.addAttribute("esame", this.esameService.esamePerId(id));
	    	return "esame/esame";
	    }
	 
	
	 //!!!!!!!!!!!!!!!!!!!TO DO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 @RequestMapping(value = " /esamiDiPaziente", method = RequestMethod.GET)
	    public String getEsamiPaziente(Model model) {
		 UserDetails nome= (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); //nome dell'utente loggato
		 	
		 
	    	model.addAttribute("paziente", nome);
	    	model.addAttribute("esami", this.esameService.tutti());
	    	return "esame/esamiDiPaziente";
	    }
	 
	 
	 
	/*@RequestMapping(value = "/confermaEsame/{tid}/{pid}", method = RequestMethod.GET)
    public String getPrere(@PathVariable("pid") Long pid,@PathVariable("tid") Long tid, Model model) {
    	Esame esame= this.esameService.esamePerId(tid);
    	Indicatore indicatore= this.indicatoreService.indicatorePerId(pid);
    	esame.getdataSostenuto().add(indicatore);
    	tipologiaesamiService.inserisci(tipologiaesame);
    	return "admin/home";
    }*/
	}

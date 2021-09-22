package it.uniroma3.siw.spring.controller;

import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import it.uniroma3.siw.spring.controller.validator.MedicoValidator;
import it.uniroma3.siw.spring.model.Esame;
import it.uniroma3.siw.spring.model.Medico;
import it.uniroma3.siw.spring.service.EsameService;
import it.uniroma3.siw.spring.service.MedicoService;


@Controller
public class MedicoController {
	
	@Autowired
	private MedicoService medicoService;
	
    @Autowired
    private MedicoValidator medicoValidator;
    @Autowired
    private EsameService esameService;
        
    @RequestMapping(value="/admin/medicoForm", method = RequestMethod.GET)
    public String addProdotto(Model model) {
    	model.addAttribute("medico", new Medico());
        return "Medico/MedicoForm";
    }

    @RequestMapping(value = "/medico/{id}", method = RequestMethod.GET)
    public String getmedico(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("medico", this.medicoService.medicoPerId(id));
    	return "Medico/Medico";
    }

    @RequestMapping(value = "/medicoForm", method = RequestMethod.POST)
    public String addProdotto(@ModelAttribute("medico")  Medico medico, Model model, BindingResult bindingResult) {
    	this.medicoValidator.validate(medico, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.medicoService.inserisci(medico);
            model.addAttribute("medico", this.medicoService.tutti());
            return "admin/home";
        }
        return "Medico/MedicoForm";
    }
    
    @RequestMapping(value = "/Medici", method = RequestMethod.GET)
    public String getPrerequisiti(Model model) {
    		model.addAttribute("medici", this.medicoService.tutti());
    		return "Medico/Medici";
    }
    
    @RequestMapping(value = "/admin/SelezionaMedico", method = RequestMethod.GET)
    public String SelezionaMedico(Model model) {
    		model.addAttribute("medici", this.medicoService.tutti());
    		return "Medico/SelezionaMedico";
    }
    
    
    
    @RequestMapping(value = "/admin/medicoSelezionato/{id}", method = RequestMethod.GET) //mostra la lista di tutti gli esami sostenuti da un medico specificato precedentemente
    public String AssociaEsameAlmedico(@PathVariable("id") Long id, Model model) {
    	List<Esame> esamiMedico = this.medicoService.medicoPerId(id).getEsami(); //lista di tutti gli esami sostenuti dal medico
    
    	model.addAttribute("esami",esamiMedico);
    	
    	return "Medico/esamiMedico";
    }
    
    @RequestMapping(value = "/admin/medicoSelezionato/esameSceltoMedico/{idMedico}/{idEsame}", method = RequestMethod.GET)
    public String AssociaEsameAlmedico(@PathVariable("idMedico") Long idMedico,@PathVariable("idEsame") Long idEsame, Model model) {
    	Medico medico = this.medicoService.medicoPerId(idMedico);
    	Esame esame = this.esameService.esamePerId(idEsame);
    	medico.getEsami().add(esame);
    	this.medicoService.inserisci(medico);
    	return "admin/home";
    }
    
    
    
}

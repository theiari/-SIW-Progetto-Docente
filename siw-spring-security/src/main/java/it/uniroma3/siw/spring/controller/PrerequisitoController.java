package it.uniroma3.siw.spring.controller;

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

import it.uniroma3.siw.spring.controller.validator.PrerequisitoValidator;
import it.uniroma3.siw.spring.controller.validator.ProdottoValidator;
import it.uniroma3.siw.spring.model.Prerequisito;
import it.uniroma3.siw.spring.model.TipologiaEsame;
import it.uniroma3.siw.spring.service.PrerequisitoService;
import it.uniroma3.siw.spring.service.ProdottoService;

@Controller
public class PrerequisitoController {
	
	@Autowired
	private PrerequisitoService prerequisitoService;
	
    @Autowired
    private PrerequisitoValidator prerequisitoValidator;
        
    @RequestMapping(value="/admin/prerequisitoForm", method = RequestMethod.GET)
    public String addProdotto(Model model) {
    	model.addAttribute("prerequisito", new Prerequisito());
        return "Prerequisiti/prerequisitoform";
    }

    @RequestMapping(value = "/prerequisito/{id}", method = RequestMethod.GET)
    public String getPrerequisito(@PathVariable("id") Long id, Model model) {
    	model.addAttribute("prerequisito", this.prerequisitoService.prerequisitoPerId(id));
    	return "Prerequisiti/Prerequisito";
    }

    @RequestMapping(value = "/prerequisitoForm", method = RequestMethod.POST)
    public String addProdotto(@ModelAttribute("prerequisito") Prerequisito prerequisito, 
    									Model model, BindingResult bindingResult) {
    	this.prerequisitoValidator.validate(prerequisito, bindingResult);
        if (!bindingResult.hasErrors()) {
        	this.prerequisitoService.inserisci(prerequisito);
            model.addAttribute("prerequisito", this.prerequisitoService.tutti());
            return "admin/home";
        }
        return "Prerequisiti/prerequisitoform";
    }
    
    @RequestMapping(value = "/prerequisiti", method = RequestMethod.GET)
    public String getPrerequisiti(Model model) {
    		model.addAttribute("prerequisiti", this.prerequisitoService.tutti());
    		return "Prerequisiti/Prerequisiti";
    }
}

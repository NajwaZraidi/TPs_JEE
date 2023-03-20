package ma.enset.web;

import lombok.AllArgsConstructor;
import ma.enset.entities.Patient;
import ma.enset.repositories.PatientRespository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//Pour une application nous avons besoin d'un Controller
@Controller
//Injection des dependances via Constructor
@AllArgsConstructor
public class PatientController {
    private PatientRespository patientRespository;

//acces à la methode patient
    @GetMapping(path="/index")
    // la methode patient return un vue
    //Module de spring MVC
    public String patient(Model model,
                          //pagination
                           @RequestParam(name="page",defaultValue = "0") int page,
                          //size de page
                          @RequestParam(name="size",defaultValue = "5") int size,
                          @RequestParam(name="Recherche",defaultValue = "") String Recherche

    ){
        Page<Patient> pagePatients=patientRespository.findByNomContains(Recherche,PageRequest.of(page, size));
        // stocker dans le modul
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("Recherche",Recherche);
        return "patients";
   }
@PostMapping("/delete")
public  String delete(long id,String Recherche,int page ){
        patientRespository.deleteById(id);
        return "redirect:/index?page="+page+"&Recherche="+Recherche;
}
@GetMapping("/")
    public  String home(){
        return "redirect:/index";
    }

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> patientList(){
        return  patientRespository.findAll();
    }
}

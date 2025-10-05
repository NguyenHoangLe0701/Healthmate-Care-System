package om.healthmate.javaproject.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InfertilityController {
    @GetMapping("/hiemmuon")
    public String showHiemMuonPage() {
        // Trả về file templates/pages/hiemmuon.html
        return "pages/hiemmuon";
    }
}

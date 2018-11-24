package donkeybug.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/index")
    public String stop(Model model) {
        return "index";
    }

    @GetMapping("/forward")
    public String forward(Model model) {
        return "index";
    }

    @GetMapping("/left")
    public String left(Model model) {
        return "index";
    }

    @GetMapping("/right")
    public String right(Model model) {
        return "index";
    }

    @GetMapping("/back")
    public String back(Model model) {
        return "index";
    }
}

package donkeybug.controller;

import donkeybug.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    @Qualifier("piCarService")
    CarService carService;

    @GetMapping("/index")
    public String stop(Model model) {
        carService.stop();
        return "index";
    }

    @GetMapping("/forward")
    public String forward(Model model) {
        carService.goForward();
        return "index";
    }

    @GetMapping("/left")
    public String left(Model model) {
        carService.turnLeft();
        return "index";
    }

    @GetMapping("/right")
    public String right(Model model) {
        carService.turnRight();
        return "index";
    }

    @GetMapping("/back")
    public String back(Model model) {
        carService.goBackward();
        return "index";
    }
}

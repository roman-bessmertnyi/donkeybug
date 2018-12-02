package donkeybug.controller;

import donkeybug.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    @Qualifier("piCarService")
    CarService carService;

    @GetMapping("/index")
    public String index(Model model) {
        carService.stop();
        return "index";
    }

    @MessageMapping("/command")
    public void command(String command) {
        switch (command) {
            case "stop":
                carService.stop();
                break;
            case "forward":
                carService.goForward();
                break;
            case "left":
                carService.turnLeft();
                break;
            case "right":
                carService.turnRight();
                break;
            case "back":
                carService.goBackward();
                break;
            default:
                carService.stop();
        }
    }
}

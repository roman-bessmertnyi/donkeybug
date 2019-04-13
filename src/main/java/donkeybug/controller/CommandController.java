package donkeybug.controller;

import donkeybug.service.car.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CommandController {
	@Autowired
	private CarService carService;

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

package btl.web;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import btl.Car;

@Controller
@RequestMapping("/car") 
public class CarController {
	private RestTemplate rest = new RestTemplate();
	
	@ModelAttribute
	public void addIngredientsToModel(Model model) {
		
	}
	
	@GetMapping
	public String showInformationForm(Model model) {
		List<Car> car = Arrays.asList(rest.getForObject("http://localhost:8080/car/recent", Car[].class));
		model.addAttribute("keyword", new String());
		model.addAttribute("cars", car);
		return "findAll";
	}
	
	@GetMapping("/find")
	public String showSearchForm(@Param("keyword") String keyword ,Model model) {
		if(keyword.trim().equals("")) return "redirect:/car";
		List<Car> car = Arrays.asList(rest.getForObject("http://localhost:8080/car/find/{brandname}", Car[].class, keyword));
		model.addAttribute("keyword", new String(keyword));
		model.addAttribute("cars", car);
		return "findAll";
	}
	
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("car", new Car());
		return "add";
	}
	
	@PostMapping("/add")
	public String addForm(@Valid Car car, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "add";
		} 
		rest.postForObject("http://localhost:8080/car", car, Car.class);
		return "redirect:/car";
	}
	
	@GetMapping("/edit")
	public String showEditForm(@RequestParam("id") Long id,Model model) {
		Car car = rest.getForObject("http://localhost:8080/car/{id}", Car.class, id);
		model.addAttribute("car", car);
		return "edit";
	}
	
	@GetMapping("/edit/save")
	public String editForm(@Valid Car car, Errors errors,Model model) {
		if (errors.hasErrors()) {
			return "edit";
		} 
		rest.put("http://localhost:8080/car/{id}", car, car.getId());
		return "redirect:/car";
	}
	
	@GetMapping("/delete")
	public String showDeleteForm(@RequestParam("id") Long id,Model model) {
		rest.delete("http://localhost:8080/car/{id}", id);
		return "redirect:/car";
	}
}

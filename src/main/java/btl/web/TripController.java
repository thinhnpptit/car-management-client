package btl.web;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import btl.Car;
import btl.Employee;
import btl.Pair;
import btl.Trip;

@Controller
@RequestMapping("/trip") 
public class TripController {
	private RestTemplate rest = new RestTemplate();
	String baseURL ="http://localhost:8080/trip";
	@ModelAttribute
	public void addTripToModel(Model model) {
		
	}
	
	@GetMapping
	public String showInfoForm(Model model) {
		List<Trip> trips = Arrays.asList(rest.getForObject(baseURL+"/recent", Trip[].class));
		model.addAttribute("keyword", new String());
		model.addAttribute("trips", trips);
		return "trip/findAll";
	}
	
	@GetMapping("/find")
	public String showSearchForm(@Param("keyword") Long keyword ,Model model) {
		if(keyword == null) return "redirect:/trip";
		List<Trip> trip = Arrays.asList(rest.getForObject("http://localhost:8080/trip/find/{route_id}", Trip[].class, keyword));
		model.addAttribute("keyword", keyword);
		model.addAttribute("trips", trip);
		return "trip/findAll";
	}
	
	@GetMapping("/add")
	public String showAddForm(Model model) {
		model.addAttribute("trip", new Trip());
		return "trip/addTrip";
	}
	
	@PostMapping("/add")
	public String addForm(@Valid Trip trip, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "trip/addTrip";
		} 
		rest.postForObject("http://localhost:8080/trip", trip, Trip.class);
		return "redirect:/trip";
	}
	
	@GetMapping("/edit")
	public String showEditForm(@RequestParam("id") Long id,Model model) {
		Trip trip = rest.getForObject("http://localhost:8080/trip/{id}", Trip.class, id);
		model.addAttribute("trip", trip);
		return "trip/editTrip";
	}
	
	@GetMapping("/edit/save")
	public String editForm(@Valid Trip trip, Errors errors,Model model) {
		if (errors.hasErrors()) {
			return "trip/editTrip";
		} 
		rest.put("http://localhost:8080/trip/{id}", trip, trip.getId());
		return "redirect:/trip";
	}
	
	@GetMapping("/delete")
	public String showDeleteForm(@RequestParam("id") Long id,Model model) {
		rest.delete("http://localhost:8080/trip/{id}", id);
		return "redirect:/trip";
	}
	
//	@GetMapping("/luong")
//	public String showSalaryForm(Model model) {
//		List<Employee> em = Arrays.asList(rest.getForObject("http://localhost:8080/employee/recent", Employee[].class));
//		ArrayList<Pair<Employee, Long> > pair = new ArrayList<>();
//		for(Employee employee : em) {
//			List<Trip> trip = Arrays.asList(rest.getForObject("http://localhost:8080/trip/drive/{id}", Trip[].class, employee.getId()));
//			long luong = 0;
//			for(Trip t: trip) {
//				if(t.getDriver().getId() == employee.getId()) {
//					int level = t.getRoute().getComplicatedlevel();
//					if(level==1) luong += 300000;
//					else if(level==2) luong+=200000;
//					else luong+=100000;
//				}else {
//					int level = t.getRoute().getComplicatedlevel();
//					if(level==1) luong += 150000;
//					else if(level==2) luong+=100000;
//					else luong+=50000;
//				}
//			}
//			pair.add( new Pair<Employee, Long>(employee, luong));
//		}
//		model.addAttribute("pairs", pair );
//		return "tk";
//	}
	
	@GetMapping("/car")
	public String showCarForm(Model model) {
		List<Car> em  = Arrays.asList(rest.getForObject("http://localhost:8080/car/recent", Car[].class));
		ArrayList<Pair<Car, Float> > pair = new ArrayList<>();
		for(Car car : em) {
			List<Trip> trip =  Arrays.asList(rest.getForObject("http://localhost:8080/trip/car/{id}", Trip[].class, car.getId()));
			float doanhthu = 0;
			for(Trip t : trip) {
				doanhthu += t.getPrice() * t.getNumberofpassengers();
			}
			pair.add( new Pair<Car, Float>(car, doanhthu));
		}
		model.addAttribute("pairs", pair);
		return "tkdoanhthu";
	}
	
}

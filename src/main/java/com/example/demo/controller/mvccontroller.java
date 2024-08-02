package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.models.mvc;
import com.example.demo.repository.mvcrepository;
import jakarta.servlet.http.HttpSession;


@Controller //when data comes from frontend
public class mvccontroller {
	@Autowired // used for connection b/w two layers
	mvcrepository repo;
	
	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
	
	@GetMapping("/")
	public String home(Model m) {
		List<mvc> li = (List<mvc>) repo.findAll();
		m.addAttribute("add-products", li);
		return "home.html";
		
	}
	
	@GetMapping("/getbyid/{id}")
	public String getbyid(@PathVariable(value="id") int id,Model m) {
		Optional<mvc> c = repo.findById(id);
		mvc v = c.get();
		m.addAttribute("products",v);
		return "edit";
		
	}
	
	//httpsession is used for get the popup's in frontend
	@PostMapping("/save_products")
	public String insert(@ModelAttribute mvc m,HttpSession hs) {
		
		repo.save(m);
		hs.setAttribute("message","successfully added");
		return "redirect:/loadform";
		
	}
	
	@PutMapping("/update")
	public String update(@ModelAttribute mvc m , HttpSession hs ) {
		
		repo.save(m);
		hs.setAttribute("message","successfully updated");
		return "redirect:/edit";	
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable(value = "id") int id,HttpSession hs) {
		repo.deleteById(id);
		hs.setAttribute("message","successfully deleted");
		return "redirect:/";
		
	}
	
	@GetMapping("/loadform")
	public String loadform() {
		return "add";
	}
	
}

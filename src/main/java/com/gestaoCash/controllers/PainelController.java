package com.gestaoCash.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gestaoCash.model.Course;
import com.gestaoCash.services.CourseService;
import com.gestaoCash.utils.DataUserAuth;

@Controller
@RequestMapping("/painel-controle")
public class PainelController {

  @Autowired
  private CourseService courseService;

  DataUserAuth data = new DataUserAuth();

  @GetMapping
  public String showPainel(Model model) {
    model.addAttribute("user", data.DataUser());

    return "/painel/painel-controle";
  }

  @GetMapping("/curso")
  public String showCourse(Model model) {
    var courses = this.courseService.findAllCourse();
    model.addAttribute("courses", courses);
    model.addAttribute("course", new Course());
    model.addAttribute("user", data.DataUser());

    return "/curso/cursos";
  }

  @GetMapping("/curso/novo")
  public String showForm(Model model) {
    model.addAttribute("newCourse", new Course());
    model.addAttribute("user", data.DataUser());

    return "/curso/add-curso";
  }

}
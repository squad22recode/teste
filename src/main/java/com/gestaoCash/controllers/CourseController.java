package com.gestaoCash.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gestaoCash.model.Course;
import com.gestaoCash.model.Users;
import com.gestaoCash.services.CourseService;
import com.gestaoCash.services.UserService;
import com.gestaoCash.utils.DataUserAuth;

@Controller
@RequestMapping("/usuario/area-cliente")
public class CourseController {

  @Autowired
  private CourseService courseService;

  @Autowired
  private UserService userService;

  DataUserAuth data = new DataUserAuth();

  @GetMapping("/area-conhecimento")
  public ModelAndView listAllCourses() {
	  ModelAndView view = new ModelAndView("/usuario/area-conhecimento");
    var courses = this.courseService.findAllCourse();
    
    Long id = data.DataUser().getId();
    Users user = userService.findUserById(id);
	view.addObject("user", user);
    view.addObject("cursos", courses);
    view.addObject("favorites", user.getFavoriteCourses());
    view.addObject("courses", this.courseService.findAllCourse());
    
    return view;
  }

  // @GetMapping("/novo")
  // public String showForm(Model model) {
  // var course = new Course();
  // model.addAttribute("curso", course);

  // return "";
  // }

  @PostMapping("/novo")
  public String saveCourse(@ModelAttribute("newCourse") Course course) {
    this.courseService.saveCourse(course);
    return "redirect:/painel-controle/curso";
  }

  // metodo para salvar cursos favoritos do usuario logado
  @PostMapping("/salvar")
  public String saveCourse(@RequestParam("name") String name, @RequestParam("conclusion") int conclusion,
      @RequestParam("description") String description, @RequestParam("url") String url,
      @RequestParam("duration") int duration) {

    var existsCourse = this.courseService.findCourseByName(name);

    var course = new Course();
    course.setNomeCurso(name);
    course.setConclusao(conclusion);
    course.setDescricao(description);
    course.setDuracao(duration);
    course.setUrl(url);

    var user = this.userService.findUserById(data.DataUser().getId());

    var courses = user.getFavoriteCourses();
    courses.add(course);

    user.setFavoriteCourses(courses);

    this.userService.saveUser(user);

    return "redirect:/usuario/area-cliente";
  }

  @GetMapping("area-conhecimento/editar/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    var course = this.courseService.findCourseById(id);
    model.addAttribute("course", course);

    return "/curso/edit";
  }

  @PostMapping("/editar/{id}")
  public String updateCourse(@ModelAttribute("course") Course updatedCourse, @PathVariable Long id) {
    this.courseService.updateCourseById(id, updatedCourse);

    return "redirect:/painel-controle/curso";
  }

  @GetMapping("area-conhecimento/delete/{id}")
  public String deleteCourse(@PathVariable Long id) {
    this.courseService.deleteCourseById(id);

    return "redirect:/painel-controle/curso";
  }

  // deletar cursos favoritos do usuario logado
  @GetMapping("area-conhecimento/excluir/{id}")
  public String deleteFavoriteCourse(@PathVariable Long id) {
    var user = this.userService.findUserById(data.DataUser().getId());
    var courses = user.getFavoriteCourses();

    var course = this.courseService.findCourseById(id);
    courses.remove(course);

    this.courseService.deleteCourseById(id);

    this.userService.saveUser(user);

    return "redirect:/usuario/area-cliente";
  }
}

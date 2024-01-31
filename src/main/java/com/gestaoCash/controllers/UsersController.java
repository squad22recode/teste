package com.gestaoCash.controllers;

import java.awt.Image;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.ImageIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestaoCash.enums.CategoryEnum;
import com.gestaoCash.enums.MonthEnum;
import com.gestaoCash.enums.StateEnum;
import com.gestaoCash.model.Address;
import com.gestaoCash.model.Client;
import com.gestaoCash.model.Company;
import com.gestaoCash.model.Course;
import com.gestaoCash.model.Expense;
import com.gestaoCash.model.Product;
import com.gestaoCash.model.Revenue;
import com.gestaoCash.model.Role;
import com.gestaoCash.model.Users;
import com.gestaoCash.repositories.AddressRepository;
import com.gestaoCash.repositories.ExpenseRespository;
import com.gestaoCash.repositories.ProductRepository;
import com.gestaoCash.repositories.RevenueRepository;
import com.gestaoCash.repositories.RoleRepository;
import com.gestaoCash.repositories.UserRepository;
import com.gestaoCash.services.ClientService;
import com.gestaoCash.services.CourseService;
import com.gestaoCash.services.ExpenseService;
import com.gestaoCash.services.ProductService;
import com.gestaoCash.services.RevenueService;
import com.gestaoCash.services.UserService;
import com.gestaoCash.utils.DataUserAuth;
import com.gestaoCash.utils.SenhaUtils;

import dto.SaleDto;
import jakarta.websocket.server.PathParam;

@Controller
@RequestMapping("/usuario")
public class UsersController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	UserRepository userepo;

	@Autowired
	ExpenseRespository expRepo;

	@Autowired
	private CourseService courseService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private ExpenseService expenseService;

	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private ProductService prodService;

	DataUserAuth data = new DataUserAuth();

	LocalDate parameterRev;

	@GetMapping("/cadastro")
	public String cadastrar(Model model) {
		model.addAttribute("states", StateEnum.values());
		model.addAttribute("user", new Users());

		return "usuario/cadastro";

	}

	@GetMapping("/editar")
	public String editUserView(Model model) {
		Users userLog = new Users();
		userLog = data.DataUser();
		Users user = userService.findUserById(userLog.getId());
		model.addAttribute("user", user);
		model.addAttribute("states", StateEnum.values());
		return "/usuario/editar";
	}

	@PostMapping("/cadastro")
	public ModelAndView cadastrar(Model model, @ModelAttribute("user") Users user,
			@RequestParam("inputImg") MultipartFile file, BindingResult result) throws IOException {
		try {
			user.setImagemPerfil(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Verificar se o e-mail já está cadastrado
	    if (userService.existsEmail(user.getEmail())) {
	        result.rejectValue("email", "email.duplicado", "E-mail já cadastrado. Escolha outro e-mail.");
	    }

		// Users existingUser = userService.findByUserEmail(user.getEmail());

		// if(existingUser != null && existingUser.getEmail() != null &&
		// !existingUser.getEmail().isEmpty()){
		// result.rejectValue("email", null,
		// "There is already an account registered with the same email");
		// }

		if (result.hasErrors()) {
			model.addAttribute("user", user);
			ModelAndView modelAndView = new ModelAndView("/cadastro");
			return modelAndView;
		}

		String senhaEncriptada = SenhaUtils.encode(user.getSenha());
		user.setSenha(senhaEncriptada);
		user.setTipoUsuario("user");

		userService.saveUser(user);
		ModelAndView modelAndView = new ModelAndView("redirect:/");
		return modelAndView;
	}

	@GetMapping("/imagem/{id}")
	@ResponseBody
	public byte[] exibirImagen() {
		Long id = data.DataUser().getId();
		// Users user = this.userepo.getReferenceById(id);
		Users user = userService.findUserById(id);

		return user.getImagemPerfil();
	}

	@GetMapping("/area-cliente")
	public ModelAndView areaDoCliente(Model model, Revenue revenue,
			@RequestParam(required = false, name = "dateExp") String dateMonthE,@RequestParam(required = false, name = "dateRev") String dateMonthR) {
		ModelAndView modelAndView = new ModelAndView("usuario/area-do-cliente");
		modelAndView.addObject("states", StateEnum.values());
		modelAndView.addObject("expense", new Expense());
		modelAndView.addObject("revenue", new Revenue());
		

		// String img = getUser.getImagemPerfil() + ":image/png;base64," + conver;
		Long id = data.DataUser().getId();

		// Image img = new ImageIcon(getUser.getImagemPerfil()).getImage();
		// model.addAttribute("img", img);

		// List<Revenue> revenues = revRepo.findRevenueByUser(data.DataUser().getId());

		// List<Revenue> revenues = revenueService.findRevenueAndUser(id);
		// List<Expense> expenses = expenseService.findExpenseAndUser(id);

		CategoryEnum[] cats = CategoryEnum.values();
		MonthEnum[] months = MonthEnum.values();

		List<Revenue> revenues;
		List<Expense> expenses;

		Locale local = new Locale("pt", "BR");
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", local);
		DateTimeFormatter formatoDois = DateTimeFormatter.ofPattern("MMMM-yyyy", local);
		
		if (dateMonthE != null) {
//			revenues = revenueService.findRevenueFilterDate(LocalDate.parse("01-" + dateMonth, formato), id)
//					.collect(Collectors.toList());
			expenses = expenseService.findExpenseFilterDate(LocalDate.parse("01-" + dateMonthE, formato), id)
					.collect(Collectors.toList());
		} else {
//			revenues = revenueService.findRevenueFilterDate(LocalDate.now(), id).collect(Collectors.toList());
			expenses = expenseService.findExpenseFilterDate(LocalDate.now(), id).collect(Collectors.toList());
		}
		
		if (dateMonthR != null) {
			revenues = revenueService.findRevenueFilterDate(LocalDate.parse("01-" + dateMonthR, formato), id)
					.collect(Collectors.toList());
	
		} else {
			revenues = revenueService.findRevenueFilterDate(LocalDate.now(), id).collect(Collectors.toList());
		}

		// ObjectMapper mapper = new ObjectMapper();
		// String reve = mapper.writeValueAsString(revenues);
		List<String> expe = new ArrayList<>();
		for (Expense e : expenses) {
			expe.add(e.toString());
		}

		// Locale local = new Locale("pt","BR");
		// DateFormat formato = new SimpleDateFormat("MMMM yyyy",local);
		Set<String> dataExp = new HashSet<>();
		Set<String> dataRev = new HashSet<>();

		
			for (Expense e : expenseService.findExpenseAndUser(id)) {
				dataExp.add(formatoDois.format(e.getData()).substring(0, 1).toUpperCase()
						.concat(formatoDois.format(e.getData()).substring(1)));
			}
		

			for (Revenue e : revenueService.findRevenueAndUser(id)) {
				dataRev.add(formatoDois.format(e.getData()).substring(0, 1).toUpperCase()
						.concat(formatoDois.format(e.getData()).substring(1)));
			}
		

		String totalMonth = revenueService.calcTotalMonth(revenueService.findRevenueAndUser(id), LocalDate.now().getYear());

		double totalRevenue = revenueService.calcTotalRevenue(revenues);
		double totalExpense = expenseService.calcTotalExpenses(expenses);
		Users user = userService.findUserById(id);
		
		
		model.addAttribute("user", user);
		model.addAttribute("totalRevenue", totalRevenue);
		model.addAttribute("totalExpense", totalExpense);
		model.addAttribute("dataExp", dataExp);
		model.addAttribute("dataRev", dataRev);
		model.addAttribute("totalMonth", totalMonth);
		model.addAttribute("months", months);
		model.addAttribute("cats", cats);
		model.addAttribute("expsString", expe);
		model.addAttribute("exps", expenses);
		model.addAttribute("revs", revenues);

		model.addAttribute("dateMonthE",
				dateMonthE != null ? dateMonthE.substring(0, 1).toUpperCase().concat(dateMonthE.substring(1)).replace("-", " ")
						: formatoDois.format(LocalDate.now()).substring(0, 1).toUpperCase()
								.concat(formatoDois.format(LocalDate.now()).substring(1)));
		model.addAttribute("dateMonthR",
				dateMonthR != null ? dateMonthR.substring(0, 1).toUpperCase().concat(dateMonthR.substring(1)).replace("-", " ")
						: formatoDois.format(LocalDate.now()).substring(0, 1).toUpperCase()
								.concat(formatoDois.format(LocalDate.now()).substring(1)));
		return modelAndView;
	}

	@PostMapping("/area-cliente/receita")
	public String addRevenue(@ModelAttribute("revenue") Revenue revenue) {

		Users user = new Users();

		// pegando os dados do usuário logado
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// UserDetailsImpl dateUser = (UserDetailsImpl) auth.getPrincipal();

		// pegando o id
		Long id = data.DataUser().getId();
		// setando o id no usuario
		user.setId(id);

		// salvando no banco de dados
		revenue.setUsuario(user);
		revenueService.saveRevenue(revenue);

		// ModelAndView modelAndView = new ModelAndView("redirect:/area-do-cliente");
		//
		return "redirect:/usuario/area-cliente";
	}

	@PostMapping("/area-cliente/despesa")
	public String addExpense(@ModelAttribute("expense") Expense expense) {

		Users user = new Users();
		Long id = data.DataUser().getId();
		user.setId(id);

		expense.setUsuario(user);

		this.expenseService.saveExpense(expense);

		return "redirect:/usuario/area-cliente";
	}

	@PostMapping("/editar")
	public String editUser(@ModelAttribute("user") Users editUser, @RequestParam("inputImg") MultipartFile file,
			BindingResult result) throws IOException {
		Long id = data.DataUser().getId();
		Users user = userService.findUserById(id);

		System.out.println("-----------------------------------------");

		try {
			if (file.isEmpty()) {

				editUser.setImagemPerfil(user.getImagemPerfil());

			} else {
				editUser.setImagemPerfil(file.getBytes());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		String typeUser = user.getTipoUsuario();
		String password = user.getSenha();

		editUser.setId(id);
		editUser.setSenha(password);
		editUser.setTipoUsuario(typeUser);

		if (user.getEmpresa() != null) {

			editUser.setEmpresa(user.getEmpresa());
		}

		userService.saveUser(editUser);
		return "redirect:/usuario/area-cliente";
	}
}

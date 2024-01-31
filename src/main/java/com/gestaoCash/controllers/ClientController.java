package com.gestaoCash.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gestaoCash.enums.StateEnum;
import com.gestaoCash.model.Client;
import com.gestaoCash.services.ClientService;
import com.gestaoCash.utils.DataUserAuth;

@Controller
@RequestMapping("/usuario/area-cliente")
public class ClientController {

  @Autowired
  private ClientService clientService;
  
  DataUserAuth data = new DataUserAuth();

//  @GetMapping
//  public String listAllClient(Model model) {
//    var clients = this.clientService.findAllClient();
//    model.addAttribute("clientes", clients);
//    model.addAttribute("novoCliente", new Client());
//    model.addAttribute("states", StateEnum.values());
//
//    return "cliente/listar";
//  }

  // @GetMapping("/novo")
  // public String showForm(Model model) {
  // Client client = new Client();
  // model.addAttribute("cliente", client);
  // return "cliente/formulario";
  // }

  @PostMapping("/cadastrar-cliente")
  public String saveClient(@ModelAttribute("client") @Validated Client client,BindingResult bindingResult, Model model) {
	  
	  if (bindingResult.hasErrors()) {
	        // Se houver erros de validação, retorne o modal com mensagens de erro
	        model.addAttribute("errorMessage", "Erro ao cadastrar o cliente. Por favor, corrija os campos destacados.");
	        return "addClientModal :: modal-content";  // Retorne apenas o conteúdo do modal, não a página inteira
	    }
	  try {
	        // Configurar a empresa e a data de criação
	        client.setEmpresa(data.DataUser().getEmpresa());
	        client.setCreatedAt(LocalDate.now());

	        // Salvar o cliente no banco de dados
	        clientService.saveClient(client);

	        // Mensagem de sucesso
	        model.addAttribute("successMessage", "Cliente cadastrado com sucesso!");

	        return "redirect:/usuario/area-cliente/empresa";
	    } catch (Exception e) {
	       
	        model.addAttribute("errorMessage", "Erro ao cadastrar o cliente. Por favor, tente novamente.");
	        return "addClientModal :: modal-content";  // Retorne apenas o conteúdo do modal, não a página inteira
	    }
  }

  @GetMapping("/editar/{id}")
  public String showEditForm(@PathVariable Long id, Model model) {
    var client = this.clientService.findClientById(id);
    model.addAttribute("editarCliente", client);

    return "cliente/editar";
  }

//  @PostMapping("/editar/{id}")
//  public String updateClient(@ModelAttribute("editarCliente") Client updatedClient, @PathVariable Long id) {
//    this.clientService.saveClient(updatedClient);
//
//    return "redirect:/clientes";
//  }

  @GetMapping("/excluir/{id}")
  public String deleteClient(@PathVariable Long id) {
    this.clientService.deleteClientById(id);

    return "redirect:/clientes";
  }

  // excluir varios
  @GetMapping("/excluir")
  public String deleteClient(@RequestParam("ids") List<Long> ids) {
    var idsNonNull = ids.stream().filter(id -> id != null).toList();
    this.clientService.deleteAllClientsById(idsNonNull);

    return "redirect:/clientes";
  }
}

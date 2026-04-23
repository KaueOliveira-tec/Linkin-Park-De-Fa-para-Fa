package com.example.SiteLinkinPark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.SiteLinkinPark.model.Usuario;
import com.example.SiteLinkinPark.model.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MenuController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/")
    public String paginaPrincipal(){
        return "index";
    }

    @GetMapping("/musicas")
    public String musica(){
        return "musicas";
    }

    @GetMapping("/integrantes_originais")
    public String integranteOriginal(){
        return "integrantes_originais";
    }

    @GetMapping("/integrantes_atuais")
    public String integranteAtual(){
        return "integrantes_atuais";
    }

    @GetMapping("/form_user")
	public String formUser(Model model) {
		model.addAttribute("usuario",new Usuario());
		return "form_user";
	}

    @PostMapping("/usuario")
	public String postCliente(@ModelAttribute Usuario usuario,
			                  Model model) {
        
		usuarioService.cadastroUsuario(usuario);
		return "form_sucesso";
	}

    @GetMapping("/login")
    public String telaLogin() {
        return "login";
    }

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuarioLogado");
        if (user != null) {
            model.addAttribute("nomeUsuario", user.getNome());
            model.addAttribute("emailUsuario", user.getEmail());
            model.addAttribute("uuid", user.getId());
        }
        return "perfil";
    }

    @GetMapping("/editar_usuario")
    public String editarUsuario(HttpSession session, Model model) {
        Usuario user = (Usuario) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", user);
        return "editar_usuario";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/usuario/atualizar")
    public String atualizarUsuario(@ModelAttribute Usuario usuario,
                                   @RequestParam String emailAtual,
                                   @RequestParam String senhaAtual,
                                   HttpSession session,
                                   Model model) {
        boolean atualizado = usuarioService.atualizarUsuario(usuario, emailAtual, senhaAtual);
        if (atualizado) {
            session.setAttribute("usuarioLogado", usuario);
            return "form_sucesso";
        }
        model.addAttribute("erro", "Atualização falhou. Verifique o e-mail e senha atuais e tente novamente.");
        model.addAttribute("usuario", usuario);
        return "editar_usuario";
    }

    @PostMapping("/efetuarLogin")
    public String efetuarLogin(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {
        Usuario user = usuarioService.login(email, senha);

        if (user != null) {
            session.setAttribute("usuarioLogado", user);
            model.addAttribute("nomeUsuario", user.getNome());
            model.addAttribute("emailUsuario", user.getEmail());
            model.addAttribute("uuid", user.getId());
            return "perfil";
        } else {
            model.addAttribute("erro", "Conta não encontrada ou senha inválida");
            return "login";
        }
    }
}

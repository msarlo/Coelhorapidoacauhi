package controller;

import dao.UsuarioDAO;

public class LoginController {

    public LoginController() {

    }

    public String realizarLogin(String email, String senha){
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.login(email,senha);
    }
}

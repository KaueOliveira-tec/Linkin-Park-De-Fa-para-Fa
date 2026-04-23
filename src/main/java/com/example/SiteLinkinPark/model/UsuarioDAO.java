package com.example.SiteLinkinPark.model;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class UsuarioDAO {

	@Autowired
	private DataSource dataSource;
	
	private JdbcTemplate jdbc;
	
	@PostConstruct
	public void initialize() {
		jdbc = new JdbcTemplate(dataSource);
	}
	
	public void cadastroUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuario(nome, email, senha)" +
	                 " VALUES (?,?,?)";
		Object[] obj = new Object[3];
		//primeiro ?
		obj[0] = usuario.getNome();
		//segundo ?
		obj[1] = usuario.getEmail();
		//terceiro ?
		obj[2] = usuario.getSenha();
		jdbc.update(sql, obj);
	}

	public Usuario verificarLogin(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
        try {
            return Usuario.conversor(jdbc.queryForMap(sql, email, senha));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean atualizarUsuario(Usuario usuario, String emailAtual, String senhaAtual) {
        String sql = "UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE email = ? AND senha = ?";
        int rows = jdbc.update(sql, usuario.getNome(), usuario.getEmail(), usuario.getSenha(), emailAtual, senhaAtual);
        return rows > 0;
    }
}


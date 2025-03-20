package br.com.comida.restaurante.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.comida.restaurante.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
}

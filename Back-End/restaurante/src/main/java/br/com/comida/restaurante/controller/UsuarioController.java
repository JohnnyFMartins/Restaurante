package br.com.comida.restaurante.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.comida.restaurante.model.Usuario;
import br.com.comida.restaurante.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repUsuario;

	// GET, POST, PUT, DELETE
	
	@PostMapping("/")
	public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
		try {
			if (usuario.getNome() == null || usuario.getNome().isEmpty()) {
				return ResponseEntity.badRequest().body("O nome do usuario não pode ser vazio.");
			}
			
			Usuario newP = repUsuario.save(usuario);

			return ResponseEntity.ok(newP);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor.");
		}
	}
	
	@GetMapping("/")
	public ResponseEntity<?> buscarUsuarios() {
		try {
			List<Usuario> usuarios = repUsuario.findAll();

			if (usuarios.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Nenhum usuario encontrado.");
			}

			return ResponseEntity.ok(usuarios);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor.");

		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarUsuario(@PathVariable("id") Long id) {
		try {
			Optional<Usuario> verificaExiste = repUsuario.findById(id);

			if (verificaExiste.isPresent()) {
				// deletar o usuario
				repUsuario.deleteById(id);

				return ResponseEntity.ok("Usuario deletado com sucesso!");
			} else {
				// retornar mensagem que não foi encontrado
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ocorreu um erro interno no servidor.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarProduto(@PathVariable("id") Long id, @RequestBody Usuario usuario) {
		try {
			Optional<Usuario> verificaExiste = repUsuario.findById(id);

			if (verificaExiste.isPresent()) {
				Usuario u = verificaExiste.get();
				u.setNome(usuario.getNome());
				u.setCpf(usuario.getCpf());
				u.setDataNascimento(usuario.getDataNascimento());
				
			
				repUsuario.save(u);

				return ResponseEntity.ok(u);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario não encontrado");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro interno no servidor.");
		}
	}
}
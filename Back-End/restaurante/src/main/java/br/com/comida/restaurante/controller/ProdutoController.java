package br.com.comida.restaurante.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.comida.restaurante.model.Produto;
import br.com.comida.restaurante.repository.ProdutoRepository;



@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoRepository repProduto;

	// GET, POST, PUT, DELETE
	@GetMapping("/")
	public ResponseEntity<?> buscarProdutos() {
		try {
			List<Produto> produtos = repProduto.findAll();

			if (produtos.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("Nenhum produto encontrado.");
			}

			return ResponseEntity.ok(produtos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");

		}

	}

	@PostMapping("/")
	public ResponseEntity<?> criarProduto(@RequestBody Produto produto) {
		try {
			if (produto.getNome() == null || produto.getNome().isEmpty()) {
				return ResponseEntity.badRequest().body("O nome do produto não pode ser vazio.");
			}

			Produto newP = repProduto.save(produto);

			return ResponseEntity.ok(newP);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletarProduto(@PathVariable("id") Long id) {
		try {
			Optional<Produto> verificaExiste = repProduto.findById(id);

			if (verificaExiste.isPresent()) {
				// deletar o produto
				repProduto.deleteById(id);

				return ResponseEntity.ok("Produto deletado com sucesso!");
			} else {
				// retornar mensagem que não foi encontrado
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("Ocorreu um erro interno no servidor.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarProduto(@PathVariable("id") Long id, @RequestBody Produto produto) {
		try {
			Optional<Produto> verificaExiste = repProduto.findById(id);

			if (verificaExiste.isPresent()) {
				Produto p = verificaExiste.get();
				p.setNome(produto.getNome());
				p.setImgUrl(produto.getImgUrl());
				p.setPrice(produto.getPrice());
				p.setDescription(produto.getDescription());
				p.setIngredientes(produto.getDescription());

				repProduto.save(p);

				return ResponseEntity.ok(p);
			} else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT)
						.body("Produto não encontrado");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor.");
		}
	}
}
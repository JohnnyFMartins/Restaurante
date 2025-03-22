package br.com.comida.restaurante.controller;
 
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.comida.restaurante.model.ItemPedido;
import br.com.comida.restaurante.model.Pedido;
import br.com.comida.restaurante.repository.PedidoRepository;
 

 
 
@CrossOrigin
@RestController
@RequestMapping("/pedido")
public class PedidoController {
	@Autowired
	private PedidoRepository repPedido;
	
	@PostMapping("/")
	public ResponseEntity<?> criar(@RequestBody Pedido pedido) {
		try {
			if (pedido.getStatus().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Preencher todos os campos");
			}
			if (pedido.getItens().size() == 0) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Não existe pedido sem produto");
			}
			Pedido pedidoCriado = repPedido.save(pedido);
			return ResponseEntity.ok(pedidoCriado);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor !");
		}
	}
	@PostMapping("/itempedido/{id}")
	public ResponseEntity<?> adicionarItem(@PathVariable Long id, @RequestBody ItemPedido itemPedido) {
		try {
			Optional<Pedido> existePedido = repPedido.findById(id);
			if (existePedido.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Pedido não encontrado");
			}
			if (itemPedido.getNome().isEmpty() || itemPedido.getDescription().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Preencha todos os campos");
			}
			Pedido pedido = existePedido.get();
			pedido.getItens().add(itemPedido);
			repPedido.save(pedido);
			return ResponseEntity.ok("Item adicionado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro interno no servidor");
		}
	}
 
}
package br.com.dio.patterns.singleton;

import br.com.dio.patterns.domain.Pedido;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Singleton "preguicoso" gerenciado pelo Spring: o container mantem
 * exatamente uma instancia deste bean por aplicacao.
 */
@Component
public class RegistroDePedidos {

    private final List<Pedido> pedidos = Collections.synchronizedList(new ArrayList<>());

    public void registrar(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> listar() {
        synchronized (pedidos) {
            return List.copyOf(pedidos);
        }
    }

    public Optional<Pedido> buscar(UUID id) {
        synchronized (pedidos) {
            return pedidos.stream().filter(p -> p.getId().equals(id)).findFirst();
        }
    }

    public int quantidade() {
        return pedidos.size();
    }
}

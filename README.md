# design-patterns-java-spring

Projeto de estudo que demonstra os padroes de projeto classicos do GoF (Singleton, Strategy e Facade) aplicados a uma API REST com Spring Boot 3.

## Padroes demonstrados

- **Singleton** (`RegistroDePedidos`) - gerenciado pelo container Spring, mantem o historico unico de pedidos.
- **Strategy** (`PoliticaDesconto` + `SeletorDeDesconto`) - politicas de desconto (SemDesconto, DescontoClienteVip, DescontoBlackFriday) intercambiaveis em tempo de execucao.
- **Facade** (`CheckoutFacade`) - orquestra dominio, Strategy e registro, expondo uma unica operacao de checkout.

## Como executar

```bash
mvn spring-boot:run
```

Endpoint: `POST http://localhost:8080/api/checkout`

## Testes

```bash
mvn test
```

9 testes (5 de Strategy, 2 de Facade, 2 de Controller).

## Autor

Ricardo Schonfelder Filho - Bootcamp Itaú - Java com Inteligência Artificial (DIO)

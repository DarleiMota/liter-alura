# LiterAlura — Projeto Java + Spring

[![Badge Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/8/docs/)
[![Badge Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Badge Alura](https://img.shields.io/badge/Alura-blue?style=for-the-badge&logo=aluratechnologies&logoColor=white)](https://www.alura.com.br)

**Desafio Final - Challenge Back-End Oracle Next Education | Alura**

---
## 📖 Sobre o Projeto

O **LiterAlura** é um aplicativo de linha de comando que permite:

- Buscar livros usando a API do [Gutendex](https://gutendex.com/)
- Armazenar livros e seus autores em um banco de dados relacional
- Listar livros por idioma, por título e por autor
- Consultar autores vivos em um determinado ano

Foi desenvolvido utilizando **Spring Boot**, **JPA (Hibernate)**, **PostgreSQL**, e organização em camadas `service`, `repository`, `dto`, `model`, e `client`.

---

## ✅ Funcionalidades implementadas

- ✅ Busca de livros por título via API Gutendex
- ✅ Armazenamento de livros no banco de dados
- ✅ Armazenamento de autores com verificação para evitar duplicidade
- ✅ Relacionamento entre livro e autor por ID (chave estrangeira)
- ✅ Listagem de todos os livros cadastrados
- ✅ Listagem de autores cadastrados
- ✅ Consulta de autores vivos em determinado ano
- ✅ Filtro e contagem de livros por idioma
- ✅ Validação para evitar dados duplicados
- ✅ Tratamento de campos nulos e ausência de dados
- ✅ Feedback amigável para o usuário no console
- ✅ Organização em camadas (DTO, Service, Repository, Model, Client)

---

## 🖥️ Demonstração da Aplicação

![Demonstração da aplicação](assets/Videoteste.gif)

---

## 📂 Estrutura do Projeto

```
src
├── main
  ├── java
    ├── br.com.literAlura
      ├── model            # Entidades JPA (Book, Author)
      ├── dto              # Mapeamento dos dados da API
      ├── repository       # Interfaces JpaRepository
      ├── controller       # Camada responsável por interações com o usuário (MenuService)
      ├── service          # Regras de negócio e lógica da aplicação
      ├── cliente          # Cliente HTTP para a API Gutendex
      └── LiterAluraApplication.java     # Classe principal
```
---

## 🔄 Fluxo principal da aplicação

1. O usuário informa um título de livro
2. A API do Gutendex retorna uma lista de livros
3. Cada livro é processado:
   - Se o autor não existir, é criado
   - Se o livro já existir para aquele autor, é ignorado
   - Caso contrário, é salvo no banco
4. A aplicação imprime o livro importado formatado no console

---

## 🌐 API REST

A aplicação também disponibiliza uma API REST simples, com endpoints que permitem listar livros e importar novos diretamente via requisições HTTP.

### 📘 Endpoints disponíveis

| Método | Rota                 | Descrição                                  |
|--------|----------------------|---------------------------------------------|
| GET    | `/books`             | Lista todos os livros armazenados           |
| GET    | `/books/{id}`        | Retorna os detalhes de um livro por ID      |
| POST   | `/books/import`      | Importa livros da API do Gutendex via termo |

### 🛠️ Detalhes de funcionamento

- A classe `BookController` atua como ponto de entrada para a API REST da aplicação.
- Os endpoints fazem uso do `BookService` para executar a lógica de negócio.
- O endpoint de importação aceita um parâmetro `termo`, usado para consultar livros na API do Gutendex.
- Todos os dados processados são armazenados no banco de dados, respeitando as validações e evitando duplicações.

## 🎓 Regras de Negócio Atendidas

- Busca de livro por título.
- Listagem de todos os livros.
- Listar autores.
- Listar autores vivos em determinado ano.
- Inserir autor e assim manter uma relação entre os dois objetos via atributo de identificação (ID)
- Exibir a quantidade de livros em um determinado idioma.
- Listar os autores vivos em um determinado ano.
- Evitar dados duplicados, null, exceptions. 
- Validação dos campos, 
- Validação do armazenamento.

---
## ⚠️ Observações

- O banco pode crescer indefinidamente com os testes. Caso deseje sempre partir de um banco limpo, configure o `spring.jpa.hibernate.ddl-auto=create`.
- Para evitar poluição no console, logs do Hibernate podem ser desativados no `application.properties`.
---

## 💬 Commits semânticos utilizados

- `feat:` Adição de novas funcionalidades
- `fix:` Correções de bugs e falhas
- `refactor:` Melhorias de código sem alterar funcionalidade
- `test:` Inclusão ou melhoria de testes
- `docs:` Alterações no README ou documentação geral
- `style:` Ajustes de formatação, identação, nomes
- `chore:` Tarefas de configuração, atualização de dependências etc.

---
## ⚙️ Tecnologias utilizadas

- ☕ **Java 21**
- 🚀 **Spring Boot**
- 🛠️ **Spring Data JPA (Hibernate)**
- 🐘 **PostgreSQL**
- 🐳 **Docker**
- 🔗 **HTTP Client** (`java.net.http`)
- 📦 **Jackson** (JSON parser)

---

## ✨ Autor

Desenvolvido por **Darlei Mota** durante a formação Back-End Java da Alura com Oracle Next Education (ONE).

- 🔗 [GitHub: darleimota](https://github.com/darleimota)
- 💼 [LinkedIn: darleimota](https://www.linkedin.com/in/darleimota)

---

> "O bug pode durar uma noite, mas a solução vem ao amanhecer." — *by Darlei*
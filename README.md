# SpikeLab

# SpikeLab 

é um blog pessoal onde compartilho estudos, artigos, experimentos e aprendizados sobre engenharia de software e tecnologia.

O objetivo é documentar conhecimento, registrar descobertas e compartilhar experiências adquiridas durante projetos, pesquisas e estudos contínuos.

---

## Arquitetura

O projeto segue princípios de **Clean Architecture**, com algumas adaptações visando reduzir a complexidade e acelerar o
desenvolvimento, sem perder organização e separação de responsabilidades.

### Adaptações adotadas

- Remoção da camada de **Presenter**  
  Para simplificar o fluxo da aplicação, a responsabilidade de formatação de dados foi centralizada em **DTOs**, que são
  utilizados tanto na entrada quanto na saída da API.

- Uso direto de DTOs no fluxo da aplicação  
  Os DTOs são responsáveis por transportar os dados entre as camadas, evitando exposição direta das entidades e
  reduzindo acoplamento com o domínio.

- Utilização de mappers para conversão entre DTOs e entidades, estabelecendo uma camada de abstração responsável por desacoplar o domínio da representação externa dos dados (API).

- Simplificação da camada de repositório  
  Utilizando **Spring Data JPA**, as queries são tratadas de forma declarativa:
    - Queries simples são resolvidas automaticamente pelo ORM
    - Queries mais específicas são tratadas na camada de **Domain (Repository)**

- Existe um módulo **shared**, responsável por centralizar componentes reutilizáveis (como DTOs e services comuns),
  promovendo reutilização de código e reduzindo acoplamento entre módulos da aplicação.

---

### Estrutura de camadas

- **Application**
    - Orquestra os fluxos da aplicação
    - Contém os **Use Cases**
    - Responsável pelo mapeamento entre DTOs
    - Responsável pela regra de negócios

- **Domain**
    - Contém as entidades centrais (ex: Post, Tag)
    - Define contratos de repositório

- **Infrastructure**
    - Controllers (entrada HTTP)
    - Integração com serviços externos

---

### Fluxo simplificado

```
User  → Controller → DTO → Use Case  → Repository → Entity → Banco
```

---

### Diagrama conceitual da clean architecture

<img width="2157" height="1344" alt="image" src="https://github.com/user-attachments/assets/b7fba6b7-5ba9-4646-98e9-3fc8d8b1f3b8" />



---

## Pipeline de Deploy 

```
Build - ok
  ↓
Tests - ok
  ↓
Code Quality - ok
  ↓
Code Security - ok
  ↓
Deploy AWS - falta
```

---

## Como rodar o projeto

### Pré-requisitos

- Docker
- Docker Compose (Rodar banco local)
- Java 21+

### Rodando banco localmente

```bash
docker-compose up
```

Limpa/Derrubar banco:

```bash
docker-compose down -v
```

### Rodando a aplicacao

```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em:

```
http://localhost:8080
```

---

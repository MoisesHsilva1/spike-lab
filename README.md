# The Moments

O **The moments** é uma aplicação para registrar e compartilhar experiências gastronômicas utilizando **tags dinâmicas**
para descrever o contexto do momento (ex: `barato`, `instagramável`, `fim de mês`).

A proposta é oferecer uma forma mais flexível e expressiva de avaliação, indo além de sistemas tradicionais baseados
apenas em notas.

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

## Arquitetura em Cloud (AWS)

A aplicação segue uma arquitetura baseada em serviços da AWS:

- **EC2** → execução da API (container Docker)
- **RDS (PostgreSQL)** → persistência de dados
- **S3** → armazenamento de imagens


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

## Checklist de tarefas 

### Core

- [x] Criar post
- [x] Listar posts com paginação e filtros
- [x] Deletar posts
- [x] Buscar por ID
- [x] Modelagem de entidades (Post ↔ Tag)
- [x] Remover dependência de Entity → DTO em toda aplicação
- [x] Listar tags com paginação
- [x] Implementar SDK AWS
- [x] Implementar integração com S3 (upload de imagens)
- [x] Implementar springdoc-openapi na API
- [x] Criar testes unitários
- [ ] Integrar SDK de autenticação (Firebase ou AWS)
- [ ] Implementar módulo de autenticação
- [ ] Implementar módulo de usuário
- [ ] Proteger rotas com token

---

### Infra

- [x] Configurar S3
- [x] Configurar Route 53
- [ ] Configurar RDS
- [ ] Configurar EC2
- [ ] Configurar CloudWatch + Grafana
- [ ] Criar dashboards de observabilidade
- [ ] Implementar CI/CD (build, testes, segurança, deploy)

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

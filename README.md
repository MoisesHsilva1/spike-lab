# The Moments

O **The moments** é uma aplicação para registrar e compartilhar experiências gastronômicas utilizando **tags dinâmicas**
para descrever o contexto do momento (ex: `barato`, `instagramável`, `fim de mês`).

A proposta é oferecer uma forma mais flexível e expressiva de avaliação, indo além de sistemas tradicionais baseados
apenas em notas.

> Este projeto é open source e tem como objetivo principal aprendizado em arquitetura de software, Java e cloud (AWS).

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
User  → Controller → DTO → Use Case → Domain → Repository  → Banco
```

---

### Diagrama conceitual da clean architecture

<img width="2157" height="1344" alt="image" src="https://github.com/user-attachments/assets/27d30a0e-5e54-40e1-b9aa-23452df80332" />

---

## Arquitetura em Cloud (AWS)

A aplicação segue uma arquitetura baseada em serviços da AWS, com foco em escalabilidade e segurança:

- **Route 53** → resolução de domínio
- **Application Load Balancer (ALB)** → ponto de entrada da aplicação
- **EC2** → execução da API (container Docker)
- **RDS (PostgreSQL)** → persistência de dados
- **S3** → armazenamento de imagens

### Segurança

- EC2 e RDS estão em **subnets privadas**
- Apenas o Load Balancer é exposto publicamente
- O banco de dados é acessível somente pela aplicação (via security groups)

---

## Fluxo da aplicação

<img width="751" height="641" alt="the-moments-diagram drawio" src="https://github.com/user-attachments/assets/4c9d95aa-59dd-49c0-8d70-446818e58c60" />

---

## Pipeline de Deploy (planejado)

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

## Checklist de tarefas | Fase atual do projeto (MVP)

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
- [ ] Implementar swagger na API
- [x] Criar testes unitários
- [ ] Integrar SDK de autenticação (Firebase ou AWS)
- [ ] Implementar módulo de autenticação
- [ ] Implementar módulo de usuário
- [ ] Proteger rotas com token

---

### Infra

- [x] Configurar S3
- [x] Configurar Route 53
- [ ] Configurar Load Balancer
- [ ] Configurar RDS
- [ ] Configurar EC2
- [ ] Configurar subnets (pública e privada)
- [ ] Configurar CloudWatch + Grafana
- [ ] Criar dashboards de observabilidade
- [ ] Configurar alertas
- [ ] Implementar CI/CD (build, testes, segurança, deploy)

---

## Como rodar o projeto

### Pré-requisitos

- Docker
- Docker Compose (Rodar banco local)
- Java 17+

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
http://localhost:3000
```

---

## Contribuição

Contribuições são bem-vindas! Este projeto também tem foco educacional.

Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch:

```bash
git checkout -b feature/sua-feature
```

3. Commit suas alterações:

```bash
git commit -m "feat: minha contribuição"
```

4. Push para o repositório:

```bash
git push origin feature/sua-feature
```

5. Abra um Pull Request para branch develop

---

## Objetivo de aprendizado

Este projeto foi criado com foco em:

- Evoluir habilidades em **Java (Spring Boot)**
- Aplicar boas práticas de arquitetura (Clean Architecture)
- Trabalhar com **cloud (AWS)**
- Praticar organização de código e documentação

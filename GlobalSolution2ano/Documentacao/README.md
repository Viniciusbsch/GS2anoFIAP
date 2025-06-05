# GS2anoFIAP - Sistema de Alertas Climáticos

Aplicação de orientação a mudanças climáticas, alertando a população sobre eventuais riscos.

## Estrutura do Projeto

O projeto está dividido em três partes principais:

- Frontend (React + Vite)
- Backend (Java JAX-RS)
- Scripts SQL

## Requisitos

- Node.js 18+ (para o Frontend)
- Java 17+ (para o Backend)
- Maven (para o Backend)
- Oracle Database (para o banco de dados)

## Instruções de Instalação e Execução

### Frontend (React)

1. Navegue até o diretório do Frontend:

```bash
cd GlobalSolution2ano/Frontend
```

2. Instale as dependências:

```bash
npm install
```

3. Inicie o servidor de desenvolvimento:

```bash
npm run dev
```

O frontend estará disponível em: http://localhost:8080

### Backend (JAX-RS)

1. Navegue até o diretório do Backend:

```bash
cd GlobalSolution2ano/Backend
```

2. Compile o projeto usando Maven:

```bash
mvn clean install
```

3. Execute a aplicação:

```bash
mvn exec:java -Dexec.mainClass="com.gs.Server"
```

O backend estará disponível em: http://localhost:8081

### Banco de Dados

1. Configure as credenciais do banco de dados Oracle no arquivo `hibernate.cfg.xml` do backend
2. O sistema está configurado para usar o banco de dados Oracle da FIAP

## Tecnologias Utilizadas

### Frontend

- React 18
- Vite
- TypeScript
- Tailwind CSS
- Shadcn UI
- React Router DOM
- React Query

### Backend

- Java 17
- JAX-RS (Jersey)
- Hibernate
- Oracle Database

## Documentação

A documentação completa do projeto está disponível no diretório `GlobalSolution2ano/Documentacao/`

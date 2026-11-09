# 📋 Swiftplan+

> Versão aprimorada do projeto acadêmico **Swift Plan** — desenvolvida por iniciativa própria após a entrega do 2º semestre.

----------

## 📌 Sobre o projeto

O **Swift Plan** foi um sistema de gestão acadêmica desenvolvido em equipe durante o 2º semestre do Tecnólogo em Banco de Dados (FATEC – Prof. Jessen Vidal). O sistema permitia que professores organizassem suas aulas e temas de forma mais prática, eliminando processos manuais e reduzindo erros operacionais.

O **Swiftplan+** nasce da percepção de que o projeto original, apesar de funcional, apresentava lacunas importantes — especialmente a **ausência de rastreabilidade das ações realizadas no sistema**.

Tratando-se de um sistema de gestão acadêmica no mundo real, a falta de rastreabilidade é um **problema sério** por inúmeros motivos. Este repositório é uma evolução independente, focada em três frentes:

🔍 **Sistema de Auditoria (Log)**

Registro de todas as ações relevantes realizadas pelos usuários

🐛 **Correção de Bugs**

Identificação e resolução de comportamentos incorretos herdados da versão original

🧹 **Refatoração de Código**

Remoção de redundâncias, melhoria de legibilidade e organização das classes

----------

## 🎯 Objetivo principal — Sistema de Auditoria

O projeto original não possuía nenhum mecanismo de rastreamento de ações. Isso significa que era impossível saber **quem fez o quê e quando** dentro do sistema.

O Swiftplan+ implementa um **registro de auditoria (log de atividades)** que captura ações como:

```
✅ Professor João criou o Tema "Introdução à POO" para a Disciplina "Programação I", na data XX/XX/XXXX, às XX:XX horas.
✅ Coordenadora Ana atribuiu a Disciplina "Banco de Dados" ao Professor Carlos, na data XX/XX/XXXX, às XX:XX horas.
✅ Professor João removeu o Tema "Algoritmos" da Disciplina "Lógica de Programação", na data XX/XX/XXXX, às XX:XX horas.

```

Esse tipo de rastreabilidade é fundamental em sistemas reais, pois permite:

-   **Auditoria e controle** de acesso e uso
-   **Identificação rápida** de quem realizou uma ação em caso de inconsistências
-   **Histórico consultável** para suporte e diagnóstico de problemas

----------

## 🛠️ Stack

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![JavaFX](https://img.shields.io/badge/JavaFX-FF0000?style=for-the-badge&logo=javafx&logoColor=white) ![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)

----------

## 📂 Estrutura do projeto

```
swiftplan_plus/
├── database/
│   └── audit_log.sql                    # 🆕 Script da tabela de auditoria
├── src/main/java/org/example/demo3/
│   ├── controller/                      # Controladores das telas (JavaFX)
│   ├── dao/                             # Acesso ao banco de dados (JDBC)
│   ├── dto/                             # Objetos de transferência para as telas
│   ├── entity/                          # Entidades do banco de dados
│   ├── audit/                           # 🆕 Módulo de auditoria (log de atividades)
│   ├── ConfigDB.java                    # Leitura do db.properties
│   ├── DatabaseConnection.java          # Conexão com o MySQL
│   ├── Launcher.java                    # Ponto de entrada da aplicação JavaFX
│   ├── SemestreLetivoService.java       # Regras de negócio de semestres
│   └── UsuarioAtual.java                # Sessão do usuário logado
├── src/main/resources/                  # Arquivos FXML e CSS das interfaces
├── pom.xml
└── README.md

```

----------

## ⚙️ Como executar

**Pré-requisitos:** JDK 25 e MySQL em execução.

1. Crie o banco de dados e execute os scripts da pasta `database/`.
2. Crie um arquivo `db.properties` na raiz do projeto (ou no diretório de execução) com as chaves:

```properties
db.url=jdbc:mysql://localhost:3306/
db.name=swiftplan
db.user=seu_usuario
db.password=sua_senha
```

3. Execute a aplicação:

```bash
./mvnw javafx:run
```

> O arquivo `db.properties` não é versionado (está no `.gitignore`), pois contém credenciais.

### 🗂️ Onde ficam os registros

-   **Auditoria (ações dos usuários):** tabela `audit_log` no banco, visível para o ADM em "📜 Histórico de Atividades".
-   **Log técnico (erros e diagnósticos):** `logs/swiftplan.log`, com rotação diária e limite de 10 MB por arquivo. Configuração em `src/main/resources/logback.xml`. A pasta `logs/` não é versionada.

----------

## 📝 Padrão de commits

Os commits seguem o padrão **Conventional Commits**, em português:

-   `feat:` nova funcionalidade
-   `fix:` correção de bug
-   `docs:` alterações na documentação
-   `test:` criação ou alteração de testes

----------

----------

## ✅ Melhorias a serem implementadas

### 🔍 Sistema de Log (Auditoria)

-   [x] Tabela `audit_log` no banco de dados (`database/audit_log.sql`) com campos: `id_audit_log`, `usuario_id`, `usuario_nome`, `tipo_usuario`, `acao`, `descricao`, `data_hora`
-   [x] Módulo `audit/` com `AuditoriaService.registrar(...)`, usado a partir da sessão do usuário logado
-   [x] Registro de auditoria nas ações de criar, editar ou remover em **Temas**
-   [x] Registro de auditoria nas ações de criar, editar ou remover em Disciplinas, Atribuições, Bloqueios, Professores, Coordenadores e Administradores
-   [ ] Criação do primeiro administrador na tela de primeiro acesso (`LoginController`) não é auditada, pois não há usuário logado nesse momento
-   [x] Tela de consulta do histórico de atividades (somente leitura), disponível para o perfil ADM em "📜 Histórico de Atividades"

### 🐛 Correção de Bugs

-   [ ] _(a ser documentado conforme os bugs forem identificados e corrigidos)_

### 🩺 Log técnico (diagnóstico)

-   [x] Logback configurado (console e arquivo rotativo em `logs/`)
-   [x] `System.out`, `System.err` e `printStackTrace()` substituídos pelo logger

### 🧹 Refatoração

-   [ ] Remoção de métodos duplicados nas classes DAO
-   [ ] Padronização de nomenclatura de variáveis e métodos
-   [ ] Separação adequada de responsabilidades (MVC)

----------

## 🔗 Projeto original

Este repositório é uma evolução independente. O projeto acadêmico original (Swift Plan — 2º semestre) pode ser acessado em:  
👉 [api_datasquad-gestaoacademica](https://github.com/guismdonofrio/api_datasquad-gestaoacademica)

----------

## 👨‍💻 Autor

**Wanderson Ricardo dos Santos**  
Estudante de Banco de Dados — FATEC São José dos Campos (Prof. Jessen Vidal)

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/wanderson-ricardo-2b54271b3/) [![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Wander717)

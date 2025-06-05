package com.gs;

import com.gs.exception.BusinessException;
import com.gs.model.*;
import com.gs.service.AlertaService;
import com.gs.service.AreaRiscoService;
import com.gs.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final Scanner scanner = new Scanner(System.in);
    private static final AlertaService alertaService = new AlertaService();
    private static final AreaRiscoService areaRiscoService = new AreaRiscoService();
    private static EntityManager em;

    public static void main(String[] args) {
        try {
            System.out.println("Carregando o sistema de alertas climaticos, aguarde um momento...");
            em = HibernateUtil.getSessionFactory().createEntityManager();
            exibirMenu();
        } catch (Exception e) {
            System.err.println("Erro ao iniciar o sistema: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
            HibernateUtil.shutdown();
        }
    }

    private static void exibirMenu() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Gerenciar Usuários");
            System.out.println("2. Gerenciar Áreas de Risco");
            System.out.println("3. Gerenciar Alertas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1:
                        gerenciarUsuarios();
                        break;
                    case 2:
                        gerenciarAreasRisco();
                        break;
                    case 3:
                        gerenciarAlertas();
                        break;
                    case 4:
                        System.out.println("Saindo do sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números para a opção do menu.");
            } catch (BusinessException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private static void gerenciarAreasRisco() {
        while (true) {
            System.out.println("""
                
                === ÁREAS DE RISCO ===
                1. Cadastrar nova área
                2. Listar áreas
                3. Atualizar área
                4. Remover área
                0. Voltar
                Escolha uma opção:\n """);

            int opcao = lerOpcao();
            switch (opcao) {
                case 1 -> cadastrarAreaRisco();
                case 2 -> listarAreasRisco();
                case 3 -> atualizarAreaRisco();
                case 4 -> removerAreaRisco();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida!, digite um número válido!");
            }
        }
    }

    private static void gerenciarAlertas() {
        while (true) {
            System.out.println("""
                
                === ALERTAS ===
                1. Criar novo alerta
                2. Listar alertas
                3. Atualizar alerta
                4. Desativar alerta
                0. Voltar
                Escolha uma opção:\n """);

            int opcao = lerOpcao();
            switch (opcao) {
                case 1 -> criarAlerta();
                case 2 -> listarAlertas();
                case 3 -> atualizarAlerta();
                case 4 -> desativarAlerta();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida! Digite um número válido!");
            }
        }
    }

    private static void gerenciarUsuarios() {
        while (true) {
            System.out.println("""
                
                === USUÁRIOS ===
                1. Cadastrar novo usuário
                2. Listar usuários
                3. Atualizar usuário
                4. Remover usuário
                5. Associar usuário a área
                6. Desassociar usuário de área
                0. Voltar
                Escolha uma opção:\n """);

            int opcao = lerOpcao();
            switch (opcao) {
                case 1 -> cadastrarUsuario();
                case 2 -> listarUsuarios();
                case 3 -> atualizarUsuario();
                case 4 -> removerUsuario();
                case 5 -> associarUsuarioArea();
                case 6 -> desassociarUsuarioArea();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida! Digite um número válido!");
            }
        }
    }

    private static void cadastrarAreaRisco() {
        try {
            System.out.print("Nome da área/região/bairro: ");
            String nome = scanner.nextLine();

            System.out.print("Nível de risco, enumerar de 1 a 5: ");
            int nivelRisco = Integer.parseInt(scanner.nextLine());

            System.out.print("Latitude: (sequência de 6 a 15 digitos pelo sistema decimal podendo ser positivo ou negativo) ");
            double latitude = Double.parseDouble(scanner.nextLine());

            System.out.print("Longitude: (sequência de 6 a 15 digitos pelo sistema decimal podendo ser positivo ou negativo) ");
            double longitude = Double.parseDouble(scanner.nextLine());

            areaRiscoService.cadastrarArea(nome, nivelRisco, latitude, longitude);
            System.out.println("Área cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para nível de risco, latitude e longitude.");
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível cadastrar a área. Verifique se os dados estão corretos.");
        }
    }

    private static void listarAreasRisco() {
        try {
            List<AreaRisco> areas = areaRiscoService.listarAreas();
            if (areas.isEmpty()) {
                System.out.println("Nenhuma área cadastrada.");
            } else {
                System.out.println("\nÁreas de Risco:");
                areas.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível listar as áreas. Tente novamente.");
        }
    }

    private static void criarAlerta() {
        try {
            // Listar áreas disponíveis
            System.out.println("\nÁreas disponíveis: ");
            listarAreasRisco();
            System.out.print("Digite o ID da área: ");
            Long idArea = Long.parseLong(scanner.nextLine());

            System.out.print("Tipo de evento (ex: Chuva, Vento, etc.): ");
            String tipoEvento = scanner.nextLine();

            System.out.print("Descrição do alerta: ");
            String descricao = scanner.nextLine();

            System.out.print("Nível de severidade (1-5): ");
            int nivelSeveridade = Integer.parseInt(scanner.nextLine());

            alertaService.criarAlerta(idArea, tipoEvento, descricao, nivelSeveridade);
            System.out.println("Alerta criado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para ID e nível de severidade.");
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível criar o alerta. Verifique se os dados estão corretos.");
        }
    }

    private static void listarAlertas() {
        try {
            List<Alerta> alertas = alertaService.listarAlertas();
            if (alertas.isEmpty()) {
                System.out.println("Nenhum alerta cadastrado.");
            } else {
                System.out.println("\nAlertas:");
                alertas.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível listar os alertas. Tente novamente.");
        }
    }

    private static void atualizarAreaRisco() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarAreasRisco();
            System.out.print("Digite o ID da área a ser atualizada: ");
            Long id = Long.parseLong(scanner.nextLine());

            AreaRisco area = em.find(AreaRisco.class, id);
            if (area != null) {
                System.out.print("Novo nome da área: ");
                area.setNome(scanner.nextLine());

                System.out.print("Novo nível de risco (1-5): ");
                area.setNivelRisco(Integer.parseInt(scanner.nextLine()));

                System.out.print("Nova latitude: ");
                area.setLatitude(Double.parseDouble(scanner.nextLine()));

                System.out.print("Nova longitude: ");
                area.setLongitude(Double.parseDouble(scanner.nextLine()));

                tx.begin();
                em.merge(area);
                tx.commit();

                System.out.println("Área atualizada com sucesso!");
            } else {
                System.out.println("Área não encontrada!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para ID, nível de risco, latitude e longitude.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível atualizar a área. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void removerAreaRisco() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarAreasRisco();
            System.out.print("Digite o ID da área a ser removida: ");
            Long id = Long.parseLong(scanner.nextLine());

            AreaRisco area = em.find(AreaRisco.class, id);
            if (area != null) {
                tx.begin();
                em.remove(area);
                tx.commit();
                System.out.println("Área removida com sucesso!");
            } else {
                System.out.println("Área não encontrada!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para o ID da área.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível remover a área. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void atualizarAlerta() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarAlertas();
            System.out.print("Digite o ID do alerta a ser atualizado: ");
            Long id = Long.parseLong(scanner.nextLine());

            Alerta alerta = em.find(Alerta.class, id);
            if (alerta != null) {
                System.out.print("Nova descrição do alerta: ");
                String descricao = scanner.nextLine();

                System.out.print("Novo nível de severidade (1-5): ");
                int nivelSeveridade = Integer.parseInt(scanner.nextLine());
                Alerta.Severidade severidade;
                switch (nivelSeveridade) {
                    case 1 -> severidade = Alerta.Severidade.BAIXO;
                    case 2 -> severidade = Alerta.Severidade.MÉDIO;
                    case 3 -> severidade = Alerta.Severidade.ALTO;
                    case 4 -> severidade = Alerta.Severidade.ALTÍSSIMO;
                    case 5 -> severidade = Alerta.Severidade.CALAMIDADE;
                    default -> throw new IllegalArgumentException("Nível de severidade inválido");
                }

                System.out.print("Nova orientação à população: ");
                String orientacao = scanner.nextLine();

                alertaService.atualizarAlerta(id, severidade, descricao, orientacao);
                System.out.println("Alerta atualizado com sucesso!");
            } else {
                System.out.println("Alerta não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para ID e nível de severidade.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível atualizar o alerta. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void desativarAlerta() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarAlertas();
            System.out.print("Digite o ID do alerta a ser desativado: ");
            Long id = Long.parseLong(scanner.nextLine());

            Alerta alerta = em.find(Alerta.class, id);
            if (alerta != null) {
                tx.begin();
                alerta.setStatus(false);
                em.merge(alerta);
                tx.commit();
                System.out.println("Alerta desativado com sucesso!");
            } else {
                System.out.println("Alerta não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para o ID do alerta.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível desativar o alerta. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void cadastrarUsuario() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            System.out.print("Nome do usuário: ");
            String nome = scanner.nextLine();

            System.out.print("Email: (exemplo: usuario@gmail.com) ");
            String email = scanner.nextLine();

            System.out.print("Telefone: (exemplo: 55 11 99999-9999) ");
            String telefone = scanner.nextLine();

            System.out.print("Notificar por email? (S/N): ");
            boolean notifEmail = scanner.nextLine().equalsIgnoreCase("S");

            System.out.print("Notificar por SMS? (S/N): ");
            boolean notifSms = scanner.nextLine().equalsIgnoreCase("S");

            Usuario usuario = new Usuario(nome, email, telefone, notifEmail, notifSms);

            tx.begin();
            em.persist(usuario);
            tx.commit();

            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível cadastrar o usuário. Verifique se o email já existe.");
        } finally {
            em.close();
        }
    }

    private static void listarUsuarios() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            List<Usuario> usuarios = query.getResultList();

            if (usuarios.isEmpty()) {
                System.out.println("Nenhum usuário cadastrado.");
            } else {
                System.out.println("\nUsuários:");
                usuarios.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro: Não foi possível listar os usuários. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void atualizarUsuario() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarUsuarios();
            System.out.print("Digite o ID do usuário a ser atualizado: ");
            Long id = Long.parseLong(scanner.nextLine());

            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                System.out.print("Novo nome do usuário: ");
                usuario.setNome(scanner.nextLine());

                System.out.print("Novo email: (exemplo: usuario@gmail.com) ");
                usuario.setEmail(scanner.nextLine());

                System.out.print("Novo telefone: (exemplo: 55 11 99999-9999) ");
                usuario.setTelefone(scanner.nextLine());

                System.out.print("Notificar por email? (S/N): ");
                usuario.setNotifEmail(scanner.nextLine().equalsIgnoreCase("S"));

                System.out.print("Notificar por SMS? (S/N): ");
                usuario.setNotifSms(scanner.nextLine().equalsIgnoreCase("S"));

                tx.begin();
                em.merge(usuario);
                tx.commit();

                System.out.println("Usuário atualizado com sucesso!");
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para ID.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível atualizar o usuário. Verifique se o email já existe.");
        } finally {
            em.close();
        }
    }

    private static void removerUsuario() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarUsuarios();
            System.out.print("Digite o ID do usuário a ser removido: ");
            Long id = Long.parseLong(scanner.nextLine());

            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                tx.begin();
                em.remove(usuario);
                tx.commit();
                System.out.println("Usuário removido com sucesso!");
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para o ID do usuário.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível remover o usuário. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void associarUsuarioArea() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarUsuarios();
            System.out.print("Digite o ID do usuário: ");
            Long idUsuario = Long.parseLong(scanner.nextLine());

            listarAreasRisco();
            System.out.print("Digite o ID da área a ser associada: ");
            Long idArea = Long.parseLong(scanner.nextLine());

            Usuario usuario = em.find(Usuario.class, idUsuario);
            AreaRisco area = em.find(AreaRisco.class, idArea);

            if (usuario != null && area != null) {
                tx.begin();
                area.adicionarUsuario(usuario);
                em.merge(usuario);
                tx.commit();
                System.out.println("Usuário associado à área com sucesso!");
            } else {
                System.out.println("Usuário ou área não encontrados!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para os IDs.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível associar o usuário à área. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static void desassociarUsuarioArea() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            listarUsuarios();
            System.out.print("Digite o ID do usuário: ");
            Long idUsuario = Long.parseLong(scanner.nextLine());

            Usuario usuario = em.find(Usuario.class, idUsuario);

            if (usuario != null) {
                if (usuario.getAreaRisco() != null) {
                    tx.begin();
                    usuario.getAreaRisco().removerUsuario(usuario);
                    em.merge(usuario);
                    tx.commit();
                    System.out.println("Usuário desassociado da área com sucesso!");
                } else {
                    System.out.println("Usuário não está associado a nenhuma área!");
                }
            } else {
                System.out.println("Usuário não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números para os IDs.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            System.out.println("Erro: Não foi possível desassociar o usuário da área. Tente novamente.");
        } finally {
            em.close();
        }
    }

    private static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
} 
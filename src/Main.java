import dao.CountryDAO;
import model.Country;
import service.ApiService;
import util.DatabaseUtil;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CountryDAO paisDAO = new CountryDAO();
    private static final ApiService apiService = new ApiService();

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║   SISTEMA DE GERENCIAMENTO DE PAÍSES - REST COUNTRIES ║");
        System.out.println("║          Projeto Prático Individual - Java            ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();

        DatabaseUtil.inicializarBanco();
        System.out.println();

        boolean executando = true;
        while (executando) {
            executando = exibirMenu();
        }

        scanner.close();
        DatabaseUtil.fecharConexao();
        System.out.println("\n✓ Sistema encerrado com sucesso. Até logo!");
    }

    private static boolean exibirMenu() {
        System.out.println("\n┌─────────────────── MENU PRINCIPAL ────────────────────┐");
        System.out.println("│ 1. Buscar país da API e salvar no banco              │");
        System.out.println("│ 2. Listar todos os países salvos                     │");
        System.out.println("│ 3. Buscar país por ID                                │");
        System.out.println("│ 4. Atualizar informações de um país                  │");
        System.out.println("│ 5. Deletar país do banco                             │");
        System.out.println("│ 6. Estatísticas do banco                             │");
        System.out.println("│ 0. Sair                                              │");
        System.out.println("└───────────────────────────────────────────────────────┘");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (opcao) {
                case 1:
                    buscarESalvarPais();
                    break;
                case 2:
                    listarTodosPaises();
                    break;
                case 3:
                    buscarPaisPorId();
                    break;
                case 4:
                    atualizarPais();
                    break;
                case 5:
                    deletarPais();
                    break;
                case 6:
                    exibirEstatisticas();
                    break;
                case 0:
                    return false;
                default:
                    System.out.println("✗ Opção inválida! Tente novamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ Por favor, digite um número válido.");
        }

        return true;
    }

    private static void buscarESalvarPais() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("         BUSCAR PAÍS NA API E SALVAR NO BANCO");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.print("Digite o nome do país (ex: Brazil, Portugal, Japan): ");
        String nomePais = scanner.nextLine();

        if (nomePais.trim().isEmpty()) {
            System.out.println("✗ Nome do país não pode estar vazio!");
            return;
        }

        Country pais = apiService.buscarPaisPorNome(nomePais);

        if (pais != null) {
            System.out.println("\n" + pais.toString());
            System.out.print("\nDeseja salvar este país no banco? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                if (paisDAO.inserir(pais)) {
                    System.out.println("✓ País salvo com sucesso no banco de dados!");
                } else {
                    System.out.println("✗ Erro ao salvar país no banco.");
                }
            } else {
                System.out.println("Operação cancelada.");
            }
        } else {
            System.out.println("✗ Não foi possível buscar o país na API.");
        }
    }

    private static void listarTodosPaises() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("              LISTA DE PAÍSES NO BANCO");
        System.out.println("═══════════════════════════════════════════════════════");

        List<Country> paises = paisDAO.listarTodos();

        if (paises.isEmpty()) {
            System.out.println("✗ Nenhum país cadastrado no banco.");
            System.out.println("💡 Use a opção 1 para buscar países na API!");
        } else {
            System.out.println("Total de países: " + paises.size());
            System.out.println();
            
            for (Country pais : paises) {
                System.out.println(pais.toStringResumido());
            }

            System.out.print("\nDeseja ver detalhes de algum país? (Digite o ID ou 0 para voltar): ");
            try {
                int id = Integer.parseInt(scanner.nextLine());
                if (id > 0) {
                    Country paisDetalhado = paisDAO.buscarPorId(id);
                    if (paisDetalhado != null) {
                        System.out.println("\n" + paisDetalhado.toString());
                    } else {
                        System.out.println("✗ País não encontrado.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("✗ ID inválido.");
            }
        }
    }

    private static void buscarPaisPorId() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("                 BUSCAR PAÍS POR ID");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.print("Digite o ID do país: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Country pais = paisDAO.buscarPorId(id);

            if (pais != null) {
                System.out.println("\n" + pais.toString());
            } else {
                System.out.println("✗ País com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido! Digite um número.");
        }
    }

    private static void atualizarPais() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("              ATUALIZAR PAÍS NO BANCO");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.print("Digite o ID do país a ser atualizado: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
            Country pais = paisDAO.buscarPorId(id);

            if (pais == null) {
                System.out.println("✗ País com ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\nDados atuais:");
            System.out.println(pais.toString());
            System.out.println("\n💡 Pressione ENTER para manter o valor atual.");

            System.out.print("\nNovo nome [" + pais.getNome() + "]: ");
            String novoNome = scanner.nextLine();
            if (!novoNome.trim().isEmpty()) {
                pais.setNome(novoNome);
            }

            System.out.print("Nova capital [" + pais.getCapital() + "]: ");
            String novaCapital = scanner.nextLine();
            if (!novaCapital.trim().isEmpty()) {
                pais.setCapital(novaCapital);
            }

            System.out.print("Nova população [" + pais.getPopulacao() + "]: ");
            String novaPopulacao = scanner.nextLine();
            if (!novaPopulacao.trim().isEmpty()) {
                pais.setPopulacao(Long.parseLong(novaPopulacao));
            }

            System.out.print("Nova área [" + pais.getArea() + "]: ");
            String novaArea = scanner.nextLine();
            if (!novaArea.trim().isEmpty()) {
                pais.setArea(Double.parseDouble(novaArea));
            }

            System.out.print("\nConfirmar atualização? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                if (paisDAO.atualizar(pais)) {
                    System.out.println("\n" + pais.toString());
                }
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("✗ Valor inválido digitado!");
        }
    }

    private static void deletarPais() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("               DELETAR PAÍS DO BANCO");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.print("Digite o ID do país a ser deletado: ");

        try {
            int id = Integer.parseInt(scanner.nextLine());
                Country pais = paisDAO.buscarPorId(id);

            if (pais == null) {
                System.out.println("✗ País com ID " + id + " não encontrado.");
                return;
            }

            System.out.println("\nPaís a ser deletado:");
            System.out.println(pais.toString());
            
            System.out.print("\n⚠️  ATENÇÃO: Esta ação não pode ser desfeita!");
            System.out.print("\nConfirmar exclusão? (S/N): ");
            String confirmacao = scanner.nextLine();

            if (confirmacao.equalsIgnoreCase("S")) {
                paisDAO.deletar(id);
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("✗ ID inválido! Digite um número.");
        }
    }

    private static void exibirEstatisticas() {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("              ESTATÍSTICAS DO BANCO");
        System.out.println("═══════════════════════════════════════════════════════");
        
        int totalPaises = paisDAO.contarPaises();
        System.out.println("📊 Total de países cadastrados: " + totalPaises);
        
        if (totalPaises > 0) {
            List<Country> paises = paisDAO.listarTodos();
            
            long totalPopulacao = 0;
            double totalArea = 0;
            
            for (Country p : paises) {
                totalPopulacao += p.getPopulacao();
                totalArea += p.getArea();
            }
            
            System.out.println("👥 População total: " + String.format("%,d", totalPopulacao) + " habitantes");
            System.out.println("🗺️  Área total: " + String.format("%,.2f", totalArea) + " km²");
            System.out.println("📈 Média populacional: " + String.format("%,d", totalPopulacao / totalPaises) + " habitantes");
        }
        
        System.out.print("\n🌐 Testando conexão com API REST Countries... ");
        if (apiService.testarConexaoApi()) {
            System.out.println("✓ Online");
        } else {
            System.out.println("✗ Offline");
        }
    }
}

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        BST arvore = new BST();

        int opcao = 0;

        do {
            System.out.println("\n========== PROJETO NETFLIX BST ==========");
            System.out.println("1 - Ler dados de arquivo");
            System.out.println("2 - Análise 1: Top 10 TV-14 de crime por IMDB");
            System.out.println("3 - Análise 2: N menores TMDB Scores");
            System.out.println("4 - Análise 3: Top 10 filmes longos por IMDB");
            System.out.println("5 - Análise 4: Média IMDB por tipo");
            System.out.println("6 - Análise 5: Top 10 populares por ano");
            System.out.println("7 - Inserir Programa");
            System.out.println("8 - Buscar Programa");
            System.out.println("9 - Remover Programa");
            System.out.println("10 - Exibir altura da árvore");
            System.out.println("11 - Salvar dados em arquivo");
            System.out.println("12 - Encerrar");
            System.out.print("Escolha: ");

            try {
                opcao = Integer.parseInt(entrada.nextLine());
            } catch (Exception e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do arquivo CSV: ");
                    String nomeArquivo = entrada.nextLine();
                    LeitorCSV.lerArquivo(nomeArquivo, arvore);
                    break;

                case 2:
                    if (!verificarArvore(arvore)) {
                        arvore.analise1Top10CrimeTV14();
                    }
                    break;

                case 3:
                    if (!verificarArvore(arvore)) {
                        System.out.print("Digite N, maior que 5: ");
                        int n = Integer.parseInt(entrada.nextLine());

                        if (n > 5) {
                            arvore.analise2PioresTmdb(n);
                        } else {
                            System.out.println("N precisa ser maior que 5.");
                        }
                    }
                    break;

                case 4:
                    if (!verificarArvore(arvore)) {
                        arvore.analise3FilmesLongosBemAvaliados();
                    }
                    break;

                case 5:
                    if (!verificarArvore(arvore)) {
                        arvore.analise4MediaImdbPorTipo();
                    }
                    break;

                case 6:
                    if (!verificarArvore(arvore)) {
                        System.out.print("Digite o ano: ");
                        int ano = Integer.parseInt(entrada.nextLine());
                        arvore.analise5MaisPopularesPorAno(ano);
                    }
                    break;

                case 7:
                    ProgramaNetFlix novo = criarProgramaManual(entrada, arvore);
                    arvore.inserir(novo);
                    System.out.println("Programa inserido com sucesso.");
                    break;

                case 8:
                    if (!verificarArvore(arvore)) {
                        System.out.print("Digite o ID buscado: ");
                        String idBusca = entrada.nextLine();

                        long inicio = System.nanoTime();
                        ProgramaNetFlix encontrado = arvore.buscar(idBusca);
                        long fim = System.nanoTime();

                        if (encontrado != null) {
                            System.out.println("\nPrograma encontrado:");
                            System.out.println(encontrado);
                        } else {
                            System.out.println("Programa não encontrado.");
                        }

                        System.out.println("Comparações realizadas: " + arvore.getComparacoes());
                        System.out.println("Tempo de busca: " + (fim - inicio) + " nanossegundos");
                    }
                    break;

                case 9:
                    if (!verificarArvore(arvore)) {
                        System.out.print("Digite o ID para remover: ");
                        String idRemover = entrada.nextLine();

                        ProgramaNetFlix antes = arvore.buscar(idRemover);

                        if (antes != null) {
                            arvore.remover(idRemover);
                            System.out.println("Programa removido com sucesso.");
                        } else {
                            System.out.println("ID não encontrado.");
                        }
                    }
                    break;

                case 10:
                    if (!verificarArvore(arvore)) {
                        System.out.println("Altura da árvore: " + arvore.altura());
                        System.out.println("Quantidade de nós: " + arvore.quantidadeNos());
                    }
                    break;

                case 11:
                    if (!verificarArvore(arvore)) {
                        System.out.print("Digite o nome do arquivo de saída: ");
                        String saida = entrada.nextLine();
                        arvore.salvarEmArquivo(saida);
                    }
                    break;

                case 12:
                    System.out.println("Aplicação encerrada.");
                    break;

                default:
                    System.out.println("Opção inválida.");
            }

        } while (opcao != 12);

        entrada.close();
    }

    private static boolean verificarArvore(BST arvore) {
        if (arvore.estaVazia()) {
            System.out.println("A árvore está vazia. Leia o arquivo primeiro.");
            return true;
        }

        return false;
    }

    private static ProgramaNetFlix criarProgramaManual(Scanner entrada, BST arvore) {
        System.out.print("Tipo, MOVIE ou SHOW: ");
        String type = entrada.nextLine().toUpperCase();

        String prefixo;

        if (type.equals("SHOW")) {
            prefixo = "ts";
        } else {
            prefixo = "tm";
            type = "MOVIE";
        }

        String id;
        int numero = arvore.quantidadeNos() + 1;

        do {
            id = prefixo + numero;
            numero++;
        } while (arvore.buscar(id) != null);

        System.out.println("ID gerado: " + id);

        System.out.print("Título: ");
        String title = entrada.nextLine();

        System.out.print("Descrição: ");
        String description = entrada.nextLine();

        System.out.print("Ano de lançamento: ");
        int releaseYear = Integer.parseInt(entrada.nextLine());

        System.out.print("Classificação etária: ");
        String ageCertification = entrada.nextLine();

        System.out.print("Duração: ");
        int runtime = Integer.parseInt(entrada.nextLine());

        System.out.print("Gêneros: ");
        String genres = entrada.nextLine();

        System.out.print("Países de produção: ");
        String productionCountries = entrada.nextLine();

        System.out.print("Temporadas, 0 se for filme: ");
        int seasons = Integer.parseInt(entrada.nextLine());

        System.out.print("IMDB ID: ");
        String imdbId = entrada.nextLine();

        System.out.print("IMDB Score: ");
        double imdbScore = Double.parseDouble(entrada.nextLine());

        System.out.print("IMDB Votos: ");
        int imdbVotes = Integer.parseInt(entrada.nextLine());

        System.out.print("TMDB Popularidade: ");
        double tmdbPopularity = Double.parseDouble(entrada.nextLine());

        System.out.print("TMDB Score: ");
        double tmdbScore = Double.parseDouble(entrada.nextLine());

        return new ProgramaNetFlix(id, title, type, description, releaseYear, ageCertification,
                runtime, genres, productionCountries, seasons, imdbId, imdbScore,
                imdbVotes, tmdbPopularity, tmdbScore);
    }
}
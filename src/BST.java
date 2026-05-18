import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class BST {
    private No raiz;
    private int comparacoes;

    public BST() {
        this.raiz = null;
        this.comparacoes = 0;
    }

    public boolean estaVazia() {
        return raiz == null;
    }

    public void inserir(ProgramaNetFlix programa) {
        raiz = inserirRec(raiz, programa);
    }

    private No inserirRec(No atual, ProgramaNetFlix programa) {
        if (atual == null) {
            return new No(programa);
        }

        if (programa.getId().compareTo(atual.getPrograma().getId()) < 0) {
            atual.setEsquerda(inserirRec(atual.getEsquerda(), programa));
        } else if (programa.getId().compareTo(atual.getPrograma().getId()) > 0) {
            atual.setDireita(inserirRec(atual.getDireita(), programa));
        }

        return atual;
    }

    public ProgramaNetFlix buscar(String id) {
        comparacoes = 0;
        return buscarRec(raiz, id);
    }

    private ProgramaNetFlix buscarRec(No atual, String id) {
        if (atual == null) {
            return null;
        }

        comparacoes++;

        if (id.equals(atual.getPrograma().getId())) {
            return atual.getPrograma();
        }

        if (id.compareTo(atual.getPrograma().getId()) < 0) {
            return buscarRec(atual.getEsquerda(), id);
        } else {
            return buscarRec(atual.getDireita(), id);
        }
    }

    public int getComparacoes() {
        return comparacoes;
    }

    public void remover(String id) {
        raiz = removerRec(raiz, id);
    }

    private No removerRec(No atual, String id) {
        if (atual == null) {
            return null;
        }

        if (id.compareTo(atual.getPrograma().getId()) < 0) {
            atual.setEsquerda(removerRec(atual.getEsquerda(), id));
        } else if (id.compareTo(atual.getPrograma().getId()) > 0) {
            atual.setDireita(removerRec(atual.getDireita(), id));
        } else {
            if (atual.getEsquerda() == null) {
                return atual.getDireita();
            }

            if (atual.getDireita() == null) {
                return atual.getEsquerda();
            }

            ProgramaNetFlix menor = encontrarMenor(atual.getDireita());
            atual.setPrograma(menor);
            atual.setDireita(removerRec(atual.getDireita(), menor.getId()));
        }

        return atual;
    }

    private ProgramaNetFlix encontrarMenor(No atual) {
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }

        return atual.getPrograma();
    }

    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(No atual) {
        if (atual == null) {
            return -1;
        }

        int esquerda = alturaRec(atual.getEsquerda());
        int direita = alturaRec(atual.getDireita());

        return 1 + Math.max(esquerda, direita);
    }

    public int quantidadeNos() {
        return quantidadeNosRec(raiz);
    }

    private int quantidadeNosRec(No atual) {
        if (atual == null) {
            return 0;
        }

        return 1 + quantidadeNosRec(atual.getEsquerda()) + quantidadeNosRec(atual.getDireita());
    }

    public void salvarEmArquivo(String nomeArquivo) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo));

            writer.println("id,title,type,description,release_year,age_certification,runtime,genres,production_countries,seasons,imdb_id,imdb_score,imdb_votes,tmdb_popularity,tmdb_score");
            salvarEmOrdem(raiz, writer);

            writer.close();
            System.out.println("Arquivo salvo com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    private void salvarEmOrdem(No atual, PrintWriter writer) {
        if (atual != null) {
            salvarEmOrdem(atual.getEsquerda(), writer);
            writer.println(atual.getPrograma().toCSV());
            salvarEmOrdem(atual.getDireita(), writer);
        }
    }

    public void analise1Top10CrimeTV14() {
        ProgramaNetFlix[] top = new ProgramaNetFlix[10];
        analise1PreOrdem(raiz, top);

        System.out.println("\nTOP 10 títulos TV-14 do gênero crime por IMDB Score:");
        imprimirTop(top, "imdb");
    }

    private void analise1PreOrdem(No atual, ProgramaNetFlix[] top) {
        if (atual != null) {
            ProgramaNetFlix p = atual.getPrograma();

            if (p.getAgeCertification().equalsIgnoreCase("TV-14") &&
                    p.getGenres().toLowerCase().contains("crime")) {
                inserirTopImdb(top, p);
            }

            analise1PreOrdem(atual.getEsquerda(), top);
            analise1PreOrdem(atual.getDireita(), top);
        }
    }

    public void analise2PioresTmdb(int n) {
        ProgramaNetFlix[] piores = new ProgramaNetFlix[n];
        analise2EmOrdem(raiz, piores);

        System.out.println("\n" + n + " títulos com menores TMDB Scores:");
        imprimirTop(piores, "tmdb");
    }

    private void analise2EmOrdem(No atual, ProgramaNetFlix[] piores) {
        if (atual != null) {
            analise2EmOrdem(atual.getEsquerda(), piores);
            inserirPiorTmdb(piores, atual.getPrograma());
            analise2EmOrdem(atual.getDireita(), piores);
        }
    }

    public void analise3FilmesLongosBemAvaliados() {
        ProgramaNetFlix[] top = new ProgramaNetFlix[10];
        analise3PosOrdem(raiz, top);

        System.out.println("\nTOP 10 filmes com mais de 120 minutos por IMDB Score:");
        imprimirTop(top, "imdb");
    }

    private void analise3PosOrdem(No atual, ProgramaNetFlix[] top) {
        if (atual != null) {
            analise3PosOrdem(atual.getEsquerda(), top);
            analise3PosOrdem(atual.getDireita(), top);

            ProgramaNetFlix p = atual.getPrograma();

            if (p.getType().equalsIgnoreCase("MOVIE") && p.getRuntime() > 120) {
                inserirTopImdb(top, p);
            }
        }
    }

    public void analise4MediaImdbPorTipo() {
        int qtdMovie = 0;
        int qtdShow = 0;
        double somaMovie = 0;
        double somaShow = 0;

        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        while (!fila.isEmpty()) {
            No atual = fila.poll();
            ProgramaNetFlix p = atual.getPrograma();

            if (p.getType().equalsIgnoreCase("MOVIE")) {
                somaMovie += p.getImdbScore();
                qtdMovie++;
            } else if (p.getType().equalsIgnoreCase("SHOW")) {
                somaShow += p.getImdbScore();
                qtdShow++;
            }

            if (atual.getEsquerda() != null) {
                fila.add(atual.getEsquerda());
            }

            if (atual.getDireita() != null) {
                fila.add(atual.getDireita());
            }
        }

        System.out.println("\nMédia de IMDB Score por tipo:");
        System.out.printf("MOVIE: %.2f\n", qtdMovie > 0 ? somaMovie / qtdMovie : 0);
        System.out.printf("SHOW: %.2f\n", qtdShow > 0 ? somaShow / qtdShow : 0);
    }

    public void analise5MaisPopularesPorAno(int ano) {
        ProgramaNetFlix[] top = new ProgramaNetFlix[10];
        analise5EmOrdem(raiz, top, ano);

        System.out.println("\nTOP 10 títulos mais populares no TMDB no ano " + ano + ":");
        imprimirTop(top, "popularidade");
    }

    private void analise5EmOrdem(No atual, ProgramaNetFlix[] top, int ano) {
        if (atual != null) {
            analise5EmOrdem(atual.getEsquerda(), top, ano);

            if (atual.getPrograma().getReleaseYear() == ano) {
                inserirTopPopularidade(top, atual.getPrograma());
            }

            analise5EmOrdem(atual.getDireita(), top, ano);
        }
    }

    private void inserirTopImdb(ProgramaNetFlix[] top, ProgramaNetFlix p) {
        for (int i = 0; i < top.length; i++) {
            if (top[i] == null || p.getImdbScore() > top[i].getImdbScore()) {
                for (int j = top.length - 1; j > i; j--) {
                    top[j] = top[j - 1];
                }

                top[i] = p;
                break;
            }
        }
    }

    private void inserirTopPopularidade(ProgramaNetFlix[] top, ProgramaNetFlix p) {
        for (int i = 0; i < top.length; i++) {
            if (top[i] == null || p.getTmdbPopularity() > top[i].getTmdbPopularity()) {
                for (int j = top.length - 1; j > i; j--) {
                    top[j] = top[j - 1];
                }

                top[i] = p;
                break;
            }
        }
    }

    private void inserirPiorTmdb(ProgramaNetFlix[] piores, ProgramaNetFlix p) {
        for (int i = 0; i < piores.length; i++) {
            if (piores[i] == null || p.getTmdbScore() < piores[i].getTmdbScore()) {
                for (int j = piores.length - 1; j > i; j--) {
                    piores[j] = piores[j - 1];
                }

                piores[i] = p;
                break;
            }
        }
    }

    private void imprimirTop(ProgramaNetFlix[] vetor, String criterio) {
        for (int i = 0; i < vetor.length; i++) {
            if (vetor[i] != null) {
                ProgramaNetFlix p = vetor[i];

                System.out.println("\n" + (i + 1) + "º lugar");
                System.out.println("ID: " + p.getId());
                System.out.println("Título: " + p.getTitle());
                System.out.println("Tipo: " + p.getType());
                System.out.println("Ano: " + p.getReleaseYear());
                System.out.println("Gêneros: " + p.getGenres());

                if (criterio.equals("imdb")) {
                    System.out.println("IMDB Score: " + p.getImdbScore());
                } else if (criterio.equals("tmdb")) {
                    System.out.println("TMDB Score: " + p.getTmdbScore());
                } else {
                    System.out.println("TMDB Popularidade: " + p.getTmdbPopularity());
                }
            }
        }
    }
}
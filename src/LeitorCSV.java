import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LeitorCSV {
    public static int lerArquivo(String nomeArquivo, BST arvore) {
        int inseridos = 0;
        int descartados = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
            br.readLine();

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] campos = separarCSV(linha);

                if (campos.length == 15 && camposPrincipaisPreenchidos(campos)) {
                    ProgramaNetFlix programa = new ProgramaNetFlix(
                            campos[0],
                            campos[1],
                            campos[2],
                            campos[3],
                            parseIntSeguro(campos[4]),
                            campos[5],
                            parseIntSeguro(campos[6]),
                            campos[7],
                            campos[8],
                            parseIntSeguro(campos[9]),
                            campos[10],
                            parseDoubleSeguro(campos[11]),
                            parseIntSeguro(campos[12]),
                            parseDoubleSeguro(campos[13]),
                            parseDoubleSeguro(campos[14])
                    );

                    arvore.inserir(programa);
                    inseridos++;
                } else {
                    descartados++;
                }
            }

            br.close();

            System.out.println("Leitura finalizada.");
            System.out.println("Registros inseridos: " + inseridos);
            System.out.println("Registros descartados: " + descartados);

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }

        return inseridos;
    }

    private static boolean camposPrincipaisPreenchidos(String[] campos) {
        return !campos[0].trim().isEmpty()
                && !campos[1].trim().isEmpty()
                && !campos[2].trim().isEmpty()
                && !campos[3].trim().isEmpty()
                && !campos[4].trim().isEmpty()
                && !campos[6].trim().isEmpty()
                && !campos[7].trim().isEmpty()
                && !campos[8].trim().isEmpty();
    }

    private static int parseIntSeguro(String valor) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                return 0;
            }

            return Integer.parseInt(valor);
        } catch (Exception e) {
            return 0;
        }
    }

    private static double parseDoubleSeguro(String valor) {
        try {
            if (valor == null || valor.trim().isEmpty()) {
                return 0;
            }

            return Double.parseDouble(valor);
        } catch (Exception e) {
            return 0;
        }
    }

    private static String[] separarCSV(String linha) {
        ArrayList<String> campos = new ArrayList<>();
        StringBuilder campoAtual = new StringBuilder();
        boolean dentroAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (c == '"') {
                if (dentroAspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                    campoAtual.append('"');
                    i++;
                } else {
                    dentroAspas = !dentroAspas;
                }
            } else if (c == ',' && !dentroAspas) {
                campos.add(campoAtual.toString().trim());
                campoAtual.setLength(0);
            } else {
                campoAtual.append(c);
            }
        }

        campos.add(campoAtual.toString().trim());

        return campos.toArray(new String[0]);
    }
}
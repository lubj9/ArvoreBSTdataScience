public class No {
    private ProgramaNetFlix programa;
    private No esquerda;
    private No direita;

    public No(ProgramaNetFlix programa) {
        this.programa = programa;
        this.esquerda = null;
        this.direita = null;
    }

    public ProgramaNetFlix getPrograma() {
        return programa;
    }

    public void setPrograma(ProgramaNetFlix programa) {
        this.programa = programa;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
    }
}
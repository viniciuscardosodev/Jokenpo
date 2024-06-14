

/**
 *
 * @author user
 */
public class Partida {
    
    private String desafiante;
    private String desafiado;
    private String jogadaDesafiante;
    private String jogadaDesafiado;
    private String ganhador;
    private String[] opcoes = {"PEDRA", "PAPEL", "TESOURA"};

    public String getDesafiante() {
        return desafiante;
    }

    public void setDesafiante(String desafiante) {
        this.desafiante = desafiante;
    }

    public String getDesafiado() {
        return desafiado;
    }

    public void setDesafiado(String desafiado) {
        this.desafiado = desafiado;
    }

    public String getJogadaDesafiante() {
        return jogadaDesafiante;
    }

    public void setJogadaDesafiante(String jogadaDesafiante) {
        this.jogadaDesafiante = jogadaDesafiante;
    }

    public String getJogadaDesafiado() {
        return jogadaDesafiado;
    }

    public void setJogadaDesafiado(String jogadaDesafiado) {
        this.jogadaDesafiado = jogadaDesafiado;
    }

    public String getGanhador() {
        return ganhador;
    }

    public void setGanhador(String ganhador) {
        this.ganhador = ganhador;
    }
    
   public void verificarGanhador(){
       int j1 = -1, j2 = -1;
       String ganhador;
       
       for (int i = 0; i < opcoes.length; i++) {
           if (jogadaDesafiante.equalsIgnoreCase(opcoes[i])) {
               j1 = i;
           }
           if (jogadaDesafiado.equalsIgnoreCase(opcoes[i])) {
               j2 = i;
           }
       }
       
        if (j1 == j2) {
            ganhador = "EMPATE";
        } else if ((j1 == 0 && j2 == 2) ||
                   (j1 == 1 && j2 == 0) ||
                   (j1 == 2 && j2 == 1)) {
           ganhador = desafiante;
        } else {
             ganhador = desafiado;
        }
        this.ganhador = ganhador;
   }
    
}

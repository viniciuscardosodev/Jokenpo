package entity;

/**
 *
 * @author user
 */
public class Partida {
    
    private String jogador1;
    private String jogador2;
    private String jogadaJ1;
    private String jogadaJ2;
    private String ganhador;
    private String[] opcoes = {"PEDRA", "PAPEL", "TESOURA"};

    public String getJogador1() {
        return jogador1;
    }

    public void setJogador1(String jogador1) {
        this.jogador1 = jogador1;
    }

    public String getJogador2() {
        return jogador2;
    }

    public void setJogador2(String jogador2) {
        this.jogador2 = jogador2;
    }

    public String getJogadaJ1() {
        return jogadaJ1;
    }

    public void setJogadaJ1(String jogadaJ1) {
        this.jogadaJ1 = jogadaJ1;
    }

    public String getJogadaJ2() {
        return jogadaJ2;
    }

    public void setJogadaJ2(String jogadaJ2) {
        this.jogadaJ2 = jogadaJ2;
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
           if (jogadaJ1.equalsIgnoreCase(opcoes[i])) {
               j1 = i;
           }
           if (jogadaJ2.equalsIgnoreCase(opcoes[i])) {
               j2 = i;
           }
       }
       
        if (j1 == j2) {
            ganhador = "EMPATE";
        } else if ((j1 == 0 && j2 == 2) ||
                   (j1 == 1 && j2 == 0) ||
                   (j1 == 2 && j2 == 1)) {
           ganhador = jogador1;
        } else {
             ganhador = jogador2;
        }
        this.ganhador = ganhador;
   }
    
}

package entity;


import interfaces.GraphicInterface;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;

public class Client implements Runnable{
    
    private Socket cliente;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;
    private GraphicInterface g;
    
    
    public Client(Socket cliente, String nick) throws IOException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        this.done = false;
        this.cliente = cliente;
        g = new GraphicInterface();
        g.setMe(nick);
        this.out = new PrintWriter(cliente.getOutputStream(),true);
        this.in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
    } 
    
    public Client(){}
    
    
    @Override
    public void run(){
        
            Thread inter = new Thread(g);
            inter.start();
            
        try{
            
            g.build(this, out, in);
            
            String dadosServidor = "";
            while (!done && (dadosServidor = in.readLine()) != null){
                String[] mensagemDividida = dadosServidor.split(" ");
               
                if (dadosServidor.startsWith("/newPlayer")){
                    g.novoJogador(mensagemDividida[1]);
                } else if (dadosServidor.startsWith("/resultado")){
                    if (mensagemDividida[1].equalsIgnoreCase("EMPATE")) {
                        g.addResultado("Empatou!");
                    } else {
                        g.addResultado(mensagemDividida[1] + " ganhou!");
                    }   
                } else if (dadosServidor.startsWith("/removerJogador")){
                    if (!mensagemDividida[1].equalsIgnoreCase(g.getNick())){
                        g.removerJogador(mensagemDividida[1]);
                    }
                } else if (dadosServidor.startsWith("/setOponente")){
                    JOptionPane.showMessageDialog(null, mensagemDividida[1] + " te desafiou e já fez uma jogada, escolha sua resposta", "Desafio!", JOptionPane.OK_OPTION);
                    g.setOponente(mensagemDividida[1]);
                } else if (dadosServidor.startsWith("/msg")){
                    String mensagemFinal = "";
                    for (int i = 1; i < mensagemDividida.length; i++) {
                        mensagemFinal += " " + mensagemDividida[i];
                    }
                    g.addMensagem(mensagemFinal);
                }
                
            }
        }catch (IOException e){
            shutdown();
            g.dispose();
        }
    }

    public void shutdown(){
        done = true;
        try {
            g.dispose();
            if(!cliente.isClosed()){
                cliente.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }
    
    
    
    
}
package server;

import entity.Partida;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private final ArrayList<ConnectionHandler> listaConn;
    private ServerSocket socketServidor;
    private boolean done;
    private ExecutorService executorService;
    private HashMap<String, ConnectionHandler> listaJogadoresAtivos;
    private List<String> jogadas;

    
    public Server() {
        done = false;
        listaConn = new ArrayList<>();
        listaJogadoresAtivos = new HashMap<>();
        jogadas = new ArrayList<>();
        jogadas.add("TESOURA");
        jogadas.add("PAPEL");
        jogadas.add("PEDRA");
        
    }

    @Override
    public void run() {
        try {
            socketServidor = new ServerSocket(3333);
            executorService = Executors.newCachedThreadPool();
            Thread verificador = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!done) {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Iterator<ConnectionHandler> iterator = listaConn.iterator();
                        while (iterator.hasNext()) {
                            ConnectionHandler ch = iterator.next();
                            if (ch.isClosed()) {
                                iterator.remove();
                                listaJogadoresAtivos.entrySet().removeIf(entry -> entry.getValue().equals(ch));
                            }
                        }
                    }
                }
            });
            verificador.start();

            while (!done) {
                Socket client = socketServidor.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                listaConn.add(handler);
                executorService.execute(handler);
            }
        } catch (IOException e) {
            if (!done) {
                e.printStackTrace();
            }
        } finally {
            shutdown();
        }
    }

    public void broadcast(String mensagem) {
        for (ConnectionHandler ch : listaConn) {
            if (ch != null) {
                ch.sendMessage(mensagem);
            }
        }
    }

    public boolean isNickDisponivel(String nick) {
        return !listaJogadoresAtivos.containsKey(nick);
    }

    public void shutdown() {
        try {
            done = true;
            if (executorService != null) {
                executorService.shutdown();
            }
            if (socketServidor != null && !socketServidor.isClosed()) {
                socketServidor.close();
            }
            for (ConnectionHandler ch : listaConn) {
                ch.shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ConnectionHandler implements Runnable {

        private final Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nick;
        private String oponente;
        private static List<Partida> jogosPendentes;

        public ConnectionHandler(Socket client) {
            this.client = client;
            this.jogosPendentes = new ArrayList<>();
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String nickname = "";
                String message = "";
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("/nick")) {
                        String[] messageSplit = message.split(" ", 2);
                        nickname = messageSplit[1];
                        if (messageSplit.length == 2 && !nickname.isBlank()) {
                           if (isNickDisponivel(nickname)){
                               out.println("OK");
                               this.nick = nickname;
                               listaJogadoresAtivos.put(nickname, this);
                            for (String key : listaJogadoresAtivos.keySet()) {
                                if (listaJogadoresAtivos.get(key).getCliente().isConnected()){
                                    broadcast("/newPlayer " + key);
                                } else {
                                    listaJogadoresAtivos.remove(key);
                                    listaJogadoresAtivos.get(key).shutdown();
                                }
                            }
                           } else {
                               out.println("INVALIDO");
                           }
                    } 
            } else
                if (message.startsWith("/jogada")){
                    boolean pendente = false;
                    Partida p = new Partida();
                    p.setJogador1(this.nick);
                    String jogada = message.split(" ")[1];
                    String op = message.split(" ")[2];
                    this.oponente = op;
                    p.setJogador2(oponente);
                    
                    p.setJogadaJ1(jogada);
                    if (oponente.equalsIgnoreCase("SERVIDOR")){
                        Random r = new Random();
                        int j = r.nextInt(jogadas.size());
                    p.setJogadaJ2(jogadas.get(j));
                    p.verificarGanhador();
                    System.out.println("Jogou contra o sistema");
                    out.println("/resultado " + p.getGanhador());
                    } else {
                      String jogador1;
                      String jogador2;
                      for (int i = 0; i < jogosPendentes.size(); i++) {
                          jogador1 = jogosPendentes.get(i).getJogador1();
                          jogador2 = jogosPendentes.get(i).getJogador2();
                          if ((jogador1.equals(this.nick) || jogador1.equals(oponente)) &&
                              (jogador2.equals(nick) || jogador2.equals(oponente)))
                          {
                              jogosPendentes.get(i).setJogadaJ2(jogada);
                              jogosPendentes.get(i).verificarGanhador();
                              out.println("/resultado " + jogosPendentes.get(i).getGanhador());
                              listaJogadoresAtivos.get(p.getJogador2()).out.println("/resultado " + jogosPendentes.get(i).getGanhador());
                              this.jogosPendentes.remove(i);
                              pendente = true;
                              break;
                          }
                      }
                      if (!pendente) {
                          jogosPendentes.add(p);
                          listaJogadoresAtivos.get(oponente).out.println("/setOponente " + this.nick);
                      }
                      
                    }
                } else if (message.startsWith("/quit")){
                    broadcast("/removerJogador " + this.nick);
                    shutdown();
                } else if (message.startsWith("/msg")){
                    String[] mensagemArray = message.split(" ");
                    String mensagemFinal = "";
                    for (int i = 1; i < mensagemArray.length; i++) {
                        mensagemFinal += " " + mensagemArray[i];
                    }
                    System.out.println("Mensagem recebida de " + nick + mensagemFinal);
                    broadcast("/msg " + this.nick + " disse: " + mensagemFinal);
                }
                
            }} catch (IOException e) {
                shutdown();
            } 
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        public void shutdown() {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
                if (client != null && !client.isClosed()) {
                    client.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public boolean isClosed() {
            return client.isClosed();
        }

        public Socket getCliente() {
            return this.client;
        }
    }

    public static void main(String[] args) {
        new Server().run();
    }
}


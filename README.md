# Jogo Pedra, Papel e Tesoura

Este projeto consiste em um jogo (Pedra, Papel e Tesoura) desenvolvido em Java, que permite a realização de partidas entre dois jogadores ou entre um jogador e o computador por meio de conexão com Sockets, possibilitando tanto rede local como computadores de diferentes redes.

## Participantes

- Lucas Caroba Restivo - 1252313985
- Gustavo Silva de Lima - 12523126193
- Vinícius Gonçalves Cardoso - 12523125969

## Índice

- [Modos de Jogo](#modos-de-jogo)
  - [Modo Jogador vs Jogador (PvP)](#modo-jogador-vs-jogador-pvp)
  - [Modo Jogador vs Computador (PvE)](#modo-jogador-vs-computador-pve)
- [Funcionalidade do Projeto](#funcionalidade-do-projeto)
- [Estrutura do Jogo](#estrutura-do-jogo)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Iniciar o Jogo](#iniciar-o-jogo)
- [Selecionar o Modo de Jogo](#selecionar-o-modo-de-jogo)
- [Jogabilidade](#jogabilidade)
- [Considerações Finais](#considerações-finais)

## Modos de Jogo

### Modo Jogador vs Jogador (PvP)

- Dois jogadores humanos competem entre si.
- O jogo alterna turnos entre os jogadores, com cada um escolhendo entre Pedra, Papel ou Tesoura.
- O resultado de cada rodada é determinado de acordo com as regras clássicas do Jokempo.

### Modo Jogador vs Computador (PvE)

- Um jogador humano compete contra o computador.
- O computador faz suas escolhas de forma aleatória, criando um desafio imprevisível para o jogador.
- Este modo é ideal para praticar e testar a sorte e estratégia do jogador.

## Funcionalidade do Projeto

### Modo Jogador vs Jogador (PvP)

- Dois jogadores humanos competem entre si.
- O jogo alterna turnos entre os jogadores, com cada um escolhendo entre Pedra, Papel ou Tesoura.
- O resultado de cada rodada é determinado de acordo com as regras clássicas do Jokempo.

### Modo Jogador vs Computador (PvE)

- Um jogador humano compete contra o computador.
- O computador faz suas escolhas de forma aleatória, criando um desafio imprevisível para o jogador.
- Este modo é ideal para praticar e testar a sorte e estratégia do jogador.

## Estrutura do Jogo

### Interface Gráfica de Usuário

- Desenvolvida para ser intuitiva e de fácil navegação e muito simples.
- Utiliza comandos de console para interação, garantindo compatibilidade com diferentes sistemas operacionais.

### Lógica do Jogo

- Pedra vence Tesoura
- Tesoura vence Papel
- Papel vence Pedra
- O jogo tem como condição de conclusão de partidas sendo elas empate, vitória e derrota.

## Tecnologias Utilizadas

- Linguagem de Programação: Java, Spring Boot e Java GUI
- Ambiente de Desenvolvimento: Console de comando padrão do sistema operacional.
- Ferramentas e Bibliotecas: Utilização de bibliotecas padrão do Java.

## Iniciar o Jogo

- Execute o jogo a partir do console rodando tanto o servidor, quanto a classe de display.
- Conectar no IP remoto ou local.

## Selecionar o Modo de Jogo

- Escolha entre Jogador vs Jogador (PvP) com a conexão de outro player ou Jogador vs Computador (PvE).

## Jogabilidade

- Siga as instruções exibidas no console para realizar as jogadas.
- Cada jogador deve escolher entre Pedra, Papel ou Tesoura.
- O console exibirá o resultado de cada rodada.
- O jogo pode ser encerrado a qualquer momento apenas finalizando o aplicativo.

## Considerações Finais

O desenvolvimento deste projeto foi feito por meio do estudo na matéria de Sistemas Distribuídos e Mobile, usando tecnologias ensinadas tanto na aula quanto aprendidas em casa. Esperamos que tenha uma boa experiência com o nosso produto e que sirva de aprendizado para todos do grupo. Este README tem como intuito auxiliar qualquer um que tente usar o aplicativo, tornando o mesmo mais simples e melhor para o uso.

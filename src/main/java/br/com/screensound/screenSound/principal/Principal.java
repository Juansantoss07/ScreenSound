package br.com.screensound.screenSound.principal;

import br.com.screensound.screenSound.modelos.Artista;
import br.com.screensound.screenSound.modelos.Musica;
import br.com.screensound.screenSound.modelos.TipoArtista;
import br.com.screensound.screenSound.repository.ArtistaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ArtistaRepository repositorio;
    Scanner leitura = new Scanner(System.in);

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                        ********** SCREEN SOUND **********
                        
                        1 - Cadastrar artistas
                        2 - Cadastrar músicas 
                        3 - Listar músicas
                        4 - Buscar músicas por artistas
                        
                        0 - Sair
                    """;
            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao){
                case 0:
                    System.out.println("Saindo... Bye Bye!");
                    break;
                case 1:
                    cadastrarArtistas();
                    break;
                case 2:
                    cadastrarMusicas();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicasPorArtista();
                    break;
            }
        }
    }

    private void buscarMusicasPorArtista() {
        System.out.println("Digite o nome do artista que deseja buscar músicas:");
        var opcaoArtistaMusicas = leitura.nextLine();
        List<Musica> musicasPorArtista = repositorio.musicasPorArtista(opcaoArtistaMusicas);
        musicasPorArtista.forEach(System.out::println);
    }

    private void listarMusicas() {
        List<Artista> musicas = repositorio.findAll();
        musicas.forEach(System.out::println);
    }

    private void cadastrarMusicas() {
        System.out.println("Cadastrar música de qual artista?:");
        var nome = leitura.nextLine();
        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nome);
        if (artista.isPresent()){
            System.out.println("Informe o nome da música:");
            var nomeMusica = leitura.nextLine();
            Musica musica = new Musica(nomeMusica);
            musica.setArtista(artista.get());
            artista.get().getMusicas().add(musica);
            repositorio.save(artista.get());
            System.out.println("A música " + musica.getTitulo() + " foi adicionada no banco." );
        } else {
            System.out.println("Hmmm... Não achamos esse artista :(");
        }
    }

    private void cadastrarArtistas() {
        var cadastrarNovo  = "S";
        while(cadastrarNovo.equalsIgnoreCase("S")) {
            System.out.println("Digite o nome do artista/banda que deseja cadastrar:");
            var nomeArtista = leitura.nextLine();

            System.out.println("Qual o tipo do seu artista? (SOLO/DUPLA/BANDA)");
            var opcaotipo = leitura.nextLine();
            TipoArtista tipoArtista = TipoArtista.valueOf(opcaotipo.toUpperCase());

            Artista artista = new Artista(nomeArtista, tipoArtista);
            var artistaGetNome = artista.getNome();
            System.out.println("O artista " + artistaGetNome + " foi cadastrado.");

            repositorio.save(artista);

            System.out.println("Deseja cadastrar outro artista? (S/N)");
            cadastrarNovo = leitura.nextLine();
        }
    }

}

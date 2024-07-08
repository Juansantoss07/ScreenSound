package br.com.screensound.screenSound.repository;

import br.com.screensound.screenSound.modelos.Artista;
import br.com.screensound.screenSound.modelos.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome ILIKE %:nomeArtista%")
    List<Musica> musicasPorArtista(String nomeArtista);
}

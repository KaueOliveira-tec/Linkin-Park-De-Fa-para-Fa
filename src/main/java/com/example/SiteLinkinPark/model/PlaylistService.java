package com.example.SiteLinkinPark.model;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistDAO playlistDAO;

    public void criarPlaylist(Playlist playlist, List<String> musicaIds) {
        playlistDAO.salvarPlaylist(playlist, musicaIds);
    }

    public List<Playlist> listarPlaylists(UUID usuarioId) {
        return playlistDAO.listarPlaylistsPorUsuario(usuarioId);
    }

    public Playlist buscarPlaylist(UUID playlistId) {
        return playlistDAO.buscarPlaylist(playlistId);
    }

    public void atualizarPlaylist(UUID playlistId, List<String> musicaIds) {
        playlistDAO.atualizarPlaylistMusicas(playlistId, musicaIds);
    }

    public void removerMusica(UUID playlistId, String musicaId) {
        playlistDAO.removerMusica(playlistId, UUID.fromString(musicaId));
    }

    public void deletarPlaylist(UUID playlistId) {
        playlistDAO.deletarPlaylist(playlistId);
    }
}

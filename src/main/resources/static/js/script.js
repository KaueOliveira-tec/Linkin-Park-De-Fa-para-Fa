/**
 * Função para rolar suavemente até o topo da página
 * @param {Event} event - O evento do clique
 */
function scrollToTop(event) {
    event.preventDefault();  // Impede o comportamento padrão do link
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
}

/**
 * Filtra e mostra apenas as músicas do álbum selecionado no formulário de playlist.
 * Os álbuns começam ocultos e só aparecem quando um álbum é selecionado.
 */
function inicializarFiltroAlbum(container) {
    if (!container) return;
    
    const select = container.querySelector('.album-select');
    const articles = container.querySelectorAll('.album-article');
    
    if (select && articles.length > 0) {
        // Ocultar todos os álbuns inicialmente
        articles.forEach(article => {
            article.hidden = true;
        });
        
        // Listener para mudança de álbum selecionado
        select.addEventListener('change', function () {
            const albumSelecionado = this.value;
            articles.forEach(article => {
                article.hidden = article.dataset.album !== albumSelecionado;
            });
        });
    }
}

/**
 * Inicializa os filtros de álbum quando o DOM estiver pronto.
 */
document.addEventListener('DOMContentLoaded', function () {
    // Processar formulário de criação de playlist
    const criarPlaylistContainer = document.getElementById('form-criar-playlist');
    inicializarFiltroAlbum(criarPlaylistContainer);
    
    // Processar formulário de atualização de playlist
    const atualizarPlaylistContainer = document.getElementById('form-atualizar-playlist');
    inicializarFiltroAlbum(atualizarPlaylistContainer);
});


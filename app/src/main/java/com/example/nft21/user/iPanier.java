package com.example.nft21.user;
import com.example.nft21.NFT.NFT;

import java.util.List;

/**
 * défini défini les opérations nécessaires à la gestion des commandes des clients.
 */
public interface iPanier {

    /**
     * ajoute au "panier" du client considéré, l'article considéré dans la quantité indiquée.
     * Le stock doit être modifié en conséquence.
     *
     * @param client   le client à considérer
     * @param article  l'article à considérer
     */
    void ajouterAuPanier(User client, NFT article);

    /**
     * supprime du panier du client considéré
     *
     * @param client   le client à considérer
     * @param article  l'article à considérer
     */
    void supprimerDuPanier(User client, NFT article);

    /**
     * donne le montant total du panier du client en etherum
     *
     * @param client le client à considérer
     * @return le montant total du panier du client
     */
    double consulterMontantPanierETH(User client);

    /**
     * donne le montant total du panier du client en euros
     *
     * @param client le client à considérer
     * @return le montant total du panier du client
     */
    double consulterMontantPanierEuros(User client);

    /**
     * vide totalement le panier du client considéré
     * @param client le client à considérer
     */
    void viderPanier(User client);

    /**
     * liste l'ensemble des commandes terminées d'un client
     *
     * @param client le client à considérer
     * @return la liste des commandes du client considéré
     */
    List<NFT> listerCommandes(User client);
}


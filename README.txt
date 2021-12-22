/// README /// UTF8

LAUNCH:
Exécuter Play.java

DEPLACEMENT:
Flèches directionnelles pour le déplacement
Le curseur blanc pour sélectionner une unité à l’aide de la touche Enter
appui sur N : passer au niveau1 ou décréter la fin de partie "Game Over" si le tour du niveau1 est terminé
appui sur R : réinitialiser (« reset ») le jeu
appui sur Enter : sélectionner l’unité(appuyer une fois) ; confirmer sa position finale choisie par le joueur (appuyer une seconde fois) ; passer à l’attaque
appui sur Tab : indiquer la fin du tour du joueur courant et passer à l’autre joueur
appui sur A : (Attack) attaquer une unité adverse
appui sur W : (Wait) attendre 
appui sur les touches LEFT, RIGHT, UP, DOWN : naviguer vers la gauche resp. vers la droite resp. vers le haut resp. vers le bas
N.B. Nous ne pouvons pas sélectionner d’autres choses.
PERSONNAGES :
Nous : Un tank et un soldat bleus
Ennemies : Un tank et un soldat rouges

COMMENT JOUER :
Le but du jeu est de jouer contre les adversaires : AIPlayer, et de gagner le tour. Pour tuer les unités opposés, il suffit d’aller sur la case où elles se situent.
Il y a deux niveaux (Level0 et Level1). 
Au début du jeu (Level0), il y a un tank et un soldat bleus (nous), et un tank et un soldat rouges (l’adversaire). 
Quand nous sélectionnons une unité (soit le tank bleu soit le soldat bleu) avec le curseur blanc, le carré blanc à mi-transparent apparaît et représente la zone de déplacement de l’unité sélectionnée. Quand nous naviguons l’unité, la flèche directionnelle rouge apparaît et représente le chemin de l’unité. Une fois placé l’unité, nous pouvons soit attaquer une des unités opposées puis devient utilisée (niveau affichage : devient semi-transparent), soit attendre le prochain tour. Elle. Nous faisons la même procédure pour l’autre unité. Nous passons le tour à l’adversaire et attendons le prochain tour.
Les unités se cachent (pour que l’adversaire ne voit pas) quand elles sont dans les forêts. 
Les unités disparaissent si elles sont tuées. 
Les unités peuvent décider la destination tant qu’elles restent dans leur zone de déplacement !
Pour contrôler les unités, veuillez voir DEPLACEMENT.

IMAGES:
Adaptées à partir de celles données avec la maquette et créées sur GIMP/Paint.net, sauf:
zelda/stick.png: https://gamepedia.cursecdn.com/minecraft_gamepedia/a/aa/Stick.png?version=71db5a8af84f921199c1614847e5cb31

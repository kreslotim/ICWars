### LAUNCH:
Exécuter Play.java

### DEPLACEMENT:
• Flèches directionnelles pour le déplacement du curseur. 
• Le curseur blanc pour sélectionner une unité à l’aide de la touche Enter.
• appui sur N : passer au niveau suivant ou décréter la fin de partie "Game Over" si le tour du dernier niveau est terminé.
• appui sur R : réinitialiser (« reset ») le jeu, en restant sur la même aire de jeu.
• appui sur Enter : sélectionner l’unité (appuyer une fois) ; confirmer sa position finale choisie par le joueur (appuyer une seconde fois) ; passer à l’action (Wait ou Attack).
• appui sur Tab : indiquer la fin du tour du joueur courant et passer à l’autre joueur (shortcut ; le processus est automatisé).
• appui sur A : (Attack) attaquer une unité adverse.
• appui sur W : (Wait) attendre le prochain tour.
• appui sur les touches LEFT, RIGHT, UP, DOWN : naviguer vers la gauche resp. vers la droite resp. vers le haut resp. vers le bas.
N.B. Nous ne pouvons pas sélectionner d’autres choses.

### PERSONNAGES :
• Nous : Un tank, un soldat et une fusée bleus
• Ennemies : Un tank, un soldat et une fusée rouges
		(les fusées sont des extensions)

### COMMENT JOUER :
Le but du jeu est de jouer contre les adversaires : AIPlayer, en éliminant toutes les unités ennemies. Une fois qu'un des joueur n'a plus d'unités disponibles, il est défait. En ayant gagné la partie, on passe au niveau suivant. S'il n'y plus de niveaux, le jeu est terminé.
Il y a deux niveaux (Level0 et Level1). 
Au début du jeu (Level0), il y a un tank et un soldat bleus (nous), et un tank et un soldat rouges (l’adversaire). 
Quand nous sélectionnons une unité (le tank bleu, le soldat bleu ou la fusée bleue) avec le curseur blanc, le carré blanc à mi-transparent apparaît et représente la zone de déplacement de l’unité sélectionnée. 
Quand nous naviguons l’unité, le chemin rouge apparaît, représentant la nouvelle position de celle-ci. Une fois placé l’unité, nous pouvons soit attaquer (A)ttack une des unités opposées, après quoi notre unité devient utilisée (niveau affichage : devient semi-transparent), soit attendre (W)ait le prochain tour (si aucun ennemi n'est dans le rayon de portée). Nous faisons la même procédure pour l(es) autre(s) unité(s). Ayant utilisé toutes nos unités, le tour passe à l’adversaire. Nous attendons le prochain tour.
Chaque case de la grille possède un certain nombre de "Defense Star" qui correspondent au points de protection sur cette case. En effet, si une unité se fait attaquer, en étant positionée sur une case avec des "Defense Star", comme par exemple: les plaines (1 Def), les forêts (3 Def), les montagnes (3 Def), alors elle ne perdra qu'une partie de ces points de vies. 
Les unités disparaissent de l'aire du jeu si elles sont tuées. 
Les unités peuvent décider la destination tant qu’elles restent dans leur zone de déplacement !
Pour contrôler les unités, veuillez voir DEPLACEMENT.
Vous pouvez quitter le jeu ou rejouer le jeu ! A vous de choisir !!

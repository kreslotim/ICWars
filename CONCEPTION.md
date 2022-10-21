CONCEPTION

CLASSES ET INTERFACES
Voici l’organisation des paquets de notre jeu ICWars :

Les liens de hiérarchie entre les classes et les interfaces  

MODIFICATIONS EFFECTUEES AU CODE FOURNI  
Dans la méthode cellInteractionOf de la classe AreaBehavior, nous avons mis 
interactable != interactor pour que l’unité ne puisse pas interagir avec le curseur 
Dans l’interface ICWarsInteractionVisitor.


IMPLEMENTATION DES FONCTIONNALITÉS PRINCIPALES DE L'ÉNONCÉ
•	Dans le paquetage game.icwars :
La classe ICWars est notre super-classe.
•	Dans game.icwars.actor.players :
La classe RealPlayer qui modélise le joueur « humain », contrôlable manuellement.
RealPlayer(joueur humain) et AIPlayer(joueur artificiel) héritent de ICWarsActor
Dans un sous-paquetage game.icwars.area
La classe ICWarsArea
Les classes Level0 et Level1, héritant de ICWarsArea
la classe ICWarsBehavior et ICWarsCell
la classe ICWarsCell
•	Dans le paquetage game.icwars.actor :
Soldat et Tank héritent de Unit




CONCEPTION

EXTENSIONS
L’ajout de la classe GameOver pour afficher l’image GAME OVER.png quand nous avons perdu.
L’ajout de la classe Victory pour afficher l’image VICTORY.png quand nous avons gagné.
P.S. les images sont faites avec cœur :D
L’ajout des conditions dans la méthode end() de la classe ICWars avec l’attribut won de type booléen initialisé à true.
La classe Rocket : un Rocket est une unité comme le Tank et le Soldat. Le friendlyRocket est positionné aux coordonnées (1, 4) et le enemyRocket est positionné aux coordonnées (7, 5) comme positions d’origines.

projetl1p4
==========

Le moteur du jeu est à coder dans Puissance4.java qui contient
des exemples d'utilisation de l'interface.

Attention. Inutile de lire/modifier les classes IHM et Plateau !

==========
Méthodes utiles fournies par la classe IHM :

* Mettre à jour le plateau
```java
void IHM.majPlateau(int t[7][7])
```

* Attendre un ordre (type enum Ordre) de l'utilisateur
```java
Ordre IHM.ordreUtilisateur()
```
type enum Ordre : COLONNE0,COLONNE1,COLONNE2,COLONNE3,COLONNE4,COLONNE5,COLONNE6,ROTATIONG,ROTATIOND,APROTAG,APROTAD,RETOUR

* Mettre à jour la zone de texte de l'IHM
```java
void majTexte(String m)
```

* Afficher une boîte de dialogue
```java
void boiteMessage(String m)
boolean boiteQuestion(String m)
```

* Activer/désactiver des boutons
```java
void activerColonnes(boolean b)
void activerRotation(boolean b)
void activerApercu(boolean b)
void activerRetour(boolean b)
```
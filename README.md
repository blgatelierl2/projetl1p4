Documentation de l'IHM
==========

Le moteur du jeu est à coder dans `Puissance4.java` qui contient pour l'instant
des exemples d'utilisation de l'interface. Pour compiler :

```
$ javac Puissance4.java
$ java Puissance4
```

**Attention.** Inutile de lire/modifier les classes `IHM` et `Plateau` !

Télécharger le code
----------

Cliquer sur le bouton **Download ZIP** à droite.

Utiliser l'IHM dans votre code
----------

Créer un objet `ihm` de classe `IHM` :

```java
IHM ihm = new IHM();
```

Cela fabriquera automatiquement la fenêtre de jeu. Puis utiliser cet objet pour invoquer les méthodes utiles fournies par la classe `IHM`, par exemple :

```java
ihm.boiteMessage("Hello world!");
```

L'utilisation de l'IHM est illustrée dans le fichier `Puissance4.java` fourni qui contient de nombreux exemples utiles.

Méthodes utiles fournies par la classe `IHM`
----------

* Mettre à jour le plateau :
```java
void IHM.majPlateau(int t[7][7])
```
Les valeurs **1** et **2** sont interprétées comme des jetons respectivement **rouge** et **jaune**, toute autre valeur est interprétée comme un **blanc**.

* Mettre à jour la zone de texte de l'IHM :
```java
void IHM.majTexte(String m)
```

* Attendre un ordre (type `enum Ordre`) de l'utilisateur :
```java
Ordre IHM.ordreUtilisateur()
```
où le type `enum Ordre` est :
```java
enum Ordre {
     COLONNE0,COLONNE1,COLONNE2,COLONNE3,COLONNE4,COLONNE5,COLONNE6,
     ROTATIONG,ROTATIOND,APROTAG,APROTAD,RETOUR,INCONNU;
}
```

* Afficher une boîte de dialogue :
```java
void IHM.boiteMessage(String m)
void IHM.boiteMessageAttention(String m)
void IHM.boiteMessageErreur(String m)
boolean IHM.boiteQuestion(String m)
String IHM.boiteChoixMultiples(String m, String choix[])
String IHM.boiteQuestionTexte(String q)
```

* Activer/désactiver des boutons :
```java
void IHM.activerColonnes(boolean b)
void IHM.activerRotation(boolean b)
void IHM.activerApercu(boolean b)
void IHM.activerRetour(boolean b)
```

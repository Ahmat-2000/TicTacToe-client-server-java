# Morpion Multi-Joueur

Ce projet implémente une version multi-joueur du célèbre jeu Morpion (Tic-Tac-Toe) en utilisant Java et Swing pour l'interface utilisateur graphique.

## Contenu du Projet

Le projet est organisé comme suit :
```plaintext
├── build
├── build.xml
├── docs
├── dist
└── src
├── client
│ ├── CleintScreen.java
│ ├── CustomJButton.java
│ ├── ReadFromServer.java
│ └── WriteToServer.java
└── server
├── ClientHandler.java
├── GameServer.java
└── Plateau.java
```
- **`src/client`** : Contient les fichiers source pour le client du jeu.
- **`src/server`** : Contient les fichiers source pour le serveur du jeu.
- **`build`** : Répertoire pour les fichiers générés lors de la compilation.
- **`dist`** : Répertoire pour les fichiers executable .jar.
- **`docs`** : Répertoire pour la documentation du projet.

## Comment Exécuter et Jouer en Multijoueur

Veuillez consulter les instructions détaillées sur la façon d'exécuter le serveur et les clients, ainsi que sur la façon de jouer en multijoueur dans le [guide d'exécution et de jeu en multijoueur](#guide-dexécution-et-de-jeu-en-multijoueur).

## Guide d'Exécution et de Jeu en Multijoueur

Pour exécuter et jouer en multijoueur au Morpion, veuillez suivre les instructions suivantes :

1. **Exécution du Serveur** :
   - Assurez-vous d'avoir installé le JDK sur votre machine.
   - Compilez et exécutez le serveur à partir du répertoire racine du projet :
     ```shell
     # pour lancer le server en local
     java -jar dist/server.jar
     ```
   - Le serveur démarrera et écoutera les connexions des clients sur le port par défaut `5050`.

2. **Exécution du Client** :
   - Compilez et exécutez le client à partir du répertoire racine du projet :
     ```shell
     # pour le premier joueur
     java -jar dist/client.jar
     # pour le sécond joueur
     java -jar dist/client.jar 
     ```
   - Le client affichera une interface utilisateur graphique pour se connecter au serveur et jouer au Morpion.

3. **Jouer en Multijoueur** :
   - Lancez deux instances du client sur deux machines différentes ou sur la même machine avec des identifiants de joueur différents.
   - Chaque joueur doit entrer l'adresse IP du serveur et se connecter en utilisant le bouton approprié dans l'interface utilisateur.
   - Le jeu commence une fois que les deux joueurs sont connectés. Ils peuvent maintenant jouer au Morpion en alternance en cliquant sur les cases disponibles dans le plateau de jeu.
   - Le serveur gère la logique du jeu, vérifie les mouvements valides et détermine le gagnant.
   - La partie se termine lorsqu'un joueur remporte la partie ou lorsque le plateau est rempli sans vainqueur.

Assurez-vous de suivre ces instructions pour configurer et jouer au Morpion en multijoueur sur vos machines locales.

## Contribuer

1. Forkez le projet.
2. Créez votre branche (`git checkout -b mabranche`).
3. Committez vos changements (`git commit -am 'Ajouter une nouvelle fonctionnalité'`).
4. Pushez vers la branche (`git push origin mabranche`).
5. Ouvrez une Pull Request.

## Auteur

 Ahmat Mahamat Ahmat

 Master 1 cybersécurité à l'université de Caen

## Contacts

 Pour des questions, contactez [ahmatmhtlouky@gmail.com](mailto:ahmatmhtlouky@gmail.com).

## License

Ce projet est en open source, donc vous êtes libre à le modifier et à l'utiliser

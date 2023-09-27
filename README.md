# DrakeEquation
02 - 2023
# Description
Écrire une application Java Swing qui permet de jouer sur les paramètres de l'équationd de Drake pour calculer le résultat de cette équation, en utilisant le gridBagLayout et les JSlider pour cela.

PARTIE 2 : Recherche de paramètres

On souhaite améliorer le système de façon à générer automatiquement des combinaisons pour lesquelles la vie apparaît au moins 1 fois dans la galaxie.
On considère que R* = 10/an, fp = 0,5, ne = 2, fl = 1 et L = 5000 ans. Ce qui en l'état actuel des connaissances semble être le plus probable. Reste les 3 paramètres f : fl, fi et fc, sur lesquels on peut agir.
Implémenter un algorithme qui génère en boucle des valeurs aléatoires (entre 0 et 1) pour ces 3 paramètres et affiche le résultat de l'équation si N >= 1.

PARTIE 3 : Affichage dynamique

Programmer un Thread pour rechercher les paramètres et utiliser SwingWorker pour exécuter la simulation et mettre à jour le résultat de l'équation de Drake et l'état des JSliders.
Ajouter un bouton Start et Stop.
Remarque : Temporiser la génération des solutions pour générer une solution toutes les secondes, par exemple.
:: ceci est un commentaire et doit impérativement commencer une ligne
:: sinon cmd l'exécute ...

:: commande pour ne pas afficher les commandes dans la console
@echo off 

:: titre de la console
title Redacteur d'Etats sur la carte

:: taille de la console
mode con cols=80 lines=16

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::: DEBUT VARIABLES DU PROGRAMME JAVA A MODIFIER SI BESOIN :::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: langue (si -fr, le programme est mis en français, sinon mettre -en pour l'anglais)
set langue="-fr"

:: carte du jeu
set carte="vanille_1066_9_15.bmp"

:: fichier de sortie
set out="res.png"

:: illustration pendant le chargement
set illustration="illustration.png"

:: Jeu qui a créé la carte
:: pour CK2, commentez la ligne suivante
::set game="-eu4"

:: Dossier du jeu
set gameDir="C:/Program Files/Steam/SteamApps/common/Crusader Kings II"

:: Dossier des mods
::set mod1="C:\...\Documents\Paradox Interactive\Crusader Kings II\MOD\swmh2.854"
::set mod2=""
::set mod3=""

:: Nombres d'Etats affichés sur la carte en sortie
:: Pour tous les avoir mettre "a"
set nbEtats="a"
::set nbEtats=10

:: Police d'écriture du texte
set police="Serif"
::set police="Dialog"

:: FACULTATIFS : à decommenter si souhaité
:: Taille maximale du texte
:: Si on ne met rien la taille est proportionnelle à la taille de l'image
:: (exactement elle vaut 100/2048*min(largueur,hauteur))
::set max=-size 100

:: Couleur du texte multicolor (couleur opposée à celle de l'Etat)
::set multicolor="-multicolor"

:: Date affichée à droite
::set dateDroite="-rightDate"

:: La date affichée ne sera que l'année
set dateAn="-yearOnly"

:: La date sera affichée en lettres
::set dateText="-textualDate"

:: [CK2] La limitation du rang des titres à écrire
::set rank="count"
::set rank="duke"
::set rank="king"
set rank="emperor"

:: Remplace les noms sur la carte. Mettre un nom de fichier CSV contenant R;G;B;NomAAfficher
:: set override="overrideFile.csv"
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::: FIN VARIABLES DU PROGRAMME JAVA A MODIFIER SI BESOIN ::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: commande du programme
java -jar Redacteur_etats_carte.jar %langue% -map %carte% -out %out% -img %illustration% ^
 %game% -gameDir %gameDir% -mod %mod1% %mod2% %mod3% -sta %nbEtats% -pol %police% %max% ^
 %multicolor% %dateDroite% %dateAn% %dateText% -rank %rank% -ov %override%
 
:: commande pour mettre en pause pour que la console ne se ferme pas à 
:: la fin de de l'exécution. (utile pour voir les erreurs)
pause

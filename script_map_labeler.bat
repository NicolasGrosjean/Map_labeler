:: this is a comment and must start a line
:: otherwise cmd run it...

:: command to not display the command in the console
@echo off 

:: console title
title Map labeler

:: console size
mode con cols=80 lines=16

::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::::::::::: START JAVA SOFTWARE VARIABLES TO CHANGE IF NEEDED ::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:: language (if -en, the software is in English. Put -fr for French)
set langue="-en"

:: game map
set carte="vanille_1066_9_15.bmp"

:: output file
set out="res.png"

:: illustration during loading
set illustration="illustration.png"

:: Game with what the map has been created
:: for CK2 comment the following line
::set game="-eu4"

:: Game directory
set gameDir="C:/Program Files/Steam/SteamApps/common/Crusader Kings II"

:: Mod directories
::set mod1="C:\...\Documents\Paradox Interactive\Crusader Kings II\MOD\swmh2.854"
::set mod2=""
::set mod3=""

:: Number of displayed states in the output map
:: To have all, put "a"
set nbEtats="a"
::set nbEtats=10

:: Font
set police="Serif"
::set police="Dialog"

:: OPTIONAL : to uncomment if needed
:: Text minimal size
:: By default, the size is proportional to the image size
:: (exactly, it is equal to 100/2048*min(width,height))
::set max=-size 100

:: Multicolor text (complement color to the state color)
::set multicolor="-multicolor"

:: Date will be displayed at right (default left)
::set dateDroite="-rightDate"

:: Date will be reduced to only the year (default day, month and year)
set dateAn="-yearOnly"

:: Date will be written in letter (by default with slashs)
::set dateText="-textualDate"

:: [CK2] Limit the name to write to the title (useful for de jure map)
::set rank="count"
::set rank="duke"
::set rank="king"
set rank="emperor"

:: Override the names in the map. Put a CSV file with R;G;B;NameToDisplay
:: set override="overrideFile.csv"
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
:::::::::::: END JAVA SOFTWARE VARIABLES TO CHANGE IF NEEDED :::::::::::::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

:: software execution
java -jar Redacteur_etats_carte.jar %langue% -map %carte% -out %out% -img %illustration% ^
 %game% -gameDir %gameDir% -mod %mod1% %mod2% %mod3% -sta %nbEtats% -pol %police% %max% ^
 %multicolor% %dateDroite% %dateAn% %dateText% -rank %rank% -ov %override%
 
:: command to pause in order to not close the console at the end
:: (useful to see errors)
pause

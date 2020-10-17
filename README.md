# EZBackup

### Installation :
Téléchargez le fichier .jar depuis l'onglet **releases** et mettez le dans votre dossier plugins, re/démarrez votre serveur et tada !

### Comment ça marche ?
Ce plugin vous permet de faire des backups automatiques de votre serveur d'une simplicité déconcertante, modifiez le fichier **config.yml** selon vos goûts et... c'est tout, vous n'aurez plus à vous soucier de quoi que ce soit ! La copie des fichiers ce fait de manière **asynchrone**, c'est-à-dire sur un thread **différent** du thread principal, en d'autres termes : les backups ne feront pas laguer/freezer votre serveur, si ça c'est pas génial !

### Commandes :
Le plugin dispose de quelques commandes ma foi fortes utiles, dont voici la liste :
* `/backup now` - *Permet de faire un backup manuellement*
* `/backup number` - *Affiche le nombre de backups stockés dans le serveur*
* `/backup maxbackups` - *Affiche le nombre maximum de backups que le serveur peut contenir (changeable dans le config.yml)*
* `/backup lastbackup` - *Affiche la date du dernier backup ainsi que la durée passée entre cette date et maintenant*

### Un problème ? Ou une suggestion (j'préfèrerais) ?
* Si un problème survint avec le plugin, n'hésitez surtout pas à le mentionner dans l'onglet **issues**, mettez simplement le code d'erreur **COMPLET** pour que je puisse tenter de le résoudre.
* Si vous souhaitez faire une suggestion *(j'en suis tout ouïe)*, utilisez de même l'onglet **issues** et faites votre suggestion en essayant d'être **clair** dans vos propos, plus ce sera **détaillé** et **précis**, plus je serai apte à répondre à votre demande. **Attention** toutefois, je me permet de **décliner** votre requête si j'estime qu'elle n'est **pas nécessaire**.

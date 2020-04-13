# Waifu market-dess
_Pierre SELEBRAN, Nathan ROCHE, Alexandre ELLERO, Timothée THIBAULT_

## Présentation
Projet réalisé dans le cadre de l'UE IVVQ du M2 Développement Logiciel, années 2019-2020.

## Organisation du projet
Le projet est organisé en deux modules : _**backend**_ et _**frontend**_.

### Frontend
- Module en Vue.js 
- Responsable de la création des IHMs de l'application.

### Backend
- Module en Spring
- Jdk 1.8
- Responsable du traitement des requètes utilisateurs.

### Build [![Build Status](https://travis-ci.com/M2DL/ivvq-2020-project-patterns.svg?token=Pbvyy9xfyTq17MRdnncR&branch=master)](https://travis-ci.com/M2DL/ivvq-2020-project-patterns)
Le build du projet est fait à l'aide de _**Maven**_.
Le module _backend_ va utiliser les IHMs créées par le module _frontend_. Ainsi, l'éxecution de la commande `mvn package` dans le module _backend_ va récupérer et copier les sources générées par la commande `mvn package` éxecutée dans le module _frontend_, et donc nécessite ce-dernier d'être construit.
L'utilisation du pom à la racine du projet permet d'automatiser le build de tous les modules.
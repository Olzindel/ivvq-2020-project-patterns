# Waifu market-desu [![Build Status](https://travis-ci.com/M2DL/ivvq-2020-project-patterns.svg?token=Pbvyy9xfyTq17MRdnncR&branch=master)](https://travis-ci.com/M2DL/ivvq-2020-project-patterns)


## Présentation
Projet réalisé par _Pierre SELEBRAN, Nathan ROCHE, Alexandre ELLERO, Timothée THIBAULT_ dans le cadre de l'UE IVVQ du M2 Développement Logiciel, années 2019-2020.

La version finale du projet est déployée sur [_cette adresse_](https://ivvq-patterns.herokuapp.com/)

## Lancement
Le projet peut être lancé en local utilisant docker-compose, et est accessible sur le port 8080 par défaut. Ce docker-compose lance aussi les tests e2e. Une erreur est possible au lancement du service e2e_tests, notamment si vous êtes sur un environnement windows. Dans ce cas, convertir le fichier _e2e/wait-for-it.sh_ au format unix devrait résoudre le problème.
#!/bin/bash

# 1. Exécuter la commande Maven pour compiler

DEST_PATH="/home/lempereur/Documents/Projets/minecraft/visualgame-server/"

# 1 lancement du server
cd "$DEST_PATH"
java -Xms1G -Xmx2G -jar paper-1.21.8-25.jar
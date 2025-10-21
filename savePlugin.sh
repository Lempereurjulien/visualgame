#!/bin/bash

pluginVersion="1.0.0"
#1. Copier target *.jar dans minecraft-server/plugins

cp "target/visualGame-$pluginVersion.jar" "/home/lempereur/Documents/Projets/minecraft/minecraft-server/plugin/"
echo "✅ Copie du plugin effectué $HOME"
git add .
echo "✅ Ajout du fichier au git"
git commit -m'plugin $pluginVersion add'
echo "✅ commit effectué"

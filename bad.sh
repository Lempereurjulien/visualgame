#!/bin/bash

# 1. Exécuter la commande Maven pour compiler
echo "Compilation du projet avec Maven"
mvn package

# 2. Vérifier si le build a réussi
if [$? -ne 0]; then
echo "❌ La compilation a échoué. Arrêt du script."
exit 1
fi

# 3. Définir les chemins
JAR_PATH="target/visualGame-1.0-SNAPSHOT.jar"
DEST_PATH="/home/lempereur/Documents/Projets/minecraft/visualgame-server/"

# 4. Vérifier si le fichier jar existe
if [ ! -f "$JAR_PATH" ]; then
  echo "❌ Le fichier $JAR_PATH n'existe pas."
  exit 1
fi

# 5. Copier le jar dans le dossier plugins (en le remplaçant)
echo "🚚 Déploiement du fichier dans $DEST_PATH"
cp "$JAR_PATH" "$DEST_PATH/plugins"

# 6. Fin
echo "✅ Déploiement terminé."

# 7 lancement du server
cd "$DEST_PATH"
java -Xms1G -Xmx2G -jar paper-1.21.8-25.jar
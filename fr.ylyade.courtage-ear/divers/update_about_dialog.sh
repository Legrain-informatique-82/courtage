#!/bin/bash

echo "**********************************************"
echo "* Modification des sources avant compilation *"
echo "**********************************************"
#Attention, si dans le processus de build svn:update est utilise, seul les fichiers nouveau ou mis a jour dans le svn sont importes du svn.
#Donc dans ce cas, les fichiers modifies ici mais pas dans le svn ne sont pas remplaces.
#Ex: si on recherche et remplace la chaine "HUSDON" par "OK" dans le fichier "test.txt", la premire fois elle sera remplace mais la seconde elle ne sera pas trouve
#car il y aura "OK" dans le fichier
#Solution : ne pas utiliser svn:update si on souhaite modifie les sources avant la compilation
#Solution (a tester) : supprimer les fichiers modifies apres compilation pour voir si svn:update les re-exporte correctement
echo "r$SVN_REVISION *** $BUILD_ID *** $BUILD_TAG ($BRANCHE)"
#sed -i s/"HUDSON"/"r$SVN_REVISION *** $BUILD_ID *** $BUILD_TAG ($BRANCHE)"/g $BASE_SCM/GestionCommerciale/plugin.properties
#On utilise le # comme delimiteur a cause du slash présent dans la variable BRANCHE
#On pourrait utilise un autre caractere : /,#,@,A
#cf : http://www.coderetard.com/2008/11/11/sed-how-to-escape-forward-slash-with-the-right-delimiter/

#sed -i s#"HUDSON"#"$GIT_COMMIT *** $BUILD_ID *** $BUILD_TAG ($BRANCHE)"#g $BASE_SCM/GestionCommerciale/plugin.properties
sed -i s#"HUDSON"#"$GIT_COMMIT *** $BUILD_ID *** $BUILD_TAG ($BRANCHE)"#g $WORKSPACE/svn/fr.ylyade.courtage-web/src/main/webapp/about.xhtml

sed -i s#"HUDSON"#"$GIT_COMMIT *** $GIT_BRANCH *** $BUILD_ID *** $BUILD_TAG ($BRANCHE)"#g $WORKSPACE/svn/fr.ylyade.courtage-web/src/main/resources/message_bundles/messages.properties
sed -i s#"REVISION_SVN"#"$GIT_COMMIT"#g $WORKSPACE/svn/fr.ylyade.courtage-web/src/main/resources/message_bundles/messages.properties
sed -i s#"BUILD_DATE_MILLIS"#"$(date +%s)"#g $WORKSPACE/svn/fr.ylyade.courtage-web/src/main/resources/message_bundles/messages.properties


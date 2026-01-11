# language: fr
Fonctionnalité: Gestion des tâches TodoManager
  En tant qu'utilisateur de TodoManager
  Je veux gérer mes tâches
  Afin d'organiser mon travail

  Contexte:
    Étant donné que le système TodoManager est disponible

  Scénario: Création d'une tâche avec un titre valide
    Quand l'utilisateur crée une tâche avec le titre "Acheter du lait"
    Alors la tâche est créée avec le statut "PENDING"

  Scénario: Création d'une tâche sans titre
    Quand l'utilisateur crée une tâche sans titre
    Alors une erreur "Le titre est requis" est levée

  Scénario: Consultation d'une tâche existante
    Étant donné qu'une tâche existe avec le titre "Faire les courses"
    Quand l'utilisateur consulte la tâche
    Alors la tâche est affichée avec son titre "Faire les courses"

  Scénario: Consultation d'une tâche inexistante
    Quand l'utilisateur consulte une tâche avec l'ID 999
    Alors une erreur "Tâche introuvable" est retournée

  Scénario: Mise à jour du titre d'une tâche
    Étant donné qu'une tâche existe avec le titre "Ancien titre"
    Quand l'utilisateur modifie le titre en "Nouveau titre"
    Alors le titre de la tâche est "Nouveau titre"

  Scénario: Mise à jour avec un titre vide
    Étant donné qu'une tâche existe avec le titre "Titre existant"
    Quand l'utilisateur tente de modifier le titre en ""
    Alors une erreur de validation est levée

  Scénario: Finalisation d'une tâche
    Étant donné qu'une tâche existe avec le titre "Tâche à finaliser"
    Quand l'utilisateur finalise la tâche
    Alors la tâche passe au statut "DONE"

  Scénario: Suppression d'une tâche
    Étant donné qu'une tâche existe avec le titre "Tâche à supprimer"
    Quand l'utilisateur supprime la tâche
    Alors la tâche n'existe plus dans le système

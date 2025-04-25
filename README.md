# 🛍️ Application de Shopping

Une application de gestion de boutique développée en Java (Swing) avec statistiques visuelles grâce à **JFreeChart**.  
Elle permet de gérer les articles, les clients, les commandes, et d’accéder à des données statistiques via une interface administrateur claire. 
Les clients peuvent également passer des commandes directement via l'application.

---

## ✨ Fonctionnalités

- 🔑 **Connexion et inscription des utilisateurs**
- 📦 **Gestion de l’inventaire** : consultation et mise à jour des articles
- 👤 **Gestion des clients** : historique des commandes, statistiques de fidélité
- 🛒 **Passage de commandes** par les clients avec mise à jour automatique des stocks
- 📊 **Statistiques avancées** :
  - Panier moyen, CA total, réductions globales
  - Clients les plus fidèles (en euros ou en nombre de commandes)
  - Revenus générés et unités vendues par article

---

## 🖼️ Aperçu visuel

- Interface Java Swing personnalisée
- Graphiques dynamiques avec `JFreeChart`

---

## 🔧 Technologies utilisées

- Java 17
- Swing (interface graphique)
- JFreeChart (graphiques statistiques)
- MySQL

---

## 📁 Structure du projet

```
shop/
├── controleur/      # Contrôleur
├── donnees/         # Accès aux données (DAO)
├── modele/          # Modèles : Article, Client, Commande
├── vue/             # Interfaces graphiques Swing
└── Main.java        # Lancement de l'application
```

---

## 📌 Auteurs

- 👨‍💻 Projet réalisé par :
  - Talia TOUTAH
  - Miguel FRANCIS
  - Julia EZZEDINE
  - Romaric BARBAUD
- Projet réalisé dans le cadre de notre projet de Java de première année du cycle d'ingénieur

---

## 📝 Licence

Ce projet est libre d’utilisation à des fins éducatives ou personnelles.

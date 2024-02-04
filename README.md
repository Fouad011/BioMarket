<h1 align="left">Développement d’un système d’application mobile d’achat en ligne avec Android Studio.</h1>

<h2 align="left">Le but du projet système BioMarket</h2>
Le projet système BioMarket vise à créer une plateforme d'achat en ligne spécialisée dans les produits biologiques. L'objectif principal est de fournir une expérience d'achat pratique et conviviale pour les clients intéressés par des produits biologiques et écologiques. Voici quelques-uns des objectifs spécifiques du projet BioMarket :
<ul>
  <li><i><b>Faciliter l'accès aux produits biologiques :</b></i> Offrir une plateforme où les utilisateurs peuvent facilement accéder à une variété de produits biologiques, favorisant ainsi un mode de vie plus sain et durable.</li>
  <li><i><b>Gestion de compte utilisateur :</b></i> Permettre aux utilisateurs de créer des comptes personnels, de gérer leurs informations personnelles, et de suivre l'historique de leurs achats. Cette fonctionnalité contribue à personnaliser l'expérience utilisateur.</li>
  <li><i><b>Gestion de catalogue :</b></i> Fournir une interface d'administration (BioMarketAdmin) permettant aux responsables de gérer le catalogue de produits, d'ajouter de nouveaux articles, de mettre à jour les informations et de gérer les stocks.</li>
  <li><i><b>Confirmation de livraison :</b></i> Permettre aux livreurs de confirmer la livraison des produits une fois qu'ils sont remis aux clients. Cette confirmation peut déclencher des notifications pour informer les clients de la réussite de la livraison.</li>
  <li><i><b>Durabilité et Responsabilité :</b></i> Promouvoir les principes de durabilité en favorisant les produits biologiques et en minimisant l'empreinte environnementale à travers des pratiques responsables.</li>
</ul>

<h2 align="left">Description</h2>
Ce projet consiste à concevoir et réaliser d’un système d’application mobile d’achat en ligne avec Android Studio, une applications mobile pour l’achat en ligne (BioMarket), et deux applications pour la gestion (BioMarketAdmin) et la livreasion (BioMarketDelivery).





<h3 align="left">La premier application "BioMarket" - <a href="https://github.com/Fouad011/BioMarket.git" >BioMarket repository</a></h3>

Ce projet est spécifiquement conçu pour l'application mobile principale, destinée à offrir une expérience optimale aux clients. Il y compris des fonctionnalités essentielles telles que la navigation intuitive à travers les produits, la gestion du panier d'achat, le processus de paiement à réception, la gestion du compte utilisateur, ainsi que la fonction de recherche pour faciliter la découverte des produits souhaités.

<!---<h4 align="left">L'interfaces Login, Register et RecoverPassword</h4>--->




<h4 align="left">L'interface Home</h4>
L'interface Home est l'interface initiale de l'application vous offre la possibilité de parcourir les produits et de faire des recherches. Cependant, pour ajouter des produits à votre panier, une connexion est nécessaire. Vous pouvez effectuer cette connexion en accédant à votre profil.
<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/interfaceHome.jpg" height="700"/>
</p>

<!--Cette section comprend la première page (fragment) et deux activités (Activity), la la première page (fragment) Contient les catégories et les classifications, une activité permet l’affichage des produits par catégories et classifications, Il contient une section qui peut certains produits associés au produit affiché sur cette page, Par lequel vous pouvez revenir à la page d’accueil (fragment) ou aller aux détails d’un produit particulier (l'autre activité).-->

Cette section englobe à la fois la première page (fragment) et deux activités (Activity). La première page (fragment) est spécifiquement consacrée aux catégories et classifications des produits. Une des activités facilite la présentation des produits organisés selon leurs catégories et classifications respectives. Cela offre la flexibilité de revenir à la page d'accueil (fragment) ou de se diriger vers les détails d'un produit particulier grâce à l'autre activité. De plus, cette activité intègre une section mettant en avant certains produits associés à celui affiché sur la page, enrichissant ainsi l'expérience de l'utilisateur.

La flèche bilatérale entre l'activité 1 et l'activité 2 indique que l'activité 1 peut être déplacée aussi bien de l'activité 1 à l'activité 2 que de l'activité 2 à l'activité 1.
La flèche indiquant la direction de l'activité 1 vers l'activité 2 indique la possibilité de passer de l'activité 1 à l'activité 2, mais elle exclut la possibilité de passer de l'activité 2 à l'activité 1.



<h4 align="left">L'interface Search</h4>
L'interface "Search" (fragment) permet aux utilisateurs de trouver rapidement et facilement les produits qu'ils recherchent, tout en mettant en évidence la possibilité d'identifier la fourchette de prix des produits.

<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/recherche.png" height="400"/>
</p>

<b>Remarque </b>
l'algorithme responsable de la recherche dans la base de données examine la récurrence de chaque mot de la requête de recherche dans les produits, en considérant à la fois le titre et la description. Il présente ensuite les produits dont le taux de répétition d'un mot est d'environ 25% ou plus. Par exemple, si la recherche porte sur "lait de vache carotte marocain 1l", un produit contenant les mots "lait" et "vache" et "marocain" et "1l" sera priorisé, tandis qu'un produit ne contenant que le mot "carotte" sera négligé dans les résultats, même s'il existe.

<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/serachShema.jpg" height="700"/>
</p>

La section de recherche contient la page d’accueil (fragment) et l’activité (activity) qui permet d’accéder aux détails du produit.


<h4 align="left">L'interface Profile</h4>
L’interface "profil" est une section où les utilisateurs peuvent gérer leurs informations personnelles et leurs paramètres. Dans cette interface , l’utilisateur voit le nom du client en haut de l’écran. Ensuite, il peut accéder à deux onglets : "Profile informations" et "My commands".

<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/ProfilShema.jpg" height="1000"/>
</p>

La section "Profil" comprend la page d'accueil (fragment) et quatre activités (activities). La page d'accueil propose deux activités, dont l'une est dédiée à la visualisation et à la modification des informations personnelles, tandis que l'autre permet de modifier la photo de profil. Les deux autres activités autorisent la navigation, l'une étant dédiée à l'affichage des commandes et l'autre à l'affichage des informations de commande et de son statut


<h4 align="left">L'interface Pannier</h4>


L'interface Pannier affiche les produits que l’utilisateur a ajouté à son panier. Elle permet à l’utilisateur de voir la liste des produits, le prix total, et de prendre des actions telles que modifier la quantité d’un produit, supprimer un produit du panier, ou passer la commande.


<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/PannierShema.jpg" height="1000"/>
</p>


La section du panier comprend à la fois la page d'accueil (fragment) et une activité (activity) dédiée à la confirmation de la commande. Une fois la commande confirmée, le client est redirigé vers une activité qui affiche des commandes, offrant la possibilité d'explorer en détail chaque commande passée.






<!---  BioMarketAdmin App --->



<h3 align="left">La deuxième application "BioMarketAdmin" - <a href="https://github.com/Fouad011/BioMarketAdmin.git" >BioMarketAdmin repository</a></h3>

La deuxième application, "BioMarketAdmin", est spécifiquement conçue pour les administrateurs et les gestionnaires afin de superviser et gérer les aspects opérationnels du système d'achat en ligne BioMarket.


<h4 align="left">L'interfaces</h4>
L'interface "home" de l'application BioMarketAdmin est conçue pour fournir une vue d'ensemble et un accès rapide aux fonctionnalités administratives essentielles. Elle offre un tableau de bord centralisé destiné aux gestionnaires et administrateurs. Voici une description de ses caractéristiques principales :
<ul>
  <li><i><b>Ajouter des Produits :</b></i> Les administrateurs peuvent accéder à une fonction dédiée pour ajouter de nouveaux produits au catalogue. Cette fonctionnalité inclut la possibilité de spécifier des détails tels que le nom du produit, la description, le prix, la catégorie, et de télécharger des images pour une représentation visuelle.</li>
  <li><i><b>Gestion des Produits :</b></i> Outre l'ajout de produits, l'interface permet également aux administrateurs d'éditer les informations existantes sur les produits. Ils peuvent mettre à jour les descriptions, ajuster les prix, modifier les quantités en stock, et même supprimer des produits du catalogue si nécessaire.</li>
  <li><i><b>Suivi des Commandes :</b></i> Permet aux administrateurs de diriger les commandes vers le livreur approprié, facilitant ainsi une gestion efficace de la logistique de livraison.</li>
  <li><i><b>Ajouter des Livreurs :</b></i> Une fonction distincte permet d'ajouter de nouveaux livreurs au système. Les administrateurs peuvent saisir les détails pertinents, tels que le nom, les coordonnées</li>
</ul>

<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/ShemaAdmin.jpg" height="1000"/>
</p>










<h3 align="left">La troisième application "BioMarketDelivery" - <a href="https://github.com/Fouad011/BioMarketDelivery.git" >BioMarketDelivery repository</a></h3>

La troisième application, "BioMarketDelivery", a été spécialement développée pour les livreurs intervenant dans le système d'achat en ligne BioMarket.
Cette application permet aux livreurs de parcourir les commandes qui leur sont assignées. Une fois la livraison effectuée, ils peuvent appuyer sur "DELIVERY OF THE ORDER" pour supprimer la commande de l'application client et l'archiver.
<p align="center">
  <img src="https://github.com/Fouad011/imagesBioMarketSystem/blob/main/DeliveryShema.jpg" height="1000"/>
</p>



<h2 align="left">Des liens pertinents et des personnes à contacter pour plus d'informations</h3>










<!---

<h3 align="left">La troisième application "BioMarketDelivery" - <a href="https://github.com/Fouad011/BioMarketDelivery.git" >BioMarketDelivery repository</a></h3>



<h2 align="left">Les vues du projet et comment les utiliser.</h3>
<h2 align="left">Des liens pertinents et des personnes à contacter pour plus d'informations</h3>-->

<h3 align="left">Connect with me:</h3>
<p align="left">
<a href="https://twitter.com/mourchid43" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/twitter.svg" alt="mourchid43" height="30" width="40" /></a>
<a href="https://linkedin.com/in/fouad011" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/linked-in-alt.svg" alt="fouad011" height="30" width="40" /></a>
<a href="https://instagram.com/m.fouad42" target="blank"><img align="center" src="https://raw.githubusercontent.com/rahuldkjain/github-profile-readme-generator/master/src/images/icons/Social/instagram.svg" alt="m.fouad42" height="30" width="40" /></a>
</p>

<h3 align="left">Languages and Tools:</h3>
<p align="left"> <a href="https://www.arduino.cc/" target="_blank" rel="noreferrer"> <img src="https://cdn.worldvectorlogo.com/logos/arduino-1.svg" alt="arduino" width="40" height="40"/> </a> <a href="https://www.gnu.org/software/bash/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/gnu_bash/gnu_bash-icon.svg" alt="bash" width="40" height="40"/> </a> <a href="https://www.w3schools.com/cpp/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/cplusplus/cplusplus-original.svg" alt="cplusplus" width="40" height="40"/> </a> <a href="https://www.w3schools.com/css/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/css3/css3-original-wordmark.svg" alt="css3" width="40" height="40"/> </a> <a href="https://www.w3.org/html/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/html5/html5-original-wordmark.svg" alt="html5" width="40" height="40"/> </a> <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/javascript/javascript-original.svg" alt="javascript" width="40" height="40"/> </a> <a href="https://www.linux.org/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/linux/linux-original.svg" alt="linux" width="40" height="40"/> </a> <a href="https://www.mysql.com/" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/mysql/mysql-original-wordmark.svg" alt="mysql" width="40" height="40"/> </a> <a href="https://www.python.org" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/python/python-original.svg" alt="python" width="40" height="40"/> </a> </p>









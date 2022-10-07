# Drone App 

### Technology used
* Springboot
* Gradle
* Mariadb
* Flyway
* Docker

### APIs to call
after download the repo, import ``Travler-Drone-APIs.postman_collection.json`` to postman

### Features supported
* Add Drone
* Query about drone battery level by ``droneId``
* Query about available drones
* Add Medicine
* Query about Medicine details by ``MedicineId``
* Start trip 
* Check Trip Status by ``tripId``
* Check all loaded medicines for a drone by ``droneId``

### How to run the project 

The project is dockrized so follow the steps below:-

* run command ``docker-compose up``
* now you can use the App via Postman


### DB & User Journey Diagrams
check file ``drone digrams musala.html`` 
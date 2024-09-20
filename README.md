# web_app_legacy #

### End-Point ###

http://localhost:8080/webapp/v2/webAppLegacy/producto?wsdl

 - webapp : pom.xml -> finalName
 - /v2/webAppLegacy/producto : sun-jaxws.xml -> url-pattern

## Instalar y Configurar Herramientas ##

### Docker ###

### Minikube ###

Bajada e instalacion
```shell
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube && rm minikube-linux-amd64
```
Iniciar Cluster
```shell
minikube start
```

Ver Dashboard
```shell
minikube dashboard
```

### kubectl ###

Descargar
```shell
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
```

Comprobar version descargada
```shell
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256"
```

Instalar
```shell
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
```

Comprobar version instalada
```shell
kubectl version --client
```

### Lens ###

Bajar llave
```shell
curl -fsSL https://downloads.k8slens.dev/keys/gpg | gpg --dearmor | sudo tee /usr/share/keyrings/lens-archive-keyring.gpg > /dev/null
```

Agregar repositorio a APT
```shell
echo "deb [arch=amd64 signed-by=/usr/share/keyrings/lens-archive-keyring.gpg] https://downloads.k8slens.dev/apt/debian stable main" | sudo tee /etc/apt/sources.list.d/lens.list > /dev/null
```

Instalar
```shell
sudo apt update && sudo apt install lens
```

Ejecutar
```shell
lens-desktop
```
### Git Descargar REPO ###

```shell
git clone https://github.com/ggchavez-hotmail/web_app_legacy.git
```

### Crear imagen ###

Login a docker hub
```shell
docker login
```

Construir imagen docker
```shell
docker build --pull --rm -f "web_app_legacy/Dockerfile" -t ggchavezhotmail/web-app-legacy:v1 "web_app_legacy"
```

Subir imagen docker
```shell
docker push ggchavezhotmail/web-app-legacy:v1
```

### Configurar y Desplegar Kubernetes ###

Crear namespace
```shell
kubectl create namespace web-app-space
```

Desplegar servicios en el cluster
```shell
kubectl apply -f service.yaml
```

Generar port-forward de servicio
```shell
kubectl port-forward deployment/webapplegacy-service-deployment 8080:8080 -n web-app-space
```

Eliminar servicios del cluster
```shell
kubectl delete -f service.yaml
```

### SOAP UI ###

Bajar interfase SOAP UI y mapear WSDL
http://localhost:8080/webapp/v2/webAppLegacy/producto?wsdl

Probar el END-POINT de Consulta, cambiar el ID=1

### JMETER ###

Activar escuchador puerto

Configurar en SOAP UI el proxy

Crear grupo de hilo

Asociar el escuchar al grupo de Hilo

Ejecutar prueba en SOAP UI

Configurar Grupo de hilo para lanzar pruebas

### Visual VM ###

Instalar
```shell
sudo apt install visualvm
```

Ejecutar
```shell
visualvm
```

Exponer servicio puerto de servicio para monitorear
```shell
kubectl port-forward deployment/webapplegacy-service-deployment 9180:9180 -n web-app-space
```

Agregar "JMX Connexion"
localhost:9180

Ejecutar prueba con JMETER

# web_app_legacy #

## Requisitos ##

Probado en maquina virtual Virtual Box (4 Core / 8Gb RAM).

### Ubuntu ###

Se utilizo Linux LITE (link)

```URL
https://www.linuxliteos.com/download.php
```

Montar VBoxAdditions

Abrir Unidad montada

Instalar Plugin

```shell
sudo sh ./VBoxLinuxAdditions.run
```

Reiniciar

```shell
sudo shutdown -r now
```

### Debian ###

Se utilizo BunsenLabs (link)

```URL
https://www.bunsenlabs.org/installation.html
```

Se debe actualizar para instalar plugin

```shell
sudo apt update
sudo apt upgrade
```

Instalar complementos

```shell
sudo apt install build-essential dkms linux-headers-$(uname -r)
```

Montar VBoxAdditions

Abrir Unidad montada

Instalar Plugin

```shell
sudo sh ./VBoxLinuxAdditions.run --nox11
```

Reiniciar

```shell
sudo shutdown -r now
```

## Instalar y Configurar Herramientas ##

### VSCode ###

Instalar dependencias

```shell
sudo apt update
sudo apt install software-properties-common apt-transport-https wget
```

Bajar Certificados para los paquetes

```shell
wget -q https://packages.microsoft.com/keys/microsoft.asc -O- | sudo apt-key add -
```

Agregar paquetes

```shell
sudo add-apt-repository "deb [arch=amd64] https://packages.microsoft.com/repos/vscode stable main"
```

Instalar Code

```shell
sudo apt install code
```

### JAVA OpenJDK UBUNTU ###

Simplemente es necesario instalar Open JDK 21.

```shell
sudo apt install openjdk-21-jdk
```

### JAVA OpenJDK DEBIAN ###

Se debe instalar Open JDK 17 (version 21 no disponible por defecto).

```shell
sudo apt install openjdk-17-jdk
```

Adicionalmente para VisualVM, se debe instalar versión por defecto.

```shell
sudo apt install default-jdk
```

### Repositorio ###

Instalar GIT

```shell
sudo apt install git
```

Descargar Repositorio

```shell
git clone https://github.com/ggchavez-hotmail/web_app_legacy.git
```

### Docker Ubuntu ###

Descargar dependencias

```shell
sudo apt update
sudo apt install ca-certificates curl gnupg
```

Bajar Certificados para los paquetes

```shell
sudo mkdir -p /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
```

Agregar paquetes

```shell
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list
```

Instalar Docker

```shell
sudo apt update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

Comprobar servicio Docker

```shell
sudo systemctl status docker
```

Agregar usuario Docker

```shell
sudo usermod -aG docker $USER
```

Reiniciar sistema operativo

```shell
sudo shutdown -r now
```

### Docker Debian ###

Proceso completo, similar al Ubuntu

```shell
sudo apt update
sudo apt install apt-transport-https ca-certificates curl gnupg
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker.gpg
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker.gpg] https://download.docker.com/linux/debian bookworm stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo usermod -aG docker ${USER}
sudo shutdown -r now
```

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

### Crear imagen ###

Login a docker hub

```shell
docker login
```

Construir imagen docker (se debe estar en la raiz del proyecto Java descargado)

```shell
docker build --pull --rm -f "Dockerfile" -t ggchavezhotmail/web-app-legacy:v1 "web_app_legacy"
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

Desplegar servicios en el cluster (se debe estar en la raiz del proyecto Java descargado).

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

```URL
https://www.soapui.org/downloads/latest-release.html
```

```shell
chmod 755 SoapUI-x64-5.7.2.sh
```

```shell
./SoapUI-x64-5.7.2.sh
```

```shell
$HOME/SmartBear/SoapUI-5.7.2/bin/SoapUI-5.7.2
```

Mapear WSDL

```URL
http://localhost:8080/webapp/v2/webAppLegacy/producto?wsdl
```

Probar el END-POINT de Consulta, cambiar el ID=1

### JMETER ###

Descargar JMETER

```shell
wget https://downloads.apache.org/jmeter/binaries/apache-jmeter-5.6.3.zip
```

Descomprimir

```shell
unzip apache-jmeter-5.6.3.zip
```

Navegar hasta binario

```shell
cd apache-jmeter-5.6.3/bin
```

Ejecutar

```shell
./jmeter
```

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

## Lanzar pruebas ##

Agregar Escuchador de Puerto en JMETER.

Configurar en SOAP UI el proxy.

Crear grupo de hilo en JMETER.

Asociar el escuchar al grupo de Hilo en JMETER.

Ejecutar prueba en SOAP UI.

Configurar Grupo de hilo para lanzar pruebas en JMETER.

Ejecutar prueba con JMETER.

## Configuración Fuentes ##

Url donde se encuentra el end-point

```URL
http://localhost:8080/webapp/v2/webAppLegacy/producto?wsdl
```

Configuracion dentro de los fuentes:

- webapp : pom.xml -> finalName
- /v2/webAppLegacy/producto : sun-jaxws.xml -> url-pattern

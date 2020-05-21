# Para empezar

### Documentación
Para que funcione la demo del client es necesario modificar algunas cosas:

* En la clase HttpClientTestService, metodo executeRequest(...) hay una variable url, esta debe cambiarse por la url del servicio protegido por HTTPS que queremos consumir.
* El fichero mytruststore.jks que debe ir en el resources lo he eliminado, pero una manera rápida de crearlo es ir a la ruta java_home_directory/jre/lib/security/, allí encontraremos el fichero cacerts. Podemos salvar este fichero aparte poniéndole de nombre mytruststore.jks.
* El fichero client1.p12 esta incluido, pero si queremos crearlo desde cero habría que usar la herramienta del keytool, los pasos los detallo al final.
* Como para crear el certificado P12 o un PEM, necesitamos el certificado de una ca que lo firme, esta ca la creamos nosotros y finalmente tenemos que añadirla a nuestro truststore de la siguiente manera: 
```
keytool -import -file caclient.crt -alias caclient -keystore mytruststore.jks.
```

### Comandos
```
openssl genrsa -des3 -out cacert.key 2048
openssl req -x509 -new -nodes -key cacert.key -sha256 -days 1024 -out cacert.pem
openssl req -new -sha256 -nodes -out mycert.csr -newkey rsa:2048 -keyout mycert.key
openssl x509 -req -in mycert.csr -CA cacert.pem -CAkey cacert.key -CAcreateserial -out mycert.crt -days 500 -sha256
openssl pkcs12 -export -inkey mycert.key -in mycert.crt -certfile cacert.pem -name mycert.bytest -caname mycert.bytest -out mycert.p12
``` 

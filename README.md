# Sorteo de Liga Profesional

Aplicación de terminal para sortear un torneo de eliminación directa. Utiliza arquitectura MVC, emparejamiento recursivo, excepciones personalizadas y serialización de objetos.

## Requisitos

- Java JDK 8 o superior.
- Ejecutar los comandos desde la carpeta raíz del proyecto.

## Estructura

```text
src/
├── controller/
├── data/
├── main/
├── model/
└── view/
```

## Compilar y ejecutar

En Linux o macOS, desde la raíz del proyecto:

```bash
javac -d out $(find src -name "*.java")
java -cp out main.Main
```

La aplicación pregunta si se desea cargar un torneo guardado. Si se responde que no, solicita la cantidad de equipos y genera un nuevo torneo.

La cantidad de equipos debe ser una potencia de 2 y ser al menos 2. Por ejemplo: 2, 4, 8 o 16.

## Persistencia

Al finalizar el sorteo, el torneo se guarda en:

```text
src/data/torneo.ser
```

El archivo contiene la liga, sus equipos, las etapas, los partidos y sus ganadores. Al iniciar la aplicación, se puede cargar ese torneo respondiendo `s`. Si no existe el archivo o no se puede cargar, la aplicación permite crear un torneo nuevo.

## Paquetes

- `model`: equipos, partidos, etapas, reglas del torneo y persistencia.
- `view`: entrada y salida por terminal.
- `controller`: coordinación entre la Vista y el Modelo.
- `main`: punto de inicio de la aplicación.

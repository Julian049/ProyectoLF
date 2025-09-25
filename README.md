# Simulador AFD

# Autores

**Julian David Bocanegra Segura** Cod: 202220214<br>
**Jorge Andres Gonzalez Diaz** Cod:202222829<br>
**Diego Alejandro Gil Otálora** Cod:202222152

Universidad Pedagógica y Tecnológica de Colombia  
Ingeniería de Sistemas y Computación - Lenguajes Formales

Tunja, 2025

## Descripción

Simulador interactivo de Autómatas Fintos Deterministas desarrollado en Java con interfaz gráfica Swing. Permite crear, modificar,validar cadenas y gestionar AFDs con funcionalidades de importación y exportación en formato JSON.

## Características Principales

- Creación interactiva de AFDs mediante interfaz gráfica.
- Validación de cadenas de entrada
- Exportación e importación de AFDs en formato JSON
- Interfaz visual con tabla de transiciones
- Arquitectura MVP (Modelo-Vista-Presentador)


## Funcionamiento

El simulador recibe los siguientes parametros para la generación del AFD.

- Conjunto de estados (Q): El simulador recibe el conjunto de estados los cuales obligatoriamente tienen que ir separados por comas ',' para efectos de funcionamiento.
El simulador procesa los estados ingresados y evalúa que no haya estados repetidos.
- Conjunto de simbolos (Σ): El simulador recibe el conjunto de simbolos los cuales obligatoriamente tienen que ir separados por comas ',' para efectos de funcionamiento.
  El simulador procesa los simbolos ingresados y evalúa que no haya estados repetidos.
- Estado inicial (q0): El simulador recibe el estado q0 ingresado y evalua dos cosas:
  - Que solo se ingrese un estado
  - Que el estado exista
  - Que no exista ya un estado inicial, en caso de existir lo reemplaza con el nuevo ingresado y da un mensaje de confirmación
 
- Conjunto de estados finales (F): El simulador recibe el conjunto de estados de aceptación los cuales obligatoriamente tienen que ir separados por comas ',' para efectos de funcionamiento.
  El simulador procesa los estados finales ingresados y evalúa que no haya repetidos y que estos estados si existan en el conjunt de estados 'Q'

- Función de transicion (δ): El simulador mostrará una tabla de estados vs simbolos y el usuario debera indicar las respectivas transiciones.

### Validación de cadenas.

- Ingrese una cadena en el campo de texto
- Click en "Ejecutar" para ver el proceso
- El sistema muestra:
  - Transiciones
  - Resultado : ACEPTADA o RECHAZADA


### Importación/Exportación

- Exportar AFD
  - Click en "Exportar" para guardar el AFD actual
  - Seleccione la ubicación de su preferencia
  - El AFD se guarda en formato JSON con toda la información
  

- Importar AFD
  - Click en "Importar" para cargar un AFD a partir de un archivo en formato JSON
  - Incluye validación para la importación

### Ejemplo de Uso

1. Ingresar Estados (q0,q1,q2,...)
2. Ingresar Simbolos (0,1,a,b,...)
3. Ingresar estado inicial (Ej: q0)
4. Ingresar estado o conjunto de estados de aceptación (Ej: q4,q5...)
5. Transiciones: Complete la tabla Estados vs Simbolos
6. Validad: Ingresar una cadena y observe el resultado (Ej: 0111)

### Formato JSON

El sistema genera archivos JSON con la siguiente estructura:

```
{
  "states": [
    {"name": "A", "isInitial": true, "isFinal": false}
  ],
  "transitions": [
    {"originState": {...}, "destinationState": {...}, "symbol": "0"}
  ],
  "symbols": ["0", "1"]
}
```

## Requisitos

- Java 8 o superior
- Bblioteca Gson

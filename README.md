# Simulador AFD

# Autores

**Julian David Bocanegra Segura** Cod: 202220214<br>
**Jorge Andres Gonzalez Diaz** Cod:202222829<br>
**Diego Alejandro Gil Otálora** Cod:202222152

Universidad Pedagógica y Tecnológica de Colombia  
Ingeniería de Sistemas y Computación - Lenguajes Formales

Tunja, 2025

## Descripción

El presente proyecto contiene el código para un simulador de Autómatas finitos deterministas (AFD).

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






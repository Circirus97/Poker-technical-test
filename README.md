# PokerTexasHolderOnboardTest

# Ejercicio Texas Holdem

El ejercicio consiste en crear un webservice que resuelva el problema segun las instrucciones dadas.

## Descripción del problema
Tu trabajo es comparar pares de manos de póquer e indicar cuál es el ganador, con que tipo de mano ganó y cuáles fueron las cartas que lo hicieron ganar.

Ejemplos:

Ganó la primera mano, con carta más alta, la carta fue el As .
Ganó la segunda mano, con par, la carta fue el rey.

Nota: el texto solo es explicativo, debe entregar el resultado en un objeto que se evalúa al correr los test.

Debe tener en cuenta que solo debe implementar 5 de los 10 tipos de manos pero en el orden normal, por ejemplo: Escalera real, Escalera de color, Poker, Full House y Color

## Descripción de las reglas de poker

Una baraja de póquer contiene 52 cartas; cada carta tiene un palo que es uno de los tréboles, diamantes, corazones o espadas (indicados con C , D, H y S respectivamente). Cada tarjeta también tiene un valor que es uno de 2, 3, 4, 5, 6, 7, 8, 9, 10, jota, reina, rey, as (denotado 2, 3, 4, 5, 6, 7, 8 , 9, 10, J, Q, K, A). Para fines de puntuación, los trajes no están ordenados, mientras que los valores se ordenan como se indicó anteriormente, siendo 2 el valor más bajo y el más alto el as.

Una mano de poker consiste en 5 cartas repartidas desde el mazo. Las manos de póquer se clasifican según el siguiente orden parcial de menor a mayor.

- Carta alta: Las manos que no encajan en ninguna categoría superior se clasifican según el valor de su carta más alta. Si las cartas más altas tienen el mismo valor, las manos se clasifican por la siguiente más alta, y así sucesivamente.

- Par: 2 de las 5 cartas en la mano tienen el mismo valor. Las manos que contienen un par se clasifican según el valor de las cartas que forman el par. Si estos valores son los mismos, las manos se clasifican según los valores de las cartas que no forman el par, en orden decreciente.
- Dos pares: La mano contiene 2 pares diferentes. Las manos que contienen 2 pares se clasifican por el valor de su par más alto. Las manos con el mismo par más alto se clasifican según el valor de su otro par. Si estos valores son los mismos, las manos se clasifican según el valor de la carta restante.
- Tres de una clase: Tres de las cartas en la mano tienen el mismo valor. Las manos que contienen tres de una clase se clasifican según el valor de las 3 cartas.
- Escalera: La mano contiene 5 cartas con valores consecutivos. Las manos que contienen una recta se clasifican por su carta más alta.
- Color: Mano contiene 5 cartas del mismo palo. Las manos que son ambos enrojecimientos se clasifican según las reglas de Carta alta.
- Full House: 3 cartas del mismo valor, con las 2 cartas restantes formando un par. Clasificado por el valor de las 3 cartas.
- Poker: 4 cartas con el mismo valor. Clasificado por el valor de las 4 cartas.
- Escalera de color: 5 cartas del mismo palo con valores consecutivos. Clasificado por la carta más alta de la mano.
- Escalera real: Cinco cartas del mismo palo del 10 al as.

## Instrucciones

Debe crear una cuenta en github y un repositorio público con el contenido de este proyecto (no un fork), una vez creado debe enviarnos la dirección del repo.

Puede usar cualquier lenguaje de programacion y cualquier framework que desee, lo importante es que el webservice tenga la estructura dada y que pase los test que se encuentran adjuntos a la coleccion de postman.

Estructura de la consulta:

```JSON
{
  "hand1": {
    "type": "string"
  },
  "hand2": {
    "type": "string"
  }
}
```

Estructura de la respuesta

```JSON

{

  "winnerHand": {
    "type": "string"
  },
  "winnerHandType": {
    "type": "string"
  },
  "compositionWinnerHand": {
    "type": "array"
  }
}
```

El resultado de la evaluación de las manos de poker debe ser un objeto que contenga cual de las manos fue la ganadora, con que tipo de mano y cuáles fueron las cartas con las que ganó, dicho resultado se debe evaluar mediante test unitarios, no se solicita ni se evalúa interfaz gráfica, solo el informe de los test unitarios.

Los valores para los tests ya fueron descritos en los test de la coleccion, debe crear las clases que representan las entidades del dominio (ejemplo: Carta, Mano, etc) y los métodos para evaluar el problema según se indicó anteriormente.

Debe implementar solo 5 de las 10 validaciones, debe indicar cuáles de las 5 seleccione.

También debe implementar los test con los casos mencionados en la descripción según las validaciones que escoja, ya que se evaluará que el test pase.

Debe publicar el servicio en un [Paas](https://en.wikipedia.org/wiki/Platform_as_a_service) que este vinculado con el repositorio del ejercicio resuelto (algo parecido al flujo de trabajo de heroku, por ejemplo puede usar [RailWay](https://railway.app/) o cualquier otro servicio que sea parecido).

## Entregable

Url de repositorio publico con la solucion del proyecto.

Url del servicio web funcionando en una plataforma que este vinculado al repositorio.

## Se evaluará:

- Que los webservices funcionen

- Que los webservices pasen los test asociados a los casos que seleccionó.

- Estilo de codificación.

- Diseño de los módulos y las clases.

- Lógica de la implementación.

- Se da un plus si crea test unitarios en el proyecto.

- Se da un plus si implementa más de las 5 validaciones, pero solo si funcionan todas las que implemente.

- Utilizacion de git, como creación de nuevas ramas, utilizar estándar en los mensajes de commit


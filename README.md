# Texas Hold'em Poker Hand Validator🎰

---
## Descripción del Proyecto

Este proyecto implementa un validador de manos de póker para el juego Texas Hold'em. Está construido utilizando el framework Spring Boot y sigue una arquitectura basada en controladores y casos de uso. El objetivo principal del proyecto es recibir dos manos de cartas, validarlas, evaluarlas y determinar cuál de ellas es la ganadora, según las reglas del póker.

---
## Componentes Principales

1. **Controlador (`PokerController`)**:
- Expone un endpoint REST (`/poker/validation`) que recibe una solicitud POST con dos manos de cartas.
- Utiliza el caso de uso (`PokerUserCase`) para procesar las manos y devuelve la mano ganadora junto con el tipo de mano

1. DTOs (`PokerHandRequest` y `PokerHandResponse`)
- `PokerHandRequest`:  Representa la solicitud que contiene dos manos de cartas, ambas validadas para asegurar que no estén vacías.
- `PokerHandResponse`: Contiene la respuesta con la mano ganadora, el tipo de mano ganadora (por ejemplo, FullHouse, Flush), y la composición de la mano ganadora.

1. **Enums (`NameCard`, `NameSuit`, `WinEnum`)**:
- Estos enumerados representan los nombres y valores de las cartas (A, K, Q, J), los palos (Heart, Diamond, Sword, Cane), y los tipos de combinaciones ganadoras (`HighCard`, `Pair`, `FullHouse`, etc.).

1. **Lógica de Negocio (`PokerUserCase`)**:
- Valida que las manos cumplan con un formato específico.
- Evalúa las manos para identificar combinaciones como pares, tríos, escaleras, flushes, y más.
- Determina la mano ganadora basándose en el valor de la combinación obtenida.
- La validación incluye determinar la carta más alta, identificar pares o tríos, y reconocer manos especiales como full house o flush.

1. **Modelo de Resultado (`WinningResult`)**:
- Esta clase encapsula los resultados de la evaluación de una mano, como si tiene una carta alta, un par, un full house, y el valor numérico asociado a esa combinación.

---

## Flujo del Proceso

1. **Recepción de Datos**: El controlador recibe un `PokerHandRequest` que contiene dos manos de cartas.
2. **Validación y Evaluación**: Las manos son validadas y evaluadas por el caso de uso `PokerUserCase`.
3. Determinación del Ganador: Basándose en las reglas del póker, se determina la mano ganadora, y se construye una respuesta con los detalles de la mano ganadora.
4. **Respuesta**: Se retorna un `PokerHandResponse` con la información de la mano ganadora.

---

## Despliegue

La aplicación está desplegada en AWS y puedes acceder al endpoint para la validación de manos de póker aquí: 

 [http://poker-test-env.eba-aqwpymdt.us-east-.elasticbeanstalk.com/poker/validation](http://poker-test-env.eba-aqwpymdt.us-east-2.elasticbeanstalk.com/poker/validation).

---

## Uso de la aplicación

Este es el curl de postman con el que puedes probar la funcionalidad de la aplicacion

```java
curl --location 'http://poker-test-env.eba-aqwpymdt.us-east-2.elasticbeanstalk.com/poker/validation' \
--header 'Content-Type: application/json' \
--data '{
    "hand1": "2H 3D 5S 9C KD",
    "hand2": "2C 3H 4S 8C AH"
}'
```

En el body de este request esta recibiendo un objeto JSON que se compone de dos atributos “hand1” y “hand2” estos dos atributos son las cartas que se le entregara a la mano uno y a la mano dos para hacer sus respectivas validaciones y determinar cual es el ganador.

Este seria un ejemplo de respuesta:

```java
{
    "winnerHand": "hand2",
    "winnerHandType": "HighCard",
    "compositionWinnerHand": [
        "As"
    ]
}
```

---

## Arquitectura

La aplicación TexasHoldem está estructurada de la siguiente manera:

1. Paquete Principal **(com.poker.TexasHoldem)**:
    1. controller:
        1. `PokerController`: Controlador principal de la aplicación que maneja las solicitudes HTTP y delega las tareas apropiadas a los servicios correspondientes.
    2. dto (Data Transfer Objects):
        1. request:
            1. `PokerHandRequest`: Clase que representa las solicitudes que contienen las manos de póker enviadas al servidor.
        2. response:
            1. `PokerHandResponse`: Clase que representa las respuestas que contienen los resultados de la validación de las manos de póker.
    3. model:
        1. enums:
            1. `NameCard`: Enum que representa los nombres de las cartas.
            2. `NameSuit`: Enum que representa los palos de las cartas.
            3. `WinEnum`: Enum que representa los diferentes resultados posibles de una mano ganadora.
        2. winning:
            1. `WinningResult`: Clase que encapsula la lógica y los resultados de las manos ganadoras.
    4. usecase:
        1. `PokerUseCase`: Clase que contiene la lógica de negocio de la aplicación, manejando las reglas del juego y la validación de las manos de póker.
    5. `TexasHoldemApplication`: Clase principal que inicializa y arranca la aplicación Spring Boot.
    
2. resources:
    1. `application.properties`: Archivo de propiedades de configuración para la aplicación Spring Boot, donde se definen configuraciones como la base de datos, puertos y otros parámetros de la aplicación.
       
3. test (com.poker.TexasHoldem):
    1. usecase:
        1. `PokerUseCaseTest`: Clase de prueba unitaria que contiene pruebas para verificar el comportamiento de la lógica de negocio en `PokerUseCase`.
        2. `TexasHoldemApplicationTests`: Clase de prueba que verifica el correcto arranque y configuración de la aplicación Spring Boot.

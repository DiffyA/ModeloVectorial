-- README --

Propósito: explicación del método main para la clase DocumentRecoveryEngine, que es la clase principal de la parte 2 de la práctica.

SECCIONES Y FASES DEL PROCESO DEL MOTOR DE RECUPERACIÓN DE DOCUMENTOS

- CREATING THE DOCUMENT ARRAY FROM THE EIREX DIRECTORY: obtiene los documentos del directorio de EIREX
- STORE INFORMATION IN THE DATABASE: tras indizar los documentos en la fase anterior, pasa la información a la base de datos (alrededor de unos 10-12 minutos para almacenar ~2000 documentos y procesar ~500000 terminos.
- OBTAINING THE QUERIES FROM THE FILE: procesa el fichero 2010-topics.xml para obtener las consultas y sus IDs.
- OBTAINING THE SIMILARITIES BETWEEN QUERIES AND DOCUMENTS IN THE DB: convierte tanto las consultas como los documentos en la BD en su representación vectorial, y procede a calcular la similitud entre cada uno de ellos usando la función cosTFIDF. Al final de este paso, cada query tendrá su propia tabla en la BD con formato T<ID SIN GUIÓN>. Por ejemplo, la query con ID 2010-001 tiene una tabla llamada T2010001
- OBTAINING THE SET OF RECOVERED DOCUMENTS AND RELEVANT DOCUMENTS: obtiene el conjunto de documentos recuperados para una consulta y el conjunto de documentos relevantes para una consulta.
- MEASUREMENTS: obtiene las métricas de los resultados provenientes del paso anterior.

Explicación del main:

El main está dividido por subsecciones denotadas por comentarios en mayúsculas rodeados de 10 guiones por lado.

El método main empieza en la linea 201 del archivo DocumentRecoveryEngine.java. En la linea 201 se crea una instancia del motor.

En la línea 204, entramos a la primera sección llamada "CREATING THE DOCUMENT ARRAY FROM THE EIREX DIRECTORY". Esta sección, como su nombre dice, se encarga de crear el array de documentos en memoria partiendo desde el directorio EIREX. El funcionamiento es que primero se indizan los documentos en estructuras internas de la clase Dictionary en memoria, y luego estas se pasarían a almacenamiento persistente en la base de datos, pero como ya se proporciona la base de datos con toda la información a la hora de entregar esta práctica, esta sección no interesa. Solo interesa si no se tiene la base de datos ya cargada.

En la línea 222, entramos a la segunda sección, llamada "STORE INFORMATION IN THE DATABASE". Como dice su nombre, aquí el código se ocupa de tomar esas estructuras que estarían almacenadas en memoria provinientes de la sección anterior y las guardaría en la base de datos. De esta sección, la única línea importante es la 224, que establece una conección con la base de datos.

En la línea 240, entramos en la sección "OBTAINING THE QUERIRES FROM THE FILE". Esta sección está destinada a leer el fichero 2010-topics.xml para extraer de ahí las consultas con sus ID's. Esto lo hace mediante la clase Crawler.

En la línea 244, entramos a la sección "OBTAINING THE SIMILARITIES BETWEEN QUERIES AND DOCUMENTS IN THE DB". Esto lo que hace es crear una representación vectorial de TODOS los documentos encontrados en la base de datos. Luego, ejecuta la función de similitud (en este caso el cosTFIDF) entre cada consulta y todos los vectores de documentos, y los almacena en la base de datos. Nuevamente, todo está comentado aquí dado que ya está la información en la base de datos proporcionada.

En la línea 255, entramos a la sección "OBTAINING THE SET OF RECOVERED DOCUMENTS AND RELEVANT DOCUMENTS". Aquí, obtendremos el conjunto de documentos recuperados y documentos relevantes de acuerdo a una consulta específica. 

En la línea 274 es donde finalmente entramos a la sección de "MEASUREMENTS", que es donde obtenemos las métricas y las imprimimos en la consola. Aquí es donde ocurre el trabajo.


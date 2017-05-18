-- README --

Prop�sito: explicaci�n del m�todo main para la clase DocumentRecoveryEngine, que es la clase principal de la parte 2 de la pr�ctica.

SECCIONES Y FASES DEL PROCESO DEL MOTOR DE RECUPERACI�N DE DOCUMENTOS

- CREATING THE DOCUMENT ARRAY FROM THE EIREX DIRECTORY: obtiene los documentos del directorio de EIREX
- STORE INFORMATION IN THE DATABASE: tras indizar los documentos en la fase anterior, pasa la informaci�n a la base de datos (alrededor de unos 10-12 minutos para almacenar ~2000 documentos y procesar ~500000 terminos.
- OBTAINING THE QUERIES FROM THE FILE: procesa el fichero 2010-topics.xml para obtener las consultas y sus IDs.
- OBTAINING THE SIMILARITIES BETWEEN QUERIES AND DOCUMENTS IN THE DB: convierte tanto las consultas como los documentos en la BD en su representaci�n vectorial, y procede a calcular la similitud entre cada uno de ellos usando la funci�n cosTFIDF. Al final de este paso, cada query tendr� su propia tabla en la BD con formato T<ID SIN GUI�N>. Por ejemplo, la query con ID 2010-001 tiene una tabla llamada T2010001
- OBTAINING THE SET OF RECOVERED DOCUMENTS AND RELEVANT DOCUMENTS: obtiene el conjunto de documentos recuperados para una consulta y el conjunto de documentos relevantes para una consulta.
- MEASUREMENTS: obtiene las m�tricas de los resultados provenientes del paso anterior.

Explicaci�n del main:

El main est� dividido por subsecciones denotadas por comentarios en may�sculas rodeados de 10 guiones por lado.

El m�todo main empieza en la linea 201 del archivo DocumentRecoveryEngine.java. En la linea 201 se crea una instancia del motor.

En la l�nea 204, entramos a la primera secci�n llamada "CREATING THE DOCUMENT ARRAY FROM THE EIREX DIRECTORY". Esta secci�n, como su nombre dice, se encarga de crear el array de documentos en memoria partiendo desde el directorio EIREX. El funcionamiento es que primero se indizan los documentos en estructuras internas de la clase Dictionary en memoria, y luego estas se pasar�an a almacenamiento persistente en la base de datos, pero como ya se proporciona la base de datos con toda la informaci�n a la hora de entregar esta pr�ctica, esta secci�n no interesa. Solo interesa si no se tiene la base de datos ya cargada.

En la l�nea 222, entramos a la segunda secci�n, llamada "STORE INFORMATION IN THE DATABASE". Como dice su nombre, aqu� el c�digo se ocupa de tomar esas estructuras que estar�an almacenadas en memoria provinientes de la secci�n anterior y las guardar�a en la base de datos. De esta secci�n, la �nica l�nea importante es la 224, que establece una conecci�n con la base de datos.

En la l�nea 240, entramos en la secci�n "OBTAINING THE QUERIRES FROM THE FILE". Esta secci�n est� destinada a leer el fichero 2010-topics.xml para extraer de ah� las consultas con sus ID's. Esto lo hace mediante la clase Crawler.

En la l�nea 244, entramos a la secci�n "OBTAINING THE SIMILARITIES BETWEEN QUERIES AND DOCUMENTS IN THE DB". Esto lo que hace es crear una representaci�n vectorial de TODOS los documentos encontrados en la base de datos. Luego, ejecuta la funci�n de similitud (en este caso el cosTFIDF) entre cada consulta y todos los vectores de documentos, y los almacena en la base de datos. Nuevamente, todo est� comentado aqu� dado que ya est� la informaci�n en la base de datos proporcionada.

En la l�nea 255, entramos a la secci�n "OBTAINING THE SET OF RECOVERED DOCUMENTS AND RELEVANT DOCUMENTS". Aqu�, obtendremos el conjunto de documentos recuperados y documentos relevantes de acuerdo a una consulta espec�fica. 

En la l�nea 274 es donde finalmente entramos a la secci�n de "MEASUREMENTS", que es donde obtenemos las m�tricas y las imprimimos en la consola. Aqu� es donde ocurre el trabajo.


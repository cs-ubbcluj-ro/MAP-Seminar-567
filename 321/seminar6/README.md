<h1>Seminar 6 - Threads </h1>

<b>Creați o aplicație cu interfață grafică care permite numărarea numerelor prime dintr-o listă și folosește mai multe
thread-uri.</b>

****

Se numara numerele prime din lista data.
Aplicația are o fereastră în care utilizatorul poate introduce:
<ol>
<li>lungimea listei de numere pe care se lucreaza</li>
<li>numărul de thread-uri pe care le va folosi programul pentru a procesa lista
</li>
</ol>

De asemenea, pe fereastră există un buton <b>Compute</b>. În momentul în care este apăsat, sunt create thread-urile (un număr de thread-uri egal cu cel introdus). <em>Fiecare thread va procesa o parte din listă în așa fel încât volumul de muncă să fie echilibrat pentru thread-urile create. Spre exemplu, dacă lista de procesat are 1000 de numere, și sunt 4 thread-uri, fiecare ar trebui să proceseze 250 de numere.</em>
Programul așteaptă până când toate thread-urile și-au finalizat execuția, moment în care durata totală de execuție se afișează într-o componentă <b>ListView</b>. 

****

Încercați să rulați programul cu număr variat de thread-uri, inclusiv cu 1. Cele mai bune rezultate ar trebui să fie obținute cu număr de thread-uri = număr de thread-uri disponibile pe CPU al calculatorului vostru.

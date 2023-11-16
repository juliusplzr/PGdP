# Arrays und Pinguine
## Größtes gemeinsames Element
Gegeben seien zwei Arrays von nicht-negativen Integern. Schreibe eine Funktion, die die größte Zahl zurückgibt, die in beiden Arrays vorkommt. Wenn es kein solches Element gibt, muss die Funktion -1 zurückgeben.

### 1. Großtes gemeinsames Element 
Implementiere die Methode greatestCommonElement(int[], int[]) in der Klasse ArraysUndPinguine.


Aufsteigende Teilfolge
Wenn wir alle Elemente aus dem Array entfernen, die nicht strikt größer sind als alle vorherigen Elemente, bekommen wir eine aufsteigende Teilfolge.

Aufsteigende Teilfolge Keine Tests
Implementiere die Methode toAscending(int[]) in der Klasse ArraysUndPinguine, die mit dem oben beschriebenen Verfahren aus der gegebenen Array eine aufsteigende Array macht und diese zurückgibt.
Beispiel: Aus dem Array {1, 5, 3, 4, 6, 6} werden die Elemente 3, 4, und eine 6 entfernt. (3 und 4 sind nicht größer als 5, die zweite 6 ist nicht größer als die erste 6.) Das Ergebnis wäre dann {1, 5, 6}.
Hinweis: Möglicherweise hat das Array, das zurückgegeben wird, eine andere Länge als die Eingabe.

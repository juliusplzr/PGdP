# Arrays und Pinguine
## Größtes gemeinsames Element
Gegeben seien zwei Arrays von nicht-negativen Integern. Schreibe eine Funktion, die die größte Zahl zurückgibt, die in beiden Arrays vorkommt. Wenn es kein solches Element gibt, muss die Funktion -1 zurückgeben.

Implementiere die Methode greatestCommonElement(int[], int[]) in der Klasse ArraysUndPinguine.


### Aufsteigende Teilfolge
Wenn wir alle Elemente aus dem Array entfernen, die nicht strikt größer sind als alle vorherigen Elemente, bekommen wir eine aufsteigende Teilfolge.

Implementiere die Methode toAscending(int[]) in der Klasse ArraysUndPinguine, die mit dem oben beschriebenen Verfahren aus der gegebenen Array eine aufsteigende Array macht und diese zurückgibt.
Beispiel: Aus dem Array {1, 5, 3, 4, 6, 6} werden die Elemente 3, 4, und eine 6 entfernt. (3 und 4 sind nicht größer als 5, die zweite 6 ist nicht größer als die erste 6.) Das Ergebnis wäre dann {1, 5, 6}.
Hinweis: Möglicherweise hat das Array, das zurückgegeben wird, eine andere Länge als die Eingabe.

### Pingulympics
Bei den Pingulympics werden die Spieler in Teams verteilt basierend auf ihren Namen. Dabei besteht jedes Team aus Pinguinen, deren Namen mit dem gleichen Buchstaben anfangen. Alex, Alice, Bob, Bertha und Cathy würden z.B. in drei Teams verteilt werden: Alex & Alice, Bob & Bertha und Cathy allein. Wir wollen jetzt rausfinden, wie viele Teams es auf den Pingulympics gibt. Dabei bestehen Namen ausschließlich aus ASCII Buchstaben, und alle Namen bestehen aus mindestens einem Buchstaben. Außerdem ist der erste Buchstabe immer groß.

Implementiere die Methode numberOfTeams(char[][]) in der Klasse ArraysUndPinguine, die die Anzahl der Teams nach der Verteilung zurückgibt. Das Eingabeparameter ist eine Array von Namen, die als char[] dargestellt werden.
Beispiel: numberOfTeams( new char[][] { {'A', 'l', 'e', 'x'}, {'B', 'e', 'r', 't', 'h', 'a'}, {'B', 'o', 'b'}, {'A', 'l', 'i', 'c', 'e'}, {'C', 'a','t','h','y'} } ) soll 3 zurückgeben.


### Pinguball
Beim Hauptspiel der Pingulympics hat jedes Team genau 4 Würfe auf ein rechteckiges Ziel. Das Ziel ist in Felder mit unterschiedlichen Punktzahlen unterteilt. Danach wird wie folgt berechnet, wie viele Punkte ein Team bekommen hat:

Jedes getroffene Feld bringt die entsprechende Menge an Punkte, allerdings nur einmal unabhängig davon wie oft das Feld getroffen wurde.
Ein Team bekommt 10 Bonuspunkte wenn alle Würfe das Ziel treffen, unabhängig davon welches Feld getroffen wurde.
Für jede Zeile oder Spalte, auf der alle Felder getroffen wurden, bekommt ein Team 5 Bonuspunkte.


Implementiere die Methode pinguballPoints(int[][] fieldPoints, int[][] hits) in der Klasse ArraysUndPinguine, die die erreichte Punktzahl zurückgibt. Dabei beschreibt fieldPoints wie viele Punkte die jeweiligen Felder bringen. hits hat dieselben Dimensionen und beschreibt wie viele Würfe die einzelnen Felder getroffen haben.
Die Punktzahl passt immer in ein int.

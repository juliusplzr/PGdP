Kreise
 In oder auf dem Kreis Keine Tests
Implementiere die Methode isOnOrInsideCircle(int r, int x, int y) in der Klasse Kreise. Sie soll zurückgeben, ob sich der Punkt (x,y) auf dem Koordinatensystem innerhalb 
oder auf dem Kreis mit Mittelpunkt (0,0) und Radius r befindet. Der Kreis selbst besteht aus Punkten mit Abstand r zum Ursprung.
Im roten Bereich
Betrachte den Kreis mit Mittelpunkt (0,0) und Radius 200, sowie das Quadrat 1, mit Ecken (0,0),(0,100),(100,0),(100,100) und das Quadrat 2 mit Ecken 
(−100,−100),(0,−100),(−100,0),(0,0). Der Bereich zwischen dem Kreis und den Quadraten ist in der folgenden Abbildung rot markiert.

Die Ränder der Quadrate und des Kreises gehören dabei zu dem roten Bereich.
Implementiere die Methode isInRedArea(int x, int y), die zurückgibt, ob der Punkt (x,y) sich auf dem oben beschriebenen roten Bereich befindet.

Für eine gegebene positive ganze Zahl n, betrachten wir jetzt die Punkte (x,y) mit x,y∈Z,−n≤x≤n,−n≤y≤n. Für n=5 sind sie auf der folgenden Abbildung dargestellt. 
Die roten Punkte befinden sich im oder auf dem Kreis.
Implementiere die Methode countPointsInCircle(int n), die die Anzahl solcher Punkte zurückgibt, die sich in oder auf dem Kreis mit dem Radius n befinden.

Sei n gegeben. Dots sei die Anzahl aller Punkte (x,y) aus Teilaufgabe 3. Red sei die Anzahl der Punkte in oder auf dem Kreis.
Dann ergibt (Red / Dots) ∗4 eine Approximation von π, die mit steigendem n immer exakter wird. Implementiere die Methode approximatePi(int n), die für n diese Approximation berechnet.

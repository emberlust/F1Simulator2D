# F1Simulator2D
## **2. Desfășurare cursa Formula 1**
La o cursa de Formula 1 participa N monoposturi (piloți). Fiecare mașină are
anumiți parametri: viteza maxima, rata de consum al combustibilului, timp de viață al
roților, viteza de accelerație.
Traseul (pista) pe care are loc cursa are curbe la stânga sau la dreapta (fiecare
are o anumita viteza maxima cu care poate fi abordata), intrare/ieșire la boxe si linie de
start/linia de finish.

Pista poate fi gândita ca o succesiune de căsuțe (pătratele). Inițial cele N
monoposturi ocupa poziții consecutive in cadrul grilei de start in spatele liniei de start.
O depășire nu poate sa aibă loc decât daca cele doua monoposturi se afla pentru un
anumit timp in aceeași căsuța moment in care cel care vine din urma poate sa încerce o
depășire. Depășirea are un succes cu o anumita probabilitate si poate sa reușească sau sa
eșueze fie cu 0, 1 sau ambele monoposturi avariate (abandon al cursei). Cursa se
termina după un anumit număr de tururi de pista.
Sa se simuleze (grafic) desfășurarea unei astfel de curse. La finalul cursei se va
afișa clasamentul si punctele aferente fiecărui concurent, precum si însumarea pe echipe
(o echipa are 2 monoposturi in cadrul cursei). Aceste informații vor fi salvate in cadrul
unor fișiere de statistica, astfel încât in orice moment sa se poată afișa clasamentul
piloților respectiv clasamentul echipelor determinat conform punctele acumulate in
celelalte curse.

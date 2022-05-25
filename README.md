# Zadanie 1 - Monopoly lite
B-OOP 2021

Vašou úlohou je naprogramovať zjednodušenú hru Monopoly ako konzolovú hru v jazyku Java. Veľkosť hracieho poľa je 24 políčok, z čoho sú:

* 4 rohové políčka (Štart, Väzenie, Polícia (Choď do väzenia) a Platba dane),
* 4 políčka šance (treba mať minimálne 5 kariet),
* 16 nehnuteľností.

Hra začína výberom počtu hráčov a ich mien. Po zadaní mien všetkých hráčov hra začína, a prvý hráč hádže kockou. Po hode kockou sa hráč posunie o toľko políčok, koľko hodil na kocke. Keď hráč stúpi na políčko, vykoná sa akcia daného políčka. Definované akcie:

* Väzenie - Návšteva väzenia
* Polícia - Uväznenie hráča do väzenia na X kôl
* Platba Dane - Hráč musí zaplatiť určitú čiastku
* Políčko Šanca - Hráč si potiahne kartu a vykoná akciu, ktorá je na karte definovaná (z balíčku si hráč potiahne vždy novú kartu, nie náhodnú, keď balíček minie, začne ho používať od znova)
* Nehnuteľnosť - Hráč môže dané políčko kúpiť za určitú sumu, ak ho ešte nik nevlastní a ak má na neho peniaze. Ak už políčko patrí inému hráčovi, musí mu zaplatiť "stojné". Každá nehnuteľnosť má predom definovanú cenu a výšku stojného.

Každý hráč obdrží určitú sumu pri prechode štartom. Hráč, ktorý nemá peniaze na zaplatenie daní alebo stojného, prehral. Hra končí, keď ostáva v hre už len jeden hrajúci hráč.

## Hodnotenie

Zadanie je hodnotené 10 bodmi. **Odovzdaný program musí byť skompilovateľný, inak je
hodnotený 0 bodmi**. Skompilovateľnosť zadania kontroluje aj github pipeline. Hlavný dôraz v hodnotení sa kladie na objektový prístup a princípy,
okrem iného:

* vhodné pomenovanie tried a metód v jednotnom jazyku (názvy tried s veľkým počiatočným písmenom, názvy metód s malým),
* vhodné použitie modifikátorov prístupu (public, private, poprípade protected) na obmedzenie prístupu k metódam a atribútom,
* využitie dedenia a polymorfizmu,
* použitie výnimiek na ošetrenie nedovoleného správania (nehádzať a nezachytávať všeobecnú triedu Exception),
* nepoužívajte nested classy,
* vo vašich triedach nevytvárajte statické metódy (v zadaní nie sú potrebné),
* v hlavnej triede (main) nevytvárajte žiadnu logiku, iba vytvorte nový objekt.

## Odovzdávanie
Zadanie si naklonujte z repozitára zadania. Svoje vypracovanie nahrajte do vášho repozitára pre toto zadanie pomocou programu Git (git commit + git push). Vypracovanie môžete "pusho-vať" priebežne. Hodnotiť sa bude iba _master_ branch. Zadanie je nutné vypracovať do **26.3.2021 23:00**.

V projekte upravujte iba súbory v priečinku _src/main_ a jeho podpriečinkoch. Ostatné súbory je upravovať zakázané (predovšetkým súbory _pom.xml_ a súbory obsahujúce github pipeline-y).

Vo svojom github účte si nastavte svoje meno (settings > profile > name), aby bolo možné priradiť riešenie ku študentovy. **Pokiaľ nebude možné spárovať študenta s riešením je zadanie hodnotené 0 bodmi!**

# Assignment 1 - Monopoly lite
B-OOP 2021

Your task is to create a simplified version of the board game Monopoly in the form of a Java console application. The board consists of the following 24 tiles:

* 4 corner tiles (start, jail, police (go to jail), tax payment)
* 4 chance tiles (the game must have at least 5 chance cards)
* 16 property tiles

The game starts by inputting the number and names of all the players. After the names have been put in, the game starts. The first player rolls the dice and move their character the corresponding number of tiles. When a player steps on a tile, the effect of the tile triggers. Tile effects:

* jail - the player is just visiting the jail
* police - imprison the player for X turns
* tax payment - the player must pay a specific amount to the bank
* chance - the player draws a chance card from a deck, and the effect of the drawn card is triggered. The players always draw a new card from the deck - not a random one. After the entire deck is drawn, the cards are put back in to the deck, and they can be drawn again.
* property - the player can buy the property for a specific cost if they have enough money and if the property doesn't have an owner. If the property belongs to another player, the player that stepped on the property must pay a "visiting fee" to the owner. The price and visiting fee of each property is fixed.

When a player passes the start tile, they receive some money. If a player can not pay the tax, or a visiting fee they loose the game. The game ends when there is only one player remaining.

## Grading
You can get 10 points for this assignment. **The program must be able to compile, otherwise 0 points are given for the assigment**. The github pipeline checks whether the program can be compiled. The main focus during grading is put on object-oriented approach and principles used by the solution. Including, but not limited to:
* appropriate naming of classes and methods in a single language (class names starting with a capital letter, method names starting with a lowercase letter),
* appropriate use of access modifiers (public, private, or protected) when restricting access to class methods and attributes
* the use of inheritance and polymorphism
* usage of exceptions when handling undesired behavior (do not catch or throw the instances of the generic Exception class)
* don't use nested classes
* don't use static methods (you don't need them to complete the assignment)
* don't put any logic into the main method and its class. The main method should only be used to create a new object

## Handing in the assigment
Clone the assignment form your repository created from this template. Upload your solutions to your repository using the Git version control system (git commit + git push). You can push commits to the repository while you work - you don't have to push everything at once. Only the code in the _master_ branch will be graded. You have until **26.3.2021 23:00** to complete the assignment.

Only edit files in the _src/main_ folder or its sub-folders. You mustn't change any other files in the repository (especially the _pom.xml_ file, and the github pipeline files).

You have to have your name set in your github account (settings > profile > name), so that we can match students with their assignments. **If we are unable to match a student with their assignment, the student will receive 0 points for the assignment!**

# financial-register

##I.	Bevezetés
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
##II.	Felhasználói dokumentáció 
###A  program által megoldott feladat
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
### A felhasználói felület bemutatása, használata
####Kezdőlap
Lorem ipsum dolor sit amet, consec...
####Regisztráció
Lorem ipsum dolor sit amet, consec...
####Bejelentkezés
Lorem ipsum dolor sit amet, consec...
####Pénzmozgás
Lorem ipsum dolor sit amet, consec...
#####Rögzítés
Lorem ipsum dolor sit amet, consec...
#####Módosítás
Lorem ipsum dolor sit amet, consec...
#####Listázás
Lorem ipsum dolor sit amet, consec...
#####Statisztika
Lorem ipsum dolor sit amet, consec...
#####Profil
Lorem ipsum dolor sit amet, consec...

##III. Fejlesztői dokumentáció
###Felhasznált technológiák
#### Gradle
Ezt az eszközt szoftverprojektek menedzselésére és az build folyamatok automatizálására fejlesztették ki. Egyik nagy előnye a multi-projekt építés menetének egyszerűsítése, ugyan szakdolgozatomban nem használom ki ezt a tulajdonságát. Támogatja ezen felül az inkrementális projektépítéseket intelligensen meghatározva, hogy mely részei frissek a függőségek felépítési fájának, így az azon részektől függő feladatok újra feldolgozása nem szükséges. Ez egy nagy könnyedség, nem kell minden függőséget interneten keresztül felkutatni, akutális verziót figyelni a programkönyvtárnak, nem kell bemásolni a projektbe is (így nem növeli a verziókezelt projektet, commitok méreténél fontos). A plugin alapú architektúrája lehetővé teszi tetszőleges parancssorból vezérelhető alkalmazás használatát, de integrálva a fejlesztő környezetünkbe megszabadulunk ezektől az ismétlődő lépésektől is. A .gradle file-ban a buildelendő projektet és annak függőségeit Groovy nyelven írom le, amit az archive összeállítása során használ. Buildelés folyamata során lefordulnak a src/main/java package-ben található forrásfájlok, bemásolódnak az erőforrások, összeáll a program class és resources mappája. Ezekből összeállítja az archive-t, ami lehet WAR vagy JAR, ezek után összeáll az összes többi archive forrással, hozzáadott típussal. Ha vannak tesztjeink, ugyan ez a folyamat játszódik le, csak még le is futtatja a teszteket. Ezek után lefut az egész építés, bekerül minden a build mappába, és kész is, megkezdődhet a deployolás folyamata, amit egy külön részben részletezek.
####Frontend
A rendszer legfelsőbb rétege, amelyet a felhasználó közvetlen elér, ezen a rétegen keresztül éri el és tudja módosítani az adatokat egy kényelmes felületen.
* #####Thymeleaf 
  *Egy elegáns, jól bővíthető és konfigurálható Java template engine, amit a nézeti rétegben tudunk használni egy alkalmazásban. Ez egy különálló keretrendszer, létező keretrendszereket egészít ki, Teljeskörű Spring integrációt biztosít statikus propertyk és Spring expression language támogatással. A feldolgozás nem szekvenciális szövegfeldolgozással, hanem DOM alapon működik. A létrehozott templatek a hozzáadott adatokból rendereli ki a kész dokumentumot (XML, XHTML, HTML5), amit a böngészőnk meg tud jeleníteni. A beérkező kérést a front controller fogadja, továbbítja a megfelelő controllernek, ami a végrehajtott operációk után visszatér egy renderelési objektummal, amit a hozzákapcsolt view template-be parse-ol, majd az eredményt a front controller elküldi a felhasználónak, amit már egy böngésző meg tud jeleníteni. Nagy előnye, hogy alkalmazás szerver nélkül is fejleszthető, így a külön álló template file-ok böngészőben megtekinthetőek. Fregmens template-k használatával erősen hozzájárul az újra felhasználhatósághoz sok fejlesztői időt megspórolva így.*
* #####Chart.js
  *Egy hatékony adatvizualizációs library, amely segítségével a rengeteg rögzített adatoknak egy letisztult megjelenítési felületet tudok biztosítani. A megjelenítés egy canvas HTML elemen történik, egy új chart objektum hívással, amit sokféleképpen lehet paraméterezni. Lehet sáv, oszlop, kevert és még sok féle diagram, testreszabva a színezést, tengelyek leírásait, ha szükséges.* 
* #####JavaScript
####Backend
A rendszer legalsóbb rétege, ahol a front-end réteg felől érkező adatok tényleges feldolgozása folyik, majd szükség esetén választ is küld annak.
* #####Spring
  *Habalasabala*
  - Boot
  - Security
* #####Java 8
* #####Apache Tomcat
  - Egy tisztán Java nyelven íródott webszerver, amely implementálja a Sun-féle Java Servlet és a JavaServer Pages specifikációkat. Ezeket a specifikációkat támogató webszervereket szokás a servlet container, a servlet engine illetve a web engine összetételekkel is illetni. Kibővíti a Java virtuális gépének futási környezetét, kezelve az adatbázis kapcsolatot, kommunikációt a klienssel. Minden kéréshez új szálat hoz létre egy folyamaton belül, így növelve a több-felhasználós alkalmazások hatékonyságán.
  
* #####Adatbázis
  *Adatbázisok alatt struktúrált adatok összességét értjük, ahol a struktúrát az adattáblák és a közöttük lévő relációk írnak le. Ezekhez az adatokhoz hozzáférhetünk, lekérdezhetjük és szerkeszthetünk egy megfelelő adatbázis-kezelő szoftver segítségével. A programom esetében MySql-re esett a választásom, ami az egyik legelterjedtebb nyílt forráskodú, többfelhasználós relációs adatbázis-kezelő szerver.* 

  - JPA
    - Szükségünk van olyan adatokra, melyek túlélik az őket létrehozó folyamatokat, majd később használni tudjuk őket. A Java perzisztenciát hasonlóképpen definiálhatjuk, ebben a kontextusban a tárolás a Java programozási nyelv segítségével történik JPA (Java Persistence API) interface-vel. Ez egy olyan interface-t biztosít, amelyet különböző módokon lehet implementálni.
    Hogy mi is az a perzisztencia? Adatok tartós tárolása a perzisztenciában részt vevő entitást pedig képzeljük el egy Java osztályként, ez az osztály a perzisztencia táblája, sorai az osztály példányainak felelnek meg. JPA defininiál egy Entity Manager API-t, ami a lekérdezéseket és a tranzakciókat az osztály objektumain futási időben dolgozza fel adatbázis felé.

  - Hogyan jön a képbe Hibernate?
    - Képzeljük el a JPA-t, mint egy művészeti ágat, Hibernate-et pedig ezt a bizonyos művészetet megvalósító keretrendszert. JPA egy protokolt ír le, sztandard specifikációkat, elnevezési és más konvenciókat, a Hibernate pedig megvalósítja ezeket. Hibernate implementációja pedig azt írja le, hogy hogyan történjen az objektumok leképezése relációkkal. Megjelölhetjük annotációkkal az osztályokat, adattagokat, de implementáció nélkül nem történne semmi. Előnye ennek a módszernek, hogy multi-projekt esetén több implementációt is használhatunk egyszerre.

		
###Program struktúra
####A program csomagszerkezete
* Controllers
* Services
* Models
  * dto
  * dao
* Repositories
* Adatbázis
  * Adatbázis elérés
  * Felépítés
			
###Deployolás folyamata
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
##IV. Egy kéréstől a válaszig
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.

## V. Fejlesztési javaslat
unit tests, integrational tests, terheléses teszt, funkcionalitás bővítés, statisztika bővítés

##VI.	Összefoglalás
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
##VII. Irodalomjegyzék
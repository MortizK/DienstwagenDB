# Dienstwagen DB

* Modul: Programmieren 2 (Java)
* Semester: 2 and der DHBW Stuttgart

Meine [Aufgabe](https://wwwlehre.dhbw-stuttgart.de/~unterstein/scripts/2025-Projekt.pdf) ist es eine text-Datei einzulesen und diese in ein Datenmodell zu implementieren.

## Datenmodell

Es gibt drei Datenklassen

1. Driver (Fahrer)
2. Car (Fahrzeug/ Dienstwagen)
3. Trip (Fahrt)

Diese haben verschidene Attribute und eine `Fahrt` verbindet einen `Fahrer` mit einem `Fahrzeug`

### Driver

```

```

### Car

```

```

### Trip

```

```

## UML Diagramm

Muss ich noch erstellen ;)

## Funktionen

Diese sollen über einen Statischen Modus implementiert werden, sodass über die Kommandozeile Argumente an die `Main-funktion` übergeben wird.

```bash
--fahrersuche="Hoff"
--fahrzeugsuche="Volkswagen"
--fahrerZeitpunkt="S-MN-9932;2024-02-14T13:57:43"
--fahrerDatum="F003;2024-08-13"
```

### Fahrersuche

Argument: `String`<br>
Rückgabe:

```bash
--fahrersuche="Luca"
[model.Driver@[id=9, name=Luca, surname=Becker, driverLicenseClass=BE], 
model.Driver@[id=32, name=Luca, surname=Mueller, driverLicenseClass=BE], 
model.Driver@[id=38, name=Luca, surname=Becker, driverLicenseClass=BE]]
```

### Fahrzeugsuche

Argument: `String`<br>
Rückgabe:

```bash
--fahrzeugsuche="Passat"
[model.Car@[id=8, brand=Volkswagen, model=Passat, numberPlate=S-IJ-7101], 
model.Car@[id=22, brand=Volkswagen, model=Passat, numberPlate=S-WX-3158]]
```

### FahrerZeitpunkt

Argument: `String` in der Form `${Kennzeichen};${Datum}`<br>
Rückgabe:

```bash
--fahrerZeitpunkt="S-MN-9932;2024-02-14T13:57:43"
Paul Becker
```

### FahrerDatum

Argument: `String` in der Form `${Fahrer};${Datum}`<br>
Rückgabe:

```bash
--fahrerDatum="F003;2024-08-13"
Ben Wagner (S-GH-3277), Mia Hoffmann (S-GH-3277).
```

## Unit Tests

Diese sollen auch für die vier oben genannten Funktionen und den import erstellt werden.
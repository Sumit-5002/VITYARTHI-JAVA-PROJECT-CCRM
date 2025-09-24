# Requirements

## Environment
- OS: Windows 10/11 (tested)
- JDK: 17 or higher (tested on Java 23)
- Tools: `javac`, `java`, `cmd`

## Build
From project root:

```
rmdir /s /q out 2>nul & mkdir out & (for /r %f in (*.java) do @echo %f) > sources.txt & javac -encoding UTF-8 -d out @sources.txt
```

## Run
```
java -cp out edu.ccrm.Main
```

## Data Files
- Place CSVs in `data/` folder:
  - `data/students.csv`
  - `data/courses.csv`

## Notes for Automated Evaluation
- No external services are required.
- The entry point is `edu.ccrm.Main`.
- Use `-ea` to enable assertions if needed.


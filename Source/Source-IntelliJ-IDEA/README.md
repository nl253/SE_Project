# Software Engineering Project 

## Running

The entry point to the application is the **"Main"** (`src/main/java/Main.java`)
The code was written using **IntelliJ** (and the app comes with it's project 
metadata so it's probably easiest to test it by opening it in IntelliJ)

We managed to get it to work with the following configurations.

| Platform  | Runs                                                 | IDE            |
|-----------|------------------------------------------------------|----------------|
| Windows   | No (seems to require a special plugin to run JavaFX) | Eclipse Neon   |
| GNU/Linux | Yes                                                  | Eclipse Photon |
| GNU/Linux | Yes                                                  | Intellij 2017  |
| MacOs     | Yes                                                  | IntelliJ 2017  |
| Windows   | Yes                                                  | IntelliJ 2017  |
 
## Source Code

- All source code is stored in `src/main/java/`.
- Compiler output: `target/` and `out/`

## Unit Tests

- We used **JUnit 5**
- If using IntelliJ or Eclipse you might be prompted to **add JUnit5 to classpath**
- We are including JUnit 5 in `lib/` but it might be necessary to manually agree to the prompt mentioned above
- All tests are stored in `src/test/java/`
- **Test plan** is in the documents that will come with the submission

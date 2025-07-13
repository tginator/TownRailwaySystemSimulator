---

# Town Railway System Simulator

This project simulates a dynamic network of towns exchanging goods via railways, demonstrating core Object-Oriented Programming (OOP) principles and design patterns. Developed for the Object-Oriented Software Engineering unit at Curtin University.

---

## Features

- **Dynamic Town & Railway Generation:** Towns and railways are created and evolve as the simulation runs.
- **Stateful Simulation:** Each railway progresses through construction and operational states, using the State pattern.
- **Goods Transport:** Goods are accumulated and transported between towns based on population and railway status.
- **Observer Pattern:** Towns and railways notify each other of changes (e.g., population, construction).
- **Factory Pattern:** Creation of different railway types is managed via factories.
- **Automatic Input:** Simulation events are generated automatically, with support for reproducible runs.
- **UML & State Diagrams:** Included for design reference.
- **Logging & Output:** Simulation progress is logged, and the current network is exported in GraphViz `.dot` format.

---

## Technologies Used

- Java (JDK 8+)
- Gradle (build & run)
- PMD (static analysis)
- JUnit (testing)
- GraphViz (network visualization)
- Command-line interface

## How to Build & Run

1. **Clone the repository** and navigate to the project root.
2. **Build the project:**
   ```sh
   ./gradlew build
   ```
3. **Run the simulation:**
   ```sh
   ./gradlew run
   ```
   - The simulation will start and print daily updates to the console.
   - Press `Enter` in the console to stop the simulation.

---

## Output

- **Console:** Daily status of all towns, railways, and goods transported.
- **File Output:**
  - `simoutput.dot`: GraphViz file representing the current town and railway network.
  - `simoutput.pdf`: (if GraphViz is installed) Visual diagram of the network.
  - `app0.log`: Log file with detailed simulation events.

---

## Project Structure

- `src/main/java/edu/curtin/oose2024s2/assignment2/` - Main simulation code
- `src/main/java/edu/curtin/oose2024s2/assignment2/patterns/` - Design pattern implementations
- `src/main/java/edu/curtin/oose2024s2/assignment2/towns/` - Town and railway logic
- `build.gradle` - Build and run configuration
- `UML.png`, `STATECHART.png` - Design diagrams

---

## Design Patterns Used

- **Observer:** Towns and railways notify each other of changes.
- **State:** Railways change behavior as they progress from construction to operational.
- **Factory:** Creation of railway types is abstracted.
- **Dependency Injection:** Factories and updaters are injected for flexibility.

---

## Notes

- Towns are guaranteed to be generated at the start of the simulation.
- Goods are accumulated based on population and transported after railways progress.
- Railways can begin transporting goods the day construction completes.
- The simulation is fully automated; no manual input is required during execution.

---

## Testing & Quality

- Run tests with:
  ```sh
  ./gradlew test
  ```
- PMD static analysis is run automatically during build.

---

## License

This project is for educational purposes as part of Curtin University's OOSE unit.

---




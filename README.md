# Overview
The first part of the project focuses on solving the symmetrical Traveling Salesman Problem (TSP) using a genetic algorithm. The algorithm explores various combinations of specific operators namely selection, crossover, and mutation to identify the optimal solution. This first part is implemented entirely in Java and handles the computational and structural aspect of solving the problem.

The second part of the project involves analyzing the results from each generation and evaluating the final outcomes of all solutions. This analysis provides insights into the performance of different operator combinations and their effectiveness in achieving optimal results.
## 

### 1. Solving TSP

#### Dataset
- **[bays29](http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/STSP.html)**: comes from TSPLIB, it has distances of 29x29 cities. Optimal solution is 2020 value.
#### Parameters
  - **Genetic Objects**:
    - **Chromosome**: A set of integers representing the order in which points are visited.
    - **Population**: A set of chromosomes, representing different potential solutions.
    - **Fitness Function**: In context of symmetrical TSP, this is inverse of the total distance between the points in the chromosome.
- **Operators**:
    - **Selection Type**:
        - **Tournament**: Selects a subset of the population (set to 20%) and picks the chromosome with the shortest route as the winner. Winner chromosome is returned.
        - **Rank**:
            - The sum of the sequence of natural numbers up to the population size is calculated (e.g., 1 + 2 + ... + N).
            - The population is sorted in descending order of route length.
            - A number is drawn from the range of the calculated sum.
            - Iterate through sorted chromosomes, adding each chromosome position number until the drawn number is exceeded. The corresponding chromosome is returned.
        - **Roulette**:
            - Fitness for each chromosome is calculated as `max distance in population - distance of current chromosome`.
            - Normalize values to 1 by dividing each fitness value by the sum of all fitness values.
            - Draw a random number between 0 and 1.
            - Iterate through chromosomes, summing fitness values until the drawn number is exceeded. Return corresponding chromosome in which the number is exceeded.
    - **Crossover Type**:
        - **Single Point**: A crossover point is chosen, and genes are swapped between parents beyond that point. Missing genes are filled using a systematic check and replacement process. this method is very computationally expensive.
        - **PMX**: Two crossover points are chosen. A segment from one parent is directly copied to the child, while other positions are filled using a mapping function to ensure unique genes. [More comprehensive description](https://en.wikipedia.org/wiki/Crossover_(evolutionary_algorithm)#Partially_mapped_crossover_(PMX))
        - **OX1**: Two points split the chromosome into three segments. The middle segment is directly copied from one parent, and the remaining positions are filled in order from the other parent, skipping duplicates. [More comprehensive description](https://en.wikipedia.org/wiki/Crossover_(evolutionary_algorithm)#Order_crossover_(OX1))
    - **Mutation Types**: 
      - **Swap**: Two random points are selected, and their values are swapped.
        - A→B→C→D→E 
        - draw 2 random points, for example B and D
        - A→D→C→B→E
      - **Adjacent Swap**: 
        - Two adjacent points are selected and swapped
        - A→B→C→D→E
        - draw 2 random adjacent, for example: C and D
        - A→B→D→C→E
      - **Inversion**: 
        - A random segment of genes is reversed
        - A→B→C→D→E
        - draw 2 random points, for example B and D
        - A→D→C→B→E
      - **Insertion**:
        - A random gene is moved to a new random position
        - A→B→C→D→E, 
        - draw random point, for example C, and new random position for it, for example after E
        - A→B→D→E→C




#### Test Parameters
- **Population**: 40
- **Generations in each test**: 1000
- **Each test repetitions**: 1000
- **Combinations of TSP operators**: 36



#### Output
- **Records**: 36 million records stored in a single JSON file for each generation.

#### Design Patterns
This project makes use of key design patterns to modularize and simplify the implementation of selection, crossover, and mutation strategies. Here's how these patterns are utilized:
- **Strategy Pattern**: this pattern is used to encapsulate different behaviors of **crossover** into interchangeable strategies, allowing the algorithm to dynamically choose the appropriate strategy based on user input.
  - The **CrossoverStrategy** interface defines a common method **crossover** that is implemented by different strategies like **SinglePointCrossover**, **PMXCrossover**, and **OX1Crossover**.
  - Example usage: 
  ```java
         CrossoverType crossoverType = ...
         CrossoverStrategy crossoverStrategy = crossoverType.getStrategy();
         CrossoverResult crossoverResult = crossoverStrategy.crossover(offspring1, offspring2, random); 

- **Factory Type**: this pattern is employed to create instances of selection and mutation strategies based on input parameters. This abstracts the instantiation logic, making the code more flexible and easier to extend.
  - Selection Type:
   ```java
        SelectionType selectionType = ...
        Selection selection = SelectionFactory.createSelection(selectionType, distanceCalculator, random);
        Chromosome parent1 = selection.selectParent(population);
        Chromosome parent2 = selection.selectParent(population);
   ```
    - Mutation Type:
   ```java
        MutationType mutationType = ...
        Mutation mutation = MutationFactory.createMutation(mutationType, random);
        Chromosome mutatedOffSpring = mutation.mutate(offspring);
   ```


### 2. Analyzing Results

#### Description
- **Combinations**: Involves 36 different combinations.

#### Results Overview
- **Selected Problem**: TSP
- **Dataset**: bays29
- **Population Sizes**: 20 and 40
- **Crossover Rate**: 90%
- **Mutation Rate**: 1%
- **Stopping Condition**: 1000 iterations

#### Combinations of optimal solution 

Below is the list of combinations of crossover, selection, and mutation operators, along with their respective counts in achieving the minimum distance result. The combinations are sorted in descending order of counts:

| **Crossover Type** | **Selection Type** | **Mutation Type**   | **Count** |
|---------------------|--------------------|---------------------|-----------|
| OX1                | RANK              | INVERSION           | 25        |
| OX1                | TOURNAMENT        | INVERSION           | 18        |
| OX1                | RANK              | SWAP                | 14        |
| OX1                | RANK              | ADJACENT_SWAP       | 13        |
| OX1                | RANK              | INSERTION           | 9         |
| OX1                | TOURNAMENT        | INSERTION           | 2         |
| OX1                | TOURNAMENT        | ADJACENT_SWAP       | 1         |

### Summary Statistics of Metrics by Operator Type

Below are the performance metrics (mean, standard deviation, percentiles, and max) for different mutation, crossover, and selection types. These metrics provide insights into the algorithm's performance across various configurations.

---

#### **Mutation Types**
| **Mutation Type** | **Mean**    | **Std Dev** | **P25**      | **P50 (Median)** | **P75**      | **Max**      |
|--------------------|-------------|-------------|--------------|------------------|--------------|--------------|
| ADJACENT_SWAP      | 3370.949778 | 243.035787  | 3208.027778  | 3367.500000      | 3534.750000  | 4114.555556  |
| INSERTION          | 2927.602333 | 170.314320  | 2813.277778  | 2924.277778      | 3042.694444  | 3500.111111  |
| INVERSION          | 2819.195556 | 137.239120  | 2726.888889  | 2816.166667      | 2910.305556  | 3260.000000  |
| SWAP               | 3051.887778 | 188.798091  | 2923.250000  | 3050.944444      | 3180.111111  | 3691.111111  |

---

#### **Crossover Types**
| **Crossover Type** | **Mean**    | **Std Dev** | **P25**      | **P50 (Median)** | **P75**      | **Max**      |
|---------------------|-------------|-------------|--------------|------------------|--------------|--------------|
| OX1                | 2809.150583 | 120.950811  | 2727.354167  | 2802.250000      | 2885.750000  | 3256.166667  |
| PMX                | 3140.899750 | 201.092242  | 3007.375000  | 3142.500000      | 3278.708333  | 3770.750000  |
| SINGLE_POINT       | 3177.176250 | 232.497435  | 3018.854167  | 3174.416667      | 3336.437500  | 3897.416667  |

---

#### **Selection Types**
| **Selection Type** | **Mean**    | **Std Dev** | **P25**      | **P50 (Median)** | **P75**      | **Max**      |
|---------------------|-------------|-------------|--------------|------------------|--------------|--------------|
| RANK               | 2591.782000 | 164.594073  | 2473.854167  | 2582.750000      | 2700.937500  | 3191.083333  |
| ROULETTE           | 3857.153833 | 204.758515  | 3730.208333  | 3871.583333      | 4003.979167  | 4364.083333  |
| TOURNAMENT         | 2678.290750 | 185.187900  | 2549.520833  | 2664.833333      | 2795.979167  | 3369.166667  |

---

### Average results of each generation plots
The plots below depict the average results per generation for different configurations of crossover, mutation, and selection operators in solving the Traveling Salesman Problem (TSP). These results are computed as follows:
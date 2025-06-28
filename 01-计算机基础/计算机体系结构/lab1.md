# title: 
# Lab1: performance evaluation based on simulation
-----------
# Target

a) Setup a popular simulator Multi2sim to evaluate the CPU performance metrics;

b) Familiar with the simulation tool to verify the given or new hardware design;

c) Understand the basic knowledge of performance evaluation such as the metrics, benchmarks and evaluation methods.


# Process
## default
![Pasted image](<resources/Pasted image 20240330182508.png>)

> Explanation as follows:
>1 .**Command Line**: The user executed an example program of Multi2Sim, including the following parameters: 
>- `m2s`: The executable file name for Multi2Sim. 
>- `test-args`: Likely the name of the example program. 
>- `hi`: The first argument passed to the `test-args` program. 
>- `SMU`: The second argument passed to the `test-args` program.
>2 .**Program Output**: The program output includes warnings about the correct command line syntax, indicating that the command should be used as `m2s [options]`.
> - `Simulation alpha-numeric ID`: Each simulation has a unique alphanumeric ID, here it is `8FDyK`.
>3. **Simulation Statistics Summary**: Provides basic statistics of the simulation, including:
>    
> - `RealTime`: The actual time the simulator ran, shown here as 0.01 seconds.
> - `SiEnd`: The status at the end of the simulation, here it is `ContextsFinished`, meaning all contexts (possibly threads or processes) have completed.
> - `RealTime (x86)`: The actual time for the x86 architecture part of the simulation, also 0.01 seconds.
> - `Instructions`: The number of instructions simulated, here 94947.
> - `InstructionsPerSecond`: The number of instructions simulated per second, here 976218.


## fuctional 
![Pasted image](<resources/Pasted image 20240330182808.png>)
## detailed
![Pasted image](<resources/Pasted image 20240330182746.png>)

>  Explanation as follows:

1. **Real Time**: The actual time spent executing the simulation, here it is 1.90 seconds.
2. **SimTime**: The simulation time inside the simulator, here it is 454272.00 nanoseconds (ns).
3. **Frequency**: The CPU frequency being simulated, here it is 1000 Megahertz (MHz).
4. **Cycles**: The number of CPU cycles completed during the simulation, here 454273.
5. **Instructions**: The total number of instructions executed, here 200432.
6. **InstructionsPerSecond**: The number of instructions the simulator executes per second, here 105645.
7. **CPI (Cycles Per Instruction)**: The average number of cycles per instruction, which can be calculated by dividing the total number of cycles by the number of instructions, not directly shown here but can be calculated as 454273 / 200432 ≈ 2.27.
8. **Committed Instructions**: The actual number of instructions that were completed, here 94949.
9. **CommittedInstructionsPerCycle**: The number of instructions completed per cycle, here 0.209.
10. **CommittedMicroInstructionsPerCycle**: The number of micro-instructions completed per cycle, here 0.3487.
11. **BranchPredictionAccuracy**: The accuracy of branch prediction, here 86.5%.

## sort.c
![Pasted image](<resources/Pasted image 20240331115804.png>)
![Pasted image](<resources/Pasted image 20240331115813.png>)


# Difficulties Encountered in the Process and Solutions
* Teacher-provided environment
![Pasted image](<resources/Pasted image 20240328062020.png>)
* My environment + Teacher-provided multi2sim
> Found that the teacher-provided multi2sim is missing `std::`
![Pasted image](<resources/Pasted image 20240328062818.png>)
* Corrected after
![Pasted image](<resources/Pasted image 20240328062940.png>)
* Verification -> Searched for source code of open source projects
* ![Pasted image](<resources/Pasted image 20240328064252.png>)
- Solved the above problem, able to successfully use `make`, but still unable to use m2s, error: [X86] error: unsupported instruction (f3 0f 1e fb)
- Unable to solve the issue, in the end had to try using the environment provided by the teacher
- Suspecting that it is not possible to use on existing new versions of Linux kernel operating systems!![Pasted image](<resources/Pasted image 20240328063900.png>)

### Experiment 6
This experiment builds on the previous one and involves running and observing GPU simulations using OpenCL benchmarks.

#### Main Experiment Process

1. **Download and Unpack OpenCL Benchmarks**
   - Download the package from the Benchmarks section of the Multi2Sim website.
   - Unpack the benchmarks. Note that the folder name may have been modified in the repository.

2. **Enter the MatrixMultiplication Directory**
   - This directory contains a pre-compiled version of an OpenCL sample from AMD's Accelerated Parallel Processing (APP) software kit.

   **Files in the Directory:**
   - **MatrixMultiplication**: A statically linked x86 executable file embedding Multi2Sim's OpenCL runtime library.
   - **MatrixMultiplication_Kernels.bin**: A binary file with matrix multiplication OpenCL kernels compiled for the AMD Southern Islands architecture.

3. **Observe GPU Simulation Results**
   - Run the simulation and observe the results.

4. **Dump ISA for Kernel Functions**
   - Use the Southern Islands disassembler to dump the ISA (Instruction Set Architecture) for all kernel functions encoded in the benchmark.

![Pasted image](<resources/Pasted image 20240618234402.png>)
![Pasted image](<resources/Pasted image 20240618234408.png>)
![Pasted image](<resources/Pasted image 20240618234414.png>)
# Toy Virtual Machine (VM) - 2IMD20 Project @ TU Eindhoven

This repository contains my implementation of a virtual machine and bytecode interpreter for a dynamically-typed toy programming language. It was developed as part of the VM Course (2IMD20) at Eindhoven University of Technology (TU/e), focusing on language design, bytecode interpretation, and JIT optimization.

# Overview
The project builds on a provided front-end (lexer and parser) that produces an Abstract Syntax Tree (AST) for a toy dynamic language. My contribution focuses on the back-end: the bytecode compiler, interpreter, runtime system, and optimization mechanisms (ropes, shapes, inline caches etc).

# Core components:
Bytecode Compiler - traverses the AST and emits bytecode instructions.

Interpreter (ToyBciLoop) - executes bytecode using an explicit stack-based architecture.

Quickening / Inline Caches - optimizes property access dynamically at runtime.

Object Model & Shapes - efficient property storage and transitions for dynamic objects.

Constant Pool & Local Variables - used for fast instruction dispatch and symbol resolution.

Testing & Benchmarks - 50+ language-level unit tests and performance benchmarks.

The project is written in Java 21, built with Maven, and runs in a controlled environment (Docker or local JVM).

# Key Packages:

| Package                    | Description                                                             |
| -------------------------- | ------------------------------------------------------------------------|
| `nl.tue.vmcourse.toy.ast`  | AST node hierarchy for expressions, statements, and literals            |
| `nl.tue.vmcourse.toy.bci`  | Bytecode compiler and interpreter loop                                  |
| `nl.tue.vmcourse.toy.lang` | Object model (ToyObject, Shape transitions)                             |
| `nl.tue.vmcourse.toy.jit`  | Optional JIT compilation entry points &rarr; ***Not yet implemented!*** |
| `tests/`                   | Unit and integration tests covering language features                   |

# Language Features Implemented

| Feature                  | Description                                      |
| ------------------------ | ------------------------------------------------ |
| **Dynamic Typing**       | Variables can hold values of any type at runtime |
| **Functions & Closures** | First-class functions, lexical scope             |
| **Control Flow**         | If/else, while, break, return                    |
| **Objects & Prototypes** | Property access, add/remove fields at runtime    |
| **Arrays & Iteration**   | Array-like structures and native methods         |
| **Error Handling**       | Type errors, undefined references                |
| **Builtin Functions**    | `println`, `defineFunction`, etc.                |

Example snippet in the Toy language:

`function greet(name) { 
  println("Hello " + name + "!"); 
}`

`function main() {
  greet("world"); 
}`

# Running Tests

All provided tests can be executed using:

  `./test_all.sh ./vm-skeleton/sl`

Each test runs the interpreter on .sl scripts and compares output with the .output or .output.error files .

Expected output:

`[OK] Output for ./tests/Add matches expected output.
[OK] Output for ./tests/Arithmetic matches expected output.
...
========== ALL DONE ==========
  tests passed: 57
  tests failed: 0`

# Benchmarks

The repository also includes micro-benchmarks measuring:

  - Arithmetic operations

  - Recursive calls (Ackermann, Fibonacci)

  - Object access and mutation

  - Function dispatch performance

# What I Learned
This project deepened my understanding of:

  - Interpreter and compiler design

  - Bytecode generation and dispatch

  - Runtime optimization techniques (quickening, inline caches)

  - Memory representation of objects

  - Designing testable, modular VM architectures

It also helped strengthen my Java systems programming and debugging skills (especially stack management, frames, and runtime states).

Author
Alexandru-Iancu Vasu 
Master’s student in Computer Science @ TU Eindhoven 

📧 [alexandru.vasu@gmail.com]

🌐 [linkedin.com/in/alexandru-iancu-vasu-2472bb289]

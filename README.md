# Distributed Job Scheduler

A distributed job scheduling system implemented in Java, designed to manage and execute jobs across multiple worker nodes.

## Overview

This system simulates a distributed computing environment where a central scheduler distributes jobs to available worker nodes. It demonstrates core operating systems concepts including process scheduling, concurrency, synchronization, and resource management.

## Features

- Centralized job queue with priority-based scheduling
- Multiple worker nodes that process jobs concurrently
- Thread-safe job assignment and completion tracking
- Load balancing across available workers
- Fault-tolerant job reassignment on worker failure

## Architecture

- **Scheduler** – Manages the job queue and assigns jobs to available workers
- **Worker Nodes** – Independent threads that pick up and execute assigned jobs
- **Job** – Encapsulates a unit of work with an ID, priority, and execution logic
- **Main** – Entry point; initializes the scheduler and worker pool

## Getting Started

### Prerequisites

- Java 11 or higher

### Running

```bash
javac src/*.java -d out
java -cp out Main
```

## Concepts Demonstrated

- Multithreading and synchronization (`synchronized`, `wait/notify`)
- Producer-consumer pattern
- Priority queues
- Distributed task coordination

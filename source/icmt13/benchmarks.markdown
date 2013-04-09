---
layout: icmt13
title: "Nuthatch Performance Results (for ICMT13)"
date: 2013-04-04 14:34
comments: true
sharing: true
footer: true
asides: foo
---
*Results as of April 8, 2013*

# Setup

* Nuthatch/J, git version from 2013-04-08: [nuthatch](https://github.com/nuthatchery/nuthatch), [nuthatch-stratego](https://github.com/nuthatchery/nuthatch-stratego), [nuthatch-javafront](https://github.com/nuthatchery/nuthatch-javafront), [nuthatch-benchmark](https://github.com/nuthatchery/nuthatch-benchmark)

* Stratego interpreter from [Spoofax v1.1](http://strategoxt.org/Spoofax).
  Stratego code is executed using the *hybrid intepreter*, where the
  standard libraries (including traversals like ```topdown``` and
  ```bottomup```) have been compiled to Java, but the actual benchmark code
  is run in an intepreter

* Stratego-to-Java compiler from [Spoofax
  v1.1](http://strategoxt.org/Spoofax). The code is compiled to Java, and
  called from the benchmark runner.

* All the implementations (Nuthatch/J, Stratego, hand-written Java) use
  Stratego's Java term implementation.

* Java 7 / OpenJDK 7u15:
```
java version "1.7.0_15"
OpenJDK Runtime Environment (IcedTea7 2.3.7) (7u15-2.3.7-0ubuntu1~12.10.1)
OpenJDK 64-Bit Server VM (build 23.7-b01, mixed mode)
```

* Hardware: AMD FX-8350 Eight-Core Processor with 32GB RAM. All tests run
  on a single core on an otherwise idle system, with ```nice -10``` and
  pinned to a single core.

# Benchmarks

Our benchmarks are centered around simple traversal and rewriting. This
should be enough to give a rough idea of performance, but should not be
taken as a definitive claim that one tool is faster than another.

Also, the reader should note that not attempt has been made yet to optimize
Nuthatch/J, and that the Stratego results below are from a combination of
compiled and interpreted code; fully compiled code would likely run faster.

For the benchmarks that perform transformation or gather information, the
results have been checked against the other implementations (e.g., the
commute benchmark results in the same Stratego term for all three
implementations).

### Collect
Traverse the tree, collecting all strings in to a list.

* [Nuthatch
  implementation](https://github.com/nuthatchery/nuthatch-benchmark/blob/master/src/nuthatch/benchmark/nuthatch/Collect.java)
  (Basically, ```walk(down(match(string(), ... result.add(walker.getData()); ...)))```)

* Stratego implementation: ```collect-all(is-string, conc)```

### Commute
Traverse the tree, and flip the arguments on all two-argument method calls.

* [Nuthatch implementation](https://github.com/nuthatchery/nuthatch-benchmark/blob/master/src/nuthatch/benchmark/nuthatch/Commute.java)
* Stratego implementation: ```topdown(try(\Invoke(m,[x,y]) -> Invoke(m,[y,x])\))``` (or ```bottomup``` for the bottomup variant)
* [Hand-written Java implementation](https://github.com/nuthatchery/nuthatch-benchmark/blob/master/src/nuthatch/benchmark/java/JavaCommute.java)

### Bottomup Build

Do a bottomup traversal, replacing every node with the number 42.

* [Nuthatch implementation](https://github.com/nuthatchery/nuthatch-benchmark/blob/master/src/nuthatch/benchmark/nuthatch/BottomupBuild.java)

* Stratego implementation: ```bottomup(!42)```


### Identity Traversal

Traverse the entire tree, doing nothing.

* [Nuthatch implementation](https://github.com/nuthatchery/nuthatch-benchmark/blob/master/src/nuthatch/benchmark/nuthatch/Traverse.java)
* Stratego implementation: ```topdown(id)```, ```bottomup(id)``` ```downup(id)```
* [Hand-written Java implementation](https://github.com/nuthatchery/nuthatch-benchmark/blob/master/src/nuthatch/benchmark/java/JavaTraverse.java)


# Results
### Source File: [JavaPatterns.java](https://github.com/nuthatchery/nuthatch-javafront/blob/master/src/nuthatch/javafront/JavaPatterns.java) (from Nuthatch/J+JavaFront)

*Stratego/I* results are from the interpreter, *Stratego/J* results are from
compiled Java code.

```
Collect
-------
Collect              (Nuthatch):  14977ms, 5000 iterations,  2995µs per iteration
Collect            (Stratego/I):  25053ms, 5000 iterations,  5011µs per iteration
Collect            (Stratego/J):  21164ms, 5000 iterations,  4233µs per iteration

Commute
-------
Commute              (Nuthatch):  23147ms, 5000 iterations,  4629µs per iteration
Commute (Topdown)  (Stratego/I): 148870ms, 5000 iterations, 29774µs per iteration
Commute (Topdown)  (Stratego/J):   4430ms, 5000 iterations,   886µs per iteration
Commute                  (Java):   3999ms, 5000 iterations,   800µs per iteration

Bottomup Build 42
-----------------
BottomupBuild        (Nuthatch):  28250ms, 5000 iterations,  5650µs per iteration
BottomupBuild      (Stratego/I):  15814ms, 5000 iterations,  3163µs per iteration
BottomupBuild      (Stratego/J):   5877ms, 5000 iterations,  1175µs per iteration

Identity Traversals
-------------------
DefaultWalk          (Nuthatch):   7557ms, 5000 iterations,  1511µs per iteration
TopDown            (Stratego/I):   5086ms, 5000 iterations,  1017µs per iteration
BottomUp           (Stratego/I):   5626ms, 5000 iterations,  1125µs per iteration
DownUp             (Stratego/I):   8515ms, 5000 iterations,  1703µs per iteration
TopDown            (Stratego/J):   2597ms, 5000 iterations,   519µs per iteration
BottomUp           (Stratego/J):   2607ms, 5000 iterations,   521µs per iteration
DownUp             (Stratego/J):   2795ms, 5000 iterations,   559µs per iteration
Traverse                 (Java):   2398ms, 5000 iterations,   480µs per iteration
```

# Conclusions

0. Nuthatch/J's default walk seems comparable speed-wise to Stratego's
```downup``` traversal (not surprising, as they have similar
power). Stratego's one-way traversals (topdown and bottomup) are slightly
faster.

0. Rewriting is a lot slower in interpreted Stratego, but almost as fast as
hand-written code in compiled Stratego.

0. Cases where Nuthatch gather information into standard Java containers
(*Collect*) run faster than even compiled Stratego code.

0. Hand-written Java code seems to be 2–10 times faster than both interpred
Stratego and Nuthatch/J. The code is also a lot more verbose, so
hand-writing code is probably not feasible for larger transformations. But,
of course, hand-written Java transformations would likely not be based on
Stratego terms, as these are rather inconvenient to use from Java.

# Running the Benchmarks Yourself

The recipe below assumes you're using Eclipse.

0. Get the [Benchmark
Project](https://github.com/nuthatchery/nuthatch-benchmark) from GitHub.

0. Get [Nuthatch/J](https://github.com/nuthatchery/nuthatch),
[Nuthatch/J+Stratego](https://github.com/nuthatchery/nuthatch-stratego) and
[Nuthatch/J+JavaFront](https://github.com/nuthatchery/nuthatch-javafront)
from GitHub, or install them from the [Nuthatch Eclipse update
site](http://updates.nuthatchery.org/).

0. To run the Stratego code, either install [Spoofax](http://strategoxt.org/Spoofax)
in your Eclipse, or install the Stratego Interpreter feature from the
[Nuthatch Eclipse update site](http://updates.nuthatchery.org/).

0. Run ‘nuthatch.benchmark.BenchmarkRunner’, with the current directory set
to either ‘src’ or ‘bin’.

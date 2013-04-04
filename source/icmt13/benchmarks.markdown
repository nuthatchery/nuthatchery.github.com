---
layout: icmt13
title: "Nuthatch Performance Results (for ICMT13)"
date: 2013-04-04 14:34
comments: true
sharing: true
footer: true
asides: foo
---
*Results as of April 4, 2013*

# Setup

* Nuthatch/J, git version from 2013-04-04: [nuthatch](https://github.com/nuthatchery/nuthatch), [nuthatch-stratego](https://github.com/nuthatchery/nuthatch-stratego), [nuthatch-javafront](https://github.com/nuthatchery/nuthatch-javafront), [nuthatch-benchmark](https://github.com/nuthatchery/nuthatch-benchmark)

* Stratego from [Spoofax v1.1](http://strategoxt.org/Spoofax).  Stratego
  code is executed using the *hybrid intepreted*, where the standard
  libraries (including traversals like ```topdown``` and ```bottomup```)
  have been compiled to Java, but the actual benchmark code is run in an
  intepreter.

* All the implementations (Nuthatch/J, Stratego, hand-written Java) use
  Stratego's Java term implementation.

* Java 7 / OpenJDK 7u15:
```
java version "1.7.0_15"
OpenJDK Runtime Environment (IcedTea7 2.3.7) (7u15-2.3.7-0ubuntu1~12.10.1)
OpenJDK 64-Bit Server VM (build 23.7-b01, mixed mode)
```

* Hardware: AMD FX-8350 Eight-Core Processor with 32GB RAM. All tests run
  on a single core on an otherwise idle system.

# Benchmarks

Our benchmarks are centered around simple traversal and rewriting. This
should be enough to give a rough idea of performance, but should not be
taken as a definitive claim that one tool is faster than another.

Also, the reader should note that not attempt has been made yet to optimize
Nuthatch/J, and that the Stratego results below are from a combination of
compiled and interpreted code; fully compiled code would likely run faster.

For the benchmarks that perform transformation, the results have been
checked against the other implementations (e.g., the commute benchmark
results in the same Stratego term for all three implementations).

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

```
Commute
-------------------
Commute            (Nuthatch):  8004ms, 1000 iterations,  8004µs per iteration
Commute                (Java):   773ms, 1000 iterations,   773µs per iteration
Commute (Topdown)  (Stratego): 31578ms, 1000 iterations, 31578µs per iteration
Commute (Bottomup) (Stratego): 32837ms, 1000 iterations, 32837µs per iteration

Bottomup Build 42
-------------------
BottomupBuild      (Nuthatch):  6277ms, 1000 iterations,  6277µs per iteration
BottomupBuild      (Stratego):  3183ms, 1000 iterations,  3183µs per iteration

Identity Traversals
-------------------
DefaultWalk        (Nuthatch):  1845ms, 1000 iterations,  1845µs per iteration
Traverse               (Java):   422ms, 1000 iterations,   422µs per iteration
TopDown            (Stratego):  1050ms, 1000 iterations,  1050µs per iteration
BottomUp           (Stratego):  1271ms, 1000 iterations,  1271µs per iteration
DownUp             (Stratego):  1829ms, 1000 iterations,  1829µs per iteration
```

# Conclusions

0. Nuthatch/J's default walk seems comparable speed-wise to Stratego's
```downup``` traversal (not surprising, as they have similar
power). Stratego's one-way traversals (topdown and bottomup) are somewhat
faster.

0. Rewriting is a lot slower in Stratego; this would likely be different
for compiled Stratego code.

0. Hand-written Java code seems to be 2–10 times faster than both Stratego
and Nuthatch/J. The code is also a lot more verbose, so hand-writing code
is probably not feasible for larger transformations. But, of course,
hand-written Java transformations would likely not be based on Stratego
terms, as these are rather inconvenient to use from Java.
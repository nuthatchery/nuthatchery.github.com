---
layout: page
title: "Nuthatch Performance Results (for ICMT13)"
date: 2013-04-04 14:34
comments: true
sharing: true
footer: true
---
*Results as of April 4, 2013*

# Setup

* Nuthatch/J, git version from 2013-04-04: [nuthatch](https://github.com/nuthatchery/nuthatch), [nuthatch-stratego](https://github.com/nuthatchery/nuthatch-stratego), [nuthatch-javafront](https://github.com/nuthatchery/nuthatch-javafront), [nuthatch-benchmark](https://github.com/nuthatchery/nuthatch-benchmark)

* Stratego from [Spoofax v1.1](http://strategoxt.org/Spoofax)

* Java 7 / OpenJDK 7u15:
```
java version "1.7.0_15"
OpenJDK Runtime Environment (IcedTea7 2.3.7) (7u15-2.3.7-0ubuntu1~12.10.1)
OpenJDK 64-Bit Server VM (build 23.7-b01, mixed mode)
```

* Hardware: AMD FX-8350 Eight-Core Processor with 32GB RAM. All tests run on a single core on an otherwise idle system.

# Benchmarks
### Commute
### Bottomup Build
### Identity Traversal


# Results
### Source File: [JavaPatterns.java]( https://github.com/nuthatchery/nuthatch-javafront/blob/master/src/nuthatch/javafront/JavaPatterns.java) (from Nuthatch/J+JavaFront)

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

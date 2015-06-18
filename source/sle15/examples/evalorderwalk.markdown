---
layout: scam14
title: "Example: EvalOrderWalk"
date: 2015-06-16 18:38
comments: true
sharing: true
footer: true
---
This example shows how a custom walk can be created that will visit tree nodes in evaluation order and apply a ```userAction```.

We rely on the default walk to bring us around the tree, with small detours as necessary. The overall evaluation order is bottom-up, so by default, we want to apply the ```userAction``` when going up (```af.up(userAction)```). We then provide special cases for when the walk should deviate from the default.
  
The full code of the example walk and example language library is at [GitHub](https://github.com/nuthatchery/nuthatch/tree/master/src/nuthatch/examples)).

<!-- more -->

{% include_code A walk that traverses a tree in evaluation order. sle15/examples/EvalOrderWalk.java %}


Sample output:
```
Declare(Var("i"), Int(10), While(Var("i"), Assign(Var("i"), Add(Var("i"), Int(-1))))):
    0: Int(10)
    1: Declare(Var("i"), Int(10), While(Var("i"), Assign(Var("i"), Add(Var("i"), Int(-1)))))
    2: Var("i")
    3: While(Int(10), Assign(Var("i"), Add(Var("i"), Int(-1))))
    4: Var("i")
    5: Int(-1)
    6: Add(Int(10), Int(-1))
    7: Assign(Var("i"), Add(Int(10), Int(-1)))
    8: While(Int(10), Assign(Var("i"), Add(Int(10), Int(-1))))
    9: Declare(Var("i"), Int(10), While(Int(10), Assign(Var("i"), Add(Int(10), Int(-1)))))
 => While(Int(10), Assign(Var("i"), Add(Int(10), Int(-1))))
```

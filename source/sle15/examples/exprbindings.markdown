---
layout: scam14
title: "Example: ExprBindings"
date: 2015-06-14 18:38
comments: true
sharing: true
footer: true
---
This example shows Nuthatch/J applied to a small expression language with variables and
let-expressions, and uses ancestor matching to find variable bindings.

Four different walks are included as examples:

1. The first is a naive approach to matching a variable with its binding; it simply matches
   a variable, then tries to match a ```Let``` ancestor with the same variable name. This leads to
   confusion because the variable to be bound in the ```Let``` (the first child) will also be matched,
   and considered bound to itself.
2. The problem of the ```Let```-variable being matched is fixed in the second ‘lessNaive’ example,
   which adds an action to the walk that redirects it past the first child of a ```Let```.
3. The less naive example has another problem; occurences of the variable within the 
   second child of a ```Let``` will be interpreted as being bound by the current ```Let```. This is wrong
   (except in the case of recursive let constructs – we’ll assume that is not the case here).
   We can fix this using a trick of the tree walking model: to reach the ancestor, one has to
   go up the tree. When we reach the ```Let```, the tree cursor still knows which branch it came in on.
   As the scope of a ```Let``` should only include its third child, we can restrict the match to
   cases where the ```Let``` was found via its third child (```from(3)```).
4. Finally, the last example combines the previous one with a little bit of extra code to
   inline the variables rather than print them, and remove the ```Let```s after the inlining is done.

The full code of the example and expression language library is at [GitHub](https://github.com/nuthatchery/nuthatch/tree/master/src/nuthatch/examples)).

<!-- more -->

{% include_code Display the bindings of variables using ancestor matching. sle15/examples/ExprBindings.java %}

---
layout: post
title: "Example: ToString"
comments: true
categories: example
author: Anya Helene Bagge
---


This simple example shows how to a walk a tree and convert it to a term
representation (such as `+(5, *(+(7, 3), 4))`, for example).

<!-- more -->

<img class="basic-alignment right" src="/images/tree-walking-150x.png" width="150" height="210" />

In the example, we carefully observe the position along the walk to
correctly parenthesize and place commas where appropriate. For a leaf, we
use its data value. If we enter a node from the parent, we use its
constructor name and add open parenthesis. Whenever we come up from a
child, we add a comma, except when we come up from the last child, where we
add the closing parenthesis.

The walk proceeds from node to node according to the <em>default walk</em>,
shown to the right.

The `SimpleWalker` carries with it a string `S` as state, which we may
append to (`appendToS()`), observe (`getS()`) and set (`setS()`).

{% include_code Convert a tree to its term representation examples/ToString.java %}

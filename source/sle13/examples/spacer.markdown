---
layout: sle13
title: "Example: Inserting Horizontal Space"
date: 2013-04-04 18:38
comments: true
sharing: true
footer: true
---

 * *Goal*: insert/remove horizontal space according to rules

 * *Basic idea*: look at previous token and next token, decide whether to
    insert space in front of next token.

   * All existing spaces are removed, except those at the beginning of a
     line (*after(START)*, *after(NL)*).

   * A list of rules deal with the particulars, for example:

      * We want space around binary operators, no space after a prefix
        operator and no space before a postfix operator.

      * No spaces around parentheses, braces and brackets.

      * No space before comma, always after.

      * etc.

   * By default, we always insert a space. The particular rules override
     this by specifying a ‘nop’ action instead.

   * Priority levels are used to make early rules take precedence over
     later rules. Also, rules for more specific token categories take
     precedence over generic categories.

<!-- more -->

{% include_code Rule-Based Token Processor to Normalise Horizontal Spaces sle13/examples/SensibleSpacing.java %}

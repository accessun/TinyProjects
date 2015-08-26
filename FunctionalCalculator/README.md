# FunctionalCalculator
This is a simple Java program used to calculate functional style math expression. The functional style math expression is usually seemingly esoteric compared to math expression of common form.

For example, simply adding two numbers, say, 1 and 2. In common form, one simply writes down:

```java
1 + 2
```

However, in functional style, operator comes before operands:

```scheme
(+ 1 2)
```

Expressions are enclosed within a pair of parentheses which defines the block of
expression. The first part is the operator or function name. In this case, the addition
operator can also be viewed as a function that performs addition operation on the
arguments passed to it. The name of this function is just a "+" symbol. Closely following
the operator is a list a arguments separated by spaces. Furthermore, the expression can be
nested by replacing argument(s) with other block(s) of expression. For example, 

```scheme
(+ (* 1 2) (- 4 3))
```

The above expression first mutiplies 1 by 2 and subtract 3 from 4, and then add the two results together to get the final result, which is 3.

The project in this directory implements a program that calculates the result of a functional style math expression using two stacks. `FunctionalCalculator.java` is the source code of the program. Note that, for the sake of simplicity, the legal expression required by the program is a little different from the above one. It is required that every operator, operand, and parenthesis be separated by a space. `ValidateExpression.java` is used to check whether an expression meets this requirement. The following is the legal form of the above expression for this program.

```scheme
( + ( * 1 2 ) ( - 4 3 ) )
```


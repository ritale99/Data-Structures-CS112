# Data-Structures-CS112
Data Structures Projects

BigInteger: 
  Background
Integer.MAX_VALUE is the maximum value of a Java int: 2147483647. If you want to work with even bigger
integers, you have the option of using the type long, which has the maximum value of Long.MAX_VALUE =
9223372036854775807.
But what if this is not enough? What if you are working on something like an astronomy application, and need to
keep track of things such as number of stars in the universe? This is of the order of 1023, larger than the maximum
long value. For situations like this, you need to be able to work with an integer type that can hold arbitrarily large
or small positive and negative values, with any number of digits. There is no built-in type in the language for this,
so you need to craft your own. In this assignment, you will do exactly this, by implementing a class called
BigInteger, with a representative small set of operations.

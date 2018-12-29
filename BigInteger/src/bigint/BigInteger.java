package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		
		integer = integer.trim();
		
		BigInteger parsedInt = new BigInteger();
		if(integer.charAt(0)=='-') {
			parsedInt.negative=true;
			integer = integer.substring(1);
			}
		
		else if(integer.charAt(0)=='+' ) {
			parsedInt.negative=false;
			integer = integer.substring(1);
			}
		
		for(int i =0; i<integer.length(); i++) {
		
			if(!Character.isDigit(integer.charAt(i))) {
				
				
					throw new IllegalArgumentException();
				
			}
		
			}
		
		while(integer.length()>=1 && integer.charAt(0)=='0') {
			integer = integer.substring(1);
		}
			
		for (int x=0; x<integer.length(); x++) {
			
			int current = integer.charAt(x)-'0';
			
			parsedInt.front= new DigitNode(current,parsedInt.front); 
			
		}
		int lengthList= lengthList(parsedInt);
		parsedInt.numDigits=lengthList;
		//System.out.println(parsedInt.numDigits);
		return parsedInt; 
		
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		
		BigInteger sum = new BigInteger();
		int sumDigit;
		
		//for cases in which either both are positive or both are negative
		if((first.negative==true && second.negative==true) || 
				(first.negative==false && second.negative==false)) {
			
			if (first.negative==true) {sum.negative=true;}
			else if (first.negative ==true) {sum.negative=false;}	
			
			int carry = 0;
				
			DigitNode firstD = first.front;
			DigitNode secondD = second.front;
		
			
			while(firstD!=null || secondD!=null) {
				
				 sumDigit = carry + (firstD!=null ? firstD.digit: 0)
						 +(secondD!=null ? secondD.digit:0);
				  
				 carry = (sumDigit >= 10) ? 1 : 0;
				 sumDigit = sumDigit % 10;
				 
				 sum.front = new DigitNode(sumDigit, sum.front);
				 
				 if (firstD!=null) {firstD = firstD.next;}
				 if (secondD!=null) {secondD = secondD.next;}
				 
				
				 }
			 if(carry>0) {
				 sum.front= new DigitNode(carry, sum.front);
				 
				 }
		
			}
		
		//for cases where one is negative and one is positive
		if((first.negative==true && second.negative==false)||
				(first.negative==false && second.negative==true)) {
			
			//one is negative one is positive but the digits are the same: you return 0
			boolean isEqual = equalLists(first, second);
			if(isEqual) {
				sum.front= new DigitNode(0,sum.front);
				sum.negative=false;
			}
			
			boolean firstListGreater = isFirstListGreater(first,second);
			
		//	System.out.println(firstListGreater);
			
			if(firstListGreater && first.negative ) {sum.negative=true;}
			if(firstListGreater && !first.negative) {sum.negative=false;}
			
			if(!firstListGreater && second.negative) {sum.negative=true;}
			if(!firstListGreater && !second.negative) {sum.negative=false;}
			
			if(firstListGreater) {
			
			DigitNode firstF = first.front;
			DigitNode secondF = second.front;
			
			//either one is not null
			while(firstF!=null || secondF!=null) {
				
				
				
				//putting a zero in the location of the null space
				 if (secondF==null) {
					secondF = new DigitNode (0, null);
				} 
				
				
				 if(firstF.digit >= secondF.digit) {
					sumDigit = (firstF.digit) -
							(secondF!=null ? secondF.digit: 0);
							sum.front = new DigitNode (sumDigit, sum.front);
				}
				else if (firstF.digit<secondF.digit && firstF.next!=null) {
					firstF.digit = firstF.digit + 10;
					firstF.next.digit = firstF.next.digit-1;
					sumDigit = (firstF.digit) -
							(secondF!=null ? secondF.digit: 0);
							sum.front = new DigitNode (sumDigit, sum.front);
				} 
				
				if (firstF!=null) {
				firstF = firstF.next;
				}
				
				if (secondF!=null) {
				secondF = secondF.next;
				}
					}
			}
			
			
			else if (!firstListGreater) {
				
				DigitNode firstF = first.front;
				DigitNode secondF = second.front;
				
				//either one is not null
				while(firstF!=null || secondF!=null) {
					
					
					
					
					//putting a zero in the location of the null space
					 if (firstF==null) {
						firstF = new DigitNode (0, null);
					} 
					
					
					 if(secondF.digit >= firstF.digit) {
						sumDigit = (secondF.digit) -
								(firstF!=null ? firstF.digit: 0);
								sum.front = new DigitNode (sumDigit, sum.front);
					}
					else if (secondF.digit<firstF.digit && secondF.next!=null) {
						secondF.digit = secondF.digit + 10;
						secondF.next.digit = secondF.next.digit-1;
						sumDigit = (secondF.digit) -
								(firstF!=null ? firstF.digit: 0);
								sum.front = new DigitNode (sumDigit, sum.front);
					} 
					
					if (firstF!=null) {
					firstF = firstF.next;}
					if (secondF!=null) {
					secondF = secondF.next; }
				}
				
			}
		int difference = differenceLists(first,second);
			if (difference == 0) {
				DigitNode prev = null; 
		        DigitNode current = sum.front; 
		        DigitNode next = null; 
		        while (current != null) { 
		            next = current.next; 
		            current.next = prev; 
		            prev = current; 
		            current = next;} 
		            sum.front=prev;
			}
			
		
		}
		
		//reverse the List
		DigitNode prev = null; 
        DigitNode current = sum.front; 
        DigitNode next = null; 
        while (current != null) { 
            next = current.next; 
            current.next = prev; 
            prev = current; 
            current = next;} 
            sum.front=prev;
            
            
            
            int length = lengthList(sum);
            if(length > 1) {
            	removeTrailingZeros(sum);
            }
            sum.numDigits = lengthList(sum);
          //  System.out.println(sum.numDigits);
           return sum; 
}
	 private static int lengthList(BigInteger l) {
		int length = 0;
		DigitNode front = l.front;
		while(front!=null) {
			length++;
			front = front.next;
		}
	
		return length;
	} 
private static BigInteger addMult(BigInteger first, BigInteger second) {
	BigInteger sum = new BigInteger();
	int sumDigit;
	
	//for cases in which either both are positive or both are negative
	if((first.negative==true && second.negative==true) || 
			(first.negative==false && second.negative==false)) {
		
		if (first.negative==true) {sum.negative=true;}
		else if (first.negative ==true) {sum.negative=false;}	
		
		int carry = 0;
			
		DigitNode firstD = first.front;
		DigitNode secondD = second.front;
	
		
		while(firstD!=null || secondD!=null) {
			
			 sumDigit = carry + (firstD!=null ? firstD.digit: 0)
					 +(secondD!=null ? secondD.digit:0);
			  
			 carry = (sumDigit >= 10) ? 1 : 0;
			 sumDigit = sumDigit % 10;
			 
			 sum.front = new DigitNode(sumDigit, sum.front);
			 
			 if (firstD!=null) {firstD = firstD.next;}
			 if (secondD!=null) {secondD = secondD.next;}
			 
			
			 }
		 if(carry>0) {
			 sum.front= new DigitNode(carry, sum.front);
			 
			 }
	
		}
	
	//for cases where one is negative and one is positive
	if((first.negative==true && second.negative==false)||
			(first.negative==false && second.negative==true)) {
		
		//one is negative one is positive but the digits are the same: you return 0
		boolean isEqual = equalLists(first, second);
		if(isEqual) {
			sum.front= new DigitNode(0,sum.front);
			sum.negative=false;
		}
		
		boolean firstListGreater = isFirstListGreater(first,second);
		
	//	System.out.println(firstListGreater);
		
		if(firstListGreater && first.negative ) {sum.negative=true;}
		if(firstListGreater && !first.negative) {sum.negative=false;}
		
		if(!firstListGreater && second.negative) {sum.negative=true;}
		if(!firstListGreater && !second.negative) {sum.negative=false;}
		
		if(firstListGreater) {
		
		DigitNode firstF = first.front;
		DigitNode secondF = second.front;
		
		//either one is not null
		while(firstF!=null || secondF!=null) {
			
			
			
			//putting a zero in the location of the null space
			 if (secondF==null) {
				secondF = new DigitNode (0, null);
			} 
			
			
			 if(firstF.digit >= secondF.digit) {
				sumDigit = (firstF.digit) -
						(secondF!=null ? secondF.digit: 0);
						sum.front = new DigitNode (sumDigit, sum.front);
			}
			else if (firstF.digit<secondF.digit && firstF.next!=null) {
				firstF.digit = firstF.digit + 10;
				firstF.next.digit = firstF.next.digit-1;
				sumDigit = (firstF.digit) -
						(secondF!=null ? secondF.digit: 0);
						sum.front = new DigitNode (sumDigit, sum.front);
			} 
			
			if (firstF!=null) {
			firstF = firstF.next;
			}
			
			if (secondF!=null) {
			secondF = secondF.next;
			}
				}
		}
		
		
		else if (!firstListGreater) {
			
			DigitNode firstF = first.front;
			DigitNode secondF = second.front;
			
			//either one is not null
			while(firstF!=null || secondF!=null) {
				
				
				
				
				//putting a zero in the location of the null space
				 if (firstF==null) {
					firstF = new DigitNode (0, null);
				} 
				
				
				 if(secondF.digit >= firstF.digit) {
					sumDigit = (secondF.digit) -
							(firstF!=null ? firstF.digit: 0);
							sum.front = new DigitNode (sumDigit, sum.front);
				}
				else if (secondF.digit<firstF.digit && secondF.next!=null) {
					secondF.digit = secondF.digit + 10;
					secondF.next.digit = secondF.next.digit-1;
					sumDigit = (secondF.digit) -
							(firstF!=null ? firstF.digit: 0);
							sum.front = new DigitNode (sumDigit, sum.front);
				} 
				
				if (firstF!=null) {
				firstF = firstF.next;}
				if (secondF!=null) {
				secondF = secondF.next; }
			}
			
		}
	int difference = differenceLists(first,second);
		if (difference == 0) {
			DigitNode prev = null; 
	        DigitNode current = sum.front; 
	        DigitNode next = null; 
	        while (current != null) { 
	            next = current.next; 
	            current.next = prev; 
	            prev = current; 
	            current = next;} 
	            sum.front=prev;
		}
		
	
	}
	
	//reverse the List
	DigitNode prev = null; 
    DigitNode current = sum.front; 
    DigitNode next = null; 
    while (current != null) { 
        next = current.next; 
        current.next = prev; 
        prev = current; 
        current = next;} 
        sum.front=prev;
        
        
        
       
       return sum; 
}

	 private static void removeTrailingZeros(BigInteger Remove) {
		 //reverse first
		 DigitNode prev = null; 
	        DigitNode current = Remove.front; 
	        DigitNode next = null; 
	        while (current != null) { 
	            next = current.next; 
	            current.next = prev; 
	            prev = current; 
	            current = next;} 
	            Remove.front=prev;
	            
	            DigitNode temp = Remove.front;
	            
	            while(temp.digit==0 && temp!=null) {
	            	Remove.front=temp.next;
	            temp = temp.next;	
	            }
	            
	            
	           
	            
	            //reverse back
	            DigitNode prev1 = null; 
	            DigitNode current2 = Remove.front; 
	            DigitNode next1 = null; 
	            while (current2 != null) { 
	                next1 = current2.next; 
	                current2.next = prev1; 
	                prev1 = current2; 
	                current2 = next1;} 
	                Remove.front=prev1;
		 
	 }
	
	
	private static int differenceLists(BigInteger first, BigInteger second) {
		int lOne=0;
		int lTwo=0;
		int diff;
		
		DigitNode frontOne = first.front;
		DigitNode frontTwo = second.front;
		
		while(frontOne!=null) {
			lOne++;
			frontOne=frontOne.next;
		}
		while(frontTwo!=null) {
			lTwo++;
			frontTwo = frontTwo.next;
		}
		diff = lOne-lTwo;
		return diff;
	
	}
	private static boolean equalLists(BigInteger first, BigInteger second) {
		boolean isEqualNegs= false;
		DigitNode firstIt = first.front;
		DigitNode secondIt = second.front;
		
		while(firstIt!= null) {
			
			if(secondIt==null || secondIt.digit!=firstIt.digit) {
					isEqualNegs = false;
					break;
			}
			else if(secondIt.digit==firstIt.digit) {
				isEqualNegs = true;
			}
			
			firstIt=firstIt.next;
			secondIt=secondIt.next;
		}
		return isEqualNegs;
	}
	private static boolean isFirstListGreater(BigInteger first, BigInteger second) {
		boolean isFirstGreaterNegs = false;
		
		int difference = differenceLists(first, second);
		if (difference>0) {return true;}
		if (difference<0) {return false;}
		BigInteger firstR = first;
		BigInteger secondR = second;
		
		//reverse both lists
		DigitNode prev1 = null; 
        DigitNode current1 = firstR.front; 
        DigitNode next1 = null; 
        while (current1 != null) { 
            next1 = current1.next; 
            current1.next = prev1; 
            prev1 = current1; 
            current1 = next1;} 
            firstR.front=prev1;
            
            DigitNode prev2 = null; 
            DigitNode current2 = secondR.front; 
            DigitNode next2 = null; 
            while (current2 != null) { 
                next2 = current2.next; 
                current2.next = prev2; 
                prev2 = current2; 
                current2 = next2;} 
                secondR.front=prev2;
         
         DigitNode frontFirst = firstR.front;
         DigitNode frontSecond = secondR.front;
       
         while(frontFirst!=null) {
        	 if(frontSecond.digit<frontFirst.digit) {isFirstGreaterNegs=true;
        	 	break;}
        	 if(frontSecond.digit>frontFirst.digit) {isFirstGreaterNegs = false;
        	 	break;} 
        	 
        	 
        	 frontFirst = frontFirst.next;
        	 frontSecond = frontSecond.next;
         }
       
		return isFirstGreaterNegs;
	}
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		
		BigInteger product= new BigInteger(); 
		int lengthFirst = lengthList(first);
		int lengthSecond = lengthList(second);
		
		
		
		//these next three if statements take care of the sign of the product
		if (first.negative==true && second.negative==true) {
			product.negative=false;
		}
		
		if(first.negative==false && second.negative ==false) {
			product.negative=false;
		}
		
		if((first.negative==true && second.negative==false) || (first.negative==false && second.negative==true)) {
			product.negative=true;
		}
		//if either digit is equal to 1 or zero
			
		if(lengthFirst==1) {
					if (first.front.digit==0){
						product.front = new DigitNode(0,product.front);
						product.negative=false;
						return product;
					}
					if(first.front.digit==1) {
						second.negative=product.negative;
						int lengthSecondTwo = lengthList(second);
						second.numDigits = lengthSecondTwo;
						return second;
					}
				}
		if(lengthSecond==1) {
			if (second.front.digit==0){
				product.front = new DigitNode(0,product.front);
				product.negative=false;
				return product;
			}
			if(second.front.digit==1) {
				first.negative=product.negative;
				int lengthFirstOne = lengthList(first);
				first.numDigits = lengthFirstOne;
				return first;
			}
		}
		
		
		int loc = 0;
        DigitNode frontOne = first.front;
        DigitNode frontTwo = second.front;
        int carry = 0;
        //loop for the first bigInteger
        while (frontOne != null) {
            //digits of the interior of the multiplication
        	BigInteger interior = new BigInteger();

            //loop for second BigInteger
            while (frontTwo != null) {
            
            	//the multiplication of the digits
                int res = frontOne.digit*frontTwo.digit+carry;
               
                //when the result of the multiplication is over two digits
                if (res >= 10) {
                    carry = res/10;
                    res = res%10;} else {carry = 0;}

              //if the list is empty
                if (interior.numDigits == 0) {
                    //add onto front
                    interior.front = new DigitNode(res, interior.front);
                } else {
                  //if the list is not empty, add onto the end of the list rather than just onto the front
                    DigitNode ptr = interior.front;
                  //loop to reach end
                    while (ptr.next != null) {
                        ptr = ptr.next;
                    }
                    //the next node is set to the digit
                    ptr.next = new DigitNode(res, null);
                   
                } 
                //increase number of digits
                interior.numDigits++;

               
                
              //advance the second bigInt  
                frontTwo = frontTwo.next; 
            }


        //counter to count how many zeros to add to have as a placeholder
            int countLoc = loc;
         //loop for adding the zeros
            while (countLoc > 0) {
                interior.front = new DigitNode(0, interior.front);
                interior.numDigits++;
                countLoc--;
            }

         // if there is still carry leftOver
            if (carry >= 1) {
              //go to the end of the list
                DigitNode curr = interior.front;
                while (curr.next != null) {
                    curr = curr.next;
                }
                //insert the carry digit to the end 
                curr.next = new DigitNode(carry, null);
                interior.numDigits++;
      
            }
            

          //add the previous interior storage to the total 
            product = addMult(product,interior);

          //iterate the front and set back the second List
          //carry is reset 
          //location is increased
            loc++;
            carry = 0;
            frontOne = frontOne.next;
            frontTwo = second.front;
        } 
        
		
        if ((first.negative == true && second.negative == true) || (first.negative == false && second.negative == false)) {
            product.negative = false;}
        else {product.negative = true;}
		 
       // System.out.println(product.numDigits);
		return product; 
		
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}

import java.util.*;

public class Driver {

	static functLib define = new functLib();
	private static int[]testCases = {

			// ENTER HEX HERE		
			0x022DA822,	//1
			0x8EF30018,	//2
			0x12A70004,	//3
			0x02689820,	//4
			0xAD930018,	//5
			0x02697824,	//6
			0xAD8FFFF4,	//7
			0x018C6020,	//8
			0x02A4A825,	//9
			0x158FFFF6,	//10
			0x8E59FFF0, // 11
			// TEST CHANGE
	};

	public static void main(String[] args) {

		// Begin loop through test cases
		for (int l = 0; l< testCases.length; l++){
			int o = testCases[l];				
			runTest(o); // Begin algorithm 
		}
	}

	private static void runTest(int hex){ // Determine format and send
		String temp = Integer.toBinaryString(hex);

		// Make sure binary string is 32 bits, add zeros if needed
		for (int i = temp.length(); i != 32; i++){
			temp = "0" + temp;
		} 
		// SEND to R-Type if beginning with 000000
		if (temp.substring(0,6).contains("000000")){
			rType(temp);
		// SEND to I-Type else
		}
		else {
			iType(temp);	
		}
	}

	private static void rType(String binary){
		int op = Integer.parseInt(binary.substring(0,6));

		String rs = binary.substring(6,11);
		int rsout = Integer.parseInt(rs,2);

		String rt = (binary.substring(11,16));
		int rtout = Integer.parseInt(rt,2);

		String rd = (binary.substring(16,21));
		int rdout = Integer.parseInt(rd,2);

		String funct = binary.substring(26,32);
		int functOut = Integer.parseInt(funct, 2);

		//Run defining methods with collected info
		define.RunRTypeOP(op, rsout, rtout, rdout, functOut);

	}

	private static void iType(String binary){
		String rs,rt;
		String offSetasString = null;
		
		String op = binary.substring(0,6);
		int opout = Integer.parseInt(op,2);

		rs = binary.substring(6,11);
		int rsout = Integer.parseInt(rs,2);

		rt = (binary.substring(11,16));
		int rtout = Integer.parseInt(rt,2);

		//Get offset address
		offSetasString = binary.substring(16);
		short offSet = (short) Long.parseLong(offSetasString,2);
		
	
		//Run defining methods with collected info
		define.RunITypeFunct(opout, rsout, rtout, offSet);

	}

}
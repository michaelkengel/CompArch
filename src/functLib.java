
public class functLib {

	private static int currentAddress = 0x7a060;
	private int opa,rsa,rta,rda,functa;
	short offSeta;

	public void RunRTypeOP(int op,int rs, int rt, int rd, int funct){
		//Store values for ease
		opa=op;
		rsa=rs;
		rta=rt;
		rda=rd;
		functa=funct;

		//Run library Function
		ROPcode(funct);
		currentAddress +=4; // Increment address by 4
	}

	public void RunITypeFunct(int op, int rs, int rt, short offSet){
		opa=op;
		rsa=rs;
		rta=rt;
		String inToOff = "0x"+offSet;
		offSeta=offSet;
		IOPcode();
		currentAddress +=4; 

	}

	private void ROPcode(int funct){
		String out=null;
		switch(funct){
		case(32):
			//add	add $d,$s,$t
			out = "add $"+rda+",$"+rsa+",$"+rta;
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + out);
		break;

		case(36):
			//and	and $d,$s,$t	
			out = "and $"+rda+",$"+rsa+",$"+rta;
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + out);
		break;
		case(37):
			//or    or $d,$s,$t	
			out = "or $"+rda+",$"+rsa+",$"+rta;
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + out);
		break;

		case(34):
			//sub	sub $d,$s,$t	
			out = "sub $"+rda+",$"+rsa+",$"+rta;
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + out);
		break;

		default:
			System.out.println("Invalid operation");
		}

	}

	private void IOPcode(){
		String outX=null;
		int refAddress;

		switch(opa){
		case(35): // 8E or 23 (bitwise is 100011)
			//lw	 lw $t,C($s)
			outX = "lw $"+rta+","+offSeta+"($"+rsa+")";
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + outX);
		break;

		case(43): // 2B or 43 (101011 with leading two bytes being 00)
			//sw	sw $t,C($s)	
			outX = "sw $"+rta+","+offSeta+"($"+rsa+")";
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + outX);
		break;

		case(4): // or 000100 
			//beq	beq $s,$t,C
		outX = "beq $"+rsa+",$"+rta+", address";
		offSeta = (short) (offSeta << 2);
		// Shifting to the left by 2	
		refAddress = currentAddress+4+(offSeta); // add two for PC then do offset


		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + outX + " 0x" + Integer.toHexString(refAddress));
		break;

		case(5):
			//bne	bne $s,$t,
		outX = "bne $"+rsa+",$"+rta+", address";
		offSeta = (short) (offSeta << 2); // Shifting to the left by 2
		refAddress = currentAddress+4+(offSeta); // add two for PC then do offset
		System.out.println("0x"+Integer.toHexString(currentAddress) + " " + outX+ " 0x" + Integer.toHexString(refAddress));
		break;

		default:
			System.out.println("Invalid operation");
		}

	}

}

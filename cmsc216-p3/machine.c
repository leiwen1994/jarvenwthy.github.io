#include <stdio.h>
#include "machine.h"
static int print_opcode (unsigned int op);

int assemble(unsigned short opcode, unsigned short reg1, unsigned short reg2,
             unsigned short reg3, unsigned short extension,
             unsigned int addr_constant, Machine_word *const word) {
	/*covert to unsigned int*/
	unsigned int optemp = (unsigned int) opcode;
	unsigned int reg1temp = (unsigned int) reg1;
	unsigned int reg2temp = (unsigned int) reg2;
	unsigned int reg3temp = (unsigned int) reg3;
	unsigned int exttemp = (unsigned int) extension;
	
	/*return if null pointer*/			 
	if (word == NULL)
		return 0;
	
	*word = 0; /*reset word*/
	
	/*bitwise operation for each component*/
	optemp <<= 27;
	reg1temp <<= 23;
	reg2temp <<= 19;
	reg3temp <<= 15;
	exttemp <<= 12;
		
	*word = optemp + reg1temp + reg2temp + reg3temp + exttemp + addr_constant;
	return 1;
}
			 
int print_instruction(Machine_word instruction) {
	unsigned int op = instruction;
	unsigned int r1 = instruction;
	unsigned int r2 = instruction;
	unsigned int r3 = instruction;
	unsigned int ext = instruction;
	unsigned int addr = instruction;
	unsigned int im = instruction;
	
	/*check for validity*/
	if (!valid_instruction(instruction))
		return 0;
	
	/*extract each component*/
	op >>= 27;
	r1 = (r1 >> 23) & 15;
	r2 = (r2 >> 19) & 15;
	r3 = (r3 >> 15) & 15;
	ext = (ext >> 12) & 7;
	addr = addr & 4095;
	im = im & 32767;
	
	/*print opcode*/
	print_opcode(op);
	
	/*print extension*/
	if (op == 0 || op == 9)
		printf("  %u", ext);
	
	/*print register*/
	if ((op >= 1 && op <= 7) || op == 11 || op == 12)
		printf("  R%u  R%u  R%u", r1, r2, r3);
	
	if (op == 8 || op == 9 || op == 13 || op == 17)
		printf("  R%u  R%u", r1, r2);
	
	if (op == 0 || op == 14 || op == 15 || op == 16)
		printf("  R%u", r1);
	
	/*print addr*/
	if (op == 10 || op == 15 || op == 16)
		printf("  %05u", addr);
	
	/*print im*/
	if (op == 14)
		printf("  %u", im);
	
	return 1;
}

int disassemble(const Machine_word program[], unsigned int program_size,
                unsigned int data_segment_size) {
	int i;
	int inst = program_size - data_segment_size;
	
	/*check input validity*/
	if (program_size < 1 || program_size > 1024 || 
		data_segment_size >= program_size || program == NULL)
		return 0;
	
	/*print*/
	for (i = 0; i < program_size; i++) {
		if (i < inst) {
			if (!valid_instruction(program[i])) /*check for validity*/
				return 0;
			print_instruction(program[i]); /*print instruction*/
			printf("\n");
		}
		else {
			printf("%08x", program[i]); /*print data*/
			printf("\n");
		}
	}
	
	return 1;				
}

int valid_instruction(Machine_word word) {
	/*extract each component*/
	unsigned int op = word;
	unsigned int r1 = word;
	unsigned int r2 = word;
	unsigned int r3 = word;
	unsigned int ext = word;
	unsigned int addr = word;
	op >>= 27;
	r1 = (r1 >> 23) & 15;
	r2 = (r2 >> 19) & 15;
	r3 = (r3 >> 15) & 15;
	ext = (ext >> 12) & 7;
	addr = addr & 4095;
	/* li_addr = li_addr & 32767; */
	
	/*check for opcode*/
	if (op > 17)
		return 0;
	
	/*check for register*/
	if (r1 > 11 || r2 > 11 || r3 > 11)
		return 0;
	
	/*check for sys extension*/
	if (op == 0 && ext > 2)
		return 0;
	
	/*check for cmp extension*/
	if (op == 9 && ext > 5)
		return 0;
	
	/*check for memory*/
	if (op == 10 || op == 15 || op == 16)
		if (addr % 4 != 0)
			return 0;
		
	/*check for R0*/
	if (op != 10 && op != 16) {
		if (op == 0 && ext == 2 && r1 == 0)
			return 0;
		if (op != 0 && r1 ==0)
			return 0;
	}
	
	return 1;
}

/*print opname according to the opcode*/
static int print_opcode (unsigned int op) {
	if (op == 0)
		printf("sys");
	if (op == 1)
		printf("add");
	if (op == 2)
		printf("sub");
	if (op == 3)
		printf("mul");
	if (op == 4)
		printf("div");
	if (op == 5)
		printf("rem");
	if (op == 6)
		printf("shl");
	if (op == 7)
		printf("shr");
	if (op == 8)
		printf("neg");
	if (op == 9)
		printf("cmp");
	if (op == 10)
		printf("br");
	if (op == 11)
		printf("and");
	if (op == 12)
		printf("or");
	if (op == 13)
		printf("not");
	if (op == 14)
		printf("li");
	if (op == 15)
		printf("lw");
	if (op == 16)
		printf("sw");
	if (op == 17)
		printf("mov");
	
	return 1;
}
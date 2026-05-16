.data
stringA: .asciiz "Enter a: "
stringB: .asciiz "Enter b: "
stringC: .asciiz "Enter c: "
stringD: .asciiz "Enter d: "
stringX: .asciiz "Enter x: "
stringRes: .asciiz "Result: "

a: .word 0
b: .word 0
c: .word 0
d: .word 0
x: .word 0

.text
.globl main

read_int:
	li $v0, 4
	syscall
	
	li $v0, 5
	syscall
	
	jr $ra
	
horner_step:
	mul $v0, $a0, $a1
	add $v0, $v0, $a2	
	jr $ra

main:
	
	la $a0, stringA
	jal read_int
	sw $v0, a
	
	
	la $a0, stringB
	jal read_int
	sw $v0, b
	
	la $a0, stringC
	jal read_int
	sw $v0, c
	
	la $a0, stringD
	jal read_int
	sw $v0, d
	
	la $a0, stringX
	jal read_int
	sw $v0, x
	
	
	
	lw $a1, x
	lw $a0, a
	lw $a2, b
	


	jal horner_step
	
	move $a0, $v0
	lw $a2, c
	
	jal horner_step
	
	move $a0, $v0
	lw $a2, d
	
	jal horner_step
	
	move $t0, $v0
	
	la $a0, stringRes
    	li $v0, 4
    	syscall
    
    	move $a0, $t0
    	li $v0, 1
    	syscall
    	
    	li $v0, 10
	syscall
	
	
	
	
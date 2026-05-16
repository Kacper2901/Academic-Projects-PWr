.data
	array: .space 400 #every integer needs 4 bytes
	promptN: .asciiz "Enter N (N<=100): "
	promptA: .asciiz "Enter number: "
	promptRes: .asciiz "Sorted array: "
	coma: .asciiz ", "
	newLine: .asciiz "\n"

.text
.globl main

#debug 111

main:
	#print(promtN)
	li $v0, 4
	la $a0, promptN
	syscall
	
	#$s0 = N = read
	li $v0, 5
	syscall
	move $s0, $v0
	
	#i = 0
	li $t0, 0
	
	jal fillArray
	jal startSorting
	jal showArray
	
	#end
	li $v0, 10
	syscall
	
	

fillArray:
	#if(i == N) startSorting
	beq $t0, $s0, backToMain
	
	#print(promptA)
	li $v0, 4
	la $a0, promptA
	syscall
	
	#v0 = read()
	li $v0, 5
	syscall
	
	# t1 = t0*4 = i*4 (index for given integer because it needs 4 bytes)
	mul $t1, $t0, 4
	
	#store $v0 (given value) into array[t1]
	sw $v0, array($t1)
	
	addi $t0, $t0, 1
	
	j fillArray
	

startSorting:
	#----STACK---
	
	# make space on a stack
	addi $sp, $sp, -4
	# save curr return adress on the stack
	sw $ra, 0($sp)
	
	#---------

	#i = -1
	li $t0, -1
	
	
	
outerLoop:
	addi $t0, $t0, 1
	#if(i == N) sortFinished	
	beq $t0, $s0, restoreAndBack
	
	#j = 0
	li $t1, 0  
	


innerLoop:
	#t2 = N - i - 1
	sub $t2, $s0, $t0
	addi $t2, $t2, -1
	
	#if(j == t2) outerLoop
	beq $t1, $t2, outerLoop
	
	# $t3 = currIdx = j*4
	mul $t3, $t1, 4
	# $t4 = nextIdx = currIdx + 4
	addi $t4, $t3, 4
	
	#load $t5 = array(currIdx), $t6 = array(nextIdx)
	lw $t5, array($t3)
	lw $t6, array($t4)
	
	#if(array(currIdx) <= array(nextIdx)) no swap
	ble $t5, $t6, noSwap
	
	# save elements from array so swap can swap them
	la $a0, array($t3)
	la $a1, array($t4)
	
	# $ra now stores new return adress
	jal swap
	
	
noSwap:
	#j++
	addi $t1, $t1, 1
	j innerLoop
	

swap:
	lw $t8, 0($a0) #t8 = value in adress a0
	lw $t9, 0($a1) #t9 = value in adress a1
	sw $t9, 0($a0) #save t9 value in address a0
	sw $t8, 0($a1) #save t8 value in address a1
	jr $ra


restoreAndBack:
	# restore and go back to main
	lw $ra, 0($sp)
	addi $sp, $sp, 4
	jr $ra


showLoop:
	# t0 = i, t1 = N
	
	#if(i == N) sortFinished
	beq $t0, $t1, backToMain
	
	# $t2 = currIdx = i*4
	mul $t2, $t0, 4
	
	# $a0 = array(currIdx)
	lw $a0, array($t2)
	
	li $v0, 1
	syscall
	
	#i++
	addi $t0, $t0, 1
	
	beq $t0, $t1, noComa
	
	li $v0, 4
	la $a0, coma
	syscall
	
	
	j showLoop
	

showArray:
	# t0 = i = 0, t1 = N = s0
	li $t0, 0
	move $t1, $s0
	
	li $v0, 4
	la $a0, newLine
	syscall
	
	la $a0, promptRes
	syscall
	
	j showLoop
	
noComa:
	j showLoop

backToMain:
	jr $ra

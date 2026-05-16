.data
	array: .space 400 #every integer needs 4 bytes
	promptN: .asciiz "Enter N (N<=100): "
	promptA: .asciiz "Enter number: "
	promptRes: .asciiz "Sorted array: "
	coma: .asciiz ", "
	newLine: .asciiz "\n"

.text
.globl main

#breakpoint 92
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
	#i = -1
	li $t0, -1
	j outerLoop
	
	
outerLoop:
	addi $t0, $t0, 1
	#if(i == N) sortFinished	
	beq $t0, $s0, backToMain
	
	#j = 0
	li $t1, 0  
	
	j innerLoop


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
	
	#else arr(currIdx) = arr(nextIdx) and inverse
	sw $t6, array($t3)
	sw $t5, array($t4)
	
	
	
noSwap:
	#j++
	addi $t1, $t1, 1
	j innerLoop
	

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

	

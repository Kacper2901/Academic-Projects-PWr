.data
    prompt:  .asciiz "Enter N: "
    err_msg: .asciiz "Error: Overflow occurred!"
    res_msg: .asciiz "Result N!: "

.text
main:
    # input
    li $v0, 4         
    la $a0, prompt
    syscall

    li $v0, 5          
    syscall
    move $s0, $v0      

    # init
    li $s1, 1   # result = 1
    li $t0, 1   # i = 1
    
    
    #if(N == 0) exit
    blez $s0, exit

while_loop:

    bgt $t0, $s0, print_result  # if (i > N) results


    mult $s1, $t0       # result * i
    mfhi $t1            # move overflow (from HI) to t1
    bne $t1, $zero, overflow_err # if(t1 != 0) overflow

    mflo $s1            # move result (from LO) to s1
    
    addi $t0, $t0, 1    # i++
    j while_loop        # jump to while_loop

print_result:

    li $v0, 4
    la $a0, res_msg
    syscall

    li $v0, 1          
    move $a0, $s1
    syscall
    j exit

overflow_err:
    li $v0, 4
    la $a0, err_msg
    syscall

exit:
    li $v0, 10          
    syscall
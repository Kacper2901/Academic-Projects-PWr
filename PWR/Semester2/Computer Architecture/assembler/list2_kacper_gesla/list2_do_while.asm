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
    li $s1, 1 # result = 1
    li $t0, 1 #i = 1

    #if(N == 0) exit
    blez $s0, exit

do_while_loop:
    mult $s1, $t0  #stores result in LO and HI
    mfhi $t1      
    bne $t1, $zero, overflow_err #if(HI != 0) overflow

    mflo $s1 #else move result of multiiplication to s1
    
    addi $t0, $t0, 1    #i++


    ble $t0, $s0, do_while_loop # If (i <= N) jumpt to do_while

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
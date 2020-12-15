/*
 * Написать программу по схеме первого задания, но в процессе-предке
 * выполнить системный вызов wait(). Убедиться, что в этом случае идентификатор
 * процесса потомка на 1 больше идентификатора процесса-предка.
 */

#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>

void report_proc();

int main() {
    pid_t child_pid1, child_pid2;

    if ((child_pid1 = fork()) == -1) {
        puts("Can't create new proc");
        exit(1);
    } else if (child_pid1 == 0) {
        //sleep(1);
        printf(":::CHILD1::::");
        printf("pid %i group id %i parent id %i\n",
        getpid(), getpgrp(), getppid());
        // exit(12);
    } else {
        if ((child_pid2 = fork()) == -1) {
            puts("Can't create new proc");
            exit(1);
        } else if (child_pid2 == 0) {
            //sleep(1);
            printf(":::CHILD2:::: pid %i group id %i parent id %i\n", getpid(), getpgrp(), getppid());
            // exit(12);
        } else {
            printf("PARENT");
            printf("pid %i group id %i\n", \
            getpid(), getpgrp());
            printf("child 1 id %i child 2 id %i\n",
            child_pid1, child_pid2);
        
            report_proc();
            report_proc();
        }
    }

    return 0;
}

void report_proc() {
    int status = 0;
    pid_t wait_rc = wait(&status);

    if (WIFEXITED(status)) {
        printf(":::PARENT:::");
        prinf("child %i finished with %i code.\n",
            wait_rc, WEXITSTATUS(status));
    } else if (WIFSIGNALED(status)) {
        printf(":::PARENT:::");
        printf("child %i finished from signal with %i code.\n",
            wait_rc, WTERMSIG(status));
    } else if (WIFSTOPPED(status)) {
        printf(":::PARENT::: child %i finished from signal with %i code.\n", wait_rc, WSTOPSIG(status));
    }
}